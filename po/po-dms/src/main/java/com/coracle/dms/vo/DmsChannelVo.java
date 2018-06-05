package com.coracle.dms.vo;

import com.coracle.dms.po.*;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DmsChannelVo extends DmsChannel {
	private static final long serialVersionUID = 1L;
	/** 产品类别ID集合*/
	@ApiModelProperty("产品类别ID集合")
	private List<Long> productTypeIdList;
	/** 销售区域ID集合 */
	@ApiModelProperty("销售区域ID集合")
	private List<Long> areaIdList;
	@ApiModelProperty("销售区域集合")
	private List<DmsChannelArea> areaList;
	/** 销售区域ID集合 */
	@ApiModelProperty("产品类别集合")
	private List<DmsChannelProduct> productList;
	@ApiModelProperty("收货地址集合")
	private List<DmsChannelAddressVo> addressList;
	@ApiModelProperty("业务员集合")
	private List<DmsChannelEmployee> employeelist;
	@ApiModelProperty(value = "渠道等级文本", hidden = true)
	private String rankText;
	@ApiModelProperty(value = "渠道类型文本", hidden = true)
	private String channelTypeText;
	@ApiModelProperty(value = "省份文本",hidden = true)
    private String provinceText;
    @ApiModelProperty(value = "城市文本",hidden = true)
    private String cityText;
    @ApiModelProperty(value = "区县文本",hidden = true)
    private String countyText;
    @ApiModelProperty(value = "归属区域文本",hidden = true)
    private String areaText;
    @ApiModelProperty(value = "员工文本",hidden = true)
    private String employeeText;
    @ApiModelProperty(value = "上级渠道文本",hidden = true)
    private String parentText;

	@ApiModelProperty("起订量产品集合")
	private List<DmsChannelMinimum> dmsChannelMinimumList;

	private Integer total;

	private PageHelper.Page<DmsProductVo> dmsProductPage;
	@ApiModelProperty("品牌分类id")
	private Long brandId;
	@ApiModelProperty("产品分类id")
	private Long categoryId;
	@ApiModelProperty("分类pathId路径")
	private String pathIds;

	private Long userId;

	/**
	 * 判断渠道客户是否已被锁单，已锁则不允许下单
	 * true：已锁，不可下单
	 * false：未锁，可以下单
	 */
	//private boolean isLocked;

	public List<Long> getProductTypeIdList() {
		return productTypeIdList;
	}

	public void setProductTypeIdList(List<Long> productTypeIdList) {
		this.productTypeIdList = productTypeIdList;
	}

	public List<Long> getAreaIdList() {
		return areaIdList;
	}

	public void setAreaIdList(List<Long> areaIdList) {
		this.areaIdList = areaIdList;
	}

	public String getRankText() {
		return rankText;
	}

	public void setRankText(String rankText) {
		this.rankText = rankText;
	}

	public String getChannelTypeText() {
		return channelTypeText;
	}

	public void setChannelTypeText(String channelTypeText) {
		this.channelTypeText = channelTypeText;
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

	public String getAreaText() {
		return areaText;
	}

	public void setAreaText(String areaText) {
		this.areaText = areaText;
	}

	public String getEmployeeText() {
		return employeeText;
	}

	public void setEmployeeText(String employeeText) {
		this.employeeText = employeeText;
	}

	public List<DmsChannelArea> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<DmsChannelArea> areaList) {
		this.areaList = areaList;
	}

	public List<DmsChannelProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<DmsChannelProduct> productList) {
		this.productList = productList;
	}


	public List<DmsChannelAddressVo> getAddressList() { return addressList;}

	public void setAddressList(List<DmsChannelAddressVo> addressList) {this.addressList = addressList;}

	public List<DmsChannelEmployee> getEmployeelist() {
		return employeelist;
	}

	public void setEmployeelist(List<DmsChannelEmployee> employeelist) {
		this.employeelist = employeelist;
	}

	public String getParentText() {
		return parentText;
	}

	public void setParentText(String parentText) {
		this.parentText = parentText;
	}



	public List<DmsChannelMinimum> getDmsChannelMinimumList() {
		return dmsChannelMinimumList;
	}

	public void setDmsChannelMinimumList(List<DmsChannelMinimum> dmsChannelMinimumList) {
		this.dmsChannelMinimumList = dmsChannelMinimumList;
	}

	public PageHelper.Page<DmsProductVo> getDmsProductPage() {
		return dmsProductPage;
	}

	public void setDmsProductPage(PageHelper.Page<DmsProductVo> dmsProductPage) {
		this.dmsProductPage = dmsProductPage;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}


	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}


	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getPathIds() {
		return pathIds;
	}

	public void setPathIds(String pathIds) {
		this.pathIds = pathIds;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/*public boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}*/
}
