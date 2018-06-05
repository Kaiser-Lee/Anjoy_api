package com.xiruo.medbid.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fred on 2016/8/24.
 */
public class PushUtil {

//    public static void main(String[] args) throws IOException {
//        PushUtil pushUtil = new PushUtil();
//
//        String content = pushUtil.push("你好，我是PushUtil。","systemmes","wangzhe");
//
//        System.out.println("content::"+content);
//    }

    public static String push(String pushUrl, String content, String msgType, String loginNames) {
        return push(pushUrl, content, msgType, loginNames, 0,"");
    }

    public static String push(String pushUrl, String content, String msgType, String loginNames,String appKey) {
        return push(pushUrl, content, msgType, loginNames, 0,appKey);
    }

    /**
     * Push
     *
     * @param pushUrl push系统部署的地址
     * @param content 消息内容
     * @param msgType 推送类型  SHJ_1~SHJ_7(生活家的),systemmes("系统消息"),intermes("接口推送消息"),clearuser("清除用户"),cleardevice("清除设备"),onlineupdate("在线更新"),sys("ios系统信息");
     * @param loginNames 消息接收方的loginName，多个用逗号隔开
     * @param flag 一个账号多终端登录时是否终止所有推送，0否，1是
     *
     * @return
     * msg:处理结果信息，如发送成功，发送失败
     * stat:处理结果标志，true,false
     */
    public static String push(String pushUrl, String content, String msgType, String loginNames, int flag,String appKey) {
        if(null == pushUrl || "".equals(pushUrl) || !pushUrl.startsWith("http")){
            return "bad url: "+pushUrl;
        }

        HttpClientUtil httpClientUtil = new HttpClientUtil();
        Map<String, String> params = new HashMap<String, String>();
        params.put("content", content);
        params.put("msgType", msgType);
        params.put("userIds", loginNames);
        params.put("flag", ""+flag);
        params.put("appKey", appKey);
        String response = httpClientUtil.postRequest(pushUrl, params);

        return response;
    }


}
