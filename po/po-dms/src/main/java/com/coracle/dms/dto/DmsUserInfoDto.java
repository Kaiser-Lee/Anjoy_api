package com.coracle.dms.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25.
 */
public class DmsUserInfoDto implements Serializable {
    private Integer source;//账号来源类型表

    private Long staffId;//对应表id

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
}
