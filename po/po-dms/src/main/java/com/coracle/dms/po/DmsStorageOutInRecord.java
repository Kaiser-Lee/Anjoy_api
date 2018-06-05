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
@ApiModel(description = "DMS出入库记录表")
public class DmsStorageOutInRecord extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("出库机构")
    private Long outOrgId;
    @ApiModelProperty("出库机构类型")
    private Integer outOrgType;
    @ApiModelProperty("入库机构")
    private Long inOrgId;
    @ApiModelProperty("入库机构类型")
    private Integer inOrgType;
    @ApiModelProperty("数量为正负数")
    private Long num;
    @ApiModelProperty("出入库(1出库，2入库)")
    private Integer outInType;
    @ApiModelProperty("出入库类型(1采购入库，2采购退货，3销售出库，4销售退货，5调拨出库，6调拨入库，7库存调整，8其他入库)")
    private Integer assignWay;
    @ApiModelProperty("仓库id")
    private Long storage;
    @ApiModelProperty("货位id")
    private Long storageLocal;
    @ApiModelProperty("产品id")
    private Long productId;
    @ApiModelProperty("产品规格id")
    private Long productSpecId;
    @ApiModelProperty("出入库时间")
    private Date outInTime;
    @ApiModelProperty("发货单号")
    private String sendNo;
    @ApiModelProperty("创建日期")
    private Date createdDate;
    @ApiModelProperty("创建人")
    private Long createdBy;
    @ApiModelProperty("最后更新时间")
    private Date lastUpdatedDate;
    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;
    @ApiModelProperty("软删除标识（0：未删除，1已删除）")
    private Integer removeFlag;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品规格组合键(规格名称:s;规格名称:白色)")
    private String specName;

    public Integer getOutOrgType() {
        return outOrgType;
    }

    public void setOutOrgType(Integer outOrgType) {
        this.outOrgType = outOrgType;
    }

    public Integer getInOrgType() {
        return inOrgType;
    }

    public void setInOrgType(Integer inOrgType) {
        this.inOrgType = inOrgType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOutOrgId() {
        return outOrgId;
    }

    public void setOutOrgId(Long outOrgId) {
        this.outOrgId = outOrgId;
    }

    public Long getInOrgId() {
        return inOrgId;
    }

    public void setInOrgId(Long inOrgId) {
        this.inOrgId = inOrgId;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Integer getOutInType() {
        return outInType;
    }

    public void setOutInType(Integer outInType) {
        this.outInType = outInType;
    }

    public Integer getAssignWay() {
        return assignWay;
    }

    public void setAssignWay(Integer assignWay) {
        this.assignWay = assignWay;
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

    public Date getOutInTime() {
        return outInTime;
    }

    public void setOutInTime(Date outInTime) {
        this.outInTime = outInTime;
    }

    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo == null ? null : sendNo.trim();
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