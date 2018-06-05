package com.coracle.dms.vo;

import com.coracle.dms.po.DmsStorageBill;
import com.coracle.dms.po.DmsStorageBillProduct;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaobu2012 on 2017/8/24.
 */
public class DmsStorageBillVo extends DmsStorageBill {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所属组织")
    private Long orgId;
    @ApiModelProperty("所属组织类型")
    private Long orgType;
    @ApiModelProperty("仓库名称")
    private String storageName;
    @ApiModelProperty("产品类别")
    private Integer categoryId;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品ID")
    private Long productId;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;

    @ApiModelProperty("库存数量")
    private String storageNum;
    @ApiModelProperty("货位code")
    private String localCode;
    @ApiModelProperty("产品编码")
    private String productCode;
    @ApiModelProperty("产品类别字符串")
    private String categoryText;
    @ApiModelProperty("产品单位字符串")
    private String unitText;
    @ApiModelProperty("产品规格字符串")
    private String specName;
    @ApiModelProperty("出入库类型字符串")
    private String assignWayText;
    @ApiModelProperty("要导出的类型  0 全部导出 1选择性ids导出 ")
    private Integer exPortType;

    @ApiModelProperty("仓库组织id")
    private Long relationId;

    @ApiModelProperty("产品列表")
    private List<Map<String,Object>> storageBillProduct;

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(String storageNum) {
        this.storageNum = storageNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    /*public List<DmsStorageBillProduct> getStorageBillProduct() {
        return storageBillProduct;
    }

    public void setStorageBillProduct(List<DmsStorageBillProduct> storageBillProduct) {
        this.storageBillProduct = storageBillProduct;
    }*/

    public List<Map<String, Object>> getStorageBillProduct() {
        return storageBillProduct;
    }

    public void setStorageBillProduct(List<Map<String, Object>> storageBillProduct) {
        this.storageBillProduct = storageBillProduct;
    }

    public String getAssignWayText() {
        return assignWayText;
    }

    public void setAssignWayText(String assignWayText) {
        this.assignWayText = assignWayText;
    }

    public Integer getExPortType() {
        return exPortType;
    }

    public void setExPortType(Integer exPortType) {
        this.exPortType = exPortType;
    }
}
