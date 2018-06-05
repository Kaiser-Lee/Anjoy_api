package com.coracle.dms.xweb.common.filter;

import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.xframework.po.UserSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 访问日志记录器
 * Created by huangbaidong
 * 2017/5/22.
 */
@WebFilter(urlPatterns = "/api/*", filterName = "logFilter")
public class AccessLogFilter implements Filter {

    Log logger = LogFactory.getLog("accessLog");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("日志记录器初始化..");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        UserSession userSession = LoginManager.getUserSession();
        if(userSession != null) {
            logger.info("USER_ID："+userSession.getId() + ",  USER_NAME：" +userSession.getUserName());
        }
        logger.info(request.getRemoteHost() + " " +request.getRequestURL());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
