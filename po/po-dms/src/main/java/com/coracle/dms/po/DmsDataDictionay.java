/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel(description = "DMS数据字典表")
public class DmsDataDictionay extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("数据项key")
    private String sKey;
    @ApiModelProperty("数据项名称")
    private String name;
    @ApiModelProperty("类型（1从属 0 独立）")
    private Long type;
    @ApiModelProperty("从属数据项id")
    private Long dependentDataItemId;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("状态（1有效，0失效）")
    private Integer active;
    @ApiModelProperty("软删除标识（0：未删除，1已删除）")
    private Integer removeFlag;

    //非持久化属性，用于存从属数据项的名称
    @ApiModelProperty("从属数据项名称")
    private String dependentDataItemName;

    public String getDependentDataItemName() {
        return dependentDataItemName;
    }

    public void setDependentDataItemName(String dependentDataItemName) {
        this.dependentDataItemName = dependentDataItemName;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getDependentDataItemId() {
        return dependentDataItemId;
    }

    public void setDependentDataItemId(Long dependentDataItemId) {
        this.dependentDataItemId = dependentDataItemId;
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

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }
}