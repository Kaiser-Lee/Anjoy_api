package com.coracle.yk.base.vo;

public class YunTongXunMsgContent {
	/*
	 * {
	  "pushType":"1",	//推送类型，1：个人，2：群组，默认为1 
	  "appId":"aaf98f8951858ab801518b9f27420bcc",	//appid	
	  "sender":"13291217102",		                //c_id
	  "receiver":["18201370642","13121353225"],		//e_id
	  "msgType":"1",	                            //消息类型，1：文本消息，2：语音消息，3：视频消息，4：图片消息，5：位置消息，6：文件 
	  "msgContent":"你好", 							//文本内容，最大长度2048字节，文本和附件二选一，不能都为空 
	  "msgDomain":"yuntongxun",						//扩展字段 
	  "msgFileName":"",								//文件名，最大长度128字节 
	  "msgFileUrl":""                               //文件绝对路径 
	} 
	 */

	private String pushType;
	private String appId;
	private String sender;
	private String[] receiver;
	private String msgType;
	private String msgContent;
	private String msgDomain;
	private String msgFileName;
	private String msgFileUrl;
	
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String[] getReceiver() {
		return receiver;
	}
	public void setReceiver(String[] receiver) {
		this.receiver = receiver;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgDomain() {
		return msgDomain;
	}
	public void setMsgDomain(String msgDomain) {
		this.msgDomain = msgDomain;
	}
	public String getMsgFileName() {
		return msgFileName;
	}
	public void setMsgFileName(String msgFileName) {
		this.msgFileName = msgFileName;
	}
	public String getMsgFileUrl() {
		return msgFileUrl;
	}
	public void setMsgFileUrl(String msgFileUrl) {
		this.msgFileUrl = msgFileUrl;
	}

	
}
