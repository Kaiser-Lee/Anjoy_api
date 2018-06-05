package com.coracle.yk.xframework.po;

import java.io.Serializable;
import java.util.List;

/**
 * @title：
 * @describe：
 * @copyright：Copyright (c) 1997-2017 coracle, Inc.
 * @company：圆舟科技
 * @author：taok
 * @date：2018-03-21 14:37
 * @version：1.0
 */
public class DmsChannelEmployeeVo implements Serializable {
    private static final long serialVersionUID = -4992957979062412363L;

    private Long salesmanId;//业务员ID
    private String salesmanName;//业务员名称
    private List<DmsChannelMutilVo> dmsChannelList;//业务员（渠道集合）

    public Long getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Long salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public List<DmsChannelMutilVo> getDmsChannelList() {
        return dmsChannelList;
    }

    public void setDmsChannelList(List<DmsChannelMutilVo> dmsChannelList) {
        this.dmsChannelList = dmsChannelList;
    }
}
