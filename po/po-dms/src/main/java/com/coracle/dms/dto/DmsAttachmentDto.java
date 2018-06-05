package com.coracle.dms.dto;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/21.
 */
public class DmsAttachmentDto extends AdapterEntity implements Serializable {
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
    @ApiModelProperty("附件主键ID")
    private Long fileId;
    @ApiModelProperty("附件名称")
    private String fileName;
    @ApiModelProperty("源文件路径")
    private String filePath;
    @ApiModelProperty("压缩图路径")
    private String fileCompressPath;
    @ApiModelProperty("缩略图路径")
    private String filePreviewPath;
    @ApiModelProperty("原文件名")
    private String orginalName;
    @ApiModelProperty("MD5")
    private String md5;
    @ApiModelProperty("文件扩展名")
    private String extension;
    @ApiModelProperty("文件大小")
    private Long size;
    @ApiModelProperty("下载次数")
    private Integer downloadCount;
    @ApiModelProperty("创建时间")
    private Date createdFileDate;

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

    public String getOrginalName() {
        return orginalName;
    }

    public void setOrginalName(String orginalName) {
        this.orginalName = orginalName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Date getCreatedFileDate() {
        return createdFileDate;
    }

    public void setCreatedFileDate(Date createdFileDate) {
        this.createdFileDate = createdFileDate;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

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
