/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("订单实体类")
public class DmsOrder extends AdapterEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("订单编号")
    private String code;

    @ApiModelProperty("订单总额")
    private BigDecimal amount;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("期望交货日期")
    private Date expectDeliveryDate;

    @ApiModelProperty("所属区域id")
    private Long areaId;

    @ApiModelProperty("所属区域")
    private String area;

    @ApiModelProperty("订单状态：1-待审核；2-待发货(待厂商发货)；3-待客户收获(待收货)；4-已完成；5-已取消。 注：标注的是厂商端的状态，括号中是订货端的状态")
    private Integer orderStatus;

    @ApiModelProperty("收款状态：1-未付款；2-待确认收款；3-部分到账；4-已收款。")
    private Integer payStatus;

    @ApiModelProperty("增值税发票id，关联表dms_user_value_added_tax_invoice（如果是普通发票类型，则该字段为null）")
    private Long invoiceId;

    @ApiModelProperty("发票信息：发票类型：1-普通发票；2-增值税发票")
    private Integer invoiceType;

    @ApiModelProperty("发票信息：发票内容")
    private String invoiceContent;

    @ApiModelProperty("发票信息：发票抬头")
    private String invoiceTitle;

    @ApiModelProperty("发票信息：纳税人识别号")
    private String invoiceTaxpayerCode;

    @ApiModelProperty("发票信息：注册地址")
    private String invoiceAddress;

    @ApiModelProperty("发票信息：电话")
    private String invoicePhone;

    @ApiModelProperty("发票信息：开户行")
    private String invoiceBank;

    @ApiModelProperty("发票信息：账号")
    private String invoiceAccount;

    @ApiModelProperty("收获地址id，关联表dms_user_address")
    private Long addressId;

    @ApiModelProperty("收货人信息：姓名")
    private String receiverName;

    @ApiModelProperty("收货人信息：手机")
    private String receiverMobile;

    @ApiModelProperty("收货人信息：收货地址")
    private String receiverAddress;

    @ApiModelProperty("发运信息：配送方式")
    private String deliverType;

    @ApiModelProperty("发运信息：备注留言")
    private String remark;

    @ApiModelProperty("运费")
    private BigDecimal freight;

    @ApiModelProperty("创建订单的账号id，关联表dms_user")
    private Long userId;

    @ApiModelProperty("审核人id")
    private Long auditor;

    @ApiModelProperty("审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditDate;

    @ApiModelProperty("审核意见：0-拒绝；1-同意")
    private Integer auditOpinion;

    @ApiModelProperty("审核备注")
    private String auditRemark;

    private Date createdDate;

    private Long createdBy;

    private Date lastUpdatedDate;

    private Long lastUpdatedBy;

    @ApiModelProperty("订单完成时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date finishedDate;

    private Integer removeFlag;

    @ApiModelProperty("订单来源：1-APP下单")
    private Integer source;

    @ApiModelProperty("订单类型：1-常规订单；2-预订单；3-特价订单")
    private Integer type;

    @ApiModelProperty("订单编码，这里指的是EAS订货单编码")
    private String thirdOrderNo;

    private Long channelId;

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getExpectDeliveryDate() {
        return expectDeliveryDate;
    }

    public void setExpectDeliveryDate(Date expectDeliveryDate) {
        this.expectDeliveryDate = expectDeliveryDate;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent == null ? null : invoiceContent.trim();
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    public String getInvoiceTaxpayerCode() {
        return invoiceTaxpayerCode;
    }

    public void setInvoiceTaxpayerCode(String invoiceTaxpayerCode) {
        this.invoiceTaxpayerCode = invoiceTaxpayerCode == null ? null : invoiceTaxpayerCode.trim();
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress == null ? null : invoiceAddress.trim();
    }

    public String getInvoicePhone() {
        return invoicePhone;
    }

    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone == null ? null : invoicePhone.trim();
    }

    public String getInvoiceBank() {
        return invoiceBank;
    }

    public void setInvoiceBank(String invoiceBank) {
        this.invoiceBank = invoiceBank == null ? null : invoiceBank.trim();
    }

    public String getInvoiceAccount() {
        return invoiceAccount;
    }

    public void setInvoiceAccount(String invoiceAccount) {
        this.invoiceAccount = invoiceAccount == null ? null : invoiceAccount.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType == null ? null : deliverType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAuditor() {
        return auditor;
    }

    public void setAuditor(Long auditor) {
        this.auditor = auditor;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Integer getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(Integer auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}