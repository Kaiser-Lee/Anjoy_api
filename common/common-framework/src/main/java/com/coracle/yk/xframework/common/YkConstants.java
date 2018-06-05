package com.coracle.yk.xframework.common;

import java.util.regex.Pattern;

public final class YkConstants {
	public final static int USE_FLAG_INVALID = 0;
	public final static int USE_FLAG_VALID = 1;
	/**
	 * remove_flag标志：已删除
	 */
	public final static int REMOVE_FLAG_INVALID = 1;
	/**
	 * remove_flag标志：未删除
	 */
	public final static int REMOVE_FLAG_VALID = 0;
	/**
	 * 用户status状态:失效
	 */
	public final static int USE_STATUS_INVALID = 1;
	/**
	 * 用户status状态:激活
	 */
	public final static int USER_STATUS_VALID = 0;
	/**
	 * 手机号匹配符
	 */
	public final static Pattern pattern = Pattern.compile("^1\\d{10}$");
	/**
	 * 消息由顾客发送
	 */
	public final static short SENT_BY_CUSTOMER = 1;
	/**
	 * 消息由导购发送
	 */
	public final static short SENT_BY_SELLER = 0;
	/**
	 * 消息已读
	 */
	public final static short IS_READ = 1;
	/**
	 * 消息未读
	 */
	public final static short NOT_READ = 0;
	/**
	 * 订单产品类型：成交
	 */
	public final static int TRACK_TYPE_DEAL = 1;
	/**
	 * 订单产品类型：意向
	 */
	public final static int TRACK_TYPE_INTENTION = 0;
	/**
	 * 下订产品类型，下订产品
	 */
	public final static int DEPOSITE_TYPE_VALUE=2;

	/**
	 * 成熟度：成交
	 */
	public final static int MATURE_DEAL = 2;
	/**
	 * 成熟度：丢单
	 */
	public final static int MATURE_LOST = 3;
	/**
	 * 动作关键属性：是
	 */
	public final static int ACTION_KEY_FLAG = 1;
	/**
	 * 动作关键属性：否
	 */
	public final static int ACTION_KEY_FLAG_NOT = 0;
	/**
	 * 默认顾客来源：2.自然顾客
	 */
	public final static int CUSTOMER_SOURCE_DEFAULT = 2;
	/**
	 * 默认顾客等级：1.普通
	 */
	public final static int CUSTOMER_LEVEL_DEFAULT = 1;
	/**
	 * 默认入库方式：导购录入
	 */
	public final static int INPUT_TYPE_DEFAULT = 3;
	/**
	 * 顾客列表是否我的顾客字段：是我的顾客
	 */
	public final static int IS_MY_CUSTOMER = 1;
	/**
	 * 顾客列表是否我的顾客字段：不是我的顾客
	 */
	public final static int IS_NOT_MY_CUSTOMER = 0;
	/**
	 * 默认性别：0，未知
	 */
	public final static int SEX_DEFAULT = 0;
	/**
	 * 默认实名：1，实名
	 */
	public final static int IS_REAL_NAME_DEFAULT = 1;
	/**
	 * 商铺二维码
	 */
	public final static int QRCODE_TYPE_MALL = 0;
	/**
	 * 门店二维码
	 */
    public final static int QRCODE_TYPE_STORE = 1;
    /**
     * 导购二维码
     */
    public final static int QRCODE_TYPE_SELLER = 2;
    /**
     * 产品二维码
     */
    public final static int QRCODE_TYPE_PRODUCT = 3;
    /** 
     * 二维码已使用的
     */
    public final static int QRCODE_USED_YES = 1;
    /** 
     * 二维码未使用的
     */
    public final static int QRCODE_USED_NO = 0;
    /** 
     * 临时二维码
     */
    public final static int QRCODE_IS_TEMPORARAY_YES = 1;
    /** 
     * 固定二维码
     */
    public final static int QRCODE_IS_TEMPORARAY_NO = 0;

	/**
	 * 机会允许添加标签的最大个数
	 */
    public final static int TRACK_ALLOW_MAX_VALUE=5;
		
	public static final String DELIMITER = "\1";
	
	/**
	 * 推送状态：初始化
	 */
	public final static int PUSH_STATUS_INIT = -1;
	/**
	 * 推送状态：已推送
	 */
	public final static int PUSH_STATUS_PUSHED = 1;
	
}




