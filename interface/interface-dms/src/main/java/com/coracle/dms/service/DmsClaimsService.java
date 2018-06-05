package com.coracle.dms.service;

import com.coracle.dms.po.DmsClaims;
import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.po.DmsTianSubStore;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsClaimsService extends IBaseService<DmsClaims> {
    int insertObject(DmsClaims dmsClaims);
    PageHelper.Page selectPageList(DmsClaims dmsClaims);
}