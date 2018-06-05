package com.coracle.dms.webservice.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @title：安井订单商品实体
 * @describe：
 * @copyright：Copyright (c) 1997-2017 coracle, Inc.
 * @company：圆舟科技
 * @author：taok
 * @date：2018-02-26 18:04
 * @version：1.0
 */
public class AnjoyOrderProductModel implements Serializable {
    private String	material	;//物料
    private String	unit	;//计量单位
    private String	remark	;//备注
    private String	reasonCode	;//原因代码
    private Boolean	isPresent	;//是否赠品
    private BigDecimal	qty	;//数量
    private BigDecimal	assistQty	;//辅助单位数量
    private BigDecimal	price	;//单价
    private BigDecimal	taxPrice	;//含税单价
    private BigDecimal cheapRate	;//减价比例
    private Integer	discountCondition	;//折扣条件
    private Integer	discountType	;//折扣方式
    private BigDecimal	discount	;//单位折扣（率）
    private BigDecimal	discountAmount	;//折扣额
    private BigDecimal	amount	;//金额
    private BigDecimal	actualPrice	;//实际单价
    private BigDecimal	actualTaxPrice	;//实际含税单价
    private BigDecimal	taxRate	;//税率
    private BigDecimal	tax	;//税额
    private BigDecimal	taxAmount	;//价税合计
    private String  sendDate	;//发货日期
    private String	deliveryDate	;//交货日期
    private String	storageOrgUnit	;//发货组织
    private BigDecimal	unOrderedQty	;//未订货数量
    private BigDecimal	quantityUnCtrl	;//不控制数量
    private String	reason	;//原因
    private Boolean	isBetweenCompanySend	;//是否跨公司发货
    private String	deliveryCustomer	;//送货客户
    private String	paymentCustomer	;//收款客户
    private String	receiveCustomer	;//应收客户
    private String	sendAddress	;//送货地址
    private String netOrderBillID;//	关联单据ID
    private String netOrderBillNumber;//	关联单据编号
    private String baseUnit;//基本计量单位	String		否	系统默认为公斤
    private BigDecimal baseQty;//	基本单位数量	BigDecimal		是
    private BigDecimal totalTax	;//本位币税额
    private BigDecimal totalAmount	;//本位币金额
    private BigDecimal totalTaxAmount	;//价税合计本位币

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean present) {
        isPresent = present;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getAssistQty() {
        return assistQty;
    }

    public void setAssistQty(BigDecimal assistQty) {
        this.assistQty = assistQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public BigDecimal getCheapRate() {
        return cheapRate;
    }

    public void setCheapRate(BigDecimal cheapRate) {
        this.cheapRate = cheapRate;
    }

    public Integer getDiscountCondition() {
        return discountCondition;
    }

    public void setDiscountCondition(Integer discountCondition) {
        this.discountCondition = discountCondition;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public BigDecimal getActualTaxPrice() {
        return actualTaxPrice;
    }

    public void setActualTaxPrice(BigDecimal actualTaxPrice) {
        this.actualTaxPrice = actualTaxPrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStorageOrgUnit() {
        return storageOrgUnit;
    }

    public void setStorageOrgUnit(String storageOrgUnit) {
        this.storageOrgUnit = storageOrgUnit;
    }

    public BigDecimal getUnOrderedQty() {
        return unOrderedQty;
    }

    public void setUnOrderedQty(BigDecimal unOrderedQty) {
        this.unOrderedQty = unOrderedQty;
    }

    public BigDecimal getQuantityUnCtrl() {
        return quantityUnCtrl;
    }

    public void setQuantityUnCtrl(BigDecimal quantityUnCtrl) {
        this.quantityUnCtrl = quantityUnCtrl;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getIsBetweenCompanySend() {
        return isBetweenCompanySend;
    }

    public void setIsBetweenCompanySend(Boolean betweenCompanySend) {
        isBetweenCompanySend = betweenCompanySend;
    }

    public String getDeliveryCustomer() {
        return deliveryCustomer;
    }

    public void setDeliveryCustomer(String deliveryCustomer) {
        this.deliveryCustomer = deliveryCustomer;
    }

    public String getPaymentCustomer() {
        return paymentCustomer;
    }

    public void setPaymentCustomer(String paymentCustomer) {
        this.paymentCustomer = paymentCustomer;
    }

    public String getReceiveCustomer() {
        return receiveCustomer;
    }

    public void setReceiveCustomer(String receiveCustomer) {
        this.receiveCustomer = receiveCustomer;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getNetOrderBillID() {
        return netOrderBillID;
    }

    public void setNetOrderBillID(String netOrderBillID) {
        this.netOrderBillID = netOrderBillID;
    }

    public String getNetOrderBillNumber() {
        return netOrderBillNumber;
    }

    public void setNetOrderBillNumber(String netOrderBillNumber) {
        this.netOrderBillNumber = netOrderBillNumber;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public BigDecimal getBaseQty() {
        return baseQty;
    }

    public void setBaseQty(BigDecimal baseQty) {
        this.baseQty = baseQty;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }
}
