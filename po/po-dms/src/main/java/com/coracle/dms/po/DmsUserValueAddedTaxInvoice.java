/**
 * create by Administrator
 * @date 2017-09
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@ApiModel(description = "DMS增值税发票信息表")
public class DmsUserValueAddedTaxInvoice extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("企业名称")
    private String companyName;
    @ApiModelProperty("纳税人识别码")
    private String taxpayerCode;
    @ApiModelProperty("注册地址")
    private String address;
    @ApiModelProperty("省")
    private Long province;
    @ApiModelProperty("市")
    private Long city;
    @ApiModelProperty("区县")
    private Long county;
    @ApiModelProperty("注册电话")
    private String phone;
    @ApiModelProperty("开户银行")
    private String bankName;
    @ApiModelProperty("开户银行账号")
    private String bankAccount;
    @ApiModelProperty("增值委托书（附件ID）")
    private Long powerOfAttorney;
    @ApiModelProperty("审批状态（0新建，1待审批，2已通过，3已拒绝）")
    private Integer status;
    @ApiModelProperty("审核用户ID")
    private Long approveUserId;
    @ApiModelProperty("审核通过时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date approveDate;
    @ApiModelProperty("审核不通过时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date rejectDate;
    @ApiModelProperty("审核意见")
    private String auditOpinion;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建日期")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdDate;
	@ApiModelProperty("创建人ID")
	private Long createdBy;
	@ApiModelProperty("最后更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastUpdatedDate;
	@ApiModelProperty("最后更新人ID")
	private Long lastUpdatedBy;
	@ApiModelProperty("软删除标识")
	private Integer removeFlag;

    private String expand1;

    private String expand2;

    private String expand3;

    private String expand4;

    private String expand5;

    private String expand6;

    private String expand7;

    private String expand8;

    private String expand9;

    private String expand10;

    private String expand11;

    private String expand12;

    private String expand13;

    private String expand14;

    private String expand15;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getTaxpayerCode() {
        return taxpayerCode;
    }

    public void setTaxpayerCode(String taxpayerCode) {
        this.taxpayerCode = taxpayerCode == null ? null : taxpayerCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getCounty() {
        return county;
    }

    public void setCounty(Long county) {
        this.county = county;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public Long getPowerOfAttorney() {
        return powerOfAttorney;
    }

    public void setPowerOfAttorney(Long powerOfAttorney) {
        this.powerOfAttorney = powerOfAttorney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Date getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(Date rejectDate) {
        this.rejectDate = rejectDate;
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

    public String getExpand1() {
        return expand1;
    }

    public void setExpand1(String expand1) {
        this.expand1 = expand1 == null ? null : expand1.trim();
    }

    public String getExpand2() {
        return expand2;
    }

    public void setExpand2(String expand2) {
        this.expand2 = expand2 == null ? null : expand2.trim();
    }

    public String getExpand3() {
        return expand3;
    }

    public void setExpand3(String expand3) {
        this.expand3 = expand3 == null ? null : expand3.trim();
    }

    public String getExpand4() {
        return expand4;
    }

    public void setExpand4(String expand4) {
        this.expand4 = expand4 == null ? null : expand4.trim();
    }

    public String getExpand5() {
        return expand5;
    }

    public void setExpand5(String expand5) {
        this.expand5 = expand5 == null ? null : expand5.trim();
    }

    public String getExpand6() {
        return expand6;
    }

    public void setExpand6(String expand6) {
        this.expand6 = expand6 == null ? null : expand6.trim();
    }

    public String getExpand7() {
        return expand7;
    }

    public void setExpand7(String expand7) {
        this.expand7 = expand7 == null ? null : expand7.trim();
    }

    public String getExpand8() {
        return expand8;
    }

    public void setExpand8(String expand8) {
        this.expand8 = expand8 == null ? null : expand8.trim();
    }

    public String getExpand9() {
        return expand9;
    }

    public void setExpand9(String expand9) {
        this.expand9 = expand9 == null ? null : expand9.trim();
    }

    public String getExpand10() {
        return expand10;
    }

    public void setExpand10(String expand10) {
        this.expand10 = expand10 == null ? null : expand10.trim();
    }

    public String getExpand11() {
        return expand11;
    }

    public void setExpand11(String expand11) {
        this.expand11 = expand11 == null ? null : expand11.trim();
    }

    public String getExpand12() {
        return expand12;
    }

    public void setExpand12(String expand12) {
        this.expand12 = expand12 == null ? null : expand12.trim();
    }

    public String getExpand13() {
        return expand13;
    }

    public void setExpand13(String expand13) {
        this.expand13 = expand13 == null ? null : expand13.trim();
    }

    public String getExpand14() {
        return expand14;
    }

    public void setExpand14(String expand14) {
        this.expand14 = expand14 == null ? null : expand14.trim();
    }

    public String getExpand15() {
        return expand15;
    }

    public void setExpand15(String expand15) {
        this.expand15 = expand15 == null ? null : expand15.trim();
    }

    public Long getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(Long approveUserId) {
        this.approveUserId = approveUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }
}