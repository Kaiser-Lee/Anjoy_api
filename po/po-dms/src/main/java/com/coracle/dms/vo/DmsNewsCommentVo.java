package com.coracle.dms.vo;

import com.coracle.dms.po.DmsNewsComment;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/8/24.
 */
public class DmsNewsCommentVo extends DmsNewsComment {
    @ApiModelProperty("用户头像url")
    private String imgUrl;

    private Integer source;

    private Long staffId;

    private String organizationName;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

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
