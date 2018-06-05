package com.coracle.dms.webservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 安井订单实体
 */
public class AnJoyOrder implements Serializable {

    //创建人id
    private String FCreatorId;

    //业务时间
    private Date FBizDate;

    //单据说明
    private String Fdescription;

    //业务类型
    private String FBizTypeId;

    //是否内部销售
    private Boolean FIsInnerSale;

    //订货客户
    private String FOrderCustomerID;

    //交货方式
    private String FDeliveryTypeID;

    //币别
    private String FCurrencyId;

    //汇率
    private BigDecimal FExchangeRate;

    //付款方式
    private String FPaymentTypeID;

    //结算方式
    private String FSettlementTypeID;

    //销售组织
    private String FSaleOrgUnitID;

    //抬头金额合计
    private BigDecimal FTotalAmount;

    //抬头税额合计
    private BigDecimal FTotalTax;

    //抬头价税合计
    private BigDecimal FTotalTaxAmount;

    //已收应收款
    private BigDecimal FPreReceived;

    //未预收款金额
    private BigDecimal FUnPrereceivedAmount;

    //送货地址
    private String FSendAddress;

    //抬头金额本位币合计
    private BigDecimal FLocalTotalAmount;

    //抬头价税本位币合计
    private BigDecimal FLocalTotalTaxAmount;

    //是否含税
    private Boolean FIsInTax;

    //联系电话
    private String FCustomerPhone;

    //联系人
    private String FLinkMan;

    //是否集中结算
    private String FIsCentralBalance;

    //收款条件
    private String FReceiveConditionID;

    //销售方库存组织
    private String FStorageOrgUnitID;

    //销售方仓库
    private String FWarehouseID;

    //归属区域
    private String CFGSQYID;

    public String getFCreatorId() {
        return FCreatorId;
    }

    public void setFCreatorId(String FCreatorId) {
        this.FCreatorId = FCreatorId;
    }

    public Date getFBizDate() {
        return FBizDate;
    }

    public void setFBizDate(Date FBizDate) {
        this.FBizDate = FBizDate;
    }

    public String getFdescription() {
        return Fdescription;
    }

    public void setFdescription(String fdescription) {
        Fdescription = fdescription;
    }

    public String getFBizTypeId() {
        return FBizTypeId;
    }

    public void setFBizTypeId(String FBizTypeId) {
        this.FBizTypeId = FBizTypeId;
    }

    public Boolean getFIsInnerSale() {
        return FIsInnerSale;
    }

    public void setFIsInnerSale(Boolean FIsInnerSale) {
        this.FIsInnerSale = FIsInnerSale;
    }

    public String getFOrderCustomerID() {
        return FOrderCustomerID;
    }

    public void setFOrderCustomerID(String FOrderCustomerID) {
        this.FOrderCustomerID = FOrderCustomerID;
    }

    public String getFDeliveryTypeID() {
        return FDeliveryTypeID;
    }

    public void setFDeliveryTypeID(String FDeliveryTypeID) {
        this.FDeliveryTypeID = FDeliveryTypeID;
    }

    public String getFCurrencyId() {
        return FCurrencyId;
    }

    public void setFCurrencyId(String FCurrencyId) {
        this.FCurrencyId = FCurrencyId;
    }

    public BigDecimal getFExchangeRate() {
        return FExchangeRate;
    }

    public void setFExchangeRate(BigDecimal FExchangeRate) {
        this.FExchangeRate = FExchangeRate;
    }

    public String getFPaymentTypeID() {
        return FPaymentTypeID;
    }

    public void setFPaymentTypeID(String FPaymentTypeID) {
        this.FPaymentTypeID = FPaymentTypeID;
    }

    public String getFSettlementTypeID() {
        return FSettlementTypeID;
    }

    public void setFSettlementTypeID(String FSettlementTypeID) {
        this.FSettlementTypeID = FSettlementTypeID;
    }

    public String getFSaleOrgUnitID() {
        return FSaleOrgUnitID;
    }

    public void setFSaleOrgUnitID(String FSaleOrgUnitID) {
        this.FSaleOrgUnitID = FSaleOrgUnitID;
    }

    public BigDecimal getFTotalAmount() {
        return FTotalAmount;
    }

    public void setFTotalAmount(BigDecimal FTotalAmount) {
        this.FTotalAmount = FTotalAmount;
    }

    public BigDecimal getFTotalTax() {
        return FTotalTax;
    }

    public void setFTotalTax(BigDecimal FTotalTax) {
        this.FTotalTax = FTotalTax;
    }

    public BigDecimal getFTotalTaxAmount() {
        return FTotalTaxAmount;
    }

    public void setFTotalTaxAmount(BigDecimal FTotalTaxAmount) {
        this.FTotalTaxAmount = FTotalTaxAmount;
    }

    public BigDecimal getFPreReceived() {
        return FPreReceived;
    }

    public void setFPreReceived(BigDecimal FPreReceived) {
        this.FPreReceived = FPreReceived;
    }

    public BigDecimal getFUnPrereceivedAmount() {
        return FUnPrereceivedAmount;
    }

    public void setFUnPrereceivedAmount(BigDecimal FUnPrereceivedAmount) {
        this.FUnPrereceivedAmount = FUnPrereceivedAmount;
    }

    public String getFSendAddress() {
        return FSendAddress;
    }

    public void setFSendAddress(String FSendAddress) {
        this.FSendAddress = FSendAddress;
    }

    public BigDecimal getFLocalTotalAmount() {
        return FLocalTotalAmount;
    }

    public void setFLocalTotalAmount(BigDecimal FLocalTotalAmount) {
        this.FLocalTotalAmount = FLocalTotalAmount;
    }

    public BigDecimal getFLocalTotalTaxAmount() {
        return FLocalTotalTaxAmount;
    }

    public void setFLocalTotalTaxAmount(BigDecimal FLocalTotalTaxAmount) {
        this.FLocalTotalTaxAmount = FLocalTotalTaxAmount;
    }

    public Boolean getFIsInTax() {
        return FIsInTax;
    }

    public void setFIsInTax(Boolean FIsInTax) {
        this.FIsInTax = FIsInTax;
    }

    public String getFCustomerPhone() {
        return FCustomerPhone;
    }

    public void setFCustomerPhone(String FCustomerPhone) {
        this.FCustomerPhone = FCustomerPhone;
    }

    public String getFLinkMan() {
        return FLinkMan;
    }

    public void setFLinkMan(String FLinkMan) {
        this.FLinkMan = FLinkMan;
    }

    public String getFIsCentralBalance() {
        return FIsCentralBalance;
    }

    public void setFIsCentralBalance(String FIsCentralBalance) {
        this.FIsCentralBalance = FIsCentralBalance;
    }

    public String getFReceiveConditionID() {
        return FReceiveConditionID;
    }

    public void setFReceiveConditionID(String FReceiveConditionID) {
        this.FReceiveConditionID = FReceiveConditionID;
    }

    public String getFStorageOrgUnitID() {
        return FStorageOrgUnitID;
    }

    public void setFStorageOrgUnitID(String FStorageOrgUnitID) {
        this.FStorageOrgUnitID = FStorageOrgUnitID;
    }

    public String getFWarehouseID() {
        return FWarehouseID;
    }

    public void setFWarehouseID(String FWarehouseID) {
        this.FWarehouseID = FWarehouseID;
    }

    public String getCFGSQYID() {
        return CFGSQYID;
    }

    public void setCFGSQYID(String CFGSQYID) {
        this.CFGSQYID = CFGSQYID;
    }
}
