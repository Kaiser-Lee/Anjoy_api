/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsProductArea;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;
import java.util.Map;

public interface DmsProductAreaMapper extends IMybatisDao<DmsProductArea> {

    /**
     * 批量新增产品库存关联关系
     */
    void batchInsert(List<DmsProductArea> productAreas);

    /**
     * 批量删除产品库存关联关系
     */
    void batchDelete(List<DmsProductArea> productAreas);

    /**
     * 根据渠道id和产品id判断该产品是否可为该渠道下账号可见：0-不可见；1-可见
     * @param param
     * @return
     */
    Integer selectByChannelIdAndProductId(Map<String, Object> param);
}