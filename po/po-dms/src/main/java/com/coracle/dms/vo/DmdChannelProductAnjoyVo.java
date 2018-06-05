package com.coracle.dms.vo;

import com.coracle.yk.base.po.AdapterEntity;

import java.io.Serializable;

/**
 * @title：
 * @describe：
 * @copyright：Copyright (c) 1997-2017 coracle, Inc.
 * @company：圆舟科技
 * @author：taok
 * @date：2018-03-04 16:11
 * @version：1.0
 */
public class DmdChannelProductAnjoyVo extends AdapterEntity implements Serializable {
    //安井-渠道编码
    private String anjoyChannelCode;
    //安井-渠道名称
    private String anjoyChannelName;
    //安井-商品编码
    private String anjoyProductCode;
    //安井-商品名称
    private String anjoyProductName;

    public String getAnjoyChannelCode() {
        return anjoyChannelCode;
    }

    public void setAnjoyChannelCode(String anjoyChannelCode) {
        this.anjoyChannelCode = anjoyChannelCode;
    }

    public String getAnjoyChannelName() {
        return anjoyChannelName;
    }

    public void setAnjoyChannelName(String anjoyChannelName) {
        this.anjoyChannelName = anjoyChannelName;
    }

    public String getAnjoyProductCode() {
        return anjoyProductCode;
    }

    public void setAnjoyProductCode(String anjoyProductCode) {
        this.anjoyProductCode = anjoyProductCode;
    }

    public String getAnjoyProductName() {
        return anjoyProductName;
    }

    public void setAnjoyProductName(String anjoyProductName) {
        this.anjoyProductName = anjoyProductName;
    }
}
