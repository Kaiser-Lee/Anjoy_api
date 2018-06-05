package com.coracle.dms.vo;

import com.coracle.dms.po.DmsContactInfo;
import com.coracle.dms.po.DmsEmployee;
import com.coracle.dms.po.DmsEmployeeOrganization;
import com.coracle.dms.po.DmsUser;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DmsEmployeeVo extends DmsEmployee {

    @ApiModelProperty("员工关联的账号")
    private String account;

    @ApiModelProperty("密码明文")
    private String plainPassword;

    @ApiModelProperty("角色id")
    private Long roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("组织")
    private String organization;

    @ApiModelProperty("组织dept")
    private String dept;

    @ApiModelProperty("岗位")
    private String post;

    @ApiModelProperty("性别")
    private String sexText;

    @ApiModelProperty("状态")
    private String statusText;

    @ApiModelProperty("联系信息关联表类型")
    private Integer contactInfoType;

    @ApiModelProperty("联系信息列表")
    private List<DmsContactInfo> contactInfoList;

    @ApiModelProperty("员工-组织关联信息")
    private List<DmsEmployeeOrganizationVo> employeeOrganizationList;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getSexText() {
        return sexText;
    }

    public void setSexText(String sexText) {
        this.sexText = sexText;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Integer getContactInfoType() {
        return contactInfoType;
    }

    public void setContactInfoType(Integer contactInfoType) {
        this.contactInfoType = contactInfoType;
    }

    public List<DmsContactInfo> getContactInfoList() {
        return contactInfoList;
    }

    public void setContactInfoList(List<DmsContactInfo> contactInfoList) {
        this.contactInfoList = contactInfoList;
    }

    public List<DmsEmployeeOrganizationVo> getEmployeeOrganizationList() {
        return employeeOrganizationList;
    }

    public void setEmployeeOrganizationList(List<DmsEmployeeOrganizationVo> employeeOrganizationList) {
        this.employeeOrganizationList = employeeOrganizationList;
    }
    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
