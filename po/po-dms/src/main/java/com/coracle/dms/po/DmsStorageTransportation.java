/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "DMS在途库存表")
public class DmsStorageTransportation extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("产品id")
    private Long productId;
    @ApiModelProperty("产品规格id")
    private Long productSpecId;
    @ApiModelProperty("数量")
    private Integer transportationNum;
    @ApiModelProperty("发出仓库")
    private Long sendStorage;
    @ApiModelProperty("发出货位")
    private Long sendStorageLocal;
    @ApiModelProperty("发货时间")
    private Date sendTime;
    @ApiModelProperty("相关单据")
    private String sendBill;
    @ApiModelProperty("关联主键id")
    private Long relationId;
    @ApiModelProperty("关联单据类型（1调货 2订单发货 3 退换货）")
    private Integer relationType;
    @ApiModelProperty("创建日期")
    private Date createdDate;
    @ApiModelProperty("创建人")
    private Long createdBy;
    @ApiModelProperty("最后更新时间")
    private Date lastUpdatedDate;
    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;
    @ApiModelProperty("删除标识：0未删除，1已删除")
    private Integer removeFlag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductSpecId() {
        return productSpecId;
    }

    public void setProductSpecId(Long productSpecId) {
        this.productSpecId = productSpecId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getTransportationNum() {
        return transportationNum;
    }

    public void setTransportationNum(Integer transportationNum) {
        this.transportationNum = transportationNum;
    }

    public Long getSendStorage() {
        return sendStorage;
    }

    public void setSendStorage(Long sendStorage) {
        this.sendStorage = sendStorage;
    }

    public Long getSendStorageLocal() {
        return sendStorageLocal;
    }

    public void setSendStorageLocal(Long sendStorageLocal) {
        this.sendStorageLocal = sendStorageLocal;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendBill() {
        return sendBill;
    }

    public void setSendBill(String sendBill) {
        this.sendBill = sendBill == null ? null : sendBill.trim();
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
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