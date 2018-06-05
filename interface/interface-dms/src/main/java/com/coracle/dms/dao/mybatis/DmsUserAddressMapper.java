/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsUserAddress;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DmsUserAddressMapper extends IMybatisDao<DmsUserAddress> {

    /**
     * 获取用户地址数目
     * @param id
     * @return
     */
    Integer getAddressCount(Long id);

    /**
     * 根据用户ID查询地址字符串
     * @param id
     * @return
     */
    /*Map<String,Object> findAddressIds(Long id);*/
}