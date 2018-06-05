package com.coracle.dms.service;

import com.coracle.dms.po.DmsOrderDeliveryItem;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsOrderDeliveryItemService extends IBaseService<DmsOrderDeliveryItem> {

    /**
     * 批量插入
     * @param itemList
     */
    void batchInsert(List<DmsOrderDeliveryItem> itemList);

    void confirm(DmsOrderDeliveryItem item, UserSession session);

    void updateInventory(DmsOrderDeliveryItem item, UserSession session);

    Integer selectUnconfirmedCount(Long orderId);
    
    Integer selectUnconfirmedCountRT(Long orderId);
    
}