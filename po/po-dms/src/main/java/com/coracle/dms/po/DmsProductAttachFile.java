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
@ApiModel(description = "DMS产品附件表")
public class DmsProductAttachFile extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("产品id")
    private Long productId;
    @ApiModelProperty("附件ID，关联t_common_attach_file表。")
    private Long attachId;
    @ApiModelProperty("附件url，冗余t_common_attach_file的url字段")
    private String attachUrl;
    @ApiModelProperty("附件大小")
    private Long attachSize;
    @ApiModelProperty("附件名称")
    private String attachName;
    @ApiModelProperty("附件类型 1产品图片")
    private Integer attachType;
    @ApiModelProperty("创建人")
    private Long createdBy;
    @ApiModelProperty("更新人")
    private Long lastUpdatedBy;
    @ApiModelProperty("创建时间")
    private Date createdDate;
    @ApiModelProperty("更新时间")
    private Date lastUpdatedDate;
    @ApiModelProperty("删除标志：0未删除1已删除")
    private Integer removeFlag;
    @ApiModelProperty("排序号")
    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAttachId() {
        return attachId;
    }

    public void setAttachId(Long attachId) {
        this.attachId = attachId;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl == null ? null : attachUrl.trim();
    }

    public Long getAttachSize() {
        return attachSize;
    }

    public void setAttachSize(Long attachSize) {
        this.attachSize = attachSize;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName == null ? null : attachName.trim();
    }

    public Integer getAttachType() {
        return attachType;
    }

    public void setAttachType(Integer attachType) {
        this.attachType = attachType;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}