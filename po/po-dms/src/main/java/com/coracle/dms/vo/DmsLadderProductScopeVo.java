package com.coracle.dms.vo;

import com.coracle.dms.po.DmsLadderPriceScope;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2018-1-12.
 */
@ApiModel("阶梯价格适用范围VO")
public class DmsLadderProductScopeVo extends DmsLadderPriceScope {

    @ApiModelProperty("适用范围名称")
    private String scopeName;

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }




}
