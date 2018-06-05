package com.coracle.dms.vo;

import com.coracle.dms.po.DmsStorageOutInRecord;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by xiaobu2012 on 2017/8/24.
 */
public class DmsStorageOutInRecordVo extends DmsStorageOutInRecord {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所属组织")
    private Long orgId;
    @ApiModelProperty("所属组织类型")
    private Long orgType;
    @ApiModelProperty("仓库名称")
    private String storageName;
    @ApiModelProperty("产品类别")
    private Integer categoryId;
    /*@ApiModelProperty("产品名称")
    private String productName;*/
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;

    @ApiModelProperty("货位code")
    private String localCode;
    @ApiModelProperty("产品类别字符串")
    private String categoryText;
    @ApiModelProperty("产品单位字符串")
    private String unitText;
  /*  @ApiModelProperty("产品规格字符串")
    private String specName;*/


    @ApiModelProperty("产品编码")
    private String productCode;
    @ApiModelProperty("产品图片")
    private String filePreviewPath;
    @ApiModelProperty("出入库类型文本")
    private String assignWayText;

    @ApiModelProperty("出入口记录总数")
    private Integer totalNum;

    @ApiModelProperty("要导出的ids集合")
    private List<Integer> exPortIds;
    @ApiModelProperty("要导出的类型  0 全部导出 1选择性ids导出 ")
    private Integer exPortType;

    @ApiModelProperty("渠道id")
    private Long channelId;

    @ApiModelProperty("门店id列表")
    private List<Long> storeIdList;

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getFilePreviewPath() {
        return filePreviewPath;
    }

    public void setFilePreviewPath(String filePreviewPath) {
        this.filePreviewPath = filePreviewPath;
    }

    public String getAssignWayText() {
        return assignWayText;
    }

    public void setAssignWayText(String assignWayText) {
        this.assignWayText = assignWayText;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public String getUnitText() {
        return unitText;
    }



    public void setUnitText(String unitText) {
        this.unitText = unitText;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getOrgType() {
        return orgType;
    }

    public void setOrgType(Long orgType) {
        this.orgType = orgType;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getExPortIds() {
        return exPortIds;
    }

    public void setExPortIds(List<Integer> exPortIds) {
        this.exPortIds = exPortIds;
    }

    public Integer getExPortType() {
        return exPortType;
    }

    public void setExPortType(Integer exPortType) {
        this.exPortType = exPortType;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public List<Long> getStoreIdList() {
        return storeIdList;
    }

    public void setStoreIdList(List<Long> storeIdList) {
        this.storeIdList = storeIdList;
    }
}
