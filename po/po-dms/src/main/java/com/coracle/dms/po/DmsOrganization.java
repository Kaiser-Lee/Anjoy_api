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

@ApiModel(description = "组织架构实体")
public class DmsOrganization extends AdapterEntity implements Serializable {

    @ApiModelProperty("组织id")
    private Long id;

    @ApiModelProperty("有效性：0-无效；1-有效")
    private Integer active;

    @ApiModelProperty("组织编码")
    private String code;

    @ApiModelProperty("组织层级深度")
    private Long depth;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("组织名称")
    private String name;

    @ApiModelProperty("组织类型")
    private Integer type;

    @ApiModelProperty("组织路径，用\".\"分隔的id串，例如：1.12.123")
    private String path;

    @ApiModelProperty("排序")
    private Long sortOrder;

    @ApiModelProperty("父组织id")
    private Long parentId;

    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createdDate;

    @ApiModelProperty("创建人ID")
    private Long createdBy;

    @ApiModelProperty("最后更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastUpdatedDate;

    @ApiModelProperty("最后更新人ID")
    private Long lastUpdatedBy;

    @ApiModelProperty("软删除标识")
    private Integer removeFlag;

    @ApiModelProperty("安井id")
    private String anjoyId;

    @ApiModelProperty("安井父id")
    private String anjoyParentId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Long getDepth() {
        return depth;
    }

    public void setDepth(Long depth) {
        this.depth = depth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getAnjoyId() {
        return anjoyId;
    }

    public void setAnjoyId(String anjoyId) {
        this.anjoyId = anjoyId;
    }

    public String getAnjoyParentId() {
        return anjoyParentId;
    }

    public void setAnjoyParentId(String anjoyParentId) {
        this.anjoyParentId = anjoyParentId;
    }
}