package com.coracle.dms.xweb.common.interceptor;

import com.alibaba.dubbo.rpc.RpcException;
import com.coracle.dms.xweb.common.enums.StatusEnum;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xframework.common.exception.runtime.*;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xframework.util.ExceptionUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

/**
 * 控制层异常拦截器
 * created by huangbaidong
 * 20170303
 */
public class ControllerInterceptor implements MethodInterceptor {

	private Logger logger=Logger.getLogger("errorLog");
	private Logger paymentLogger=Logger.getLogger("paymentLog");
	private Logger bmsLogger=Logger.getLogger("bmsLog");

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object object=null;
		try {
			JsonResult jsonResult = new JsonResult();
			try{
				object = invocation.proceed();
				return object;
			} catch(Exception e){
				jsonResult.setStatus(StatusEnum.ERROR.getStatus());
				jsonResult.setCode(null);
				String ex_message = e.getMessage();
				if(BlankUtil.isNotEmpty(ex_message)) {
					if (ex_message.contains(ServiceException.class.getName())) {
						ex_message = ex_message.substring(0,ex_message.lastIndexOf(ServiceException.class.getName()));
						ex_message = ex_message.replace(ServiceException.class.getName(),"");
						ex_message = ex_message.replace("\r\n","");
						ex_message = ex_message.replace("\n","");
						ex_message = ex_message.replace("\r","");
						ex_message = ex_message.replace(":","");
					}
				}
				String stack_message = ExceptionUtil.getStackTrace(e);
				if(stack_message.contains(ServiceException.class.getName())){
					jsonResult.setMessage(ex_message);
				} else if(e instanceof ParamException){
					jsonResult.setMessage(ex_message);
				} else if(e instanceof PaymentException){
					//记录到支付日志 xweb-payment.log
					paymentLogger.error(ex_message);
					jsonResult.setMessage(ex_message);
				} else if(e instanceof BmsException){
					//记录到支付日志 xweb-bms.log
					bmsLogger.error(ex_message);
					jsonResult.setMessage(ex_message);
				} else if(e instanceof ControllerException) {
					jsonResult.setMessage(ex_message);
				} else if(e instanceof RpcException){
					jsonResult.setMessage("服务异常:"+ e.getMessage());
				} else {
					e.printStackTrace();
					jsonResult.setMessage("服务器内部错误");
				}
				//记录通用错误日志文件xweb-error.log
				logger.error(ExceptionUtil.getErrorClassInfo(e.getMessage()) + jsonResult.getMessage());
				return jsonResult;
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}


}
