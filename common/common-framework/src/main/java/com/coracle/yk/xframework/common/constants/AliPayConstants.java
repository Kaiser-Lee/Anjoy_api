package com.coracle.yk.xframework.common.constants;

/**
 * Describe:
 * Created by:liaodz
 * Created on:2017/4/10
 * Version:
 */
public class AliPayConstants {

    /**
     * 交易状态（交易创建，等待买家付款）
     */
    public static final String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";

    /**
     * 交易状态（未付款交易超时关闭，或支付完成后全额退款）
     */
    public static final String TRADE_STATUS_TRADE_CLOSED = "TRADE_CLOSED";

    /**
     * 交易状态（交易支付成功）
     */
    public static final String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

    /**
     * 交易状态（交易结束，不可退款）
     */
    public static final String TRADE_STATUS_TRADE_FINISHED = "TRADE_FINISHED";
}
