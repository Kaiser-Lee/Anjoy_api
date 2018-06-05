package com.coracle.dms.xweb.common.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsOrderOperationLog;
import com.coracle.dms.service.DmsOrderOperationLogService;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.base.annotation.OperationLog;
import com.coracle.yk.xframework.po.UserSession;
import com.xiruo.medbid.util.RegexUtils;

/**
 *
 * @version 1.0
 */
public class OperationLogInterceptor extends HandlerInterceptorAdapter {	
	
	private final Logger logger = Logger.getLogger(OperationLogInterceptor.class);
	
	@Autowired
    private DmsOrderOperationLogService dmsOrderOperationLogService;
	
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		try {
	        Method method = ((HandlerMethod)handler).getMethod();
	        OperationLog operationLog = method.getAnnotation(OperationLog.class);
	        if(null != operationLog){
	        	UserSession user = LoginManager.getUserSession();
	        	if(user != null){
	        		// 操作描述
	    			String description = operationLog.description(), paramName;
	    			if(StringUtils.isNotEmpty(description)){
		    			List<String> args = RegexUtils.findMatchContents("\\{\\w+\\}", operationLog.description());	    			
		    			if(null!=args && !args.isEmpty()){
		    				for (String arg : args) {
		    					if(StringUtils.isNotEmpty(arg)){
		    						paramName = arg.substring(1, arg.length()-1);
		    						description = description.replace(arg, StringUtils.isNotEmpty(request.getParameter(paramName)) ? request.getParameter(paramName) : "NULL");
		    					}
		    				}
		    			}
	    			}	    			
	    			
	        		DmsOrderOperationLog orderLog = new DmsOrderOperationLog();
		            orderLog.setOrderId(0L);
		            orderLog.setOperation(operationLog.operation());
		            orderLog.setRelatedType(operationLog.relatedType());
		            orderLog.setCreatedBy(user.getId());
		            orderLog.setCreatedDate(new Date());
		            orderLog.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
		            dmsOrderOperationLogService.insert(orderLog);
	        	}
	        }
        } catch (Exception e) {
			logger.error("操作日志记录异常", e);
		}
	}
}
