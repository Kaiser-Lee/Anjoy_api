/**
 * create by apple
 * @date 2018-01
 */
package com.coracle.dms.po.tz;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("订单实体类")
public class TgOrder extends AdapterEntity implements Serializable {
    private Long id;

    @ApiModelProperty("订单编号")
    private String code;

    @ApiModelProperty("要货单号")
    private String getProCode;

    @ApiModelProperty("订单总额")
    private BigDecimal amount;

    @ApiModelProperty("客户名称(经销商)")
    private String customerName;

    @ApiModelProperty("客户编码(经销商)")
    private String customerCode;

    @ApiModelProperty("客户订单编码")
    private String customerOrderCode;

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("购货单位")
    private String buyCompany;

    @ApiModelProperty("渠道")
    private String channel;

    @ApiModelProperty("订单状态：1-待审核；2-待发货(待厂商发货)；3-待客户收获(待收货)；4-已完成；5-已取消。 注：标注的是厂商端的状态，括号中是订货端的状态")
    private Integer orderStatus;

    @ApiModelProperty("订单类型:1-常规订单;2-项目订单;3-合作伙伴订单")
    private Integer orderType;

    @ApiModelProperty("创建订单的账号id")
    private Long userId;

    @ApiModelProperty("创建日期")
    private Date createdDate;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("最后更新时间")
    private Date lastUpdatedDate;

    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;

    @ApiModelProperty("删除标识：0-未删除；1-已删除")
    private Integer removeFlag;

    @ApiModelProperty("订单审核通过时间")
    private Date finishedDate;

    @ApiModelProperty("收货地址(项目订单)")
    private String gatherAddress;

    @ApiModelProperty("收单地址(项目订单)")
    private String gatherOrderAddress;

    @ApiModelProperty("项目信息(含终端)")
    private String projectMessage;

    @ApiModelProperty("业务员")
    private String salesmanName;

    @ApiModelProperty("折扣率")
    private BigDecimal discountRate;

    @ApiModelProperty("保质金")
    private BigDecimal guaranteeQuality;

    @ApiModelProperty("可用余额")
    private BigDecimal remainingSum;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("订单反馈信息")
    private String orderBackMsg;

    @ApiModelProperty("授信方式(项目订单)")
    private String creditExtensionMode;

    @ApiModelProperty("合同/订单名称(项目订单)")
    private String contractOrderName;

    @ApiModelProperty("销售方(项目订单)")
    private String sellType;

    @ApiModelProperty("实际收货地址")
    private String gatherRealityAddress;

    @ApiModelProperty("项目合作(项目订单)")
    private String projectCooperation;

    @ApiModelProperty("结算对应号(项目订单)")
    private String closeHomologous;

    @ApiModelProperty("成套厂(项目订单)")
    private String completeFactory;

    @ApiModelProperty("特价单号(项目订单)")
    private String specialOfferCode;

    @ApiModelProperty("终端附加信息(项目订单)")
    private String projectAddMsg;

    @ApiModelProperty("附件ID(项目订单)")
    private Long fileId;

    @ApiModelProperty("收货省市(合作伙伴订单)")
    private String gatherCity;

    private static final long serialVersionUID = 1L;

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

    public String getGetProCode() {
        return getProCode;
    }

    public void setGetProCode(String getProCode) {
        this.getProCode = getProCode == null ? null : getProCode.trim();
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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getCustomerOrderCode() {
        return customerOrderCode;
    }

    public void setCustomerOrderCode(String customerOrderCode) {
        this.customerOrderCode = customerOrderCode == null ? null : customerOrderCode.trim();
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

    public String getBuyCompany() {
        return buyCompany;
    }

    public void setBuyCompany(String buyCompany) {
        this.buyCompany = buyCompany == null ? null : buyCompany.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getGatherAddress() {
        return gatherAddress;
    }

    public void setGatherAddress(String gatherAddress) {
        this.gatherAddress = gatherAddress == null ? null : gatherAddress.trim();
    }

    public String getGatherOrderAddress() {
        return gatherOrderAddress;
    }

    public void setGatherOrderAddress(String gatherOrderAddress) {
        this.gatherOrderAddress = gatherOrderAddress == null ? null : gatherOrderAddress.trim();
    }

    public String getProjectMessage() {
        return projectMessage;
    }

    public void setProjectMessage(String projectMessage) {
        this.projectMessage = projectMessage == null ? null : projectMessage.trim();
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName == null ? null : salesmanName.trim();
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getGuaranteeQuality() {
        return guaranteeQuality;
    }

    public void setGuaranteeQuality(BigDecimal guaranteeQuality) {
        this.guaranteeQuality = guaranteeQuality;
    }

    public BigDecimal getRemainingSum() {
        return remainingSum;
    }

    public void setRemainingSum(BigDecimal remainingSum) {
        this.remainingSum = remainingSum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOrderBackMsg() {
        return orderBackMsg;
    }

    public void setOrderBackMsg(String orderBackMsg) {
        this.orderBackMsg = orderBackMsg == null ? null : orderBackMsg.trim();
    }

    public String getCreditExtensionMode() {
        return creditExtensionMode;
    }

    public void setCreditExtensionMode(String creditExtensionMode) {
        this.creditExtensionMode = creditExtensionMode == null ? null : creditExtensionMode.trim();
    }

    public String getContractOrderName() {
        return contractOrderName;
    }

    public void setContractOrderName(String contractOrderName) {
        this.contractOrderName = contractOrderName == null ? null : contractOrderName.trim();
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType == null ? null : sellType.trim();
    }

    public String getGatherRealityAddress() {
        return gatherRealityAddress;
    }

    public void setGatherRealityAddress(String gatherRealityAddress) {
        this.gatherRealityAddress = gatherRealityAddress == null ? null : gatherRealityAddress.trim();
    }

    public String getProjectCooperation() {
        return projectCooperation;
    }

    public void setProjectCooperation(String projectCooperation) {
        this.projectCooperation = projectCooperation == null ? null : projectCooperation.trim();
    }

    public String getCloseHomologous() {
        return closeHomologous;
    }

    public void setCloseHomologous(String closeHomologous) {
        this.closeHomologous = closeHomologous == null ? null : closeHomologous.trim();
    }

    public String getCompleteFactory() {
        return completeFactory;
    }

    public void setCompleteFactory(String completeFactory) {
        this.completeFactory = completeFactory == null ? null : completeFactory.trim();
    }

    public String getSpecialOfferCode() {
        return specialOfferCode;
    }

    public void setSpecialOfferCode(String specialOfferCode) {
        this.specialOfferCode = specialOfferCode == null ? null : specialOfferCode.trim();
    }

    public String getProjectAddMsg() {
        return projectAddMsg;
    }

    public void setProjectAddMsg(String projectAddMsg) {
        this.projectAddMsg = projectAddMsg == null ? null : projectAddMsg.trim();
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getGatherCity() {
        return gatherCity;
    }

    public void setGatherCity(String gatherCity) {
        this.gatherCity = gatherCity == null ? null : gatherCity.trim();
    }
}