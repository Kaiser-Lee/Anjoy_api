package com.coracle.dms.dto;

import com.coracle.dms.po.DmsCustomers;
import com.coracle.dms.vo.DmsCustomersVo;
import com.coracle.dms.vo.DmsDynamicVo;
import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/5.
 */
public class DmsRetailOutStorageDto extends AdapterEntity implements Serializable {
    @ApiModelProperty("客户信息")
    private DmsCustomersVo dmsCustomersVo;
    @ApiModelProperty("客户动态信息及产品信息")
    private DmsDynamicVo dmsDynamicVo;

    public DmsDynamicVo getDmsDynamicVo() {
        return dmsDynamicVo;
    }

    public void setDmsDynamicVo(DmsDynamicVo dmsDynamicVo) {
        this.dmsDynamicVo = dmsDynamicVo;
    }

    public DmsCustomersVo getDmsCustomersVo() {
        return dmsCustomersVo;
    }

    public void setDmsCustomersVo(DmsCustomersVo dmsCustomersVo) {
        this.dmsCustomersVo = dmsCustomersVo;
    }
}
