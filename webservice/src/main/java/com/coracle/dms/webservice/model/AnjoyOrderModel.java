package com.coracle.dms.webservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @title：安井订单实体
 * @describe：
 * @copyright：Copyright (c) 1997-2017 coracle, Inc.
 * @company：圆舟科技
 * @author：taok
 * @date：2018-02-26 17:58
 * @version：1.0
 */
public class AnjoyOrderModel implements Serializable {

    private String	creator	;//创建者
    private String bizDate	;//业务日期
    private String	description	;//单据说明
    private String	bizType	;//业务类型
    private boolean	isInnerSale	;//是否内部销售
    private String	orderCustomer	;//订货客户
    private String	deliveryType	;//交货方式
    private String	currency	;//币别
    private BigDecimal	exchangeRate	;//汇率
    private String	paymentType	;//付款方式
    private String	settlementType	;//结算方式
    private String	saleOrgUnit	;//销售组织
    private BigDecimal	totalAmount	;//抬头金额合计
    private BigDecimal	totalTax	;//抬头税额合计
    private BigDecimal	totalTaxAmount	;//抬头价税合计
    private BigDecimal	preReceived	;//已收应收款
    private BigDecimal	unPrereceivedAmount	;//未预收款金额
    private String	sendAddress	;//送货地址
    private BigDecimal	localTotalAmount	;//抬头金额本位币合计
    private BigDecimal	localTotalTaxAmount	;//抬头价税本位币合计
    private Boolean	isInTax	;//是否含税
    private String	customerPhone	;//联系电话
    private String	linkMan	;//联系人
    private Boolean	isCentralBalance	;//是否集中结算
    private String	receiveCondition	;//收款条件
    private String	storageOrgUnit	;//销售方库存组织
    private String	warehouse	;//销售方仓库
    private String	adminOrgUnit	;//归属区域
    private String sourceBillType;//来源单据类型
    private String sourceBillId;//圆舟DMS 订单编号

    private List<AnjoyOrderProductModel> entry = new ArrayList<>();

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getBizDate() {
        return bizDate;
    }

    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public boolean getIsInnerSale() {
        return isInnerSale;
    }

    public void setIsInnerSale(boolean innerSale) {
        isInnerSale = innerSale;
    }

    public String getOrderCustomer() {
        return orderCustomer;
    }

    public void setOrderCustomer(String orderCustomer) {
        this.orderCustomer = orderCustomer;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getSaleOrgUnit() {
        return saleOrgUnit;
    }

    public void setSaleOrgUnit(String saleOrgUnit) {
        this.saleOrgUnit = saleOrgUnit;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public BigDecimal getPreReceived() {
        return preReceived;
    }

    public void setPreReceived(BigDecimal preReceived) {
        this.preReceived = preReceived;
    }

    public BigDecimal getUnPrereceivedAmount() {
        return unPrereceivedAmount;
    }

    public void setUnPrereceivedAmount(BigDecimal unPrereceivedAmount) {
        this.unPrereceivedAmount = unPrereceivedAmount;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public BigDecimal getLocalTotalAmount() {
        return localTotalAmount;
    }

    public void setLocalTotalAmount(BigDecimal localTotalAmount) {
        this.localTotalAmount = localTotalAmount;
    }

    public BigDecimal getLocalTotalTaxAmount() {
        return localTotalTaxAmount;
    }

    public void setLocalTotalTaxAmount(BigDecimal localTotalTaxAmount) {
        this.localTotalTaxAmount = localTotalTaxAmount;
    }

    public Boolean getIsInTax() {
        return isInTax;
    }

    public void setIsInTax(Boolean inTax) {
        isInTax = inTax;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public Boolean getIsCentralBalance() {
        return isCentralBalance;
    }

    public void setIsCentralBalance(Boolean isCentralBalance) {
        this.isCentralBalance = isCentralBalance;
    }

    public String getReceiveCondition() {
        return receiveCondition;
    }

    public void setReceiveCondition(String receiveCondition) {
        this.receiveCondition = receiveCondition;
    }

    public String getStorageOrgUnit() {
        return storageOrgUnit;
    }

    public void setStorageOrgUnit(String storageOrgUnit) {
        this.storageOrgUnit = storageOrgUnit;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getAdminOrgUnit() {
        return adminOrgUnit;
    }

    public void setAdminOrgUnit(String adminOrgUnit) {
        this.adminOrgUnit = adminOrgUnit;
    }

    public List<AnjoyOrderProductModel> getEntry() {
        return entry;
    }

    public void setEntry(List<AnjoyOrderProductModel> entry) {
        this.entry = entry;
    }

    public String getSourceBillType() {
        return sourceBillType;
    }

    public void setSourceBillType(String sourceBillType) {
        this.sourceBillType = sourceBillType;
    }

    public String getSourceBillId() {
        return sourceBillId;
    }

    public void setSourceBillId(String sourceBillId) {
        this.sourceBillId = sourceBillId;
    }
}
