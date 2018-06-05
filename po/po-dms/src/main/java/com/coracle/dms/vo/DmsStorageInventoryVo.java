package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import com.coracle.dms.po.DmsStorageInventory;

import java.util.List;

/**
 * Created by xiaobu2012 on 2017/8/29.
 */
public class DmsStorageInventoryVo extends DmsStorageInventory{
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @ApiModelProperty("数量")
    private Integer addOrSubtractNum;
    @ApiModelProperty("出入库(1出库，2入库)")
    private Integer outInType;
    @ApiModelProperty("是否在途(1是0否)")
    private Boolean transFlag;
    @ApiModelProperty("出库机构")
    private Long outOrgId;
    @ApiModelProperty("出库机构类型")
    private Integer outOrgType;
    @ApiModelProperty("入库机构")
    private Long inOrgId;
    @ApiModelProperty("入库机构类型")
    private Integer inOrgType;
    @ApiModelProperty("出入库类型(1采购入库，2采购退货，3销售出库，4销售退货，5调拨出库，6调拨入库，7库存调整，8其他入库)")
    private int assignWay;
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
    @ApiModelProperty("产品缩略图地址")
    private String filePreviewPath;
    @ApiModelProperty("产品类别")
    private Integer categoryId;
    @ApiModelProperty("产品编码")
    private String productCode;
    @ApiModelProperty("关键字搜索")
    private String kw;
    @ApiModelProperty("类型：1按产品库存列表，2按门店库存列表")
    private String listType;
    @ApiModelProperty("类型：1渠道，2门店")
    private String type;
    @ApiModelProperty("门店类型")
    private String storageType;
    @ApiModelProperty("门店名称")
    private String storeName;
    @ApiModelProperty("门店id")
    private Long storeId;
    @ApiModelProperty("当前登录用户id")
    private Long userId;

    @ApiModelProperty("出入库类型文本")
    private String assignWayText;
    @ApiModelProperty("要导出的ids集合")
    private List<Integer> exPortIds;
    @ApiModelProperty("要导出的类型  0 全部导出 1选择性ids导出 ")
    private Integer exPortType;

    @ApiModelProperty("可用量统计")
    private Long useNumTotal;

    @ApiModelProperty("渠道id")
    private Long channelId;

    @ApiModelProperty("门店id列表")
    private List<Long> storeIdList;

    @ApiModelProperty("APP产品库存列表")
    private List<DmsStorageInventoryVo> getProductList;

    public Integer getOutOrgType() {
        return outOrgType;
    }

    public void setOutOrgType(Integer outOrgType) {
        this.outOrgType = outOrgType;
    }

    public Integer getInOrgType() {
        return inOrgType;
    }

    public void setInOrgType(Integer inOrgType) {
        this.inOrgType = inOrgType;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public List<DmsStorageInventoryVo> getGetProductList() {
        return getProductList;
    }

    public void setGetProductList(List<DmsStorageInventoryVo> getProductList) {
        this.getProductList = getProductList;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

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

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
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

    public String getFilePreviewPath() {
        return filePreviewPath;
    }

    public void setFilePreviewPath(String filePreviewPath) {
        this.filePreviewPath = filePreviewPath;
    }

    public Integer getAddOrSubtractNum() {
        return addOrSubtractNum;
    }

    public void setAddOrSubtractNum(Integer addOrSubtractNum) {
        this.addOrSubtractNum = addOrSubtractNum;
    }

    

    public Integer getOutInType() {
		return outInType;
	}

	public void setOutInType(Integer outInType) {
		this.outInType = outInType;
	}

	public int getAssignWay() {
        return assignWay;
    }

    public Long getOutOrgId() {
        return outOrgId;
    }

    public void setOutOrgId(Long outOrgId) {
        this.outOrgId = outOrgId;
    }

    public Long getInOrgId() {
        return inOrgId;
    }

  

    public Boolean getTransFlag() {
		return transFlag;
	}

	public void setTransFlag(Boolean transFlag) {
		this.transFlag = transFlag;
	}

	public void setInOrgId(Long inOrgId) {
        this.inOrgId = inOrgId;
    }

    public void setAssignWay(int assignWay) {
        this.assignWay = assignWay;
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

    public Long getUseNumTotal() {
        return useNumTotal;
    }

    public void setUseNumTotal(Long useNumTotal) {
        this.useNumTotal = useNumTotal;
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
