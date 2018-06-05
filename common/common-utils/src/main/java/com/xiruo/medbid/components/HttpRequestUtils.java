package com.xiruo.medbid.components;

import com.xiruo.medbid.util.UtilConstants;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpRequestUtils {

    // default time out setting , half minute
    private static final int defaultTimeOut = 30 * 1000;

    private static void validateUrl(String url) {
        if (!URLUtils.isUseHttpProtocol(url)) {
            throw new java.lang.IllegalArgumentException(String.format(
                    "The URL %s is illegal", url));
        }
    }

    public static String doGet(String url, String charSetName, int timeOut)
            throws Exception {
        validateUrl(url);
        try {
            URL ur = new URL(url);
            URLConnection con = ur.openConnection();
            con.setConnectTimeout(timeOut);
            con.setReadTimeout(timeOut);
            BufferedReader rd = new BufferedReader(new InputStreamReader(con
                    .getInputStream(), charSetName));
            StringBuilder sb = new StringBuilder();
            try {
                int k = rd.read();
                while (k != -1) {
                    sb.append((char) k);
                    k = rd.read();
                }
            } catch (Exception ee) {
            } finally {
                if (rd != null) {
                    rd.close();
                }
            }
            return sb.toString();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static String doGet(String url, String charSetName) throws Exception {
        return doGet(url, charSetName, defaultTimeOut);
    }

    public static String doGet(String url) throws Exception {
        return doGet(url, UtilConstants.DEFAULT_CHARSET, defaultTimeOut);
    }

    public static void doGetFile(String url, int timeOut, String fullFileName)
            throws Exception {
        validateUrl(url);
        InputStream is = null;
        OutputStream os = null;
        try {
            URL ur = new URL(url);
            URLConnection con = ur.openConnection();
            con.setConnectTimeout(timeOut);
            con.setReadTimeout(timeOut);

            is = con.getInputStream();

            // 1K cache
            byte[] bs = new byte[1024];
            // length
            int len;

            os = new FileOutputStream(fullFileName);
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static InputStream doGetStream(String url, int timeOut)
            throws Exception {
        validateUrl(url);
        InputStream is = null;
        try {
            URL ur = new URL(url);
            URLConnection con = ur.openConnection();
            con.setConnectTimeout(timeOut);
            con.setReadTimeout(timeOut);
            is = con.getInputStream();
            return is;
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception unusede) {
                }
            }
        }
    }

    public static String doPost(String url, Map<String, String> parameters,
                                int timeOut, String charSetName) throws Exception {
        // validate
        validateUrl(url);

        // generate post data form parameters
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> kv : parameters.entrySet()) {
            sb.append(kv.getKey());
            sb.append("=");
            sb.append(URLUtils.decode(kv.getValue()));
            sb.append("&");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        byte[] postData = BytesUtils.toBytes(sb);
        try {
            URL ur = new URL(url);
            URLConnection con = ur.openConnection();

            // setting
            con.setConnectTimeout(timeOut);
            con.setReadTimeout(timeOut);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            con.setRequestProperty("Content-Length", postData.length + "");
            OutputStream os = con.getOutputStream();

            os.write(postData);
            os.flush();
            os.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(con
                    .getInputStream(), charSetName));
            StringBuilder rsb = new StringBuilder();
            try {
                int k = rd.read();
                while (k != -1) {
                    rsb.append((char) k);
                    k = rd.read();
                }
            } catch (Exception ee) {
            } finally {
                try {
                    rd.close();
                } catch (Exception e) {

                }
            }
            return rsb.toString();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static String doPost(String url, Map<String, String> parameters,
                                int timeOut) throws Exception {
        return HttpRequestUtils
                .doPost(url, parameters, timeOut, UtilConstants.DEFAULT_CHARSET);
    }

    public static String doPost(String url, Map<String, String> parameters)
            throws Exception {
        return HttpRequestUtils.doPost(url, parameters, defaultTimeOut,
                UtilConstants.DEFAULT_CHARSET);
    }

    public static int doHead(String url, int timeOut) throws Exception {
        validateUrl(url);
        try {
            URL ur = new URL(url);
            HttpURLConnection con = (HttpURLConnection) ur.openConnection();
            con.setConnectTimeout(timeOut);
            return con.getResponseCode();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static int doHead(String url) throws Exception {
        return doHead(url, defaultTimeOut);
    }

    public static JSONObject doPostByJson(String httpUrl, JSONObject jsonObject) throws IOException {
        return doPostByJson(httpUrl, jsonObject, 20000);
    }

    public static JSONObject doPostByJson(String httpUrl, JSONObject jsonObject, Integer timeout) throws IOException {
        StringBuffer sb = null;
        HttpURLConnection connection=null;
        OutputStreamWriter out=null;
        BufferedReader reader=null;
        JSONObject returnObj=null;
        try {
            //创建连接
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            if (null != timeout) {
                connection.setReadTimeout(60 * 1000);
            } else {
                connection.setReadTimeout(timeout);
            }
//            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            connection.connect();

            //POST请求
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            System.out.println("请求参数:"+jsonObject.toString());
            out.write(jsonObject.toString());
            out.flush();

            //读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String lines;
            sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                sb.append(lines);
            }
            System.out.println("响应参数："+sb);
            if(sb.length()>0){
                returnObj= JSONObject.fromObject(sb.toString().replaceAll("\n","").replaceAll("null","\"null\""));
            }
            // 断开连接
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(out!=null){
                out.flush();
                out.close();
            }
            if(reader!=null){
                reader.close();
            }
            if(connection!=null){
                connection.disconnect();
            }
        }
        return returnObj;
    }


}
