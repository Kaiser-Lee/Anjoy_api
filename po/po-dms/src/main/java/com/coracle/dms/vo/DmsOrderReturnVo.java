package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.coracle.dms.po.DmsOrderReturn;

public class DmsOrderReturnVo extends DmsOrderReturn {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("退换货配送对象")
	private DmsOrderReturnDeliveryVo returnDelivery;
	@ApiModelProperty("渠道配送对象")
	private DmsOrderReturnDeliveryVo returnChannelDelivery;
	@ApiModelProperty("退换记录产品列表")
	private List<DmsOrderReturnItemVo> returnItemList;
	@ApiModelProperty("待发货产品列表")
	private List<DmsOrderReturnItemVo> undeliveredList;
	@ApiModelProperty("已发货产品列表")
	private List<DmsOrderDeliveryItemVo> deliveredList;
	@ApiModelProperty("退换货购物车ID集合")
	private List<Long> returnCartIdList;
	@ApiModelProperty(value = "退换货类型文本", hidden = true)
	private String orderTypeText;
	@ApiModelProperty(value = "订货端状态文本", hidden = true)
	private String buyerStatusText;
	@ApiModelProperty(value = "品牌商状态文本", hidden = true)
	private String sellerStatusText;
	@ApiModelProperty(value = "所属区域文本", hidden = true)
	private String areaText;
	@ApiModelProperty(value = "开始时间（查询使用）", hidden = true)
	private String createTimeStart;
	@ApiModelProperty(value = "结束时间（查询使用）", hidden = true)
	private String createTimeEnd;
	@ApiModelProperty(value = "关键字查询（目前产品名称）", hidden = true)
	private String kw;
	
	public DmsOrderReturnDeliveryVo getReturnDelivery() {
		return returnDelivery;
	}
	public void setReturnDelivery(DmsOrderReturnDeliveryVo returnDelivery) {
		this.returnDelivery = returnDelivery;
	}
	public DmsOrderReturnDeliveryVo getReturnChannelDelivery() {
		return returnChannelDelivery;
	}
	public void setReturnChannelDelivery(DmsOrderReturnDeliveryVo returnChannelDelivery) {
		this.returnChannelDelivery = returnChannelDelivery;
	}
	public List<DmsOrderReturnItemVo> getReturnItemList() {
		return returnItemList;
	}
	public void setReturnItemList(List<DmsOrderReturnItemVo> returnItemList) {
		this.returnItemList = returnItemList;
	}
	public List<DmsOrderReturnItemVo> getUndeliveredList() {
		return undeliveredList;
	}
	public void setUndeliveredList(List<DmsOrderReturnItemVo> undeliveredList) {
		this.undeliveredList = undeliveredList;
	}
	public List<DmsOrderDeliveryItemVo> getDeliveredList() {
		return deliveredList;
	}
	public void setDeliveredList(List<DmsOrderDeliveryItemVo> deliveredList) {
		this.deliveredList = deliveredList;
	}
	public List<Long> getReturnCartIdList() {
		return returnCartIdList;
	}
	public void setReturnCartIdList(List<Long> returnCartIdList) {
		this.returnCartIdList = returnCartIdList;
	}
	public String getOrderTypeText() {
		return orderTypeText;
	}
	public void setOrderTypeText(String orderTypeText) {
		this.orderTypeText = orderTypeText;
	}
	public String getBuyerStatusText() {
		return buyerStatusText;
	}
	public void setBuyerStatusText(String buyerStatusText) {
		this.buyerStatusText = buyerStatusText;
	}
	public String getSellerStatusText() {
		return sellerStatusText;
	}
	public void setSellerStatusText(String sellerStatusText) {
		this.sellerStatusText = sellerStatusText;
	}
	public String getAreaText() {
		return areaText;
	}
	public void setAreaText(String areaText) {
		this.areaText = areaText;
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
	public String getKw() {
		return kw;
	}
	public void setKw(String kw) {
		this.kw = kw;
	}
    
	
	
	

}
