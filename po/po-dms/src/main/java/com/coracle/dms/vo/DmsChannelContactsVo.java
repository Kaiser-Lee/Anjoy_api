package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.coracle.dms.po.DmsChannelContacts;
import com.coracle.dms.po.DmsContactInfo;
import com.coracle.dms.po.DmsUser;

public class DmsChannelContactsVo extends DmsChannelContacts{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 联系人联系方式集合 */
	@ApiModelProperty("联系人联系方式集合")
	private List<DmsContactInfo> contactInfoList;
	@ApiModelProperty("手机号码")
	private String mobile;
	@ApiModelProperty(value = "性别文本",hidden = true)
    private String sexText;
    @ApiModelProperty(value = "岗位文本",hidden = true)
    private String postText;
    @ApiModelProperty(value = "所属公司文本",hidden = true)
    private String channelText;
    @ApiModelProperty(value = "手机号码",hidden = true)
    private String mobileText;
    @ApiModelProperty(value = "座机",hidden = true)
    private String phoneText;
    @ApiModelProperty(value = "邮箱",hidden = true)
    private String emailText;
    @ApiModelProperty(value = "QQ",hidden = true)
    private String qqText;
    @ApiModelProperty(value = "微信",hidden = true)
    private String weiText;
    @ApiModelProperty(value = "用户账号信息",hidden = true)
    private DmsUser dmsUser;
    
	public List<DmsContactInfo> getContactInfoList() {
		return contactInfoList;
	}

	public void setContactInfoList(List<DmsContactInfo> contactInfoList) {
		this.contactInfoList = contactInfoList;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getPhoneText() {
		return phoneText;
	}

	public void setPhoneText(String phoneText) {
		this.phoneText = phoneText;
	}

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
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

	public String getChannelText() {
		return channelText;
	}

	public void setChannelText(String channelText) {
		this.channelText = channelText;
	}
	
	

}
