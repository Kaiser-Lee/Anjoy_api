package com.coracle.dms.vo;

import com.coracle.dms.po.DmsInfoComment;

/**
 * Created by Administrator on 2017/8/25.
 */
public class DmsInfoCommentVo extends DmsInfoComment {
    private Integer source;//账号来源类型表

    private Long staffId;//对应表id

    private String organizationName;

    private String photoUrl;

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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
