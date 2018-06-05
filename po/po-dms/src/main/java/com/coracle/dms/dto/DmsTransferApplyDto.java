package com.coracle.dms.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import com.coracle.yk.base.po.AdapterEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

public class DmsTransferApplyDto extends AdapterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("注解ID")
    private Long id;
	@ApiModelProperty("申请单号")
    private String applyNo;
	@ApiModelProperty("调货类型（1-订货端铺货；2-门店之间调货）")
    private Integer applyType;
	@ApiModelProperty("入库产品id")
    private Long billProductId;
	@ApiModelProperty("入库产品规格id")
	private Long productSpecId;
	@ApiModelProperty("出货单位")
    private Long deliveryStoreId;
	@ApiModelProperty("收货单位")
    private Long receiveStoreId;
	@ApiModelProperty("申请数量")
    private Long applyNum;
	@ApiModelProperty("申请日期")
    private Date applyDate;
	@ApiModelProperty("处理状态（1-已处理；0-未处理）")
    private Integer processStatus;
	@ApiModelProperty("我发起的-发货状态（1-等待对方发货；2-待收货；3-已完成）")
    private Integer deliveryStatus;
	@ApiModelProperty("我收到的-收货状态（1-待发货；2-已发货；3-已完成）")
    private Integer receiveStatus;
	@ApiModelProperty("备注")
    private String remark;
	@ApiModelProperty("创建日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createdDate;
	@ApiModelProperty("创建人ID")
	private Long createdBy;
	@ApiModelProperty("最后更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date lastUpdatedDate;
	@ApiModelProperty("最后更新人ID")
	private Long lastUpdatedBy;
	@ApiModelProperty("软删除标识")
	private Integer removeFlag;
	@ApiModelProperty("仓库货位id")
	private Long storageLocalId;
	@ApiModelProperty("调货申请ID")
    private Long applyId;
	@ApiModelProperty("实发数量")
    private Integer deliveryNum;
	@ApiModelProperty("配送方式")
    private String deliveryType;
	@ApiModelProperty("收货人")
    private String receiver;
	@ApiModelProperty("收货人手机")
    private String mobile;
	@ApiModelProperty("省份")
    private String province;
	@ApiModelProperty("城市")
    private String city;
	@ApiModelProperty("区县")
    private String county;
	@ApiModelProperty("详细地址")
    private String address;
	@ApiModelProperty("物流公司")
    private String expressCompany;
	@ApiModelProperty("物流单号")
    private String expressNo;
	@ApiModelProperty("配送备注")
    private String deliveryRemark;
	@ApiModelProperty("发货日期")
	private Date deliveryDate;
	
	@ApiModelProperty(value = "省份文本", hidden = true)
	private String provinceText;
	@ApiModelProperty(value = "城市文本", hidden = true)
	private String cityText;
	@ApiModelProperty(value = "区县文本", hidden = true)
	private String countyText;
	@ApiModelProperty(value = "出货单位文本", hidden = true)
	private String deliveryStoreText;
	@ApiModelProperty(value = "收货单位文本", hidden = true)
	private String receiveStoreText;
	@ApiModelProperty(value = "产品名称文本", hidden = true)
	private String productText;
	@ApiModelProperty(value = "产品图片路径文本", hidden = true)
	private String filePreviewPath;
	@ApiModelProperty(value ="我申请的-发货状态文本", hidden = true)
	private String deliveryStatusText;
	@ApiModelProperty(value ="我收到的-收货状态文本", hidden = true)
    private String receiveStatusText;
	@ApiModelProperty(value ="规格文本", hidden = true)
	private String productSpecText;
	@ApiModelProperty(value = "开始时间（查询使用）",hidden = true)
    private String createTimeStart;
    @ApiModelProperty(value = "结束时间（查询使用）",hidden = true)
    private String createTimeEnd;

    private Long channelId;
    @ApiModelProperty(value = "门店组织id（查询使用）",hidden = true)
    private Long storeId; 
    @ApiModelProperty(value ="现有量", hidden = true)
    private Integer inventory;


    
    public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	private Integer deliveryStoreType;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public Integer getApplyType() {
		return applyType;
	}
	public void setApplyType(Integer applyType) {
		this.applyType = applyType;
	}
	public Long getBillProductId() {
		return billProductId;
	}
	public void setBillProductId(Long billProductId) {
		this.billProductId = billProductId;
	}
	public Long getDeliveryStoreId() {
		return deliveryStoreId;
	}
	public void setDeliveryStoreId(Long deliveryStoreId) {
		this.deliveryStoreId = deliveryStoreId;
	}
	public Long getReceiveStoreId() {
		return receiveStoreId;
	}
	public void setReceiveStoreId(Long receiveStoreId) {
		this.receiveStoreId = receiveStoreId;
	}
	public Long getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(Long applyNum) {
		this.applyNum = applyNum;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Integer getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}
	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public Integer getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(Integer receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Long getApplyId() {
		return applyId;
	}
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	public Integer getDeliveryNum() {
		return deliveryNum;
	}
	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getDeliveryRemark() {
		return deliveryRemark;
	}
	public void setDeliveryRemark(String deliveryRemark) {
		this.deliveryRemark = deliveryRemark;
	}
	public Long getProductSpecId() {
		return productSpecId;
	}
	public void setProductSpecId(Long productSpecId) {
		this.productSpecId = productSpecId;
	}
	public String getProvinceText() {
		return provinceText;
	}
	public void setProvinceText(String provinceText) {
		this.provinceText = provinceText;
	}
	public String getCityText() {
		return cityText;
	}
	public void setCityText(String cityText) {
		this.cityText = cityText;
	}
	public String getCountyText() {
		return countyText;
	}
	public void setCountyText(String countyText) {
		this.countyText = countyText;
	}
	public String getDeliveryStoreText() {
		return deliveryStoreText;
	}
	public void setDeliveryStoreText(String deliveryStoreText) {
		this.deliveryStoreText = deliveryStoreText;
	}
	public String getReceiveStoreText() {
		return receiveStoreText;
	}
	public void setReceiveStoreText(String receiveStoreText) {
		this.receiveStoreText = receiveStoreText;
	}
	public String getProductText() {
		return productText;
	}
	public void setProductText(String productText) {
		this.productText = productText;
	}
	public String getFilePreviewPath() {
		return filePreviewPath;
	}
	public void setFilePreviewPath(String filePreviewPath) {
		this.filePreviewPath = filePreviewPath;
	}
	public String getDeliveryStatusText() {
		return deliveryStatusText;
	}
	public void setDeliveryStatusText(String deliveryStatusText) {
		this.deliveryStatusText = deliveryStatusText;
	}
	public String getReceiveStatusText() {
		return receiveStatusText;
	}
	public void setReceiveStatusText(String receiveStatusText) {
		this.receiveStatusText = receiveStatusText;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Long getStorageLocalId() {
		return storageLocalId;
	}
	public void setStorageLocalId(Long storageLocalId) {
		this.storageLocalId = storageLocalId;
	}
	public String getProductSpecText() {
		return productSpecText;
	}
	public void setProductSpecText(String productSpecText) {
		this.productSpecText = productSpecText;
	}
	public Integer getDeliveryStoreType() {
		return deliveryStoreType;
	}
	public void setDeliveryStoreType(Integer deliveryStoreType) {
		this.deliveryStoreType = deliveryStoreType;
	}
	public Integer getInventory() {
		return inventory;
	}
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	
	

}
