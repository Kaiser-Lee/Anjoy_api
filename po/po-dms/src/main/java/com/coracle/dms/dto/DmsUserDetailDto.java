package com.coracle.dms.dto;

import com.coracle.dms.po.DmsContactInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
public class DmsUserDetailDto implements Serializable {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("头像文件地址")
    private String photoUrl;
    @ApiModelProperty("来源：1-员工(dms_employee)；2-渠道商(dms_channel_contacts)；3-门店(dms_seller)")
    private Integer source;
    @ApiModelProperty("根据账号来源，关联人员id：1-dms_employee；2-dms_channel_contacts；3-dms_seller")
    private Long staffId;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty(value = "性别文本",hidden = true)
    private String sexText;
    @ApiModelProperty("生日")
    private Date birthday;
    @ApiModelProperty(value = "状态：0-禁用；1-启用；2-待审核",hidden = true)
    private Integer status;
    @ApiModelProperty("联系信息列表")
    private List<DmsContactInfo> dmsContactInfoList;
    @ApiModelProperty(value = "个人信息详情用：手机号码字符串列表",hidden = true)
    private List<String> mobileTextList;
    @ApiModelProperty(value = "个人信息详情用：邮箱",hidden = true)
    private String emailText;
    @ApiModelProperty(value = "个人信息详情用：座机",hidden = true)
    private String phoneText;
    @ApiModelProperty(value = "个人信息详情用：QQ",hidden = true)
    private String qqText;
    @ApiModelProperty(value = "个人信息详情用：微信",hidden = true)
    private String weiText;
    @ApiModelProperty(value = "账号审核用：申请人名称",hidden = true)
    private String applyName;
    @ApiModelProperty("岗位")
    private String post;
    @ApiModelProperty(value = "岗位文本",hidden = true)
    private String postText;
    @ApiModelProperty(value = "账号审核用：账号审核用：渠道名称",hidden = true)
    private String channelName;
    @ApiModelProperty(value = "账号审核用：门店名称",hidden = true)
    private String storeName;
    @ApiModelProperty(value = "账号审核用：系统内组织名称",hidden = true)
    private String orgName;
    @ApiModelProperty(value = "账号审核用：角色名称",hidden = true)
    private String roleName;
    @ApiModelProperty(value = "账号审核用：申请时间",hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyDate;
    @ApiModelProperty(value = "账号审核用：审核名称",hidden = true)
    private String auditName;
    @ApiModelProperty(value = "账号审核用：审核时间",hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date auditDate;
    @ApiModelProperty(value = "账号审核用：审核意见",hidden = true)
    private String auditOpinion;
    @ApiModelProperty(value = "账号审核用：审核备注",hidden = true)
    private String auditRemark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<DmsContactInfo> getDmsContactInfoList() {
        return dmsContactInfoList;
    }

    public void setDmsContactInfoList(List<DmsContactInfo> dmsContactInfoList) {
        this.dmsContactInfoList = dmsContactInfoList;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public List<String> getMobileTextList() {
        return mobileTextList;
    }

    public void setMobileTextList(List<String> mobileTextList) {
        this.mobileTextList = mobileTextList;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

    public String getPhoneText() {
        return phoneText;
    }

    public void setPhoneText(String phoneText) {
        this.phoneText = phoneText;
    }

    public String getQqText() {
        return qqText;
    }

    public void setQqText(String qqText) {
        this.qqText = qqText;
    }

    public String getWeiText() {
        return weiText;
    }

    public void setWeiText(String weiText) {
        this.weiText = weiText;
    }

    public String getSexText() {
        return sexText;
    }

    public void setSexText(String sexText) {
        this.sexText = sexText;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
