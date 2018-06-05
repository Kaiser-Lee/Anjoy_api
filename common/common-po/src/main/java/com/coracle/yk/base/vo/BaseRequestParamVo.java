package com.coracle.yk.base.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbaidong on 2017/3/13.
 */
public class BaseRequestParamVo implements Serializable {

    @ApiModelProperty("ID数组")
    private List<Long> ids;

    @ApiModelProperty("类型")
    private Long type;

    @ApiModelProperty("状态")
    private Long status;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
