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
@ApiModel(description = "DMS联系信息表")
public class DmsContactInfo extends AdapterEntity implements Serializable {
    private Long id;
    @ApiModelProperty("关联表类型：1-渠道联系人；2-门店联系人；3-员工")
    private Integer relatedTableType;
    @ApiModelProperty("关联表id，根据related_table_type字段的不同，关联不同的表的主键id")
    private Long relatedTableId;
    @ApiModelProperty("联系方式类型：1-手机；2-座机；3-Email；4-QQ；5-微信；6-钉钉")
    private Integer type;
    @ApiModelProperty("联系方式内容")
    private String content;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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