package com.coracle.yk.xservice.base.intf;

import com.coracle.yk.xservice.common.XserviceException;

import java.io.Serializable;
import java.util.List;


public interface IBaseService<T> {


    //	@RedisEvict(type = Object.class)
    int deleteByPrimaryKey(Object id);

    //	@RedisEvict(type = Object.class)
    int insert(T po);

    //	@RedisEvict(type = Object.class)
    int insertSelective(T po);

    //    @RedisCache(type = Object.class, result = RESULT_TYPE_SINGLE)
    T selectByPrimaryKey(Object id);

    //    @RedisEvict(type = Object.class)
    int updateByPrimaryKeySelective(T po);

    //    @RedisEvict(type = Object.class)
    int updateByPrimaryKey(T po);

    /** 全量查询 */
    List<T> selectByCondition();
    /** 条件查询 */
    List<T> selectByCondition(T condition);

    int updateByIdsSelective(T po, List ids);

}
