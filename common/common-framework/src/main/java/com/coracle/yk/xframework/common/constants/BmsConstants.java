package com.coracle.yk.xframework.common.constants;

/**
 * Created by huangbaidong
 * 2017/5/25.
 */
public class BmsConstants {

    //SAP客户主数据
    public static final int API_1 = 1;

    //SAP客户主数据-新增零售商接口
    public static final int API_2 = 2;

    //定时同步商品库存
    public static final int API_5 = 5;

    //订单完成付款时调用
    public static final int API_21 = 21;

    //APP发货扫码时调用
    public static final int API_22 = 22;

    //选择APP发货，完成发货时调用
    public static final int API_23 = 23;

    //零售商确认收货时调用
    public static final int API_25 = 25;

    //选择PDA发货时调用
    public static final int API_26 = 26;

    //盘点扫码时验证产品条码合法性时地道用
    public static final int API_51 = 51;

    //盘点扫码完毕后调用
    public static final int API_52 = 52;

    //接口05：BMS库存数据（服务商/零售商）
    public static String Api_05_GetStock = "GetStock";

    //接口22：条码合法性校验
    public static String Api_22_SendOutCheck = "SendOutCheck";

    //接口23：APP发货单 （ APP to BMS ）
    public static String Api_23_SendOut = "SendOut";

    //接口25：零售商完成收货（ APP to BMS ）
    public static String Api_25_RecvIn = "RecvIn";

    //接口26：同步APP订单SO （ APP to BMS）
    public static String Api_26_SendOrder = "SendOrder";

    //接口51：盘点条码合法性检查 （ APP to BMS ）
    public static String Api_51_STKCheck = "STKCheck";

    //接口52：同步APP盘点单 （ APP to BMS ）
    public static String Api_52_STKOrder = "STKOrder";
}
