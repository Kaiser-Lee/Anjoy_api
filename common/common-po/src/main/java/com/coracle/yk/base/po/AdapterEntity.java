package com.coracle.yk.base.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Set;

public class AdapterEntity implements java.io.Serializable, Cloneable {
	public final static int USE_FLAG_INVALID = 0;
	public final static int USE_FLAG_VALID = 1;

	protected java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
	/**
	 * 
	 */
	private static final long serialVersionUID = -8312183615633798727L;

	@ApiModelProperty("页码")
	private java.lang.Integer p;

	@ApiModelProperty("每页条数")
	private java.lang.Integer s;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private java.lang.Long recordCount;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private java.lang.Integer listSize;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Integer start;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Integer limit;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private java.lang.Integer listItemSize;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private java.lang.Integer pageItemSize;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private String query;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private String filter;

	private List<Long> ids;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private List<String> stringIds;

	@ApiModelProperty(hidden = true)
	private String orderField;

	@ApiModelProperty(hidden = true)
	private String orderString;

	//extjs support
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Set<Long> Itemselector;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Object sort;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private String command;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private String extra;

	@ApiModelProperty("访问类型")
	private Integer reqType;

	public Integer getReqType() {
		return reqType;
	}

	public void setReqType(Integer reqType) {
		this.reqType = reqType;
	}

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public Integer getS() {
		return s;
	}

	public void setS(Integer s) {
		this.s = s;
	}

	public java.lang.Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(java.lang.Long recordCount) {
		this.recordCount = recordCount;
	}
	public java.lang.Integer getListSize() {
		return listSize;
	}
	public void setListSize(java.lang.Integer listSize) {
		this.listSize = listSize;
	}
	public java.lang.Integer getListItemSize() {
		return listItemSize;
	}
	public void setListItemSize(java.lang.Integer listItemSize) {
		this.listItemSize = listItemSize;
	}
	public java.lang.Integer getPageItemSize() {
		return pageItemSize;
	}
	public void setPageItemSize(java.lang.Integer pageItemSize) {
		this.pageItemSize = pageItemSize;
	}
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public List<String> getStringIds() {
		return stringIds;
	}
	public void setStringIds(List<String> stringIds) {
		this.stringIds = stringIds;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderString() {
		return orderString;
	}
	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Object getSort() {
		return sort;
	}
	public void setSort(Object sort) {
		this.sort = sort;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public Set<Long> getItemselector() {
		return Itemselector;
	}
	public void setItemselector(Set<Long> itemselector) {
		Itemselector = itemselector;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
