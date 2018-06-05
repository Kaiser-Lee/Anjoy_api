package com.coracle.dms.xweb.common.interceptor;

import com.coracle.dms.xweb.common.enums.StatusEnum;
import com.coracle.dms.xweb.common.handler.LoginUrlHandler;
import com.coracle.yk.xframework.common.PositecConstants;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import net.sf.json.JSONArray;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof ResourceHttpRequestHandler){
			return true;
		}

		String path = request.getRequestURI();
		for (String url : LoginUrlHandler.URL_LIST) {
			if (path.indexOf(url) > -1) {
				return true;
			}
		}
		Map<String,Object> map = new HashMap<>();
		UserSession user = (UserSession)request.getSession().getAttribute(UserSession.USER_SESSION_KEY);
		if (user != null) {
			//判断是否有接口权限
			/*if(path.indexOf(PositecConstants.API_PREFIX)>-1&&user.getEmployeeType()!=0){
				List<Map<String,Object>> list= (List<Map<String, Object>>) request.getSession().getAttribute(UserSession.USER_ROLE_SESSION_KEY);
				Boolean matchResult=false;//匹配结果
				if(BlankUtil.isNotEmpty(list)){//无接口权限
					PathMatcher matcher = new AntPathMatcher();
					for (Map<String,Object> resourceMap:list){
						System.out.println(resourceMap);
						if(matcher.match(request.getContextPath()+resourceMap.get("url").toString(),path)){
							matchResult=true;
							break;
						}
					}
				}
				if(!matchResult){
					map.put("status", StatusEnum.ERROR.getStatus());
					map.put("message", "权限不足,请联系管理员！");
					map.put("code", -2);
					response.getWriter().print(JSONArray.fromObject(map).get(0));
					return false;
				}

			}*/
			return true;
		}else {
			map.put("status", StatusEnum.ERROR.getStatus());
			map.put("message", UserSession.NO_LOGIN_MSG);
			map.put("code", -1);
			response.getWriter().print(JSONArray.fromObject(map).get(0));
			return false;
		}

	}

}
