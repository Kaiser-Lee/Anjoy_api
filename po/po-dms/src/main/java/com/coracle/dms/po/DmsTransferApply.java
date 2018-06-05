/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class DmsTransferApply extends AdapterEntity implements Serializable {
	
	@ApiModelProperty("注解ID")
    private Long id;
	@ApiModelProperty("申请单号")
    private String applyNo;
	@ApiModelProperty("调货类型（1-订货端铺货；2-门店之间调货）")
    private Integer applyType;
	@ApiModelProperty("入库产品规格id")
	private Long productSpecId;
	@ApiModelProperty("入库产品id")
    private Long billProductId;
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

    private static final long serialVersionUID = 1L;

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
        this.applyNo = applyNo == null ? null : applyNo.trim();
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

	public Long getProductSpecId() {
		return productSpecId;
	}

	public void setProductSpecId(Long productSpecId) {
		this.productSpecId = productSpecId;
	}
    
}