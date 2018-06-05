package com.coracle.yk.xframework.common;


/**
 * 南通魅力需求部分常量
 * @author sunyu_000
 *
 */
public interface OAConstants {
	
	/*
	 * 时间类型-上班
	 */
	public final static Integer TIME_TYPE_GO_TO_WORK = 0;
	/*
	 * 时间类型-上午下班
	 */
	public final static Integer TIME_TYPE_MORNING_GO_OFF_WORK = 1;
	/*
	 * 时间类型-外出
	 */
	public final static Integer TIME_TYPE_GO_OUT = 2;
	/*
	 * 时间类型-下午上班
	 */
	public final static Integer TIME_TYPE_AFTERNOON_GO_TO_WORK = 3;
	/*
	 * 时间类型-下班
	 */
	public final static Integer TIME_TYPE_GO_OFF_WORK = 4;
	/*
	 * 时间类型-任意时间
	 */
	public final static Integer TIME_TYPE_ANY_TIME = 5;
	
	/*
	 * 签到类型-上下班签到
	 */
	public final static Integer SIGN_TYPE_WORK = 0;
	/*
	 * 签到类型-外出签到
	 */
	public final static Integer SIGN_TYPE_GO_OUT = 1;
	
	/*
	 * 签到状态-异常
	 */
	public final static Integer SIGN_STATUS_UNUSUAL = 0;
	/*
	 * 签到状态-正常
	 */
	public final static Integer SIGN_STATUS_NORMAL = 1;
	/*
	 * 签到状态-补卡
	 */
	public final static Integer SIGN_STATUS_FILL = 2;
	/*
	 * 签到状态-外出签到
	 */
	public final static Integer SIGN_STATUS_GO_OUT = 3;
	
	/*
	 * 考勤方式-签到考勤
	 */
	public final static Integer CHECK_SIGN = 0;
	/*
	 * 考勤方式-拍照签到
	 */
	public final static Integer CHECK_PHOTO = 1;
	
	/*
	 * 签到提醒-提醒类型-班次
	 */
	public final static String SIGN_ALERT_ATTENDAN = "ATTENDAN";
	/*
	 * 签到提醒-提醒类型-公共
	 */
	public final static String SIGN_ALERT_COMMOND = "COMMOND";

}
