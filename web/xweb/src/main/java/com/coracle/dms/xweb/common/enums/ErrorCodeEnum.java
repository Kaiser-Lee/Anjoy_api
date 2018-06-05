package com.coracle.dms.xweb.common.enums;

import com.coracle.yk.xframework.util.StringUtil;

/**
 * ErrorCode以及ErrorMessage
 * @author sunyu_000
 *
 */
public enum ErrorCodeEnum {
	DEFAULT("000", "SERVICE_UNAVAILABLE", "服务不可用"),
	SUCCESS("200", "SUCCESS", "操作成功"),
	INCORRECT_CLIENT_VERSION("201", "INCORRECT_CLIENT_VERSION", "客户端版本不对，需升级sdk"),
	BLOCKED("301", "BLOCKED", "被封禁"),
	INCORRECT_USERNAME_OR_PASSWORD("302", "INCORRECT_USERNAME_OR_PASSWORD", "用户名或密码错误"),
	IP_LIMIT("315", "IP_LIMIT", "IP限制"),
	ILLEGAL_OPERATION_OR_NO_AUTHORIZATION("403", "ILLEGAL_OPERATION_OR_NO_AUTHORIZATION", "非法操作或没有权限"),
	OBJECT_NOT_EXIST("404", "OBJECT_NOT_EXIST", "对象不存在"),
	PARAMETER_TOO_LONG("405", "PARAMETER_TOO_LONG", "参数长度过长"),
	OBJECT_READ_ONLY("406", "OBJECT_READ_ONLY", "对象只读"),
	CLIENT_REQUEST_TIMEOUT("408", "CLIENT_REQUEST_TIMEOUT", "客户端请求超时"),
	SMS_VERIFICATION_FAILED("413", "SMS_VERIFICATION_FAILED", "验证失败(短信服务)"),
	INCORRECT_PARAMETER("414", "INCORRECT_PARAMETER", "参数错误"),
	CLIENT_NETWORK_EXCEPTION("415", "CLIENT_NETWORK_EXCEPTION", "客户端网络问题"),
	FREQUENCY_CONTROL("416", "FREQUENCY_CONTROL", "频率控制"),
	AUTO_LOGIN_FAILURE("417", "AUTO_LOGIN_FAILURE", "自动登录失效"),
	SMS_CHANEL_UNAVAILABLE("418", "SMS_CHANEL_UNAVAILABLE", "通道不可用(短信服务)"),
	AMOUNT_UPPER_LIMIT("419", "AMOUNT_UPPER_LIMIT", "数量超过上限"),
	ACCOUNT_UNAVAILABLE("422", "ACCOUNT_UNAVAILABLE", "账号被禁用"),
	HTTP_REPEAT_REQUEST("431", "HTTP_REPEAT_REQUEST", "HTTP重复请求"),
	INTERNAL_SERVER_ERROR("500", "INTERNAL_SERVER_ERROR", "服务器内部错误"),
	SERVER_BUSY("503", "SERVER_BUSY", "服务器繁忙"),
	INVALID_PROTOCOL("509", "INVALID_PROTOCOL", "无效协议"),
	SERVICE_UNAVAILABLE("514", "SERVICE_UNAVAILABLE", "服务不可用"),
	UNPACK_ERROR("998", "UNPACK_ERROR", "解包错误"),
	PACK_ERROR("999", "PACK_ERROR", "打包错误"),
	
	// 群相关错误码 
	REACH_THE_UPPER_LIMIT_OF_THE_NUMBER_OF_GROUP("801", "REACH_THE_UPPER_LIMIT_OF_THE_NUMBER_OF_GROUP", "群人数达到上限"),
	PERMISSION_DENIED("802", "PERMISSION_DENIED", "没有权限"),
	GROUP_NOT_EXIST("803", "GROUP_NOT_EXIST", "群不存在"),
	USER_NOT_IN_GROUP("804", "USER_NOT_IN_GROUP", "用户不在群"),
	GROUP_TYPE_NOT_MATCH("805", "GROUP_TYPE_NOT_MATCH", "群类型不匹配"),
	REACH_THE_UPPER_LIMIT_OF_THE_AMOUNT_OF_GROUP("806", "REACH_THE_UPPER_LIMIT_OF_THE_AMOUNT_OF_GROUP", "创建群数量达到限制"),
	MEMBER_STATUS_ERROR("807", "MEMBER_STATUS_ERROR", "群成员状态错误"),
	APPLY_SUCCESS("808", "APPLY_SUCCESS", "申请成功"),
	ALREADY_IN_GROUP("809", "ALREADY_IN_GROUP", "已经在群内"),
	INVITE_SUCCESS("810", "INVITE_SUCCESS", "邀请成功"),
	
	// 音视频通话相关错误码
	CHANEL_UNAVAILABLE("9102", "CHANEL_UNAVAILABLE", "通道失效"),
	CALL_HAS_BEEN_RESPONSE("9103", "CALL_HAS_BEEN_RESPONSE", "已经在他端对这个呼叫响应过了"),
	
	// 数据通道服务相关错误码 
	CHANEL_REQUEST_UNAVAILABLE("11001", "CHANEL_REQUEST_UNAVAILABLE", "数据通道请求没有可达的被叫者"),
	
	// 聊天室相关错误码 
	IM_SERVER_STATUS_ERROR("13001", "IM_SERVER_STATUS_ERROR", "IM主连接状态异常"),
	CHAT_ROOM_STATUS_ERROR("13002", "CHAT_ROOM_STATUS_ERROR", "聊天室状态异常"),
	USER_IN_BLACKLIST("13003", "USER_IN_BLACKLIST", "账号在黑名单中,不允许进入聊天室"),
	USER_IN_BLOCKLIST("13004", "USER_IN_BLOCKLIST", "在禁言列表中,不允许发言"),
	
	// 特定业务相关错误码 s
	INCORRECT_EMAIL_FORMAT("10431", "INCORRECT_EMAIL_FORMAT", "输入email不是邮箱"),
	INCORRECT_MOBILE_FORMAT("10432", "INCORRECT_MOBILE_FORMAT", "输入mobile不是手机号码"),
	INCORRECT_REPEAT_PASSWORD("10433", "INCORRECT_REPEAT_PASSWORD", "注册输入的两次密码不相同"),
	ENTERPRISE_NOT_EXIST("10434", "ENTERPRISE_NOT_EXIST", "企业不存在"),
	INCORRECT_USERNAME_OR_PASSWORD_OF_BUSINESS("10435", "INCORRECT_USERNAME_OR_PASSWORD_OF_BUSINESS", "登陆密码或帐号不对"),
	APP_NOT_EXIST("10436", "APP_NOT_EXIST", "app不存在"),
	EMAIL_HAS_BEEN_REGISTR("10437", "EMAIL_HAS_BEEN_REGISTR", "email已注册"),
	MOBILE_HAS_BEEN_REGISTR("10438", "MOBILE_HAS_BEEN_REGISTR", "手机号已注册"),
	APP_ALREADY_EXIST("10441", "APP_ALREADY_EXIST", "app名字已经存在");
	 
    private String code;
    private String name;
    private String message;
    
    private ErrorCodeEnum(String code, String name, String message) {
        this.code = code;
        this.name = name;
        this.message = message;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public String getMessage() {
    	return this.message;
    }
    
    public static String getMessage(String code) {
    	if (StringUtil.isBlank(code)) {
    		return null;
    	}
    	for (ErrorCodeEnum e : ErrorCodeEnum.values()) {
    		if (e.getCode().equals(code)) {
    			return e.getMessage();
    		}
    	}
    	
    	return null;
    }
	    
}
