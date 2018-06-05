package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import com.coracle.dms.po.DmsOrderReturnCart;

public class DmsOrderReturnCartVo extends DmsOrderReturnCart {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "产品名称文本", hidden = true)
	private String productText;
	@ApiModelProperty(value = "产品图片路径文本", hidden = true)
	private String picUrl;
	@ApiModelProperty(value = "原订单产品数量-控制退换数量选择", hidden = true)
	private Integer oldOrderNum;
	public String getProductText() {
		return productText;
	}
	public void setProductText(String productText) {
		this.productText = productText;
	}
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getOldOrderNum() {
		return oldOrderNum;
	}
	public void setOldOrderNum(Integer oldOrderNum) {
		this.oldOrderNum = oldOrderNum;
	}
	
	
}
