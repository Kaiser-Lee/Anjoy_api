package com.coracle.dms.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/30.
 */
public class DmsTreeRelationDto implements Serializable {
    private Long userId;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {

        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
