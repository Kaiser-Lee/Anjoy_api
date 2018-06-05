/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel(description = "DMS产品表-大数据类型")
public class DmsProductWithBLOBs extends DmsProduct implements Serializable {
    @ApiModelProperty("图文详情")
    private String graphicDetails;
    @ApiModelProperty("产品参数")
    private String parameter;

    private static final long serialVersionUID = 1L;

    public String getGraphicDetails() {
        return graphicDetails;
    }

    public void setGraphicDetails(String graphicDetails) {
        this.graphicDetails = graphicDetails == null ? null : graphicDetails.trim();
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }
}