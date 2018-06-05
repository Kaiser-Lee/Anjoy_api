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
import java.util.Date;

@ApiModel("发货记录表实体")
public class DmsOrderDeliveryItem extends AdapterEntity implements Serializable {
    private Long id;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("关联类型：1-订单；2-退换货")
    private Integer relatedType;

    @ApiModelProperty("发货单id")
    private Long orderDeliveryId;

    @ApiModelProperty("订单产品明细id")
    private Long orderProductId;

    @ApiModelProperty("发货数量")
    private Integer count;

    @ApiModelProperty("发货仓库id")
    private Long storageId;

    @ApiModelProperty("发货货位id")
    private Long storageLocalId;

    @ApiModelProperty("发货日期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date deliverDate;

    @ApiModelProperty("收货日期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date receiveDate;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    public Long getOrderDeliveryId() {
        return orderDeliveryId;
    }

    public void setOrderDeliveryId(Long orderDeliveryId) {
        this.orderDeliveryId = orderDeliveryId;
    }

    public Long getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Long orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

    public Long getStorageLocalId() {
        return storageLocalId;
    }

    public void setStorageLocalId(Long storageLocalId) {
        this.storageLocalId = storageLocalId;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
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