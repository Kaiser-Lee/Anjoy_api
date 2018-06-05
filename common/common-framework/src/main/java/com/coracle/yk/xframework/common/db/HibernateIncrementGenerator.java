package com.coracle.yk.xframework.common.db;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.boot.model.naming.ObjectNameNormalizer;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentifierGeneratorHelper;
import org.hibernate.id.IntegralDataTypeHolder;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.mapping.Table;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

/**
 * <b>increment</b><br>
 * <br>
 * An <tt>IdentifierGenerator</tt> that returns a <tt>long</tt>, constructed by
 * counting from the maximum primary key value at startup. Not safe for use in a
 * cluster!<br>
 * <br>
 * Mapping parameters supported, but not usually needed: tables, column.
 * (The tables parameter specified a comma-separated list of table names.)
 *
 * @author Gavin King
 * @author Steve Ebersole
 * @author Brett Meyer
 */
@SuppressWarnings({"deprecation","rawtypes"})
public class HibernateIncrementGenerator implements IdentifierGenerator, Configurable {
	private static final Logger LOG = Logger.getLogger(HibernateIncrementGenerator.class );
	private final static Map<String, String> CAT_MAP = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3943256055278109533L;

		{
			put("ykee_manage", DatabaseContextHolder.DS_TYPE_MANAGE);
			put("ykee_comm", DatabaseContextHolder.DS_TYPE_COMMON);
			put("ykee_program", DatabaseContextHolder.DS_TYPE_PROGRAM);
			put("ykee_product", DatabaseContextHolder.DS_TYPE_PRODUCTION);
			put("ykee_security", DatabaseContextHolder.DS_TYPE_SECURITY);
			put("ykee_customer", DatabaseContextHolder.DS_TYPE_CUSTOMER);
			put("ykee_org", DatabaseContextHolder.DS_TYPE_ORG);
		}
	};

	private Class returnClass;
	private String sql;
	private String column;
	private String schema;
	private String catalog;
	private String tableList;

	private IntegralDataTypeHolder previousValueHolder;

	@Override
	public synchronized Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		if ( sql != null ) {
			initializePreviousValueHolder( session );
		}
		
		return previousValueHolder.makeValueThenIncrement();
	}

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		returnClass = type.getReturnedClass();

		final JdbcEnvironment jdbcEnvironment = serviceRegistry.getService( JdbcEnvironment.class );
		final ObjectNameNormalizer normalizer =
				(ObjectNameNormalizer) params.get( PersistentIdentifierGenerator.IDENTIFIER_NORMALIZER );

		column = params.getProperty( "column" );
		if ( column == null ) {
			column = params.getProperty( PersistentIdentifierGenerator.PK );
		}
		column = normalizer.normalizeIdentifierQuoting( column ).render( jdbcEnvironment.getDialect() );

		tableList = params.getProperty( "tables" );
		if ( tableList == null ) {
			tableList = params.getProperty( PersistentIdentifierGenerator.TABLES );
		}
		String[] tables = StringHelper.split( ", ", tableList );

		schema = normalizer.toDatabaseIdentifierText(
				params.getProperty( PersistentIdentifierGenerator.SCHEMA )
		);
		catalog = normalizer.toDatabaseIdentifierText(
				params.getProperty( PersistentIdentifierGenerator.CATALOG )
		);
		StringBuilder buf = new StringBuilder();
		for ( int i = 0; i < tables.length; i++ ) {
			final String tableName = normalizer.toDatabaseIdentifierText( tables[i] );
			if ( tables.length > 1 ) {
				buf.append( "select max(" ).append( column ).append( ") as mx from " );
			}
			buf.append( Table.qualify( catalog, schema, tableName ) );
			if ( i < tables.length - 1 ) {
				buf.append( " union " );
			}
		}
		if ( tables.length > 1 ) {
			buf.insert( 0, "( " ).append( " ) ids_" );
			column = "ids_.mx";
		}

		sql = "select max(" + column + ") from " + buf.toString();
	}
	
	private void exchangeDatabase() {
		LOG.info("Catalog: " + catalog);
		String dsType = CAT_MAP.get(catalog);
		if(dsType != null) {
			DatabaseContextHolder.setCustomerType(dsType);
		} else {
			DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DS_TYPE_MANAGE);
		}
		LOG.info("Current Database: " + DatabaseContextHolder.getCustomerType());
	}

	private void initializePreviousValueHolder(SessionImplementor session) {
		previousValueHolder = IdentifierGeneratorHelper.getIntegralDataTypeHolder( returnClass );

		final boolean debugEnabled = LOG.isDebugEnabled();
		if ( debugEnabled ) {
			LOG.debug("Fetching initial value: " + sql );
		}
		exchangeDatabase();
		try {
			PreparedStatement st = session.getJdbcCoordinator().getStatementPreparer().prepareStatement( sql );
			try {
				ResultSet rs = session.getJdbcCoordinator().getResultSetReturn().extract( st );
				try {
					if ( rs.next() ) {
						previousValueHolder.initialize( rs, 0L ).increment();
					}
					else {
						previousValueHolder.initialize( 1L );
					}
					sql = null;
					if ( debugEnabled ) {
						LOG.debug("First free id: " + previousValueHolder.makeValue() );
					}
				}
				finally {
					session.getJdbcCoordinator().getResourceRegistry().release( rs, st );
				}
			}
			finally {
				session.getJdbcCoordinator().getResourceRegistry().release( st );
				session.getJdbcCoordinator().afterStatementExecution();
			}
		}
		catch (SQLException sqle) {
			throw session.getFactory().getSQLExceptionHelper().convert(
					sqle,
					"could not fetch initial value for increment generator",
					sql
			);
		}
	}
}
