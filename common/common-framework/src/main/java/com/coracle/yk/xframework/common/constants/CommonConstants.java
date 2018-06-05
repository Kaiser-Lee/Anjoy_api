package com.coracle.yk.xframework.common.constants;

import java.math.BigDecimal;

/**
 * Created by huangbaidong on 2017/3/16.
 */
public class CommonConstants {

    //品牌商
    public static final int EMPLOYEE_TYPE_PPS = 0;

    //服务商
    public static final int EMPLOYEE_TYPE_FWS = 1;

    //零售商
    public static final int EMPLOYEE_TYPE_LSS = 2;

    //客服
    public static final int EMPLOYEE_TYPE_KF = 3;

    //仓管
    public static final int EMPLOYEE_TYPE_CG = 4;
    //管理员
    public static final int EMPLOYEE_TYPE_GLY = 5;
    //品牌商客服
    public static final int EMPLOYEE_TYPE_PPSKF = 6;
    //销售员
    public static final int EMPLOYEE_TYPE_XSY = 7;
    //销售支持
    public static final int EMPLOYEE_TYPE_XSZC = 8;

    //是/有效
    public static final int YES = 1;

    //否/无效
    public static final int NO = 0;


    /**
     * 尾款支付通知时间（15分钟）
     */
    public static final long WKNoticeTime = 15 * 60 * 1000;
    /**
     * 有促销规则未付款订单付款通知时间（45分钟）
     */
    public static final long hasRuleNoticeTime = 45 * 60 * 1000;
    /**
     * 有促销规则未付款超时时间（1小时）
     */
    public static final long hasRulePayTimeout = 60 * 60 * 1000;

    /**
     * 自动收货时间（20天）
     */
    public static final long autoReceivingTime = 20 * 24 * 60 * 60 * 1000;

    /**
     * 线下支付超时时间（15天）
     */
    public static final long offlinePayTimeOut = 15 * 24 * 60 * 60 * 1000;

    /**
     * 线下支付超时提醒时间（14天）
     */
    public static final long offlineNoticeTime = 14 * 24 * 60 * 60 * 1000;

    /**
     * 没有促销规则未付款订单付款通知时间（23小时）
     */
    public static final long hasNotRuleNoticeTime = 23 * 60 * 60 * 1000;
    /**
     * 没有促销规则未付款超时时间（24小时）
     */
    public static final long hasNotRulePayTimeout = 24 * 60 * 60 * 1000;

    /**
     * 押金
     */
    public static final BigDecimal DEPOSIT=new BigDecimal("0.01");

    /**
     * 已经未付押金
     */
    public static final Long DEPOSIT_STATUS_FALSE=0l;

    /**
     * 未支已经押金
     */
    public static final Long DEPOSIT_STATUS_TRUE=1l;


    //定金状态（未付款）
    public static final int DEPOSIT_STATUS_WFK = 0;

    //定金状态（已付定金）
    public static final int DEPOSIT_STATUS_YFDJ = 1;

    //定金状态（已付尾款）
    public static final int DEPOSIT_STATUS_YFWK = 2;




    /**
     * 支付方式（支付宝支付）
     */
    public static final int PAY_SERVER_TYPE_ALI_PAY=1;

    /**
     * 支付方式（微信支付）
     */
    public static final int PAY_SERVER_TYPE_WECHAT = 2;

    /**
     * 支付方式（农行银联）
     */
    public static final int PAY_SERVER_TYPE_UNIONPAY = 3;

    /**
     * 支付方式（线下支付）
     */
    public static final int PAY_SERVER_TYPE_OFFLINE = 0;


    /**
     * 付款状态（待付款）
     */
    public static final int PAY_STATUS_WAIT=1;

    /**
     * 付款状态（已付款）
     */
    public static final int PAY_STATUS_OVER=2;

    /**
     * 付款状态（已关闭）
     */
    public static final int PAY_STATUS_CLOSE=3;

    /**
     * 付款状态（回调异常）
     */
    public static final int PAY_STATUS_CALLBACK_EXCEPTION=4;


    /**
     * 付款订单类型（支付押金）
     */
    public static final int PAY_ORDER_TYPE_DEPOSIT=0;


    /**
     * 付款订单类型（支付订单）
     */
    public static final int PAY_ORDER_TYPE_ORDER=1;

    /**
     * 订单支付类型（定金）
     */
    public static final int ORDER_PAY_TYPE_DEPOSIT=1;

    /**
     * 订单支付类型（尾款）
     */
    public static final int ORDER_PAY_TYPE_RETAINAGE=2;


    /**
     * 订单支付类型（全款）
     */
    public static final int ORDER_PAY_TYPE_PAYMENT =3;

    /**
     * 审批状态（待提交）
     */
    public static final int APPROVE_STATUS_DTJ = 0;

    /**
     * 审配状态（待审批）
     */
    public static final int APPROVE_STATUS_DSP = 1;

    /**
     * 审配状态（审批通过）
     */
    public static final int APPROVE_STATUS_TG = 2;

    /**
     * 审配状态（审批不通过）
     */
    public static final int APPROVE_STATUS_BTG = 3;


    /**
     * 优惠券类型（积分兑换优惠券）
     */
    public static final int COUPON_TYPE_POINT=1;

    /**
     * 优惠券类型（系统发放优惠券）
     */
    public static final int COUPON_TYPE_SYSTEM=2;

    /**
     * 第三方对接系统SAP系统
     */
    public static final int SOAP_SYSTEM_TYPE_SAP = 1;

    /**
     * 第三方对接系统BMS系统
     */
    public static final int SOAP_SYSTEM_TYPE_BMS = 2;

    /**
     * 发货方式APP
     */
    public static final int DESPATCH_MODE_APP = 1;

    /**
     * 发货方式PAD
     */
    public static final int DESPATCH_MODE_PDA = 2;

    /**
     * 提交订单（购物车）
     */
    public static final int ORDER_SUBMIT_WAY_SHOPPING_CART = 1;

    /**
     * 提交订单（立即购买）
     */
    public static final int ORDER_SUBMIT_WAY_BUY = 2;

    /**
     * 提交订单（预售规则）
     */
    public static final int ORDER_SUBMIT_WAY_PRESELL = 3;

    /**
     * 提交订单（搭配销售）
     */
    public static final int ORDER_SUBMIT_WAY_TIE_IN_SALE = 4;

    /**
     * 支付方式：在线支付
     */
    public static final int PAY_WAY_ONLINE = 1;

    /**
     * 支付方式：公司转账
     */
    public static final int PAY_WAY_OFFLINE = 2;

}
