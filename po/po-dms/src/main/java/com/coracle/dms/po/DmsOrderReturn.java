/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.po;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.coracle.yk.base.po.AdapterEntity;

public class DmsOrderReturn extends AdapterEntity implements Serializable {
	@ApiModelProperty("主键ID")
    private Long id;
	@ApiModelProperty("用户ID")
    private Long userId;
	@ApiModelProperty("订单类型（1-退换；2-换货）")
    private Integer orderType;
	@ApiModelProperty("订单号")
    private String code;
	@ApiModelProperty("订单总额，不包括优惠")
    private BigDecimal amount;
	@ApiModelProperty("客户名称")
    private String customerName;
	@ApiModelProperty("联系人")
    private String contacts;
	@ApiModelProperty("联系手机")
    private String phone;
	@ApiModelProperty("所属区域")
    private Integer areaId;
	@ApiModelProperty("申请日期")
    private Date applyDate;
	@ApiModelProperty("订货端状态（1-待审核；2-待退货；3-退货中（待厂商收货）；4-待发货；5-待收货；6-已完成；7-交易关闭）")
    private Integer buyerStatus;
	@ApiModelProperty("品牌商状态（1-待审核；2-待客户发货；3-待收货；4-待发货；5-待客户收货；6-已完成）")
    private Integer sellerStatus;
	@ApiModelProperty("审核状态（0-待审核；1-已审核；2-拒绝）")
    private Integer auditStatus;
	@ApiModelProperty("审核人")
    private Long auditBy;
	@ApiModelProperty("审核时间")
    private Date auditDate;
	@ApiModelProperty("备注")
    private String remark;
	@ApiModelProperty("创建日期")
//	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createdDate;
	@ApiModelProperty("创建人ID")
	private Long createdBy;
	@ApiModelProperty("最后更新时间")
//	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date lastUpdatedDate;
	@ApiModelProperty("最后更新人ID")
	private Long lastUpdatedBy;
	@ApiModelProperty("软删除标识")
	private Integer removeFlag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getBuyerStatus() {
        return buyerStatus;
    }

    public void setBuyerStatus(Integer buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    public Integer getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(Integer sellerStatus) {
        this.sellerStatus = sellerStatus;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Long getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(Long auditBy) {
        this.auditBy = auditBy;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}