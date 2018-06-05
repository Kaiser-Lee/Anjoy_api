/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("订单商品清单实体类")
public class DmsOrderProduct extends AdapterEntity implements Serializable {
    private Long id;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("产品编码")
    private String productCode;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品规格id")
    private Long productSpecId;

    @ApiModelProperty("产品规格")
    private String specUnionKey;

    @ApiModelProperty("购物车id")
    private Long shoppingcartId;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("单价(原价)")
    private BigDecimal originalPrice;

    @ApiModelProperty("单价(终价)")
    private BigDecimal price;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("退换货数量")
    private Integer returnCount;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("产品促销活动信息")
    private Long promotionProductId;

    @ApiModelProperty("促销活动主题")
    private String promotionSubject;

    @ApiModelProperty("阳光单价")
    private BigDecimal yankonPrice;

    @ApiModelProperty("重量")
    private BigDecimal weight;

    @ApiModelProperty("体积")
    private BigDecimal volume;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdDate;

    private Long createdBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastUpdatedDate;

    private Long lastUpdatedBy;

    private Integer removeFlag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Long getProductSpecId() {
        return productSpecId;
    }

    public void setProductSpecId(Long productSpecId) {
        this.productSpecId = productSpecId;
    }

    public String getSpecUnionKey() {
        return specUnionKey;
    }

    public void setSpecUnionKey(String specUnionKey) {
        this.specUnionKey = specUnionKey == null ? null : specUnionKey.trim();
    }

    public Long getShoppingcartId() {
        return shoppingcartId;
    }

    public void setShoppingcartId(Long shoppingcartId) {
        this.shoppingcartId = shoppingcartId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPromotionProductId() {
        return promotionProductId;
    }

    public void setPromotionProductId(Long promotionProductId) {
        this.promotionProductId = promotionProductId;
    }

    public String getPromotionSubject() {
        return promotionSubject;
    }

    public void setPromotionSubject(String promotionSubject) {
        this.promotionSubject = promotionSubject;
    }

    public BigDecimal getYankonPrice() {
        return yankonPrice;
    }

    public void setYankonPrice(BigDecimal yankonPrice) {
        this.yankonPrice = yankonPrice;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}