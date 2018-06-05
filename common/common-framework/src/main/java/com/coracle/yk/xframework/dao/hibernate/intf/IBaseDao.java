package com.coracle.yk.xframework.dao.hibernate.intf;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.stat.Statistics;

/**
 * <p>Title: 通用业务接口</p>
 * <p>Description: 基础技术平台</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: </p>
 * @author 
 * @version 
 */
@SuppressWarnings("rawtypes")
public interface IBaseDao<T> {
	public void initialize(Object obj);
	/**
	 * 使对象处理受管状态
	 */
	public void manage(T vo);
	/**
	 * 新增对象
	 * @param vo 保存数据对象
 	 */
	public void add(T vo);
	/**
	 * 新增对象，并返回对象
	 * @param vo
	 * @return
	 */
	public T addCallBack(T vo);
	/**
	 * 新增或修改对象
	 * @param vo
	 */
	public void saveOrUpdate(T vo);
	
	public void merge(T vo);
	public void refresh(T vo);
	/**
	 * 修改对象，不存在修改失败
	 * @param vo
	 */
	public void update(T vo);
	
	/**
	 * 删除对象
	 * @param vo
	 */
	public void delete(T vo);
	/**
	 * 批量删除
	 * @param entity
	 * @param wkey
	 * @param list
	 * @return
	 */
	
	public int delete(final String entity, final String wkey, final Set<Object> list) ;
	/**
	 * 获取总数
	 * @param entity
	 * @param whereMap
	 * @return
	 */
	public long count(String entity ,Map whereMap);
    /**
     * 按key值删除对象                                    
     * @param entity
     * @param keyName
     * @param value
     * @return
     */
    public int del(String entity ,String keyName,Object value);
    /**
     * 根据Map条件删除
     * @param entity
     * @param valueMap
     * @return
     */
    public int delWhere(String entity, Map valueMap);
    /**
     * 多条件查找
     * @param entity
     * @param map
     * @return
     */
    public T fetch(String entity,Map map);
    /**
     * 但条件查找
     * @param entity
     * @param key
     * @param obj
     * @return
     */
    public T fetch(String entity ,String key, Object obj);
    /**
     * hql多条件执行,-1失败，1成功
     * @param hql
     * @param paraList
     * @return
     */
    public int hqlExcute(String hql,List paraList);
    /**
     * 多条件 hql查询
     * @param hql
     * @param paraList
     * @return
     */
    public List<T> hqlQuery(String hql,Object paras);
    /**
     * hql总数查询
     * @param hql
     * @param paraList
     * @return
     */
    public long hqlCount(String hql,List paraList);
    public long sqlCount(String sql,List paraList);
    /**
     * 多条件分页查询
     * @param hql
     * @param start
     * @param limit
     * @param paraList
     * @return
     */
    public List<T> hqlQuery(String hql,int start,int limit,List paraList);
    public List<T> sqlQuery(String sql,int start,int limit,List paraList);
    /**
     * 多条件查询
     * @param entity
     * @param map
     * @return
     */
    public  List<T>  queryAll(String entity,  Map map);
    /**
     * 多条件分页查找
     * @param entity
     * @param map
     * @param start
     * @param limit
     * @return
     */
    public  List<T> queryPage(String entity,Map map,int start,int limit);

    /**
     * 根据实体名称更新  -1失败 1成功
     * @param entity
     * @param vMap
     * @param wMap
     * @return
     */
    public  int update(String entity,Map vMap,Map wMap);
    /**
     * 根据实体名称更新单字段值 -1失败，1成功
     * @param entity
     * @param vkey
     * @param vObject
     * @param wkey
     * @param wOper
     * @param wObjec
     * @return
     */
    public int update(String entity,String vkey,Object vObject,String wkey, String wOper, Object wObject);
    /**
     * 根据实体名称更新单字段值 -1失败，1成功
     * @param entity
     * @param vkey
     * @param vObject
     * @param wkey
     * @param list
     * @return
     */
    
	public int update(final String entity, final String vkey,
			final Object vObject, final String wkey, final Set<Object> list);
    /**
     * 获取Hibernate Statistics信息
     * @return
     */
	public Statistics getSessionStatistics();
	/**
	 * 清除所有缓存
	 */
	public void clear();
	/**
	 * 清除单个实体
	 * @param vo
	 */
	public void evict(T vo);
	/**
	 * 刷新
	 */
	public void flush();
	/**
	 * 分页查询
	 * @param hql 查询语句
	 * @param start 开始位置
	 * @param limit 每页条数
	 * @param paraNames 查询点位符名称集合
	 * @param paraValues 查询条件值集合
	 * @return list
	 */
	public List<T> hqlQuery(final String hql, final int start, final int limit,
			final Object paraNames,final Object paraValues);
	/**
	 * 查询
	 * @param hql
	 * @param paraNames 查询占位符名称集合
	 * @param paraValues 查询条件值集合
	 * @return
	 */
	public List<T> hqlQuery(final String hql, final Object paraNames,final Object paraValues);
	/**
	 * 查询总数
	 * @param hql 查询语句
	 * @param paraNames 查询占位符名称集合
	 * @param paraValues 查询条件集合
	 * @return count 总数
	 */
	public long hqlCount(final String hql, final List<String> paraNames,
			final List paraValues);
	
	/**
	 * 清除二级缓存
	 * @author Xiruo.Jiang
	 * @date 2012-2-1
	 * @param clazz
	 */
	public void evict(Class clazz[]);
	
	public void callProcedureWithoutResult(String procName, List<Object> paramList);
}
