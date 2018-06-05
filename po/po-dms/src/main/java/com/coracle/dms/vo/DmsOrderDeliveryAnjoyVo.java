package com.coracle.dms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("发货记录表实体")
public class DmsOrderDeliveryAnjoyVo implements Serializable {

    @ApiModelProperty("发货单号")
    private String code;
    @ApiModelProperty("物流公司")
    private String logisticsCompany;
    @ApiModelProperty("物流单号")
    private String logisticsCode;
    @ApiModelProperty("司机姓名")
    private String driverName;
    @ApiModelProperty("手机号")
    private String driverMobile;
    @ApiModelProperty("车牌号")
    private String plateNumber;

    //预留字段
    @ApiModelProperty("预留字段")
    private String attr1;
    @ApiModelProperty("预留字段")
    private String attr2;
    @ApiModelProperty("预留字段")
    private String attr3;
    @ApiModelProperty("预留字段")
    private String attr4;
    @ApiModelProperty("预留字段")
    private String attr5;

    @ApiModelProperty("发货清单")
    private List<DmsOrderDeliveryItemAnjoyVo> orderDeliveryItemList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    public List<DmsOrderDeliveryItemAnjoyVo> getOrderDeliveryItemList() {
        return orderDeliveryItemList;
    }

    public void setOrderDeliveryItemList(List<DmsOrderDeliveryItemAnjoyVo> orderDeliveryItemList) {
        this.orderDeliveryItemList = orderDeliveryItemList;
    }
}
