package com.coracle.dms.vo;

import com.coracle.dms.po.DmsContactInfo;
import com.coracle.dms.po.DmsSeller;
import com.coracle.dms.po.DmsUser;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/19.
 */
public class DmsSellerVo extends DmsSeller {
    @ApiModelProperty("联系信息列表")
    private List<DmsContactInfo> dmsContactInfoList;
    private List<Map<String,Object>> dmsContactInfoMapList;
    @ApiModelProperty(value = "性别文本",hidden = true)
    private String sexText;
    @ApiModelProperty(value = "岗位文本",hidden = true)
    private String postText;
    @ApiModelProperty(value = "手机号码，取第一个",hidden = true)
    private String mobileText;
    @ApiModelProperty(value = "邮箱",hidden = true)
    private String emailText;
    @ApiModelProperty(value = "座机",hidden = true)
    private String phoneText;
    @ApiModelProperty(value = "QQ",hidden = true)
    private String qqText;
    @ApiModelProperty(value = "微信",hidden = true)
    private String weiText;
    @ApiModelProperty(value = "用户账号信息",hidden = true)
    private DmsUser dmsUser;
    @ApiModelProperty(value = "门店名称",hidden = true)
    private String storeName;
    @ApiModelProperty("是否开通账号：1-开通账号,其他不开通")
    private Integer isOpenAccount;

    public List<DmsContactInfo> getDmsContactInfoList() {
        return dmsContactInfoList;
    }

    public void setDmsContactInfoList(List<DmsContactInfo> dmsContactInfoList) {
        this.dmsContactInfoList = dmsContactInfoList;
    }

    public List<Map<String, Object>> getDmsContactInfoMapList() {
        return dmsContactInfoMapList;
    }

    public void setDmsContactInfoMapList(List<Map<String, Object>> dmsContactInfoMapList) {
        this.dmsContactInfoMapList = dmsContactInfoMapList;
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

    public String getMobileText() {
        return mobileText;
    }

    public void setMobileText(String mobileText) {
        this.mobileText = mobileText;
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

    public DmsUser getDmsUser() {
        return dmsUser;
    }

    public void setDmsUser(DmsUser dmsUser) {
        this.dmsUser = dmsUser;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(Integer isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }
}
