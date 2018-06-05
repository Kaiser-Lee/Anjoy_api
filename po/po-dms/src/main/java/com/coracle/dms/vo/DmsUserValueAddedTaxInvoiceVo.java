package com.coracle.dms.vo;

import com.coracle.dms.po.DmsUserValueAddedTaxInvoice;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/9/5.
 */
public class DmsUserValueAddedTaxInvoiceVo extends DmsUserValueAddedTaxInvoice {
    @ApiModelProperty("附件名称")
    private String fileName;
    @ApiModelProperty("源文件路径")
    private String filePath;
    @ApiModelProperty("压缩图路径")
    private String fileCompressPath;
    @ApiModelProperty("缩略图路径")
    private String filePreviewPath;
    @ApiModelProperty("文件大小")
    private Long size;
    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("审核人名称")
    private String approveName;
    @ApiModelProperty(value = "省份文本",hidden = true)
    private String provinceText;
    @ApiModelProperty(value = "城市文本",hidden = true)
    private String cityText;
    @ApiModelProperty(value = "区县文本",hidden = true)
    private String countyText;
    @ApiModelProperty(value = "创建人文本",hidden = true)
    private String createdByText;
    @ApiModelProperty(value = "开始时间（查询使用）",hidden = true)
    private String createTimeStart;
    @ApiModelProperty(value = "结束时间（查询使用）",hidden = true)
    private String createTimeEnd;
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileCompressPath() {
        return fileCompressPath;
    }

    public void setFileCompressPath(String fileCompressPath) {
        this.fileCompressPath = fileCompressPath;
    }

    public String getFilePreviewPath() {
        return filePreviewPath;
    }

    public void setFilePreviewPath(String filePreviewPath) {
        this.filePreviewPath = filePreviewPath;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getApproveName() {
        return approveName;
    }

    public void setApproveName(String approveName) {
        this.approveName = approveName;
    }

    public String getProvinceText() {
        return provinceText;
    }

    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public String getCountyText() {
        return countyText;
    }

    public void setCountyText(String countyText) {
        this.countyText = countyText;
    }

	public String getCreatedByText() {
		return createdByText;
	}

	public void setCreatedByText(String createdByText) {
		this.createdByText = createdByText;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}



}
