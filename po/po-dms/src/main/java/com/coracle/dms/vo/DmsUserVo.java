package com.coracle.dms.vo;

import com.coracle.dms.po.DmsRole;
import com.coracle.dms.po.DmsUser;
import io.swagger.annotations.ApiModelProperty;

public class DmsUserVo extends DmsUser {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色code")
    private String roleCode;

    @ApiModelProperty(value = "明文密码", hidden = true)
    private String plainPassword;

    @ApiModelProperty("账号归属人姓名")
    private String name;

    @ApiModelProperty("来源(文本)")
    private String sourceText;

    @ApiModelProperty("状态(文本)")
    private String statusText;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
