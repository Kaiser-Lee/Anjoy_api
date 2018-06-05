package com.coracle.dms.service;

import com.coracle.dms.po.DmsFootprint;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsFootprintService extends IBaseService<DmsFootprint> {
    void insert(Long userId, Long productId);

    PageHelper.Page<DmsProductVo> list(DmsFootprint param, UserSession session);
}