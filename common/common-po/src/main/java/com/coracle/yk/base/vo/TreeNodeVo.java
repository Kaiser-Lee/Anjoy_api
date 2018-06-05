package com.coracle.yk.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 树形结构返回类型
 * @author  hcs
 * @date    2017-08-24
 */
@ApiModel(description = "树形结构返回类型")
public class TreeNodeVo implements Serializable {

    @ApiModelProperty("节点id")
    private Long id;

    @ApiModelProperty("节点名称")
    private String name;

    @ApiModelProperty("排序")
    private Long sortOrder;

    @ApiModelProperty("子节点列表")
    private List<TreeNodeVo> child;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<TreeNodeVo> getChild() {
        return child;
    }

    public void setChild(List<TreeNodeVo> child) {
        this.child = child;
    }
}
