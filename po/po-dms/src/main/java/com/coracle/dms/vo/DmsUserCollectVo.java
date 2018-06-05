package com.coracle.dms.vo;

import com.coracle.dms.po.DmsUserCollect;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
public class DmsUserCollectVo extends DmsUserCollect {
    @ApiModelProperty(value = "产品名称",hidden = true)
    private String productName;//产品名称
    @ApiModelProperty(value = "缩略图",hidden = true)
    private String imgUrl;//缩略图
    @ApiModelProperty(value = "价格",hidden = true)
    private BigDecimal suggestPrice;//价格
    @ApiModelProperty(value = "原价",hidden = true)
    private BigDecimal showPrice;//价格
    @ApiModelProperty(value = "促销可用数量",hidden = true)
    private Integer promotionCount;
    @ApiModelProperty(value = "促销价格", hidden = true)
    private BigDecimal promotionPrice;
    @ApiModelProperty(value = "库存数目",hidden = true)
    private Integer productNum;//库存数目
    @ApiModelProperty(value = "前端专用，用于规格修改时默认勾选",hidden = true)
    private String jsonObject;
    @ApiModelProperty(value = "有效状态",hidden = true)
    private Integer active;
    @ApiModelProperty(value = "渠道id", hidden = true)
    private Long channelId;
    @ApiModelProperty(value = "渠道编码")
    private String channelCode;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getSuggestPrice() {
        return suggestPrice;
    }

    public void setSuggestPrice(BigDecimal suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public BigDecimal getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
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

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }


    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
}
