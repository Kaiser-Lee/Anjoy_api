/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsDynamicProduct;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsDynamicProductMapper extends IMybatisDao<DmsDynamicProduct> {

    /**
     * 批量增加客户购买产品记录
     * @param list
     */
    void batchInsert(List<DmsDynamicProduct> list);
}