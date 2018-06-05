package com.coracle.dms.service;

import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsClaimsProductService extends IBaseService<DmsClaimsProduct> {

    PageHelper.Page selectPageList(DmsClaimsProduct vo);
}


