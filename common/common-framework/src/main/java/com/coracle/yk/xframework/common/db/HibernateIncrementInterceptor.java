package com.coracle.yk.xframework.common.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.hibernate.boot.model.naming.ObjectNameNormalizer;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

@SuppressWarnings({ "rawtypes", "unused" })
public class HibernateIncrementInterceptor {
	private static final Logger LOG = Logger.getLogger(HibernateIncrementInterceptor.class );
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
			put("youkee_security", DatabaseContextHolder.DS_TYPE_SECURITY);
			put("ykee_customer", DatabaseContextHolder.DS_TYPE_CUSTOMER);
			put("ykee_org", DatabaseContextHolder.DS_TYPE_ORG);
		}
	};
	
	private Class returnClass;
	private String column;
	private String schema;
	private String catalog;
	private String tableList;
	public void parseDatabaseInfo(JoinPoint jp) {
    	// result是方法的最终返回结果
//        Object result = null;
        // 得到类名、方法名和参数
//        String clazzName = jp.getTarget().getClass().getName();
//        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        
        Type type = (Type)args[0];
        returnClass = type.getReturnedClass();
        Properties params = (Properties)args[1];
        ServiceRegistry serviceRegistry = (ServiceRegistry)args[2];
        
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
		

		schema = normalizer.toDatabaseIdentifierText(
				params.getProperty( PersistentIdentifierGenerator.SCHEMA )
		);
		catalog = normalizer.toDatabaseIdentifierText(
				params.getProperty( PersistentIdentifierGenerator.CATALOG )
		);
	}
	
	public void exchangeDatabase(JoinPoint jp) {
		LOG.info("Catalog: " + catalog);
		String dsType = CAT_MAP.get(catalog);
		if(dsType != null) {
			DatabaseContextHolder.setCustomerType(dsType);
		} else {
			DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DS_TYPE_MANAGE);
		}
		LOG.info("Current Database: " + DatabaseContextHolder.getCustomerType());
	}
}
