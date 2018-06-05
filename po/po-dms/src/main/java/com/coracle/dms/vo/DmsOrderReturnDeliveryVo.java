package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import com.coracle.dms.po.DmsOrderReturnDelivery;

public class DmsOrderReturnDeliveryVo extends DmsOrderReturnDelivery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "配送方式文本",hidden = true)
	private String deliverWayText;
	@ApiModelProperty(value = "物流公司文本",hidden = true)
	private String expressCompanyText;
	
	public String getDeliverWayText() {
		return deliverWayText;
	}
	public void setDeliverWayText(String deliverWayText) {
		this.deliverWayText = deliverWayText;
	}
	public String getExpressCompanyText() {
		return expressCompanyText;
	}
	public void setExpressCompanyText(String expressCompanyText) {
		this.expressCompanyText = expressCompanyText;
	}
	
	
}
