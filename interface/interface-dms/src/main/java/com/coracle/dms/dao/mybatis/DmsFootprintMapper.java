/**
 * create by hcs
 * @date 2018-01
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsFootprint;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsFootprintMapper extends IMybatisDao<DmsFootprint> {
    /**
     * 根据用户id获取用户最近浏览的十个产品id列表
     *
     * @param userId
     * @return
     */
    List<Long> listRecentTenProductIdByUserId(Long userId);
}