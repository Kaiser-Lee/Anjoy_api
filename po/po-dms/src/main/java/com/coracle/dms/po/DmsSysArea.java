/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel(description = "DMS系统_区域表")
public class DmsSysArea extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("区域名称")
    private String name;
    @ApiModelProperty("区域编码")
    private String areaCode;
    @ApiModelProperty("有效性(1有效0失效)")
    private Integer active;
    @ApiModelProperty("排序")
    private Integer sortOrder;
    @ApiModelProperty("父id")
    private Long parentAreaId;
    @ApiModelProperty("id路径")
    private String areaPath;
    @ApiModelProperty("全路径")
    private String fullPath;

    //用于接收前端管理地区参数，不作数据库持久化处理
    @ApiModelProperty("用于接收前端管理地区参数")
    private String regionId;
    @ApiModelProperty("用于返回前端管理地区的中文字符串")
    private String regionName;
    //安井-区域FID
    private String anjoyId;

    private static final long serialVersionUID = 1L;

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
        this.name = name == null ? null : name.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
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

    public Long getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(Long parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public String getAreaPath() {
        return areaPath;
    }

    public void setAreaPath(String areaPath) {
        this.areaPath = areaPath == null ? null : areaPath.trim();
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath == null ? null : fullPath.trim();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAnjoyId() {
        return anjoyId;
    }

    public void setAnjoyId(String anjoyId) {
        this.anjoyId = anjoyId;
    }
}