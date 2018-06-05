/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DmsBackGoodsMaterial extends AdapterEntity implements Serializable {
    private Long id;

    private Long backGoodsOrderId;

    private Date createdDate;

    private Long createdBy;

    private Date lastUpdatedDate;

    private Long lastUpdatedBy;

    private Integer removeFlag;

    private String channel;

    private String productLine;

    private String materialNo;

    private String materialText;

    private String productionCompany;

    private String property;

    private String unit;

    private BigDecimal price;

    private BigDecimal sellPrice;

    private Integer returnCount;

    private BigDecimal sellAmount;

    private String originalOutOrder;

    private Float originalOrderDiscount;

    private BigDecimal afterDiscountPrice;

    private BigDecimal afterDiscountAmount;

    private String problemDescription;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBackGoodsOrderId() {
        return backGoodsOrderId;
    }

    public void setBackGoodsOrderId(Long backGoodsOrderId) {
        this.backGoodsOrderId = backGoodsOrderId;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine == null ? null : productLine.trim();
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo == null ? null : materialNo.trim();
    }

    public String getMaterialText() {
        return materialText;
    }

    public void setMaterialText(String materialText) {
        this.materialText = materialText == null ? null : materialText.trim();
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany == null ? null : productionCompany.trim();
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public BigDecimal getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(BigDecimal sellAmount) {
        this.sellAmount = sellAmount;
    }

    public String getOriginalOutOrder() {
        return originalOutOrder;
    }

    public void setOriginalOutOrder(String originalOutOrder) {
        this.originalOutOrder = originalOutOrder == null ? null : originalOutOrder.trim();
    }

    public Float getOriginalOrderDiscount() {
        return originalOrderDiscount;
    }

    public void setOriginalOrderDiscount(Float originalOrderDiscount) {
        this.originalOrderDiscount = originalOrderDiscount;
    }

    public BigDecimal getAfterDiscountPrice() {
        return afterDiscountPrice;
    }

    public void setAfterDiscountPrice(BigDecimal afterDiscountPrice) {
        this.afterDiscountPrice = afterDiscountPrice;
    }

    public BigDecimal getAfterDiscountAmount() {
        return afterDiscountAmount;
    }

    public void setAfterDiscountAmount(BigDecimal afterDiscountAmount) {
        this.afterDiscountAmount = afterDiscountAmount;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription == null ? null : problemDescription.trim();
    }
}