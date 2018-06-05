package com.coracle.dms.vo;

import com.coracle.dms.po.DmsCustomers;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/8/28.
 */
public class DmsCustomersVo extends DmsCustomers {
    @ApiModelProperty(value = "性别文本",hidden = true)
    private String sexText;
    @ApiModelProperty(value = "职业文本",hidden = true)
    private String occupationText;
    @ApiModelProperty(value = "省份文本",hidden = true)
    private String provinceText;
    @ApiModelProperty(value = "城市文本",hidden = true)
    private String cityText;
    @ApiModelProperty(value = "区县文本",hidden = true)
    private String countyText;
    @ApiModelProperty(value = "所属单位",hidden = true)
    private String OrgName;

    public String getSexText() {
        return sexText;
    }

    public void setSexText(String sexText) {
        this.sexText = sexText;
    }

    public String getOccupationText() {
        return occupationText;
    }

    public void setOccupationText(String occupationText) {
        this.occupationText = occupationText;
    }

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

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }
}
