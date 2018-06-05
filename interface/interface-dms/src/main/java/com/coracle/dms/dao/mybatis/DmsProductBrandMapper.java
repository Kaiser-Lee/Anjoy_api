/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsProductBrand;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsProductBrandMapper extends IMybatisDao<DmsProductBrand> {
    /**
     * 分页获取产品品牌列表
     * @param dmsProductBrand
     * @return
     */
    List<DmsProductBrand> getPageList(DmsProductBrand dmsProductBrand);
}