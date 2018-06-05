package com.coracle.dms.xweb.common.constants;

/**
 * Created by yjr on 2017/4/5.
 */
public class WeiXinPayConstans {
    /**
     * 服务号相关信息
     */
    public final static String APPID = "wxa281779d42d7691c";//服务号的应用号
    public final static String MCH_ID = "1457112502";//商户号
    public final static String API_KEY = "2daf6c8021fa4ef0b41338b77213cf1z";//API密钥
    public final static String NOTIFY_URL = "http://positec.server.ngrok.cc/xweb/api/v2/weiXinPay/wxNotify";//微信回调地址
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //参数异常
    public final static String PARM_FAIL="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[参数错误]]></return_msg></xml>";
    //支付成功
    public final static String PAY_SUCCESS="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    //报文为空
    public final static String PARM_IS_NULL="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml>";
    //签名验证失败
    public final static String SIGN_FAIL="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[通知签名验证失败]]></return_msg></xml>";
   //流水不存在
    public final static String NO_ORDER="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[无法获取流水信息]]></return_msg></xml>";


}
