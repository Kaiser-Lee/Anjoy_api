package com.coracle.dms.dto;

import com.coracle.dms.vo.DmsStorageInventoryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/21.
 */
@ApiModel("报表用中间类")
public class DmsSellNumByCategoryDto implements Serializable {
    @ApiModelProperty("产品ID")
    private Long productId;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品销售数量")
    private Integer saleNum;
    @ApiModelProperty("产品分类ID")
    private Long categoryId;
    @ApiModelProperty("产品分类名称")
    private String categoryName;
    @ApiModelProperty("产品分类路径")
    private String categoryPath;
    @ApiModelProperty("产品父分类ID")
    private Long categoryParentId;
    @ApiModelProperty("产品所在分类一级分类ID")
    private Long rootParentId;
    @ApiModelProperty("产品所在分类一级分类名称")
    private String rootParentName;
    @ApiModelProperty("库龄产品信息")
    private Map<String,Object> topByOldLibraryList;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Long getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(Long categoryParentId) {
        this.categoryParentId = categoryParentId;
    }

    public Long getRootParentId() {
        return rootParentId;
    }

    public void setRootParentId(Long rootParentId) {
        this.rootParentId = rootParentId;
    }

    public String getRootParentName() {
        return rootParentName;
    }

    public void setRootParentName(String rootParentName) {
        this.rootParentName = rootParentName;
    }

    public Map<String, Object> getTopByOldLibraryList() {
        return topByOldLibraryList;
    }

    public void setTopByOldLibraryList(Map<String, Object> topByOldLibraryList) {
        this.topByOldLibraryList = topByOldLibraryList;
    }
}
