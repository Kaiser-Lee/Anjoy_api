package com.xiruo.medbid.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @类描述：HTTP请求处理工具类
 * @项目名称：shj
 * @包名： com.xiruo.medbid.util
 * @类名称：HttpClientUtil.java
 * @创建人：zzw
 * @创建时间：2016/8/3 20:09
 * @修改人：zzw
 * @修改时间：
 * @修改备注：
 * @version v1.0
 * @see [nothing]
 * @bug [nothing]
 * @Copyright shj
 */
public class HttpClientUtil { private CloseableHttpClient httpclient;

    public HttpClientUtil() {
        httpclient = HttpClients.createDefault();
    }

    public String getRequest(String url) {
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return getContent(entity);
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String postRequest(String url, Map<String, String> params) {
        String content = null;
        try {
            HttpPost httpPost = new HttpPost(url);
//            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            httpPost.setEntity(new UrlEncodedFormEntity(toNameValuePairList(params),"utf-8"));
            CloseableHttpResponse response = httpclient.execute(httpPost);

            try {
                HttpEntity entity = response.getEntity();
//                response.getStatusLine();
                if (entity != null) {
                    content = getContent(entity);
                    EntityUtils.consume(entity);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    private String getContent(HttpEntity entity) throws IOException {
        InputStream is = entity.getContent();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    private List<NameValuePair> toNameValuePairList(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        return nvps;
    }

    public static void main(String[] args) throws IOException {
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        Map<String, String> params = new HashMap<String, String>();
        params.put("p", "1");
        params.put("s", "11");
        String content = httpClientUtil.postRequest("http://localhost:8080/xsimple//pm/pc/AppPmProjectComplaint/complaint/list", params);

        System.out.println(content);
    }
}

