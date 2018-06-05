package com.coracle.dms.vo;

import com.coracle.dms.po.DmsOrderDelivery;
import com.coracle.dms.po.DmsOrderDeliveryItem;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DmsOrderDeliveryVo extends DmsOrderDelivery {
    @ApiModelProperty("发货清单")
    List<DmsOrderDeliveryItem> orderDeliveryItemList;

    public List<DmsOrderDeliveryItem> getOrderDeliveryItemList() {
        return orderDeliveryItemList;
    }

    public void setOrderDeliveryItemList(List<DmsOrderDeliveryItem> orderDeliveryItemList) {
        this.orderDeliveryItemList = orderDeliveryItemList;
    }
}
