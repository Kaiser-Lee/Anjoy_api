package com.coracle.dms.vo;

import com.coracle.dms.po.DmsStorageTransportation;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by xiaobu2012 on 2017/9/11.
 */
public class DmsStorageTransportationVo extends DmsStorageTransportation {

    @ApiModelProperty("仓库名称")
    private String storageName;
    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("所属组织")
    private Long orgId;
    @ApiModelProperty("所属组织类型")
    private Long orgType;

    @ApiModelProperty("货位code")
    private String localCode;
    @ApiModelProperty("产品类别字符串")
    private String categoryText;
    @ApiModelProperty("产品单位字符串")
    private String unitText;
    @ApiModelProperty("产品规格字符串")
    private String specName;

    @ApiModelProperty("产品类别")
    private Integer categoryId;
    @ApiModelProperty("产品编码")
    private String productCode;

    @ApiModelProperty("出入库类型文本")
    private String assignWayText;
    @ApiModelProperty("要导出的ids集合")
    private List<Integer> exPortIds;
    @ApiModelProperty("要导出的类型  0 全部导出 1选择性ids导出 ")
    private Integer exPortType;

    @ApiModelProperty("渠道id")
    private Long channelId;

    @ApiModelProperty("门店id列表")
    private List<Long> storeIdList;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getAssignWayText() {
        return assignWayText;
    }

    public void setAssignWayText(String assignWayText) {
        this.assignWayText = assignWayText;
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
