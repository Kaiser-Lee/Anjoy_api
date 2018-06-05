package com.coracle.dms.service;

import com.coracle.dms.po.DmsOrderDelivery;
import com.coracle.dms.vo.DmsOrderDeliveryAnjoyVo;
import com.coracle.dms.vo.DmsOrderDeliveryVo;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsOrderDeliveryService extends IBaseService<DmsOrderDelivery> {
    void create(DmsOrderDeliveryVo orderDeliveryVo, UserSession session);

    void createAnjoy(DmsOrderDeliveryAnjoyVo orderDeliveryVo);
}