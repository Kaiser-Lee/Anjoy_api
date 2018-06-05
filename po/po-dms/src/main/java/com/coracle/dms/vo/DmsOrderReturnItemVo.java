package com.coracle.dms.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import com.coracle.dms.po.DmsOrderReturnItem;
import com.coracle.yk.base.vo.TreeNode;

public class DmsOrderReturnItemVo extends DmsOrderReturnItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "图片路径", hidden = true)
	private String picUrl;
	@ApiModelProperty(value = "待发数量", hidden = true)
	private Integer undeliveredCount;
	@ApiModelProperty(value = "已发货数量", hidden = true)
	private Integer deliveredCount;
	@ApiModelProperty(value = "已收货数量", hidden = true)
	private Integer receivedCount;
	@ApiModelProperty(value = "评价数量", hidden = true)
	private Integer evaluationCount;
	@ApiModelProperty(value = "仓库列表", hidden = true)
	private List<TreeNode> storageList;
	@ApiModelProperty(value = "旧订单编号", hidden = true)
	private String oldOrderCode;
	private Integer totalReturnNum;
	private Integer totalDeliveryNum;
	@ApiModelProperty("发货仓库id")
    private Long storageId;
    @ApiModelProperty("发货货位id")
    private Long storageLocalId;
	
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getUndeliveredCount() {
		return undeliveredCount;
	}

	public void setUndeliveredCount(Integer undeliveredCount) {
		this.undeliveredCount = undeliveredCount;
	}

	public Integer getDeliveredCount() {
		return deliveredCount;
	}

	public void setDeliveredCount(Integer deliveredCount) {
		this.deliveredCount = deliveredCount;
	}

	public Integer getReceivedCount() {
		return receivedCount;
	}

	public void setReceivedCount(Integer receivedCount) {
		this.receivedCount = receivedCount;
	}

	public Integer getEvaluationCount() {
		return evaluationCount;
	}

	public void setEvaluationCount(Integer evaluationCount) {
		this.evaluationCount = evaluationCount;
	}

	public List<TreeNode> getStorageList() {
		return storageList;
	}

	public void setStorageList(List<TreeNode> storageList) {
		this.storageList = storageList;
	}

	public String getOldOrderCode() {
		return oldOrderCode;
	}

	public void setOldOrderCode(String oldOrderCode) {
		this.oldOrderCode = oldOrderCode;
	}

	public Integer getTotalReturnNum() {
		return totalReturnNum;
	}

	public void setTotalReturnNum(Integer totalReturnNum) {
		this.totalReturnNum = totalReturnNum;
	}

	public Integer getTotalDeliveryNum() {
		return totalDeliveryNum;
	}

	public void setTotalDeliveryNum(Integer totalDeliveryNum) {
		this.totalDeliveryNum = totalDeliveryNum;
	}

	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}

	public Long getStorageLocalId() {
		return storageLocalId;
	}

	public void setStorageLocalId(Long storageLocalId) {
		this.storageLocalId = storageLocalId;
	}

	
}
