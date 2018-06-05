package com.coracle.dms.vo;

import com.coracle.dms.po.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Describe:产品Vo
 * Created by:zzy
 * Created on:2017/8/22
 */
public class DmsProductVo extends DmsProductWithBLOBs {

    @ApiModelProperty("产品图片id集合")
    private List<DmsProductAttachFile> picIds;

    @ApiModelProperty("产品图片url集合")
    private List<Map<String, Object>> picUrls;

    @ApiModelProperty("产品缩略图url")
    private String picUrl;

    @ApiModelProperty("产品类别字符串")
    private String categoryText;

    @ApiModelProperty("产品单位字符串")
    private String unitText;

    @ApiModelProperty("产品品牌字符串")
    private String brandText;

    @ApiModelProperty("产品标签id集合")
    private List<Map<String, Object>> labelingIds;

    @ApiModelProperty("产品销售区域id集合")
    private List<Long> saleAreaIds;

    @ApiModelProperty("规格属性矩阵集合")
    private List<DmsProductSpecMatrixConfigVo> productSpecMatrixs;

    @ApiModelProperty("产品状态字符串")
    private String activeText;

    @ApiModelProperty("产品库存量")
    private Integer inventory = 0;

    @ApiModelProperty("产品是否收藏")
    private Integer isCollection;

    @ApiModelProperty("分类pathId路径")
    private String pathIds;

    @ApiModelProperty("评论数量")
    private Integer evaluationCount;

    @ApiModelProperty("产品规格id")
    private Long specId;

    @ApiModelProperty("产品编码")
    private String productCode;

    @ApiModelProperty("规格组合键(规格名称:s;规格名称:白色)")
    private String specUnionKey;

    @ApiModelProperty("关键字搜索")
    private String kw;

    @ApiModelProperty("1渠道2门店")
    private String type;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("渠道销售区域id列表")
    private List<Long> channelAreaIdList;

    @ApiModelProperty("产品销售区域数量")
    private Integer productAreaCount;

    @ApiModelProperty("产品订单数量，用作前端判断规格是否可以删除")
    private Integer orderNum;

    @ApiModelProperty("产品的促销活动数量")
    private Integer promotionCount;

    @ApiModelProperty("最低价")
    private BigDecimal minPrice = new BigDecimal(0);

    @ApiModelProperty("最低促销价")
    private BigDecimal minPromotionPrice = new BigDecimal(0);

    @ApiModelProperty("产品促销信息")
    private DmsPromotionProductVo promotion;

    @ApiModelProperty("建议零售价(规格产品)")
    private BigDecimal suggestedRetailPrice = new BigDecimal(0);

    @ApiModelProperty("库存量")
    private Integer storageNum = 0;

    @ApiModelProperty("有规格产品的原价")
    private BigDecimal originalPrice = new BigDecimal(0);

    @ApiModelProperty("促销类型，数据字典")
    private String promotionType;

    @ApiModelProperty("当前订货端登录账号所属渠道商id")
    private Long channelId;

    @ApiModelProperty("当前订货端登录账号所属渠道商编码")
    private String channelCode;

    @ApiModelProperty("产品id列表")
    private List<Long> productIdList;
    @ApiModelProperty("是否整板下单")
    private Integer isboard;
    @ApiModelProperty("整板下单量")
    private Long boardQuantity;
    @ApiModelProperty("整板下单量/最小起订量")
    private Long minOrderQuantity;

    @ApiModelProperty("浏览日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date BrowseDate;

    @ApiModelProperty("是否是我的足迹功能")
    private Boolean isFootprint;

    @ApiModelProperty("规格名称")
    private String specificationsText;

    @ApiModelProperty("产品列表")
    private List<DmsProduct> productList;

    @ApiModelProperty("该产品在购物车中的数量")
    private Long shopCarCount;
    @ApiModelProperty("购物车id")
    private Long shopCarId;

    @ApiModelProperty("同步过来的价格")
    private BigDecimal realPrice;

    @ApiModelProperty("是否有主图")
    private String isBigPicture;
    @ApiModelProperty("是否有缩略图")
    private String isSmallPicture;
    @ApiModelProperty("图文详情")
    private String isGraphicDetails;
    @ApiModelProperty("产品参数")
    private String isParameter;
    //null 或 0 标识没有白名单产品
    private Integer existProductWhiteList;

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Integer isCollection) {
        this.isCollection = isCollection;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSpecUnionKey() {
        return specUnionKey;
    }

    public void setSpecUnionKey(String specUnionKey) {
        this.specUnionKey = specUnionKey;
    }

    public List<DmsProductAttachFile> getPicIds() {
        return picIds;
    }

    public void setPicIds(List<DmsProductAttachFile> picIds) {
        this.picIds = picIds;
    }

    public List<Map<String, Object>> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<Map<String, Object>> picUrls) {
        this.picUrls = picUrls;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    public String getBrandText() {
        return brandText;
    }

    public void setBrandText(String brandText) {
        this.brandText = brandText;
    }

    public List<Map<String, Object>> getLabelingIds() {
        return labelingIds;
    }

    public void setLabelingIds(List<Map<String, Object>> labelingIds) {
        this.labelingIds = labelingIds;
    }

    public List<Long> getSaleAreaIds() {
        return saleAreaIds;
    }

    public void setSaleAreaIds(List<Long> saleAreaIds) {
        this.saleAreaIds = saleAreaIds;
    }

    public String getActiveText() {
        return activeText;
    }

    public void setActiveText(String activeText) {
        this.activeText = activeText;
    }

    public List<DmsProductSpecMatrixConfigVo> getProductSpecMatrixs() {
        return productSpecMatrixs;
    }

    public void setProductSpecMatrixs(List<DmsProductSpecMatrixConfigVo> productSpecMatrixs) {
        this.productSpecMatrixs = productSpecMatrixs;
    }

    public String getPathIds() {
        return pathIds;
    }

    public void setPathIds(String pathIds) {
        this.pathIds = pathIds;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getEvaluationCount() {
        return evaluationCount;
    }

    public void setEvaluationCount(Integer evaluationCount) {
        this.evaluationCount = evaluationCount;
    }

    public List<Long> getChannelAreaIdList() {
        return channelAreaIdList;
    }

    public void setChannelAreaIdList(List<Long> channelAreaIdList) {
        this.channelAreaIdList = channelAreaIdList;
    }

    public Integer getProductAreaCount() {
        return productAreaCount;
    }

    public void setProductAreaCount(Integer productAreaCount) {
        this.productAreaCount = productAreaCount;
    }

    public Integer getPromotionCount() {
        return promotionCount;
    }

    public void setPromotionCount(Integer promotionCount) {
        this.promotionCount = promotionCount;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMinPromotionPrice() {
        return minPromotionPrice;
    }

    public void setMinPromotionPrice(BigDecimal minPromotionPrice) {
        this.minPromotionPrice = minPromotionPrice;
    }

    public DmsPromotionProductVo getPromotion() {
        return promotion;
    }

    public void setPromotion(DmsPromotionProductVo promotion) {
        this.promotion = promotion;
    }

    public BigDecimal getSuggestedRetailPrice() {
        return suggestedRetailPrice;
    }

    public void setSuggestedRetailPrice(BigDecimal suggestedRetailPrice) {
        this.suggestedRetailPrice = suggestedRetailPrice;
    }

    public Integer getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(Integer storageNum) {
        this.storageNum = storageNum;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public List<Long> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Long> productIdList) {
        this.productIdList = productIdList;
    }

    public Integer getIsboard() {
        return isboard;
    }

    public void setIsboard(Integer isboard) {
        this.isboard = isboard;
    }

    public Long getBoardQuantity() {
        return boardQuantity;
    }

    public void setBoardQuantity(Long boardQuantity) {
        this.boardQuantity = boardQuantity;
    }


    public Long getMinOrderQuantity() {
        return minOrderQuantity;
    }


    public void setMinOrderQuantity(Long minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }


    public Date getBrowseDate() {
        return BrowseDate;
    }

    public void setBrowseDate(Date browseDate) {
        BrowseDate = browseDate;
    }

    public Boolean getFootprint() {
        return isFootprint;
    }

    public void setFootprint(Boolean footprint) {
        isFootprint = footprint;
    }

    public String getSpecificationsText() {
        return specificationsText;
    }

    public void setSpecificationsText(String specificationsText) {
        this.specificationsText = specificationsText;
    }

    public List<DmsProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<DmsProduct> productList) {
        this.productList = productList;
    }


    public Long getShopCarCount() {
        return shopCarCount;
    }

    public void setShopCarCount(Long shopCarCount) {
        this.shopCarCount = shopCarCount;
    }

    public Long getShopCarId() {
        return shopCarId;
    }

    public void setShopCarId(Long shopCarId) {
        this.shopCarId = shopCarId;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }


    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getIsBigPicture() {
        return isBigPicture;
    }

    public void setIsBigPicture(String isBigPicture) {
        this.isBigPicture = isBigPicture;
    }

    public String getIsSmallPicture() {
        return isSmallPicture;
    }

    public void setIsSmallPicture(String isSmallPicture) {
        this.isSmallPicture = isSmallPicture;
    }

    public String getIsGraphicDetails() {
        return isGraphicDetails;
    }

    public void setIsGraphicDetails(String isGraphicDetails) {
        this.isGraphicDetails = isGraphicDetails;
    }

    public String getIsParameter() {
        return isParameter;
    }

    public void setIsParameter(String isParameter) {
        this.isParameter = isParameter;
    }

    public Integer getExistProductWhiteList() {
        return existProductWhiteList;
    }

    public void setExistProductWhiteList(Integer existProductWhiteList) {
        this.existProductWhiteList = existProductWhiteList;
    }
}
