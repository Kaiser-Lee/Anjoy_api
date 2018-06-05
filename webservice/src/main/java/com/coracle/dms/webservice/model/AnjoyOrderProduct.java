package com.coracle.dms.webservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 安井订单商品实体
 */
public class AnjoyOrderProduct implements Serializable {

    //物料
    private String FMaterialID;

    //计量单位
    private String FUnitID;

    //备注
    private String FRemark;

    //原因编码
    private String FReasonCodeID;

    //赠品
    private Boolean FIsPresent;

    //数量
    private BigDecimal FQty;

    //辅助单位数量
    private BigDecimal FAssistQty;

    //单价
    private BigDecimal FPrice;

    //含税单价
    private BigDecimal FTaxPrice;

    //减价比例
    private BigDecimal FCheapRate;

    //折扣条件
    private Integer FDiscountCondition;

    //折扣方式
    private Integer FDiscountType;

    //单价折扣（率）
    private BigDecimal FDiscount;

    //折扣额
    private BigDecimal FDiscountAmount;

    //金额
    private BigDecimal FAmount;

    //实际单价
    private BigDecimal FActualPrice;

    //实际含税单价
    private BigDecimal FActualTaxPrice;

    //税率
    private BigDecimal FTaxRate;

    //税额
    private BigDecimal FTax;

    //价税合计
    private BigDecimal FTaxAmount;

    //发货日期
    private Date FSendDate;

    //交货日期
    private Date FDeliveryDate;

    //库存组织
    private String FStorageOrgUnitID;

    //未订货数量
    private BigDecimal FUnOrderedQty;

    //不控制数量
    private BigDecimal FQuantityUnCtrl;

    //原因
    private String FReason;

    //跨公司发货
    private Boolean FIsBetweenCompanySend;

    //送货客户
    private String FDeliveryCustomerID;

    //收款客户
    private String FPaymentCustomerID;

    //应收客户
    private String FReceiveCustomerID;

    //送货地址
    private String FSendAddress;

    public String getFMaterialID() {
        return FMaterialID;
    }

    public void setFMaterialID(String FMaterialID) {
        this.FMaterialID = FMaterialID;
    }

    public String getFUnitID() {
        return FUnitID;
    }

    public void setFUnitID(String FUnitID) {
        this.FUnitID = FUnitID;
    }

    public String getFRemark() {
        return FRemark;
    }

    public void setFRemark(String FRemark) {
        this.FRemark = FRemark;
    }

    public String getFReasonCodeID() {
        return FReasonCodeID;
    }

    public void setFReasonCodeID(String FReasonCodeID) {
        this.FReasonCodeID = FReasonCodeID;
    }

    public Boolean getFIsPresent() {
        return FIsPresent;
    }

    public void setFIsPresent(Boolean FIsPresent) {
        this.FIsPresent = FIsPresent;
    }

    public BigDecimal getFQty() {
        return FQty;
    }

    public void setFQty(BigDecimal FQty) {
        this.FQty = FQty;
    }

    public BigDecimal getFAssistQty() {
        return FAssistQty;
    }

    public void setFAssistQty(BigDecimal FAssistQty) {
        this.FAssistQty = FAssistQty;
    }

    public BigDecimal getFPrice() {
        return FPrice;
    }

    public void setFPrice(BigDecimal FPrice) {
        this.FPrice = FPrice;
    }

    public BigDecimal getFTaxPrice() {
        return FTaxPrice;
    }

    public void setFTaxPrice(BigDecimal FTaxPrice) {
        this.FTaxPrice = FTaxPrice;
    }

    public BigDecimal getFCheapRate() {
        return FCheapRate;
    }

    public void setFCheapRate(BigDecimal FCheapRate) {
        this.FCheapRate = FCheapRate;
    }

    public Integer getFDiscountCondition() {
        return FDiscountCondition;
    }

    public void setFDiscountCondition(Integer FDiscountCondition) {
        this.FDiscountCondition = FDiscountCondition;
    }

    public Integer getFDiscountType() {
        return FDiscountType;
    }

    public void setFDiscountType(Integer FDiscountType) {
        this.FDiscountType = FDiscountType;
    }

    public BigDecimal getFDiscount() {
        return FDiscount;
    }

    public void setFDiscount(BigDecimal FDiscount) {
        this.FDiscount = FDiscount;
    }

    public BigDecimal getFDiscountAmount() {
        return FDiscountAmount;
    }

    public void setFDiscountAmount(BigDecimal FDiscountAmount) {
        this.FDiscountAmount = FDiscountAmount;
    }

    public BigDecimal getFAmount() {
        return FAmount;
    }

    public void setFAmount(BigDecimal FAmount) {
        this.FAmount = FAmount;
    }

    public BigDecimal getFActualPrice() {
        return FActualPrice;
    }

    public void setFActualPrice(BigDecimal FActualPrice) {
        this.FActualPrice = FActualPrice;
    }

    public BigDecimal getFActualTaxPrice() {
        return FActualTaxPrice;
    }

    public void setFActualTaxPrice(BigDecimal FActualTaxPrice) {
        this.FActualTaxPrice = FActualTaxPrice;
    }

    public BigDecimal getFTaxRate() {
        return FTaxRate;
    }

    public void setFTaxRate(BigDecimal FTaxRate) {
        this.FTaxRate = FTaxRate;
    }

    public BigDecimal getFTax() {
        return FTax;
    }

    public void setFTax(BigDecimal FTax) {
        this.FTax = FTax;
    }

    public BigDecimal getFTaxAmount() {
        return FTaxAmount;
    }

    public void setFTaxAmount(BigDecimal FTaxAmount) {
        this.FTaxAmount = FTaxAmount;
    }

    public Date getFSendDate() {
        return FSendDate;
    }

    public void setFSendDate(Date FSendDate) {
        this.FSendDate = FSendDate;
    }

    public Date getFDeliveryDate() {
        return FDeliveryDate;
    }

    public void setFDeliveryDate(Date FDeliveryDate) {
        this.FDeliveryDate = FDeliveryDate;
    }

    public String getFStorageOrgUnitID() {
        return FStorageOrgUnitID;
    }

    public void setFStorageOrgUnitID(String FStorageOrgUnitID) {
        this.FStorageOrgUnitID = FStorageOrgUnitID;
    }

    public BigDecimal getFUnOrderedQty() {
        return FUnOrderedQty;
    }

    public void setFUnOrderedQty(BigDecimal FUnOrderedQty) {
        this.FUnOrderedQty = FUnOrderedQty;
    }

    public BigDecimal getFQuantityUnCtrl() {
        return FQuantityUnCtrl;
    }

    public void setFQuantityUnCtrl(BigDecimal FQuantityUnCtrl) {
        this.FQuantityUnCtrl = FQuantityUnCtrl;
    }

    public String getFReason() {
        return FReason;
    }

    public void setFReason(String FReason) {
        this.FReason = FReason;
    }

    public Boolean getFIsBetweenCompanySend() {
        return FIsBetweenCompanySend;
    }

    public void setFIsBetweenCompanySend(Boolean FIsBetweenCompanySend) {
        this.FIsBetweenCompanySend = FIsBetweenCompanySend;
    }

    public String getFDeliveryCustomerID() {
        return FDeliveryCustomerID;
    }

    public void setFDeliveryCustomerID(String FDeliveryCustomerID) {
        this.FDeliveryCustomerID = FDeliveryCustomerID;
    }

    public String getFPaymentCustomerID() {
        return FPaymentCustomerID;
    }

    public void setFPaymentCustomerID(String FPaymentCustomerID) {
        this.FPaymentCustomerID = FPaymentCustomerID;
    }

    public String getFReceiveCustomerID() {
        return FReceiveCustomerID;
    }

    public void setFReceiveCustomerID(String FReceiveCustomerID) {
        this.FReceiveCustomerID = FReceiveCustomerID;
    }

    public String getFSendAddress() {
        return FSendAddress;
    }

    public void setFSendAddress(String FSendAddress) {
        this.FSendAddress = FSendAddress;
    }
}
