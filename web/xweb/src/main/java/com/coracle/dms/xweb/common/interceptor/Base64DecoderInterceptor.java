package com.coracle.dms.xweb.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.coracle.yk.base.vo.JsonResult;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;

import java.io.*;

/**
 * 控制层异常拦截器
 * created by huangbaidong
 * 20170303
 */
public class Base64DecoderInterceptor implements MethodInterceptor {
	private Logger logger=Logger.getLogger(Base64DecoderInterceptor.class);

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object object = invocation.proceed();
		/*if(object instanceof JsonResult) {
			JsonResult jsonResult = (JsonResult)object;
			if(jsonResult.isEncode()) {
				String base64 = Base64Utils.encodeToString(JSON.toJSONString(jsonResult.getData()).getBytes("UTF-8"));
				jsonResult.setData(base64);
			}
		}*/

		return object;
	}

	public static void main(String[] args) throws IOException {
		DataOutputStream dos = new DataOutputStream(System.out);
		dos.writeChars("哈哈哈");
		PipedInputStream pis = new PipedInputStream(new PipedOutputStream());
	}


}
