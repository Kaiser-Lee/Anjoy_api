/**
 * create by hcs
 * @date 2017-10
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("促销适用产品表")
public class DmsPromotionProduct extends AdapterEntity implements Serializable {
    private Long id;

    @ApiModelProperty("促销活动id")
    private Long promotionId;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("产品分类id")
    private Long productCategoryId;

    @ApiModelProperty("产品规格id")
    private Long productSpecId;

    @ApiModelProperty("折扣")
    private BigDecimal discount;

    @ApiModelProperty("总量限制")
    private Integer quantityLimit;

    @ApiModelProperty("每人限制")
    private Integer personalLimit;

    @ApiModelProperty("已售数量")
    private Integer soldQuantity;

    private Date createdDate;

    private Long createdBy;

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

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getProductSpecId() {
        return productSpecId;
    }

    public void setProductSpecId(Long productSpecId) {
        this.productSpecId = productSpecId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getQuantityLimit() {
        return quantityLimit;
    }

    public void setQuantityLimit(Integer quantityLimit) {
        this.quantityLimit = quantityLimit;
    }

    public Integer getPersonalLimit() {
        return personalLimit;
    }

    public void setPersonalLimit(Integer personalLimit) {
        this.personalLimit = personalLimit;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
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
}