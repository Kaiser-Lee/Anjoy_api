package com.coracle.yk.xframework.po;

import java.util.Set;

public class AdapterEntity implements java.io.Serializable, Cloneable {
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
	protected java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
	/**
	 * 
	 */
	private static final long serialVersionUID = -8312183615633798727L;
	
	private java.lang.Integer page;
	private java.lang.Long recordCount;
	private java.lang.Integer listSize;
	private java.lang.Integer pageSize;
	private java.lang.Integer listItemSize;
	private java.lang.Integer pageItemSize;
	private String query;
	private String filter;
	private Set<Long> ids;
	private Set<String> stringIds;
	private String orderField;
	private String orderString;
	private Integer start;
	private Integer limit;
	//extjs support
	private Set<Long> Itemselector;
	private Object sort;
	private String command;
	private String extra;
	public java.lang.Integer getPage() {
		return page;
	}
	public void setPage(java.lang.Integer page) {
		this.page = page;
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
	public java.lang.Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(java.lang.Integer pageSize) {
		this.pageSize = pageSize;
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
	public Set<Long> getIds() {
		return ids;
	}
	public void setIds(Set<Long> ids) {
		this.ids = ids;
	}
	public Set<String> getStringIds() {
		return stringIds;
	}
	public void setStringIds(Set<String> stringIds) {
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

}
