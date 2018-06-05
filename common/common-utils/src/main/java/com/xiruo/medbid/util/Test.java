package com.xiruo.medbid.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**     
 * 
 * @author jianglfa       
 * @version 1.0     
 * @created Aug 18, 2011 4:30:37 PM    
 */

public class Test {


	public static void main(String[] args) {

		/*String message = "\n" +
				"\t\tcom.coracle.yk.xframework.common.exception.runtime.ServiceException: 根据ID未查到订单信息\n" +
				"\t\tcom.coracle.yk.xframework.common.exception.runtime.ServiceException: 根据ID未查到订单信息\n" +
				"\t\tat com.coracle.dms.service.impl.BsdOrderServiceImpl.applyRefund(BsdOrderServiceImpl.java:385)\n" +
				"\t\tat com.coracle.dms.service.impl.BsdOrderServiceImpl$$FastClassBySpringCGLIB$$36f7efb0.invoke(<generated>)\n" +
				"\t\tat org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n" +
				"\t\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:720)\n" +
				"\t\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157)\n" +
				"\t\tat org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:99)\n" +
				"\t\tat org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:281)\n" +
				"\t\tat org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:96)\n" +
				"\t\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)\n" +
				"\t\t";
		Pattern pattern = Pattern.compile("at.*\\(.+\\.java:\\d+\\)");
		Matcher matcher = pattern.matcher(message);
		if(matcher.find()) {
			System.out.println(matcher.group());
		}*/


		System.out.println(new BigDecimal(1).divide(new BigDecimal(0), BigDecimal.ROUND_UP));

	}

}
