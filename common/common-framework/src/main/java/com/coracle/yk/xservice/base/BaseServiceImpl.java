package com.coracle.yk.xservice.base;


import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.intf.IBaseService;
import com.coracle.yk.xservice.common.XserviceException;

import java.util.List;

/**
 * Created by huangbaidong on 2016/7/12.
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {

    public abstract IMybatisDao<T> getBaseDao();

    @Override
    public int deleteByPrimaryKey(Object id) {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T po) {
        return getBaseDao().insert(po);
    }

    @Override
    public int insertSelective(T po) {
        return getBaseDao().insertSelective(po);
    }

    @Override
    public T selectByPrimaryKey(Object id) {
        return getBaseDao().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T po) {
        return getBaseDao().updateByPrimaryKeySelective(po);
    }

    @Override
    public int updateByPrimaryKey(T po) {
        return getBaseDao().updateByPrimaryKey(po);
    }

	/**
	 * {@inheritDoc}
	*/
	@Override
	public List<T> selectByCondition() {
		return selectByCondition(null);
	}

	/**
	 * {@inheritDoc}
	*/
	@Override
	public List<T> selectByCondition(T condition) {
		return getBaseDao().selectByCondition(condition);
	}

	/**
	 * {@inheritDoc}
	*/
	@Override
	public int updateByIdsSelective(T po, List ids) {
		return getBaseDao().updateByIdsSelective(po, ids);
	}
}
