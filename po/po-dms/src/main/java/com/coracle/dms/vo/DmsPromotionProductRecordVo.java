package com.coracle.dms.vo;

import com.coracle.dms.po.DmsPromotionProductRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class DmsPromotionProductRecordVo extends DmsPromotionProductRecord {
    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品规格")
    private String productSpec;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("订单编号")
    private String orderCode;

    @ApiModelProperty("下单时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
