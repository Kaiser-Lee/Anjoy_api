package com.coracle.dms.vo;

import com.coracle.dms.po.DmsEmployeeOrganization;
import io.swagger.annotations.ApiModelProperty;

public class DmsEmployeeOrganizationVo extends DmsEmployeeOrganization {
    @ApiModelProperty("组织")
    private String organization;

    @ApiModelProperty("岗位")
    private String post;

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
