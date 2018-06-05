package com.coracle.dms.vo;

import com.coracle.dms.po.DmsPromotionScope;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("促销适用范围VO")
public class DmsPromotionScopeVo extends DmsPromotionScope {
    @ApiModelProperty("适用范围名称")
    private String scopeName;

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }
}
