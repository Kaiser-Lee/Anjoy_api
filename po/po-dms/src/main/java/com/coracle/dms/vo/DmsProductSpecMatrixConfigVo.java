package com.coracle.dms.vo;

import com.coracle.dms.po.DmsProductSpecMatrixConfig;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class DmsProductSpecMatrixConfigVo extends DmsProductSpecMatrixConfig{
    @ApiModelProperty("库存量")
    private Integer inventory;

    @ApiModelProperty("1渠道2门店")
    private String type;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("促销可用数量")
    private Integer promotionCount;

    @ApiModelProperty("促销价")
    private BigDecimal promotionPrice;

    @ApiModelProperty("当前登录订货端用户所属的渠道商id")
    private Long channelId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getPromotionCount() {
        return promotionCount;
    }

    public void setPromotionCount(Integer promotionCount) {
        this.promotionCount = promotionCount;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}
