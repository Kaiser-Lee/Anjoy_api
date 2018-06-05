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
import java.util.List;

@ApiModel("关联树实体")
public class DmsTreeRelation extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("关联类型")
    private Integer relatedType;
    @ApiModelProperty("关联类型对应表中的id")
    private Long relatedId;
    @ApiModelProperty("名称（冗余）")
    private String name;
    @ApiModelProperty("父ID")
    private Long parentId;
    @ApiModelProperty("类型")
    private Integer type;

    private Date createdDate;

    private Long createdBy;

    private Date lastUpdatedDate;

    private Long lastUpdatedBy;

    private Integer removeFlag;

    @ApiModelProperty("路径")
    private String path;

    private List<DmsTreeRelation> dmsTreeRelationList;//对应的字集合

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<DmsTreeRelation> getDmsTreeRelationList() {
        return dmsTreeRelationList;
    }

    public void setDmsTreeRelationList(List<DmsTreeRelation> dmsTreeRelationList) {
        this.dmsTreeRelationList = dmsTreeRelationList;
    }

}