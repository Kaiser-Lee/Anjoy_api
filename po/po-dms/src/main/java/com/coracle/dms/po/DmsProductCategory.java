/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DmsProductCategory extends AdapterEntity implements Serializable {
	@ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("编号")
    private String code;
    @ApiModelProperty("分类路径id")
    private String path;
    @ApiModelProperty("分类全路径")
    private String fullPath;
    @ApiModelProperty("分类级别")
    private Integer level;
    @ApiModelProperty("有效性")
    private Integer active;
    @ApiModelProperty("上级分类id")
    private Long parentId;
    @ApiModelProperty("类别描述")
    private String description;
    @ApiModelProperty("附件id")
    private Long attachId;
    @ApiModelProperty("类别图片")
    private String photoUrl;
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

	private List nodes = new ArrayList();
	private String rootName;
	

    public List getNodes() {
		return nodes;
	}

	public void setNodes(List nodes) {
		this.nodes = nodes;
	}

	private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    
    public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
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

    public Long getAttachId() {
		return attachId;
	}

	public void setAttachId(Long attachId) {
		this.attachId = attachId;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
}