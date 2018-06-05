package com.coracle.yk.xframework.dao.hibernate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.coracle.yk.xframework.dao.hibernate.intf.IBaseDao;

/**
 * <p>
 * Title: 通用业务实现
 * </p>
 * <p>
 * Description: 基础技术平台
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: 
 * </p>
 * 
 * @author 
 * @version 
 */

@Repository("baseDao")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseDaoImpl<T> extends BaseDaoSupport implements IBaseDao<T> {
	public void initialize(Object obj) {
		getHibernateTemplate().initialize(obj);
	}
	public void add(T vo) {
		getHibernateTemplate().save(vo);
	}

	public void delete(T vo) {
		getHibernateTemplate().delete(vo);
	}

	public T addCallBack(T vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}

	public void saveOrUpdate(T vo) {
		getHibernateTemplate().saveOrUpdate(vo);
	}

	public long count(String entity, Map whereMap) {
		String sql = "select count(*) from " + entity + " f where 1=1 ";
		String where = "";
		final List paraList = new ArrayList();
		if (whereMap != null) {
			Set set = whereMap.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (!key.equals(BaseConstants.DAO_ORDERBY)) {
					if (key.equals(BaseConstants.DAO_SQL)) {
						// 自定义hql查询
						where = where
								+ (String) whereMap.get(BaseConstants.DAO_SQL);
					} else if (key.indexOf("_") != -1) {
						where = where + " and f."
								+ key.substring(0, key.indexOf("_")) + " "
								+ key.substring(key.indexOf("_") + 1) + " ? ";
						paraList.add(whereMap.get(key));
					} else {
						where = where + " and f." + key + " = ? ";
						paraList.add(whereMap.get(key));
					}
				}
			}
		}
		sql = sql + where;
		long obj = (Long) getHibernateTemplate().find(sql, paraList.toArray())
				.iterator().next();
		return obj;
	}

	public int del(String entity, String keyName, Object value) {
		int result = 1;
		String sql = "delete " + entity + " f where f." + keyName + " = ? ";
		result = getHibernateTemplate().bulkUpdate(sql, value);
		return result;
	}

	public int delWhere(String entity, Map valueMap) {
		int result = 1;
		String sql = "delete " + entity + " f where ";
		String where = "";
		List paraList = new ArrayList();
		if (valueMap != null) {
			Set set = valueMap.keySet();
			Iterator it = set.iterator();
			int count = 0;
			while (it.hasNext()) {
				String key = (String) it.next();
				if (key.equals("soc_sql")) {
					// 自定义hql查询
					where = (String) valueMap.get("soc_sql");
				} else if (key.indexOf("_") != -1) {
					if (count == 0) {
						where = where + " f."
								+ key.substring(0, key.indexOf("_")) + " "
								+ key.substring(key.indexOf("_") + 1) + " ? ";
					} else {
						where = where + " and f."
								+ key.substring(0, key.indexOf("_")) + " "
								+ key.substring(key.indexOf("_") + 1) + " ? ";
					}
					paraList.add(valueMap.get(key));
				} else {
					if (count == 0) {
						where = where + " f." + key + " = ? ";
					} else {
						where = where + " and f." + key + " = ? ";
					}
					paraList.add(valueMap.get(key));
				}
				count++;
			}
		}
		sql = sql + where;
		result = getHibernateTemplate().bulkUpdate(sql, paraList.toArray());
		return result;
	}

	public T fetch(String entity, Map map) {
		String sql = "from " + entity + " f where 1=1 ";
		String where = "";
		List paraList = new ArrayList();
		if (map != null) {
			Set set = map.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (key.equals(BaseConstants.DAO_SQL)) {
					where = where + (String) map.get(BaseConstants.DAO_SQL);
				} else if (key.indexOf("_") != -1) {
					where = where + " and f."
							+ key.substring(0, key.indexOf("_")) + " "
							+ key.substring(key.indexOf("_") + 1) + " ? ";
					paraList.add(map.get(key));
				} else {
					where = where + " and f." + key + " = ? ";
					paraList.add(map.get(key));
				}
			}
		}
		sql = sql + where;
		getHibernateTemplate().setCacheQueries(true);
		Iterator iterator = getHibernateTemplate()
				.find(sql, paraList.toArray()).iterator();
		if (iterator.hasNext())
			return (T) iterator.next();
		else
			return null;
	}

	public T fetch(String entity, String key, Object obj) {
		String sql = "from " + entity + " f where f." + key + " = ? ";
		getHibernateTemplate().setCacheQueries(true);
		Iterator iterator = getHibernateTemplate().find(sql, obj).iterator();
		if (iterator.hasNext())
			return (T) iterator.next();
		else
			return null;
	}

	public int hqlExcute(String hql, List paraList) {
		int result = -1;
		if (paraList != null)
			result = getHibernateTemplate().bulkUpdate(hql, paraList.toArray());
		else
			result = getHibernateTemplate().bulkUpdate(hql);
		return result;
	}
	
	public List<T> hqlQuery(String hql, Object paras, boolean initialize) {
		getHibernateTemplate().setCacheQueries(true);
		List list = null;
		if (paras != null)
			list = getHibernateTemplate().find(hql, obj2Array(paras,null));
		else
			list = getHibernateTemplate().find(hql);
		if(!list.isEmpty() && initialize) {
			Class classType = list.get(0).getClass();
			Field fields[] = classType.getDeclaredFields();      
			Field field = null;
			String fieldName = "";
			String firstLetter = "";
			String getMethodName = "";
			List<Method> getMethodList = new ArrayList<Method>();
			for (int i = 0; i < fields.length; i++) {      
				field = fields[i];
				if(field.isSynthetic()) {
					fieldName = field.getName();      
					firstLetter = fieldName.substring(0, 1).toUpperCase();      
					// 获得和属性对应的getXXX()方法的名字      
					getMethodName = "get" + firstLetter + fieldName.substring(1);
					try {
						getMethodList.add(classType.getMethod(getMethodName, new Class[] {}));
					} catch(Exception e) {}
				}
			}
			for(Object o : list) {
				getHibernateTemplate().initialize(o);
				for(Method method : getMethodList) {
					try {
						getHibernateTemplate().initialize(method.invoke(o, new Object[] {}));
					} catch(Exception e) {}
				}
			}
		}
		return list;
	}

	public List<T> hqlQuery(String hql, Object paras) {
		return hqlQuery(hql, paras, false);
	}
	
	public List hqlQuery(final String hql, final Object paraNames,final Object paraValues) {
		getHibernateTemplate().setCacheQueries(true);
		String[] para = (String[])obj2Array(paraNames,new String[0]);
		Object[] v = obj2Array(paraValues,null);
		return getHibernateTemplate().findByNamedParam(hql, para, v);
	}

	public long hqlCount(String hql, List paraList) {
		String countStr = hql.substring(hql.indexOf("from"));
		countStr = "select count(*) " + countStr;
		if (paraList != null)
			return (Long) this.getHibernateTemplate()
					.find(countStr, paraList.toArray()).iterator().next();
		else
			return (Long) this.getHibernateTemplate().find(countStr).iterator()
					.next();

	}
	public long sqlCount(final String sql, final List paraList) {
		String countStr = sql.substring(sql.indexOf("from"));
		countStr = "select count(*) " + countStr;
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {
					public List<T> doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createSQLQuery(sql);
						query.setCacheable(true);
						if (paraList != null) {
							for (int i = 0; i < paraList.size(); i++) {
								query.setParameter(i, paraList.get(i));
							}
						}
						return query.list();
					}
				});
		
		return (Long)list.get(0);

	}
	public long hqlCount(final String hql, final List<String> paraNames,
			final List paraValues) {
		String countStr = hql.substring(hql.indexOf("from"));
		countStr = "select count(*) " + countStr;
		String[] para = new String[paraNames.size()];
		paraNames.toArray(para);
		return (Long) getHibernateTemplate()
				.findByNamedParam(countStr, para, paraValues.toArray()).iterator()
				.next();
	}

	public List<T> hqlQuery(final String hql, final int start, final int limit,final List paraList) {
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {
					public List<T> doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery(hql)
								.setFirstResult(start).setMaxResults(limit);
						query.setCacheable(true);
						if (paraList != null) {
							for (int i = 0; i < paraList.size(); i++) {
								query.setParameter(i, paraList.get(i));
							}
						}
						return query.list();
					}
				});
		return list;
	}
	
	public List<T> sqlQuery(final String sql, final int start, final int limit,final List paraList) {
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {
					public List<T> doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createSQLQuery(sql)
								.setFirstResult(start).setMaxResults(limit);
						query.setCacheable(true);
						if (paraList != null) {
							for (int i = 0; i < paraList.size(); i++) {
								query.setParameter(i, paraList.get(i));
							}
						}
						return query.list();
					}
				});
		return list;
	}
	
	public List<T> hqlQuery(final String hql, final int start, final int limit,
			final Object paraNames,final Object paraValues) {
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {
					public List<T> doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery(hql)
								.setFirstResult(start).setMaxResults(limit);
						query.setCacheable(true);
	                    if(paraValues != null){
	                    	String[] names = (String[])obj2Array(paraNames,new String[0]);
	                    	Object[] values = obj2Array(paraValues,null);
	                        for(int i = 0,len = values.length; i < len ; i++)
	                        	applyNamedParameterToQuery(query, names[i], values[i]);

	                    }
		                    return query.list();
					}
				});
		return list;
	}

	public List hqlQuery(String hql) {
		getHibernateTemplate().setCacheQueries(true);
		return getHibernateTemplate().find(hql);
	}

	public List queryAll(String entity, Map map) {
		String orderBy = " order by f.id ";
		String sql = " from " + entity + " f where 1=1 ";
		String where = "";
		List paraList = new ArrayList();
		if (map != null) {
			Set set = map.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (key.equals(BaseConstants.DAO_ORDERBY)) {
					// 排序
					orderBy = (String) map.get(BaseConstants.DAO_ORDERBY);
				} else if (key.equals(BaseConstants.DAO_SQL)) {
					// 自定义hql查询
					where = where + (String) map.get(BaseConstants.DAO_SQL);
				} else if (key.indexOf("_") != -1) {
					where = where + " and f."
							+ key.substring(0, key.indexOf("_")) + " "
							+ key.substring(key.indexOf("_") + 1) + " ? ";
					paraList.add(map.get(key));
				} else {
					where = where + " and f." + key + " = ? ";
					paraList.add(map.get(key));
				}
			}
		}
		sql = sql + where + " " + orderBy;
		getHibernateTemplate().setCacheQueries(true);
		return getHibernateTemplate().find(sql, paraList.toArray());
	}

	public List<T> queryPage(String entity, Map map, final int start,
			final int limit) {
		String orderBy = " order by f.id ";
		String sql = "from " + entity + " f where 1=1 ";
		String where = "";
		final List paraList = new ArrayList();
		if (map != null) {
			Set set = map.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (key.equals(BaseConstants.DAO_ORDERBY)) {
					// 排序
					orderBy = (String) map.get(BaseConstants.DAO_ORDERBY);
				} else if (key.equals(BaseConstants.DAO_SQL)) {
					// 自定义hql查询
					where = where + (String) map.get(BaseConstants.DAO_SQL);
				} else if (key.indexOf("_") != -1) {
					where = where + " and f."
							+ key.substring(0, key.indexOf("_")) + " "
							+ key.substring(key.indexOf("_") + 1) + " ? ";
					paraList.add(map.get(key));
				} else {
					where = where + " and f." + key + " = ? ";
					paraList.add(map.get(key));
				}
			}
		}
		sql = sql + where + " " + orderBy;
		final String fsql = new String(sql);
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {
					public List<T> doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery(fsql)
								.setFirstResult(start).setMaxResults(limit);
						query.setCacheable(true);
						if (paraList != null) {
							for (int i = 0; i < paraList.size(); i++) {
								query.setParameter(i, paraList.get(i));
							}
						}
						return query.list();
					}
				});
		return list;
	}

	public int update(String entity, Map vMap, Map wMap) {
		int result = -1;
		String sql = "update " + entity + " set ";
		String setStr = "";
		String whereStr = "";
		List valueList = new ArrayList();
		if (vMap != null) {
			Set set = vMap.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				setStr = setStr + key + "=?,";
				valueList.add(vMap.get(key));
			}
		}
		if (setStr.trim().length() > 0) {
			setStr = setStr.substring(0, setStr.length() - 1);
		}
		sql = sql + setStr;
		if (wMap != null && wMap.size() > 0) {
			sql = sql + " where ";
			Set set = wMap.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				whereStr = whereStr + key + "=? and ";
				valueList.add(wMap.get(key));
			}
		}
		if (whereStr.trim().length() > 0) {
			whereStr = whereStr.substring(0, whereStr.length() - 4);
		}
		sql = sql + whereStr;
		result = getHibernateTemplate().bulkUpdate(sql, valueList.toArray());
		return result;
	}

	public int update(final String entity, final String vkey,
			final Object vObject, final String wkey, final String wOper,
			final Object wObject) {
		int result = 1;
		result = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query = null;
						query = session.createQuery("update " + entity
								+ " set " + vkey + " = :v where " + wkey + " "
								+ wOper + " :w");
						query.setParameter("v", vObject);
						query.setParameter("w", wObject);
						return query.executeUpdate();
					}
				});

		return result;
	}

	public int update(final String entity, final String vkey,
			final Object vObject, final String wkey, final Set<Object> list) {
		int result = 1;
		result = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query = null;
						query = session.createQuery("update " + entity
								+ " set " + vkey + " = :v where " + wkey
								+ " in (:w)");

						query.setParameter("v", vObject);
						query.setParameterList("w", list);
						return query.executeUpdate();
					}
				});

		return result;
	}
	public int delete(final String entity, final String wkey, final Set<Object> list) {
		int result = 1;
		result = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query = null;
						query = session.createQuery("delete " + entity
								+ "  where " + wkey+ " in (:w)");
						query.setParameterList("w", list);
						return query.executeUpdate();
					}
				});

		return result;
	}
	public Statistics getSessionStatistics() {
			Statistics stats = null;
			try {
				SessionFactory sf =  getHibernateTemplate().getSessionFactory();
				stats = sf.getStatistics();
				stats.setStatisticsEnabled(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return stats;
	}

	public void update(T vo) {
		getHibernateTemplate().update(vo);	
	}

	public void clear() {
		getHibernateTemplate().clear();
		
	}

	public void evict(T vo) {
		getHibernateTemplate().evict(vo);
	}

	public void flush() {
		getHibernateTemplate().flush();
	}
	public void manage(T vo) {
		getHibernateTemplate().update(vo);
	}

	protected void applyNamedParameterToQuery(Query queryObject, String paramName, Object value)
    throws HibernateException
	{
	    if(value instanceof Collection)
	        queryObject.setParameterList(paramName, (Collection)value);
	    else
	    if(value instanceof Object[])
	        queryObject.setParameterList(paramName, (Object[])(Object[])value);
	    else
	        queryObject.setParameter(paramName, value);
	}
	
	//将数组或集合转换为数组
	private static Object[] obj2Array(Object obj,Object[] arr){
		if(obj instanceof Object[]){
			arr = (Object[])obj;
		}else if(obj instanceof Collection){
			Collection c = (Collection)obj;
			if(arr == null){
				arr = new Object[c.size()];
			}
			arr = c.toArray(arr);
		}
		return arr;
	}
	
	public static void main(String[] args){
//		String hql = "from TblSystemUserInfo u where u.id in (:uid) and u.realName=:name";
//		String countStr = hql.substring(hql.indexOf("from"));
//		countStr = "select count(*) " + countStr;
//		
//		System.out.print(countStr);
		List a = new ArrayList();
		a.add("xxx");
		
		String[] o = (String[])obj2Array(a,new String[0]);
		
		System.out.print(o.length);
	}

	public void merge(T vo) {
		getHibernateTemplate().merge(vo);
	}

	public void refresh(T vo) {
		getHibernateTemplate().refresh(vo);
	}

	public void evict(Class clazz[]) {
		Session session = getSessionFactory().getCurrentSession();
		for(Class cls : clazz) {
			session.evict(cls);
			System.out.println("Evict " + cls.getName() + " SecondCache completed.");
		}
	}

	public void callProcedureWithoutResult(final String procName,
			final List<Object> paramList) {
		
		getHibernateTemplate().execute(new HibernateCallback<Integer>() { 
            public Integer doInHibernate(Session session) { 
                try { 
//                    ProcedureCall pc = session.getNamedProcedureCall(procName); 
                	SQLQuery sq = session.createSQLQuery(procName);
//                    String procName = "{call procedureName(?)}"; 
                    if(paramList != null && !paramList.isEmpty()) {
            			int index = 0;
            			for(Object obj : paramList) {
            				sq.setString(index++, (String)obj);
            			}
            		}
                    return sq.executeUpdate();
                } catch (Exception e) { 
                	e.printStackTrace(); 
                }
                
                return 0;
            } 
        }); 
	}
}
