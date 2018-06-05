package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import com.coracle.dms.po.DmsRemark;

public class DmsRemarkVo extends DmsRemark {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("关联表类型：1-渠道（dms_channel）；2-渠道联系人（dms_contacts）；3-门店（dms_shop）；4-门店店员（dms_seller）")
    private Integer relatedTableType;
	@ApiModelProperty("关联表id，根据related_table_type字段的不同，关联不同的表的主键id")
    private Long relatedTableId;
    
    private Long remarkId;
    

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

	public Long getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
	}

	 

    
}
