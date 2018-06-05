package com.coracle.dms.vo;

import com.coracle.dms.po.DmsShoppingCart;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class DmsShoppingCartVo extends DmsShoppingCart {
    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("产品价格")
    private BigDecimal price;

    @ApiModelProperty("可用促销数量")
    private Integer promotionCount;

    @ApiModelProperty("促销价格")
    private BigDecimal promotionPrice;

    @ApiModelProperty("产品库存量")
    private Integer inventory;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("缩略图url")
    private String picUrl;

    @ApiModelProperty("产品是否上架")
    private Integer active;

    @ApiModelProperty("促销信息")
    private DmsPromotionProductVo promotion;

    @ApiModelProperty("渠道id")
    private Long channelId;

    @ApiModelProperty("渠道Eas编码")
    private String channelCode;

    @ApiModelProperty("继续操作")
    private Integer goOn;

    @ApiModelProperty("去年同期销售量（去年今月的销售数量）")
    private Long salesCountSameMonthLastYear;

    @ApiModelProperty("本月已下单数量（本月截止昨天为止已下单的数量）")
    private Long salesCountThisMonth;

    @ApiModelProperty("今年至今的销售量")
    private Long salesCountThisYear;

    @ApiModelProperty("去年至今的销售量")
    private Long salesCountLastYear;


    @ApiModelProperty("去年今月的第一天00:00:00")
    private String startTimeLastYear;

    @ApiModelProperty("去年今月的最后一天23:59:59")
    private String endTimeLastYear;

    @ApiModelProperty("今年第一天00:00:00")
    private String startTimeFirstDateThisYear;

    @ApiModelProperty("去年第一天00:00:00")
    private String startTimeFirstDateLastYear;

    @ApiModelProperty("本月第一天00:00:00")
    private String startTimeThisMonth;

    @ApiModelProperty("当前时间")
    private String now;

    @ApiModelProperty("筛选条件：产品分类")
    private Long categoryId;

    @ApiModelProperty("筛选条件：产品规格")
    private String specifications;

    @ApiModelProperty("产品规格")
    private String specificationsText;

    @ApiModelProperty("")
    private Boolean selected;

    @ApiModelProperty("整板数量")
    private Long boardQuantity;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("产品code")
    private String code;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPromotionCount() {
        return promotionCount;
    }

    public void setPromotionCount(Integer promotionCount) {
        this.promotionCount = promotionCount;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public DmsPromotionProductVo getPromotion() {
        return promotion;
    }

    public void setPromotion(DmsPromotionProductVo promotion) {
        this.promotion = promotion;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Integer getGoOn() {
        return goOn;
    }

    public void setGoOn(Integer goOn) {
        this.goOn = goOn;
    }

    public Long getSalesCountSameMonthLastYear() {
        return salesCountSameMonthLastYear;
    }

    public void setSalesCountSameMonthLastYear(Long salesCountSameMonthLastYear) {
        this.salesCountSameMonthLastYear = salesCountSameMonthLastYear;
    }

    public Long getSalesCountThisMonth() {
        return salesCountThisMonth;
    }

    public void setSalesCountThisMonth(Long salesCountThisMonth) {
        this.salesCountThisMonth = salesCountThisMonth;
    }

    public String getStartTimeLastYear() {
        return startTimeLastYear;
    }

    public void setStartTimeLastYear(String startTimeLastYear) {
        this.startTimeLastYear = startTimeLastYear;
    }

    public String getEndTimeLastYear() {
        return endTimeLastYear;
    }

    public void setEndTimeLastYear(String endTimeLastYear) {
        this.endTimeLastYear = endTimeLastYear;
    }

    public String getStartTimeThisMonth() {
        return startTimeThisMonth;
    }

    public void setStartTimeThisMonth(String startTimeThisMonth) {
        this.startTimeThisMonth = startTimeThisMonth;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getSpecificationsText() {
        return specificationsText;
    }

    public void setSpecificationsText(String specificationsText) {
        this.specificationsText = specificationsText;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Long getBoardQuantity() {
        return boardQuantity;
    }

    public void setBoardQuantity(Long boardQuantity) {
        this.boardQuantity = boardQuantity;
    }

    public String getStartTimeFirstDateThisYear() {
        return startTimeFirstDateThisYear;
    }

    public void setStartTimeFirstDateThisYear(String startTimeFirstDateThisYear) {
        this.startTimeFirstDateThisYear = startTimeFirstDateThisYear;
    }

    public String getStartTimeFirstDateLastYear() {
        return startTimeFirstDateLastYear;
    }

    public void setStartTimeFirstDateLastYear(String startTimeFirstDateLastYear) {
        this.startTimeFirstDateLastYear = startTimeFirstDateLastYear;
    }

    public Long getSalesCountThisYear() {
        return salesCountThisYear;
    }

    public void setSalesCountThisYear(Long salesCountThisYear) {
        this.salesCountThisYear = salesCountThisYear;
    }

    public Long getSalesCountLastYear() {
        return salesCountLastYear;
    }

    public void setSalesCountLastYear(Long salesCountLastYear) {
        this.salesCountLastYear = salesCountLastYear;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



}
