package com.coracle.yk.xframework.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coracle.yk.xframework.dao.hibernate.intf.IBaseJdbcDao;

@Repository("baseJdbcDao")
@SuppressWarnings({"rawtypes","unchecked"})
public class BaseJdbcDaoImpl<T> implements IBaseJdbcDao<T> {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int exceute(String sql, Object[] objects) {
		if (objects != null)
			return jdbcTemplate.update(sql, objects);
		else
			return jdbcTemplate.update(sql);
	}

	public long count(String sql, Object[] objects) {
		String countStr = sql.substring(sql.indexOf("from"));
		countStr = "select count(*) " + countStr;
		if (objects == null) {
			return jdbcTemplate.queryForObject(countStr, Long.class);
		} else {
			return jdbcTemplate.queryForObject(countStr, objects, Long.class);
		}
	}

	public List query(String sql, Object[] objects) {
		if (objects == null) {
			return jdbcTemplate.queryForList(sql);
		} else {
			return jdbcTemplate.queryForList(sql, objects);
		}
	}

	public Map queryForObject(String sql, Object[] objects) {
		if (objects == null) {
			return jdbcTemplate.queryForMap(sql);
		} else {
			return jdbcTemplate.queryForMap(sql, objects);
		}
	}

	public T fetch(String sql, Object[] objects, Class<T> entityClass) {
		if (objects == null) {
			return (T) jdbcTemplate
					.queryForObject(sql, entityClass);
		} else {
			return (T) jdbcTemplate
					.queryForObject(sql, objects, entityClass);
		}
	}

	public List<T> query(String sql, Object[] objects, Class<T> entityClass) {
		if (objects == null) {
			return jdbcTemplate
					.queryForList(sql, entityClass);
		} else {
			return jdbcTemplate
					.queryForList(sql, objects, entityClass);
		}
	}

	public T fetch(String sql, Object[] objects, RowMapper rowMapper) {
		if (objects == null) {
			return (T) jdbcTemplate.queryForObject(sql, rowMapper);
		} else {
			return (T) jdbcTemplate.queryForObject(sql, objects,
					rowMapper);
		}
	}

	public List<T> query(String sql, Object[] objects, RowMapper rowMapper) {
		if (objects == null) {
			return jdbcTemplate.query(sql, rowMapper);
		} else {
			return jdbcTemplate.query(sql, objects, rowMapper);
		}
	}

	public long insert(final String sql, final Object[] objects) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = null;
				ps = connection.prepareStatement(sql, new String[] { "ID" });
				if (objects != null)
					for (int i = 0; i < objects.length; i++) {
						ps.setObject(i + 1,  objects[i]);
					}
				return ps;
			}
		}, keyHolder);
		long generatedId = keyHolder.getKey().longValue();
		return generatedId;
	}
	
	public void batchUpdate(String[] sqls) {
		jdbcTemplate.batchUpdate(sqls);
	}
	
	public void batchUpdate(String sql, BatchPreparedStatementSetter pss) {
		jdbcTemplate.batchUpdate(sql, pss);
	}
}
