package com.coracle.dms.vo;

import com.coracle.dms.po.DmsOrderProduct;
import com.coracle.yk.base.vo.TreeNode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DmsOrderProductVo extends DmsOrderProduct {

    @ApiModelProperty("待发数量")
    private Integer undeliveredCount;

    @ApiModelProperty("缩略图url")
    private String picUrl;

    @ApiModelProperty("已发货数量")
    private Integer deliveredCount;

    @ApiModelProperty("已收货数量")
    private Integer receivedCount;

    @ApiModelProperty("评价数量")
    private Integer evaluationCount;

    @ApiModelProperty("可退货数量")
    private Integer availableCount;

    @ApiModelProperty("仓库列表")
    private List<TreeNode> storageList;

    @ApiModelProperty("订单产品的促销数量")
    private Integer promotionCount;

    @ApiModelProperty("渠道id")
    private Long channelId;

    @ApiModelProperty("物流公司")
    private String logisticsCompany;

    @ApiModelProperty("物流单号")
    private String logisticsCode;

    @ApiModelProperty("订单id")
    private Long orderId;

    public Integer getUndeliveredCount() {
        return undeliveredCount;
    }

    public void setUndeliveredCount(Integer undeliveredCount) {
        this.undeliveredCount = undeliveredCount;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    public Integer getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }

    public List<TreeNode> getStorageList() {
        return storageList;
    }

    public void setStorageList(List<TreeNode> storageList) {
        this.storageList = storageList;
    }

    public Integer getPromotionCount() {
        return promotionCount;
    }

    public void setPromotionCount(Integer promotionCount) {
        this.promotionCount = promotionCount;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    @Override
    public Long getOrderId() {
        return orderId;
    }

    @Override
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}
