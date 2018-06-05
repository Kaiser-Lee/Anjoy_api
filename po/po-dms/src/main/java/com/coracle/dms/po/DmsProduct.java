/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@ApiModel(description = "DMS产品表")
public class DmsProduct extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("产品名称")
    private String name;
    @ApiModelProperty("产品编码")
    private String code;
    @ApiModelProperty("产品类别ID")
    private Long categoryId;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("品牌ID")
    private Long brandId;
    @ApiModelProperty("条形码")
    private String barCode;
    @ApiModelProperty("产品标价")
    private BigDecimal showPrice;
    @ApiModelProperty("建议零售价")
    private BigDecimal suggestedPrice;
    @ApiModelProperty("上市时间")
    private Date listingTime;
    @ApiModelProperty("关键字")
    private String keyword;
    @ApiModelProperty("产地")
    private String placeOfOrigin;
    @ApiModelProperty("产品状态：1已上架、0已下架")
    private Integer active;
    @ApiModelProperty("产品标签（字典）")
    private Long labeling;
    @ApiModelProperty("产品规格")
    private String specifications;
    @ApiModelProperty("规格型号")
    private String specificationsModel;
    @ApiModelProperty("销售区域")
    private String saleArea;
    @ApiModelProperty("产品略缩图，附件ID")
    private Long picId;
    @ApiModelProperty("销量")
    private Long salesVolume;
    @ApiModelProperty("浏览次数")
    private Long viewCount;
    @ApiModelProperty("创建日期")
    private Date createdDate;
    @ApiModelProperty("创建人")
    private Long createdBy;
    @ApiModelProperty("最后更新日期")
    private Date lastUpdatedDate;
    @ApiModelProperty("最后更新人")
    private Long lastUpdatedBy;
    @ApiModelProperty("软删除标识：0未删除1已删除")
    private Integer removeFlag;
    @ApiModelProperty("前端专用，用于规格修改时默认勾选")
    private String jsonObject;

    @ApiModelProperty("最小包装量/数（安井、阳光、天正）")
    private Long minPackageQuantity;

    @ApiModelProperty("装箱数（天正）")
    private Long packageQuantity;

    @ApiModelProperty("大客户价（阳光）")
    private BigDecimal bigCustomerPrice;

    @ApiModelProperty("中客户价（阳光）")
    private BigDecimal midCustomerPrice;

    @ApiModelProperty("小客户价（阳光）")
    private BigDecimal smallCustomerPrice;

    @ApiModelProperty("辅助单位（安井）")
    private String auxiliaryUnit;

    @ApiModelProperty("EAS编码（安井）")
    private String easCode;

    @ApiModelProperty("换算率（安井）")
    private BigDecimal conversionRate;

    @ApiModelProperty("重量（安井）")
    private BigDecimal weight;

    @ApiModelProperty("体积（安井）")
    private BigDecimal volume;

    @ApiModelProperty("备注（安井）")
    private String remark;

    @ApiModelProperty("排序号（安井）")
    private Integer sortOrder;

    @ApiModelProperty("图文详情")
    private String graphicDetails;

    @ApiModelProperty("产品参数")
    private String parameter;

    @ApiModelProperty("安井id")
    private String anjoyId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public BigDecimal getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
    }

    public BigDecimal getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(BigDecimal suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public Date getListingTime() {
        return listingTime;
    }

    public void setListingTime(Date listingTime) {
        this.listingTime = listingTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin == null ? null : placeOfOrigin.trim();
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Long getLabeling() {
        return labeling;
    }

    public void setLabeling(Long labeling) {
        this.labeling = labeling;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications == null ? null : specifications.trim();
    }

    public String getSaleArea() {
        return saleArea;
    }

    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea == null ? null : saleArea.trim();
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public Long getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
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

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Long getMinPackageQuantity() {
        return minPackageQuantity;
    }

    public void setMinPackageQuantity(Long minPackageQuantity) {
        this.minPackageQuantity = minPackageQuantity;
    }

    public BigDecimal getBigCustomerPrice() {
        return bigCustomerPrice;
    }

    public void setBigCustomerPrice(BigDecimal bigCustomerPrice) {
        this.bigCustomerPrice = bigCustomerPrice;
    }

    public BigDecimal getMidCustomerPrice() {
        return midCustomerPrice;
    }

    public void setMidCustomerPrice(BigDecimal midCustomerPrice) {
        this.midCustomerPrice = midCustomerPrice;
    }

    public BigDecimal getSmallCustomerPrice() {
        return smallCustomerPrice;
    }

    public void setSmallCustomerPrice(BigDecimal smallCustomerPrice) {
        this.smallCustomerPrice = smallCustomerPrice;
    }

    public Long getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(Long packageQuantity) {
        this.packageQuantity = packageQuantity;
    }



    public String getAuxiliaryUnit() {
        return auxiliaryUnit;
    }

    public void setAuxiliaryUnit(String auxiliaryUnit) {
        this.auxiliaryUnit = auxiliaryUnit;
    }

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getAnjoyId() {
        return anjoyId;
    }

    public void setAnjoyId(String anjoyId) {
        this.anjoyId = anjoyId;
    }

    public String getSpecificationsModel() {
        return specificationsModel;
    }

    public void setSpecificationsModel(String specificationsModel) {
        this.specificationsModel = specificationsModel;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getGraphicDetails() {
        return graphicDetails;
    }

    public void setGraphicDetails(String graphicDetails) {
        this.graphicDetails = graphicDetails;
    }
}