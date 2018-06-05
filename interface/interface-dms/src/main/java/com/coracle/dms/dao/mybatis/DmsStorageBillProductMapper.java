/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsStorageBillProduct;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsStorageBillProductMapper extends IMybatisDao<DmsStorageBillProduct> {

    /**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsStorageBillProduct> list);

    /**
     * 删除
     * @param billProductId
     */
    void deleteByStorageBillProductId(Long billProductId);
}