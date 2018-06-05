package com.coracle.yk.base.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class TreeNode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("节点id")
	private String key;

	@ApiModelProperty("节点id")
	private String value;

	@ApiModelProperty("节点名称")
	private String label;

	@ApiModelProperty("是否子节点，默认false")
	private boolean isLeaf;

	@ApiModelProperty("排序号")
	private Integer sortOrder;

	@ApiModelProperty("层级")
	private String level;

	@ApiModelProperty("子节点列表")
	private List<TreeNode> children;

	public TreeNode() {
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean leaf) {
		isLeaf = leaf;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
