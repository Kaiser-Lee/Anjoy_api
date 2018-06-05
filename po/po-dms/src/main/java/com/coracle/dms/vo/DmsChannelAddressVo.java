package com.coracle.dms.vo;

import com.coracle.dms.po.DmsChannelAddress;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2018-1-16.
 */
public class DmsChannelAddressVo  extends DmsChannelAddress {


    @ApiModelProperty(value = "省份文本",hidden = true)
    private String provinceText;
    @ApiModelProperty(value = "城市文本",hidden = true)
    private String cityText;
    @ApiModelProperty(value = "区县文本",hidden = true)
    private String countyText;

    public String getProvinceText() {
        return provinceText;
    }

    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public String getCountyText() {
        return countyText;
    }

    public void setCountyText(String countyText) {
        this.countyText = countyText;
    }
}
