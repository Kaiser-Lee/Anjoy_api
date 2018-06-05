/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel(description = "DMS系统地区表")
public class DmsSysRegion extends AdapterEntity implements Serializable {
    @ApiModelProperty("地区id")
    private Integer regionId;
    @ApiModelProperty("'父id")
    private Integer parentRegionId;
    @ApiModelProperty("地区名称")
    private String regionName;
    @ApiModelProperty("地区编码")
    private String regionCode;
    @ApiModelProperty("id路径")
    private String regionPath;
    @ApiModelProperty("数字")
    private Integer dataNum;
    @ApiModelProperty("排序")
    private Integer sortOrder;
    @ApiModelProperty("是否有效(1有效0失效)")
    private Integer active;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("全路径")
    private String fullPath;

    private static final long serialVersionUID = 1L;

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getParentRegionId() {
        return parentRegionId;
    }

    public void setParentRegionId(Integer parentRegionId) {
        this.parentRegionId = parentRegionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode == null ? null : regionCode.trim();
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath == null ? null : regionPath.trim();
    }

    public Integer getDataNum() {
        return dataNum;
    }

    public void setDataNum(Integer dataNum) {
        this.dataNum = dataNum;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath == null ? null : fullPath.trim();
    }
}