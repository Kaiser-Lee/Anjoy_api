/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@ApiModel(description = "DMS附件关联关系表")
public class DmsAttachmentRelation extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("附件Id")
    private Long attachId;
    @ApiModelProperty("关联表类型：1-渠道（dms_channel）；2-渠道联系人（dms_contacts）；3-门店（dms_store）；4-门店店员（dms_seller）")
    private Integer relatedTableType;
    @ApiModelProperty("关联表id，根据related_table_type字段的不同，关联不同的表的主键id")
    private Long relatedTableId;
    @ApiModelProperty("下载次数")
    private Long downloadNum;
    @ApiModelProperty("创建时间")
    private Date createdDate;
    @ApiModelProperty("创建人")
    private Long createdBy;
    @ApiModelProperty("最后修改时间")
    private Date lastUpdatedDate;
    @ApiModelProperty("最后修改人")
    private Long lastUpdatedBy;
    @ApiModelProperty("软删除标识（0：未删除，1已删除）")
    private Integer removeFlag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttachId() {
        return attachId;
    }

    public void setAttachId(Long attachId) {
        this.attachId = attachId;
    }

    public Integer getRelatedTableType() {
        return relatedTableType;
    }

    public void setRelatedTableType(Integer relatedTableType) {
        this.relatedTableType = relatedTableType;
    }

    public Long getRelatedTableId() {
        return relatedTableId;
    }

    public void setRelatedTableId(Long relatedTableId) {
        this.relatedTableId = relatedTableId;
    }

    public Long getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(Long downloadNum) {
        this.downloadNum = downloadNum;
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