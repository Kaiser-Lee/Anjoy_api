package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import com.coracle.dms.po.DmsCommonAttachFile;
import com.coracle.dms.po.DmsProductCategory;

public class DmsProductCategoryVo extends DmsProductCategory{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("分类图片信息")
	private DmsCommonAttachFile dmsCommonAttachFile;

	public DmsCommonAttachFile getDmsCommonAttachFile() {
		return dmsCommonAttachFile;
	}

	public void setDmsCommonAttachFile(DmsCommonAttachFile dmsCommonAttachFile) {
		this.dmsCommonAttachFile = dmsCommonAttachFile;
	}
	
	

}
