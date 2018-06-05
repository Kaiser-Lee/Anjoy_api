package com.coracle.dms.vo;

import com.coracle.dms.po.DmsOrganization;
import com.coracle.dms.po.DmsOrganizationArea;
import com.coracle.dms.po.DmsSysArea;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DmsOrganizationVo extends DmsOrganization{

    @ApiModelProperty("参数: 管理区域id列表")
    private List<Long> areaIdList;

    @ApiModelProperty("组织管理的区域列表")
    private List<DmsSysArea> areaList;

    public List<Long> getAreaIdList() {
        return areaIdList;
    }

    public void setAreaIdList(List<Long> areaIdList) {
        this.areaIdList = areaIdList;
    }

    public List<DmsSysArea> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<DmsSysArea> areaList) {
        this.areaList = areaList;
    }
}
