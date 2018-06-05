package com.coracle.dms.service;

import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.po.DmsTianGoodsStore;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsTianGoodsStoreService extends IBaseService<DmsTianGoodsStore> {

    PageHelper.Page selectPageList(DmsTianGoodsStore vo);
}