/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@ApiModel(description = "阶梯价格")
public class DmsLadderPrice extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("阶梯价格项id")
    private Long ladderpriceporductId;

    @ApiModelProperty("产品id")
    private Long productId;
    @ApiModelProperty("产品分类id")
    private Long categoryId;
    @ApiModelProperty("数量始")
    private Long startCount;
    @ApiModelProperty("数量至")
    private Long endCount;
    @ApiModelProperty("折扣类型")
    private String discountCategory;
    @ApiModelProperty("折扣")
    private String discount;

    private Date createDate;

    private Long createBy;

    private Date lastUpdateDate;

    private Long lastUpdateBy;

    private Integer removeFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getLadderpriceporductId() {
        return ladderpriceporductId;
    }

    public void setLadderpriceporductId(Long ladderpriceporductId) {
        this.ladderpriceporductId = ladderpriceporductId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getStartCount() {
        return startCount;
    }

    public void setStartCount(Long startCount) {
        this.startCount = startCount;
    }

    public Long getEndCount() {
        return endCount;
    }

    public void setEndCount(Long endCount) {
        this.endCount = endCount;
    }

    public String getDiscountCategory() {
        return discountCategory;
    }

    public void setDiscountCategory(String discountCategory) {
        this.discountCategory = discountCategory == null ? null : discountCategory.trim();
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount == null ? null : discount.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }


}