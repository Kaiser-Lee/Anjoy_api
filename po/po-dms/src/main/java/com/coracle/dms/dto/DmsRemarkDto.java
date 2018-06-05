package com.coracle.dms.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import com.coracle.yk.base.po.AdapterEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

public class DmsRemarkDto extends AdapterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("主键ID")
	private Long id;
	@ApiModelProperty("备注ID")
	private Long remarkId;
	@ApiModelProperty("关联表类型：1-渠道（dms_channel）；2-渠道联系人（dms_contacts）；3-门店（dms_store）；4-门店店员（dms_seller）")
	private Integer relatedTableType;
	@ApiModelProperty("关联表id，根据related_table_type字段的不同，关联不同的表的主键id")
	private Long relatedTableId;
	@ApiModelProperty("备注主题")
	private String subject;
	@ApiModelProperty("备注内容")
	private String content;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
