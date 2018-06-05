package com.coracle.yk.xframework.dao.hibernate.intf;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

@SuppressWarnings("rawtypes")
public interface IBaseJdbcDao<T> {
	/**
	 * 带参数执行sql语句
	 * @param sql
	 * @param paraList
	 * @return
	 * @throws 
	 */
	public int exceute(String sql, Object[] objects) ;
	/**
	 * 带参数返回总数
	 * @param sql
	 * @param objects
	 * @return
	 */
	public long count(String sql,Object[] objects);
	/**
	 * 带参数返回list Map对象
	 * @param sql
	 * @param objects
	 * @return
	 */
	public List query(String sql,Object[] objects);
	/**
	 * 带参数返回T对象
	 * @param sql
	 * @param objects
	 * @return
	 */
	public Map queryForObject(String sql, Object[] objects);
	/**
	 * 返回实体对象
	 * @param sql
	 * @param parraList
	 * @return
	 */
	public T fetch(String sql ,Object[] objects,Class<T> entityClass);
	/**
	 * 返回list实体对象
	 * @param sql
	 * @param objects
	 * @param entityClass
	 * @return
	 */
	public List<T> query(String sql ,Object[] objects,Class<T> entityClass);
	/**
	 * 返回对象，传入rowMapper
	 * @param sql
	 * @param objects
	 * @param rowMapper
	 * @return
	 */
	public T fetch(String sql, Object[] objects,RowMapper rowMapper);
	/**
	 * 返回list，传入rowMapper
	 * @param sql
	 * @param objects
	 * @param rowMapper
	 * @return
	 */
	public List<T> query(String sql, Object[] objects,RowMapper rowMapper);
	/**
	 * 新增语句返回，主键
	 * @param sql
	 * @param objects
	 * @return
	 */
	public long insert(String sql,Object[] objects);
	
	/**
	 * 批量更新
	 * @author Xiruo.Jiang
	 * @date 2012-11-9
	 * @param sqls
	 */
	public void batchUpdate(String[] sqls);
	public void batchUpdate(String sql, BatchPreparedStatementSetter pss);
}
