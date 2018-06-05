package com.coracle.dms.xweb.common.resolver;

import com.coracle.yk.xframework.po.UserSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class UserSessionArgumentResolver implements HandlerMethodArgumentResolver{
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(UserSession.class);
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter,ModelAndViewContainer mavContainer, NativeWebRequest webRequest,WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		UserSession user = (UserSession)request.getSession().getAttribute(UserSession.USER_SESSION_KEY);
		return user;
	}
}