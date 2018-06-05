package com.coracle.dms.xweb.common.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WeixinUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public Integer getDealerId() {
		return dealerId;
	}
	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}
	public Integer getMallId() {
		return mallId;
	}
	public void setMallId(Integer mallId) {
		this.mallId = mallId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	private Integer customerId;//顾客id
	private Integer storeId; // 所属门店id
	private Integer dealerId; // 所属经销商Id
	private Integer mallId;//所属商铺id
	private Integer orgId;//所属机构Id
	
	public static Map<String,Object> MappingWeixinUser(Map<String,Object> param, WeixinUser user){
		if(param == null){
			param = new HashMap<>();			
		}
		param.put("customerId", user.getCustomerId());
		param.put("storeId", user.getStoreId());
		param.put("dealerId", user.getDealerId());
		param.put("mallId", user.getMallId());
		param.put("orgId", user.getOrgId());
		return param;

	}
	
}
