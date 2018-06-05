/**
 * create by xiaobu2012
 * @date 2017-09
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@ApiModel(description = "DMS销售出库记录表")
public class DmsStorageSaleOut extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("出货单位")
    private Long sendUnit;
    @ApiModelProperty("发货日期")
    private Date sendDate;
    @ApiModelProperty("销售订单")
    private String saleOrder;
    @ApiModelProperty("配送方式")
    private String deliveryType;
    @ApiModelProperty("物流公司")
    private String expressCompany;
    @ApiModelProperty("物流单号")
    private String expressNo;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("创建日期")
    private Date createdDate;
    @ApiModelProperty("创建人")
    private Long createdBy;
    @ApiModelProperty("最后更新时间")
    private Date lastUpdatedDate;
    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;
    @ApiModelProperty("0未删除，1已删除")
    private Integer removeFlag;
    @ApiModelProperty("销售数量")
    private Integer num;
    @ApiModelProperty("产品id")
    private Long productId;
    @ApiModelProperty("产品规格id")
    private Long productSpecId;
    @ApiModelProperty("仓库")
    private Long storage;
    @ApiModelProperty("货位")
    private Long storageLocal;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSendUnit() {
        return sendUnit;
    }

    public void setSendUnit(Long sendUnit) {
        this.sendUnit = sendUnit;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(String saleOrder) {
        this.saleOrder = saleOrder == null ? null : saleOrder.trim();
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo == null ? null : expressNo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductSpecId() {
        return productSpecId;
    }

    public void setProductSpecId(Long productSpecId) {
        this.productSpecId = productSpecId;
    }

    public Long getStorage() {
        return storage;
    }

    public void setStorage(Long storage) {
        this.storage = storage;
    }

    public Long getStorageLocal() {
        return storageLocal;
    }

    public void setStorageLocal(Long storageLocal) {
        this.storageLocal = storageLocal;
    }
}