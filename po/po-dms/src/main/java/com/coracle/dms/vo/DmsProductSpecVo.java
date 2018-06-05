package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.coracle.dms.po.DmsProductSpec;
import com.coracle.dms.po.DmsProductSpecParam;

public class DmsProductSpecVo extends DmsProductSpec {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("规格值列表")
	private List<DmsProductSpecParam> specValueList;
	@ApiModelProperty("产品类型名称")
	private String categoryName;

	public List<DmsProductSpecParam> getSpecValueList() {
		return specValueList;
	}

	public void setSpecValueList(List<DmsProductSpecParam> specValueList) {
		this.specValueList = specValueList;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	 
	
	

}
