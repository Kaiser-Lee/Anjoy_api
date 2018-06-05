package com.coracle.yk.xdatabase.base.hibernate.intf;

import java.util.List;
import java.util.Set;

import com.coracle.yk.xdatabase.common.XdatabaseException;
import com.coracle.yk.xframework.po.AdapterEntity;

public interface IHibernateDao<T extends AdapterEntity> {
	List<T> findPoByField(String fieldName, String fieldValud) throws XdatabaseException;
	List<T> findPoPageList(T po, int start, int limit) throws XdatabaseException;
	Long findPoCount(T po) throws XdatabaseException;
	T findPoById(long id) throws XdatabaseException;
	List<T> findAllPos();
	List<T> findPosByIdSet(Set<Long> idSet) throws XdatabaseException;
	long insertPo(T po) throws XdatabaseException;
	void modifyPo(T po) throws XdatabaseException;
	void removePo(Long id) throws XdatabaseException;
	void removePos(Set<Long> idSet) throws XdatabaseException;
}
