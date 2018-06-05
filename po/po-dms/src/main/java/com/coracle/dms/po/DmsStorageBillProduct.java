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
@ApiModel(description = "DMS入库单-对应产品表")
public class DmsStorageBillProduct extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("入库单id")
    private Long storageBillId;
    @ApiModelProperty("产品id")
    private Long productId;
    @ApiModelProperty("产品规格id")
    private Long productSpecId;
    @ApiModelProperty("入库数量")
    private Integer storageNum;
    @ApiModelProperty("可用量")
    private Integer useNum;
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

    public Long getProductSpecId() {
        return productSpecId;
    }

    public void setProductSpecId(Long productSpecId) {
        this.productSpecId = productSpecId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStorageBillId() {
        return storageBillId;
    }

    public void setStorageBillId(Long storageBillId) {
        this.storageBillId = storageBillId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(Integer storageNum) {
        this.storageNum = storageNum;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
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