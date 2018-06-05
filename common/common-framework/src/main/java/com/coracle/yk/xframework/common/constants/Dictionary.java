package com.coracle.yk.xframework.common.constants;

/**
 * Created by huangbaidong on 2017/3/5.
 */
public class Dictionary {

    //满减金额
    public static final int PROMOTION_RULE_MJJE = 2;
    //满减数量（当用户订单所有适用商品数量之和满足达标数量时，订单里的所有适用商品按优惠方式给予优惠）
    public static final int PROMOTION_RULE_MJSL = 3;

    //限时限量
    public static final int PROMOTION_RULE_XSXL = 4;
    //预售规则
    public static final int PROMOTION_RULE_YS = 5;
    //满减数量(当用户订单所有适用商品数量之和满足达标数量时，会自动减去一件商品的价格)
    public static final int PROMOTION_RULE_MJSL_2 = 6;
    //搭配销售
    public static final int PROMOTION_RULE_TIE_IN_SALE = 7;
    //阶梯促销1（单个产品按照单次下单的量取不同值。如达标5件，促销价格为299；达标10件，促销价格269）
    public static final int PROMOTION_RULE_LADDER_1 = 8;
    //阶梯促销2
    public static final int PROMOTION_RULE_LADDER_2= 9;

    /**
     * 优惠方式（折扣）
     */
    public static final int PROMOTION_DISCOUNT_TYPE_ZK = 11;

    /**
     * 优惠方式（金额）
     */
    public static final int PROMOTION_DISCOUNT_TYPE_JE = 12;


    /**
     * 定金优惠方式（折扣）
     */
    public static final int DEPOSIT_DISCOUNT_TYPE_ZK = 71;

    /**
     * 定金优惠方式（金额）
     */
    public static final int DEPOSIT_DISCOUNT_TYPE_JE = 72;


    //积分类型（评论）
    public static final long SCORE_TYPE_COMMENT = 19;

    //积分类型（转发）
    public static final long SCORE_TYPE_FOWORD = 20;

    //积分类型（订单）
    public static final long SCORE_TYPE_ORDER = 21;

    //积分类型（兑换）
    public static final long SCORE_TYPE_CHANGE = 52;

    //积分类型（订单）
    public static final long SCORE_TYPE_BBS = 22;

    //积分类型（订单评价）
    public static final long SCORE_TYPE_DDPJ = 96;

    /**
     * 订单状态（待付款）
     */
    public static final int ORDER_STATUS_DFK = 31;

    /**
     * 订单状态（待审批）
     */
    public static final int ORDER_STATUS_DSP = 32;

    /**
     * 订单状态（待发货）
     */
    public static final int ORDER_STATUS_DFH = 33;

    /**
     * 订单状态（待收货）
     */
    public static final int ORDER_STATUS_DSH = 34;

    /**
     * 订单状态（已完成）
     */
    public static final int ORDER_STATUS_YWC = 35;

    /**
     * 订单状态（待退货）
     */
    public static final int ORDER_STATUS_DTH = 36;

    /**
     * 订单状态（已退货）
     */
    public static final int ORDER_STATUS_YTH = 37;

    /**
     * 订单状态（已取消）
     */
    public static final int ORDER_STATUS_YQX = 38;

    /**
     * 订单类型（普通订单）
     */
    public static final int ORDER_TYPE_NORMAL = 41;

    /**
     * 订单类型（预售订单）
     */
    public static final int ORDER_TYPE_PRESELL = 42;

    /**
     * 下单方式（商城下单）
     */
    public static final int ORDER_MODE_SCXD = 51;

    /**
     * 下单方式（积分兑换）
     */
    public static final int ORDER_MODE_JFDH = 52;

}
