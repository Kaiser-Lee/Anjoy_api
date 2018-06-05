/**
 * create by apple
 * @date 2018-01
 */
package com.coracle.dms.po.tz;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("订单产品实体类")
public class TgOrderProduct extends AdapterEntity implements Serializable {
    private Long id;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("产品id")
    private Long productId;

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

    @ApiModelProperty("牌价(原价)")
    private BigDecimal originalPrice;

    @ApiModelProperty("下浮价(终价)")
    private BigDecimal price;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("订单商品状态")
    private Integer status;

    @ApiModelProperty("创建日期")
    private Date createdDate;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("最后更新时间")
    private Date lastUpdatedDate;

    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;

    @ApiModelProperty("删除标识：0-未删除；1-已删除")
    private Integer removeFlag;

    @ApiModelProperty("产品的促销活动id")
    private Long promotionProductId;

    @ApiModelProperty("促销活动主题")
    private String promotionSubject;

    @ApiModelProperty("参考可用量")
    private Integer availableReferenceQuantity;

    @ApiModelProperty("生产周期(天)")
    private Integer productionCycle;

    @ApiModelProperty("物流周期(天)")
    private Integer logisticsCycle;

    @ApiModelProperty("参考到货期")
    private Date referenceArrivalDate;

    @ApiModelProperty("预计到货期")
    private Date predictArrivalDate;

    @ApiModelProperty("提货地点")
    private String pickProAddress;

    @ApiModelProperty("分批发货")
    private String partShipments;

    @ApiModelProperty("终端下浮率")
    private BigDecimal terminalPriceRate;

    @ApiModelProperty("终端单价")
    private BigDecimal terminalUnitPrice;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("审核下浮价")
    private BigDecimal checkPrice;

    @ApiModelProperty("下浮率")
    private BigDecimal priceRate;

    @ApiModelProperty("审核下浮率")
    private BigDecimal checkPriceRate;

    @ApiModelProperty("是否紧急;1-紧急,2-不紧急")
    private Integer isExigency;

    @ApiModelProperty("审核意见")
    private String checkMsg;

    @ApiModelProperty("产品线")
    private String proLine;

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
        this.unit = unit == null ? null : unit.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getAvailableReferenceQuantity() {
        return availableReferenceQuantity;
    }

    public void setAvailableReferenceQuantity(Integer availableReferenceQuantity) {
        this.availableReferenceQuantity = availableReferenceQuantity;
    }

    public Integer getProductionCycle() {
        return productionCycle;
    }

    public void setProductionCycle(Integer productionCycle) {
        this.productionCycle = productionCycle;
    }

    public Integer getLogisticsCycle() {
        return logisticsCycle;
    }

    public void setLogisticsCycle(Integer logisticsCycle) {
        this.logisticsCycle = logisticsCycle;
    }

    public Date getReferenceArrivalDate() {
        return referenceArrivalDate;
    }

    public void setReferenceArrivalDate(Date referenceArrivalDate) {
        this.referenceArrivalDate = referenceArrivalDate;
    }

    public Date getPredictArrivalDate() {
        return predictArrivalDate;
    }

    public void setPredictArrivalDate(Date predictArrivalDate) {
        this.predictArrivalDate = predictArrivalDate;
    }

    public String getPickProAddress() {
        return pickProAddress;
    }

    public void setPickProAddress(String pickProAddress) {
        this.pickProAddress = pickProAddress == null ? null : pickProAddress.trim();
    }

    public String getPartShipments() {
        return partShipments;
    }

    public void setPartShipments(String partShipments) {
        this.partShipments = partShipments == null ? null : partShipments.trim();
    }

    public BigDecimal getTerminalPriceRate() {
        return terminalPriceRate;
    }

    public void setTerminalPriceRate(BigDecimal terminalPriceRate) {
        this.terminalPriceRate = terminalPriceRate;
    }

    public BigDecimal getTerminalUnitPrice() {
        return terminalUnitPrice;
    }

    public void setTerminalUnitPrice(BigDecimal terminalUnitPrice) {
        this.terminalUnitPrice = terminalUnitPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getCheckPrice() {
        return checkPrice;
    }

    public void setCheckPrice(BigDecimal checkPrice) {
        this.checkPrice = checkPrice;
    }

    public BigDecimal getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(BigDecimal priceRate) {
        this.priceRate = priceRate;
    }

    public BigDecimal getCheckPriceRate() {
        return checkPriceRate;
    }

    public void setCheckPriceRate(BigDecimal checkPriceRate) {
        this.checkPriceRate = checkPriceRate;
    }

    public Integer getIsExigency() {
        return isExigency;
    }

    public void setIsExigency(Integer isExigency) {
        this.isExigency = isExigency;
    }

    public String getCheckMsg() {
        return checkMsg;
    }

    public void setCheckMsg(String checkMsg) {
        this.checkMsg = checkMsg == null ? null : checkMsg.trim();
    }

    public String getProLine() {
        return proLine;
    }

    public void setProLine(String proLine) {
        this.proLine = proLine;
    }
}