package com.coracle.yk.base.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 继续跟进行为，订单vo
 * @author sunyu_000
 *
 */
public class YkTrackBehaviorVo implements Serializable {

	private static final long serialVersionUID = -6003959816536573890L;
	
	private String customerName;
	private String photo;
	private String mature;
	private Integer programId;
	private	 List<BaseVo> products;
	private String createdDate;
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getMature() {
		return mature;
	}
	public void setMature(String mature) {
		this.mature = mature;
	}
	public List<BaseVo> getProducts() {
		return products;
	}
	public void setProducts(List<BaseVo> products) {
		this.products = products;
	}
	public Integer getProgramId() {
		return programId;
	}
	public void setProgramId(Integer programId) {
		this.programId = programId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
