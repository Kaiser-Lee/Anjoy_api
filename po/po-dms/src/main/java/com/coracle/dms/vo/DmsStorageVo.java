package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.coracle.dms.po.DmsStorage;
import com.coracle.dms.po.DmsStorageArea;
import com.coracle.dms.po.DmsStorageLocal;

public class DmsStorageVo extends DmsStorage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("辐射区域ID集合")
	private List<Long> areaIdList;
	@ApiModelProperty("辐射区域")
	private List<DmsStorageArea> storageAreaList;
	@ApiModelProperty("货位列表")
	private List<DmsStorageLocal> storageLocalList;
	@ApiModelProperty(value = "省份文本", hidden = true)
	private String provinceText;
	@ApiModelProperty(value = "城市文本", hidden = true)
	private String cityText;
	@ApiModelProperty(value = "区县文本", hidden = true)
	private String countyText;
	@ApiModelProperty(value = "所属组织文本", hidden = true)
	private String orgText;
	@ApiModelProperty(value = "所属省市区关键字", hidden = true)
	private String kw;
	
	public List<Long> getAreaIdList() {
		return areaIdList;
	}
	public void setAreaIdList(List<Long> areaIdList) {
		this.areaIdList = areaIdList;
	}
	public List<DmsStorageArea> getStorageAreaList() {
		return storageAreaList;
	}
	public void setStorageAreaList(List<DmsStorageArea> storageAreaList) {
		this.storageAreaList = storageAreaList;
	}
	public List<DmsStorageLocal> getStorageLocalList() {
		return storageLocalList;
	}
	public void setStorageLocalList(List<DmsStorageLocal> storageLocalList) {
		this.storageLocalList = storageLocalList;
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
	public String getOrgText() {
		return orgText;
	}
	public void setOrgText(String orgText) {
		this.orgText = orgText;
	}
	public String getKw() {
		return kw;
	}
	public void setKw(String kw) {
		this.kw = kw;
	}
	
	

}
