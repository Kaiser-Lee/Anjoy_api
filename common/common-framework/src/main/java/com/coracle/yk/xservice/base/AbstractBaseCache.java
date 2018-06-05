package com.coracle.yk.xservice.base;

import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.intf.IBaseService;
import com.coracle.yk.xservice.common.XserviceException;

/**
 * @author Xiruo
 *
 */
public abstract class AbstractBaseCache<T> implements IBaseService<T> {

	public abstract IMybatisDao<T> getBaseService();

//	@Override
//	public List<T> findPoByField(String fieldName, String fieldValue) throws XserviceException {
//		List<T> list = null;
//		try {
//			list = getBaseService().findPoByField(fieldName, fieldValue);
//		} catch(Exception e) {
//			throw new XserviceException(e);
//		}
//		return list;
//	}
//
//	@Override
//	public List<T> findPoPageList(T po) throws XserviceException {
//		List<T> list = null;
//		try {
//			list = getBaseService().findPoPageList(po);
//		} catch(Exception e) {
//			throw new XserviceException(e);
//		}
//		return list;
//	}
//
//	@Override
//	public List<T> findPosByIds(String ids) throws XserviceException {
//		List<T> list = null;
//		try {
//			list = getBaseService().findPosByIds(ids);
//		} catch(Exception e) {
//			throw new XserviceException(e);
//		}
//		return list;
//	}
//
//	@Override
//	public Integer findPoCount(T po) throws XserviceException {
//		int count = 0;
//		try {
//			count = getBaseService().findPoCount(po);
//		} catch(Exception e) {
//			throw new XserviceException(e);
//		}
//		return count;
//	}
//
//	@Override
//	public void removePos(String ids) throws XserviceException {
//		try {
//			getBaseService().removePos(ids);
//		} catch(Exception e) {
//			throw new XserviceException(e);
//		}
//	}
//
//	@Override
//	public List<T> findAllPos() throws XserviceException {
//		List<T> list = null;
//		try {
//			list = getBaseService().findAllPos();
//		} catch(Exception e) {
//			throw new XserviceException(e);
//		}
//		return list;
//	}

	@Override
	public T selectByPrimaryKey(Object id) {
		T region = null;
		region = (T)getBaseService().selectByPrimaryKey(id);
		return region;
	}

	@Override
	public int insert(T po) {
		int id = 0;
		id = getBaseService().insert(po);
		return id;
	}

	@Override
	public int insertSelective(T po) {
		int id = 0;
		id = getBaseService().insertSelective(po);
		return id;
	}

	@Override
	public int updateByPrimaryKeySelective(T po) {
		int id = 0;
		id = getBaseService().updateByPrimaryKeySelective(po);
		return id;
	}

	@Override
	public int updateByPrimaryKey(T po) {
		int id = 0;
		id = getBaseService().updateByPrimaryKey(po);
		return id;
	}

	@Override
	public int deleteByPrimaryKey(Object id) {
		int ret = 0;
		ret = getBaseService().deleteByPrimaryKey(id);
		return ret;
	}

}
