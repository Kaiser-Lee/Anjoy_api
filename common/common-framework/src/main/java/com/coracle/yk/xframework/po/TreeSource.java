package com.coracle.yk.xframework.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjr on 2016/7/15.
 * 树形对象
 */
public class TreeSource implements Serializable{
    private Integer id;
    private String text;//节点名称
    private Integer parentId;//父id
    private Boolean expanded;//是否展开
    private Boolean leaf;//是否叶子节点
    private String action;//动作
    private String url;//url
    private String image;//图标
    private String path;//层级
    private List<TreeSource> children = new ArrayList<TreeSource>();
    private String sortNum;
    private Boolean checked;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }
    public Boolean getLeaf() {
        return leaf;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public Boolean getExpanded() {
        return expanded;
    }
    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
    public List<TreeSource> getChildren() {
        return children;
    }
    public void setChildren(List<TreeSource> children) {
        this.children = children;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "TreeSource{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", parentId=" + parentId +
                ", expanded=" + expanded +
                ", leaf=" + leaf +
                ", action='" + action + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", path='" + path + '\'' +
                ", children=" + children +
                ", sortNum='" + sortNum + '\'' +
                ", checked=" + checked +
                '}';
    }
}
