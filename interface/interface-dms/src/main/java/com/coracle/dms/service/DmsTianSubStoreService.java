package com.coracle.dms.service;

import com.coracle.dms.po.DmsTianGoodsStore;
import com.coracle.dms.po.DmsTianSubStore;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsTianSubStoreService extends IBaseService<DmsTianSubStore> {
    PageHelper.Page selectPageList(DmsTianSubStore vo);
}