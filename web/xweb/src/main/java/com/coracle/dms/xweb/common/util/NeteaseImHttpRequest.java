package com.coracle.dms.xweb.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.coracle.yk.xframework.util.CheckSumBuilder;

/*
 * 网易云信包装类
 */
@SuppressWarnings({ "resource", "deprecation" })
public class NeteaseImHttpRequest {
	public final static String AppKey = "cc39b6f8183819f7fed540ad411b889d";
	public final static String AppSecret = "938a18ef31fa";
	public final static String Nonce = "123456";
	public final static String Host = "https://api.netease.im/";
	public final static String agentFrom = "p00000001";//发送给顾客的消息通过此账号进行代理转发
    
    /**
     * 获取手机验证码
     * @author jinyou
     * */
    public static String sendCode(String mobile, String deviceId, String appKey, String appSecret) throws Exception{
		String action = "sms/sendcode.action";
				
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("mobile", mobile));
		nvps.add(new BasicNameValuePair("deviceId", deviceId));
		 
		return httpRequest(action, nvps, AppKey, AppSecret);
	}
    
    /**
     * 校验手机验证码
     * @author jinyou
     * */
    public static String verifyCode(String mobile, String code, String appKey, String appSecret) throws Exception{
		String action = "sms/verifycode.action";
				
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("mobile", mobile));
		nvps.add(new BasicNameValuePair("code", code));
		 
		return httpRequest(action, nvps, AppKey, AppSecret);
	}
    
    /*
     * 执行请求，公用方法
     */
    public static String httpRequest(String action, List<NameValuePair> param, String appKey, String appSecret) throws Exception{
		String url = Host + action;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, Nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", Nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                                           
        httpPost.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
        HttpResponse response = httpClient.execute(httpPost);
        
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
    
//    public static void main(String[] args) {
//    	Map<String, Object> map = new HashMap<String, Object>();
//		map.put("name", "图片发送与2016-03-10 14:30");
//		map.put("ext", "jpg");
//		map.put("w", 1024);
//		map.put("h", 800);
//		map.put("size", 38826);
//	
//
//		String strJson = JSONObject.fromObject(map).toString();
//		System.out.println(strJson);
//		
//		System.out.println(CheckSumBuilder.getMD5("http://mmbiz.qpic.cn/mmbiz/KKkkRfb0FVXIgrmqboSehtibhBO0pz8wPiagExSUa0ETCEvn38k55BnQcibicsIJ2uo7hFsp0vMsIBv5rcVvJT3VDw/0"));
//	}
}
