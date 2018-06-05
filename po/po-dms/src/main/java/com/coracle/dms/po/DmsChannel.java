/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.dms.enums.PlatformEnum;
import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DmsChannel extends AdapterEntity implements Serializable {
	
	@ApiModelProperty("主键ID")
    private Long id;
	@ApiModelProperty("渠道名称")
    private String name;
	@ApiModelProperty("渠道编号")
    private String code;
	@ApiModelProperty("渠道简称")
    private String shortName;
	@ApiModelProperty("上级渠道")
    private Long parentId;
	@ApiModelProperty("等级.大客户、中客户、小客户")
    private String rank;
	@ApiModelProperty("渠道类型.销售公司、代理商、经销商、分销商")
    private String channelType;
	@ApiModelProperty("联系人")
    private String contacts;
	@ApiModelProperty("联系人手机")
    private String contactsMobile;
	@ApiModelProperty("省份")
    private String province;
	@ApiModelProperty("城市")
    private String city;
	@ApiModelProperty("区县")
    private String county;
	@ApiModelProperty("详细地址")
    private String address;
	@ApiModelProperty("归属区域")
    private Long areaId;
    @ApiModelProperty("所属业务员")
    private Long employeeId;
	@ApiModelProperty("公司电话")
    private String compnyPhone;
	@ApiModelProperty("公司传真")
    private String companyFax;
	@ApiModelProperty("公司员工数量")
    private Long employeeNum;
	@ApiModelProperty("公司网站")
    private String companyWebsite;
	@ApiModelProperty("CEO")
    private String companyCeo;
	@ApiModelProperty("公司年收入")
    private BigDecimal companyIncome;
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
	@ApiModelProperty("免邮金额（阳光）")
    private BigDecimal postageFreePrice;
    @ApiModelProperty("eas编码（安井）")
    private String easNum;
    @ApiModelProperty("物流电话（安井）")
    private String logisticsNum;
    @ApiModelProperty("是否开启（安井）")
    private Integer active;
    @ApiModelProperty("信用额度（安井）")
    private Integer creditLimit;
    @ApiModelProperty("账期（安井）")
    private Long accountLimit;
    @ApiModelProperty("发票类型（安井）")
    private String invoiceType;
    @ApiModelProperty("发票抬头（安井）")
    private String invoiceHead;
    @ApiModelProperty("税号（安井）")
    private String taxNum;
    @ApiModelProperty("银行（安井）")
    private String bank;
    @ApiModelProperty("银行账号（安井）")
    private String bankNum;
    @ApiModelProperty("起订量")
    private Long minOrderQuantity;
    @ApiModelProperty("安井id")
    private String anjoyId;
    @ApiModelProperty("安井父id")
    private String anjoyParentId;
    @ApiModelProperty("安井归属区域id")
    private String anjoyCfbibscidId;
    @ApiModelProperty("安井销售区域id")
    private String anjoySaleOrgId;
    /** 平台类型 */
    private PlatformEnum platformEnum;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile == null ? null : contactsMobile.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getCompnyPhone() {
        return compnyPhone;
    }

    public void setCompnyPhone(String compnyPhone) {
        this.compnyPhone = compnyPhone == null ? null : compnyPhone.trim();
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax == null ? null : companyFax.trim();
    }

    public Long getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Long employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite == null ? null : companyWebsite.trim();
    }

    public String getCompanyCeo() {
        return companyCeo;
    }

    public void setCompanyCeo(String companyCeo) {
        this.companyCeo = companyCeo == null ? null : companyCeo.trim();
    }

    public BigDecimal getCompanyIncome() {
        return companyIncome;
    }

    public void setCompanyIncome(BigDecimal companyIncome) {
        this.companyIncome = companyIncome;
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

    public BigDecimal getPostageFreePrice() {
        return postageFreePrice;
    }

    public void setPostageFreePrice(BigDecimal postageFreePrice) {
        this.postageFreePrice = postageFreePrice;
    }

    public String getEasNum() {
        return easNum;
    }

    public void setEasNum(String easNum) {
        this.easNum = easNum == null ? null : easNum.trim();
    }

    public String getLogisticsNum() {
        return logisticsNum;
    }

    public void setLogisticsNum(String logisticsNum) {
        this.logisticsNum = logisticsNum == null ? null : logisticsNum.trim();
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Long getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(Long accountLimit) {
        this.accountLimit = accountLimit;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead == null ? null : invoiceHead.trim();
    }

    public String getTaxNum() {
        return taxNum;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum == null ? null : taxNum.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum == null ? null : bankNum.trim();
    }

    public Long getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(Long minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    public String getAnjoyId() {
        return anjoyId;
    }

    public void setAnjoyId(String anjoyId) {
        this.anjoyId = anjoyId;
    }

    public String getAnjoyParentId() {
        return anjoyParentId;
    }

    public void setAnjoyParentId(String anjoyParentId) {
        this.anjoyParentId = anjoyParentId;
    }

    public PlatformEnum getPlatformEnum() {
        return platformEnum;
    }

    public void setPlatformEnum(PlatformEnum platformEnum) {
        this.platformEnum = platformEnum;
    }

    public String getAnjoyCfbibscidId() {
        return anjoyCfbibscidId;
    }

    public void setAnjoyCfbibscidId(String anjoyCfbibscidId) {
        this.anjoyCfbibscidId = anjoyCfbibscidId;
    }

    public String getAnjoySaleOrgId() {
        return anjoySaleOrgId;
    }

    public void setAnjoySaleOrgId(String anjoySaleOrgId) {
        this.anjoySaleOrgId = anjoySaleOrgId;
    }
}