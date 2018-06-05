/**
 * create by apple
 * @date 2018-01
 */
package com.coracle.dms.po.tz;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("订单产品分批发运实体类")
public class TgOrderProductPart extends AdapterEntity implements Serializable {
    private Long id;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单产品id")
    private Long orderProductId;

    @ApiModelProperty("物料名称")
    private String productName;

    @ApiModelProperty("默认发货周期(天)")
    private Integer logisticsCycle;

    @ApiModelProperty("约定发货日期")
    private Date appointDeliveryDate;

    @ApiModelProperty("发货数量")
    private Integer deliveryCount;

    @ApiModelProperty("创建日期")
    private Date createdDate;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("最后更新时间")
    private Date lastUpdatedDate;

    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;

    @ApiModelProperty("删除标识")
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

    public Long getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Long orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getLogisticsCycle() {
        return logisticsCycle;
    }

    public void setLogisticsCycle(Integer logisticsCycle) {
        this.logisticsCycle = logisticsCycle;
    }

    public Date getAppointDeliveryDate() {
        return appointDeliveryDate;
    }

    public void setAppointDeliveryDate(Date appointDeliveryDate) {
        this.appointDeliveryDate = appointDeliveryDate;
    }

    public Integer getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(Integer deliveryCount) {
        this.deliveryCount = deliveryCount;
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