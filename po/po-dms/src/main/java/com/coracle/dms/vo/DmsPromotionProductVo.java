package com.coracle.dms.vo;

import com.coracle.dms.po.DmsPromotionProduct;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("促销适用产品VO")
public class DmsPromotionProductVo extends DmsPromotionProduct {

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品分类名称")
    private String productCategoryName;

    @ApiModelProperty("产品规格名称")
    private String productSpecName;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("产品标价")
    private BigDecimal showPrice;

    @ApiModelProperty("折扣后价格")
    private BigDecimal discountedPrice;

    @ApiModelProperty("促销活动主题")
    private String promotionSubject;

    @ApiModelProperty("促销活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("促销活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty("促销活动是否已结束：0-否；1-是")
    private Integer isFinished;

    @ApiModelProperty("当前登录的渠道商账号所属的渠道id")
    private Long channelId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductSpecName() {
        return productSpecName;
    }

    public void setProductSpecName(String productSpecName) {
        this.productSpecName = productSpecName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getPromotionSubject() {
        return promotionSubject;
    }

    public void setPromotionSubject(String promotionSubject) {
        this.promotionSubject = promotionSubject;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}
