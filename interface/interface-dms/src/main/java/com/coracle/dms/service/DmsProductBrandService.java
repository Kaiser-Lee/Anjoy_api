package com.coracle.dms.service;

import com.coracle.dms.po.DmsProductBrand;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsProductBrandService extends IBaseService<DmsProductBrand> {
    /***
     * 新增或者修改产品品牌
     * @param dmsProductBrand
     */
    void saveOrUpdate(DmsProductBrand dmsProductBrand, UserSession userSession);
    /***
     * 删除产品品牌，软删除
     * @param dmsProductBrand
     */
    void delete(DmsProductBrand dmsProductBrand, UserSession userSession);
    /**
     * 全局变量分页查询
     * @param dmsProductBrand
     * @return
     */
    PageHelper.Page<DmsProductBrand> selectForPageList(DmsProductBrand dmsProductBrand, UserSession userSession);
}