package com.coracle.dms.xweb.web;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings("unused")
public class UrlInterceptor implements HandlerInterceptor {
	//Logger loger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	private List<String> excludedUrls;  
	   
	public void setExcludedUrls(List<String> excludedUrls) {  
		this.excludedUrls = excludedUrls;  
	}  
	
//	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {		
	}

//	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

//	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object o) throws Exception {
		
		Integer contentLength = request.getContentLength();
		//String str = request.getReader().toString();
		//System.out.println("str="+str);

		if(contentLength > 0)
			return true;
		else{
			RequestDispatcher rd = request.getRequestDispatcher("check");
			rd.forward(request, response);
			return false;
		}

	}

}
