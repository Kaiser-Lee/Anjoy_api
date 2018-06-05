package com.coracle.dms.vo;

import com.coracle.dms.po.DmsOrderDeliveryItem;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class DmsOrderDeliveryItemVo extends DmsOrderDeliveryItem {

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品编码")
    private String productCode;

    @ApiModelProperty("产品规格")
    private String productSpec;

    @ApiModelProperty("产品价格")
    private BigDecimal productPrice;

    @ApiModelProperty("产品缩略图")
    private String picUrl;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("发货仓库")
    private String storage;

    @ApiModelProperty("发运单号")
    private String orderDeliveryCode;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getOrderDeliveryCode() {
        return orderDeliveryCode;
    }

    public void setOrderDeliveryCode(String orderDeliveryCode) {
        this.orderDeliveryCode = orderDeliveryCode;
    }
}
