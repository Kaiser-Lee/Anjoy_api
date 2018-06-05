package com.coracle.yk.xframework.po;


import java.io.Serializable;


public class UserSession implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String USER_SESSION_KEY = "user";

	public static final String USER_ROLE_SESSION_KEY = "user_role_interface";

	public static final String NO_LOGIN_MSG = "no login";
	
	private Long id;
	private String userName;
	private Long orgId; // 组织id
	private String orgName;
	private Long superiorId ; // 上级id
	private Integer employeeType;
	private String loginName;
	private String contactUserName;//联系人
	private String orgPath;//组织机构路径
	private String orgCode;
	private String imgUrl;//图像
	private Long serviceProviderId;//所属服务商id
	private String serviceProviderName;//所属服务商name
	private String orgIds;//分管组织id集合
	private Integer level;//用户等级

	private Long roleId;//角色id
	private String roleName;//角色名字
	private String roleCode;//角色code

	private Long superiorOrgId;//上级渠道id
	private String superiorOrgName;//上级渠道名称

	private DmsChannelMutilVo dmsChannelVo;//联系人经销商存储对象

	private DmsChannelEmployeeVo dmsChannelEmployeeVo;//业务员-渠道信息
	private DmsChannelInfoVo dmsChannelInfoVo;//经销商信息
	private boolean showChannelButton;
	private boolean salesman;//当前是否为业务员：true 是
	private String platformType;//PC, APP

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

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public Long getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	public UserSession() {

	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getOrgPath() {
		return orgPath;
	}

	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getSuperiorId() {
		return superiorId;
	}

	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setSuperiorId(Long superiorId) {
		this.superiorId = superiorId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(Integer employeeType) {
		this.employeeType = employeeType;
	}


	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Long getSuperiorOrgId() {
		return superiorOrgId;
	}

	public void setSuperiorOrgId(Long superiorOrgId) {
		this.superiorOrgId = superiorOrgId;
	}

	public String getSuperiorOrgName() {
		return superiorOrgName;
	}

	public void setSuperiorOrgName(String superiorOrgName) {
		this.superiorOrgName = superiorOrgName;
	}

	public DmsChannelMutilVo getDmsChannelVo() {
		return dmsChannelVo;
	}

	public void setDmsChannelVo(DmsChannelMutilVo dmsChannelVo) {
		this.dmsChannelVo = dmsChannelVo;
	}

	public DmsChannelEmployeeVo getDmsChannelEmployeeVo() {
		return dmsChannelEmployeeVo;
	}

	public void setDmsChannelEmployeeVo(DmsChannelEmployeeVo dmsChannelEmployeeVo) {
		this.dmsChannelEmployeeVo = dmsChannelEmployeeVo;
	}

	public DmsChannelInfoVo getDmsChannelInfoVo() {
		return dmsChannelInfoVo;
	}

	public void setDmsChannelInfoVo(DmsChannelInfoVo dmsChannelInfoVo) {
		this.dmsChannelInfoVo = dmsChannelInfoVo;
	}

	public boolean isShowChannelButton() {
		return showChannelButton;
	}

	public void setShowChannelButton(boolean showChannelButton) {
		this.showChannelButton = showChannelButton;
	}

	public boolean getSalesman() {
		return salesman;
	}

	public void setSalesman(boolean salesman) {
		this.salesman = salesman;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
}
