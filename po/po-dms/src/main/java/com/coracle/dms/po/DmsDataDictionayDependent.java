/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel(description = "DMS系统数据字典从属表")
public class DmsDataDictionayDependent extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("数据项id")
    private Long dictionaryId;
    @ApiModelProperty("从属数据项id")
    private Long dependentDataItemId;
    @ApiModelProperty("从属数据项值")
    private String dependentDataItemValue;
    @ApiModelProperty("数据项值key")
    private String sKey;
    @ApiModelProperty("数据项值名称")
    private String name;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("有效性（1有效，0失效）")
    private Integer active;
    @ApiModelProperty("排序")
    private Integer sortOrder;
    @ApiModelProperty("软删除标识（0：未删除，1已删除）")
    private Integer removeFlag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Long getDependentDataItemId() {
        return dependentDataItemId;
    }

    public void setDependentDataItemId(Long dependentDataItemId) {
        this.dependentDataItemId = dependentDataItemId;
    }

    public String getDependentDataItemValue() {
        return dependentDataItemValue;
    }

    public void setDependentDataItemValue(String dependentDataItemValue) {
        this.dependentDataItemValue = dependentDataItemValue == null ? null : dependentDataItemValue.trim();
    }

    public String getsKey() {
        return sKey;
    }

    public void setsKey(String sKey) {
        this.sKey = sKey == null ? null : sKey.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }
}