package com.coracle.yk.base.param;

/**
 * 跟进列表param
 * @author sunyu_000
 *
 */
public class YkTrackParam extends BaseParam {

	private static final long serialVersionUID = -1900039339702617189L;
	
	private Integer matureId;
	private Integer customerId;
	private Integer ln;
	private Integer gn;
	private Integer sellerId;
	private String startDate;
	private String endDate;
	
	
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
	public Integer getMatureId() {
		return matureId;
	}
	public void setMatureId(Integer matureId) {
		this.matureId = matureId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getLn() {
		return ln;
	}
	public void setLn(Integer ln) {
		this.ln = ln;
	}
	public Integer getGn() {
		return gn;
	}
	public void setGn(Integer gn) {
		this.gn = gn;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
}
