package com.coracle.dms.xweb.common.session;

import com.coracle.yk.xframework.po.UserSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;

/**
 * 
 * LoginManager
 * @author jianglingfeng
 * @date 2007-11-9
 * @version
 */
public final class LoginManager {



    /**
     * 缓存登录对象实体
     * @author jianglingfeng
     * @date 2007-11-9
     * @see
     */
    @Deprecated
    public static void setUserSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserSession userSession = new UserSession();
        request.getSession().setAttribute(UserSession.USER_SESSION_KEY, userSession);
    }

    /**
     * 返回登录用户实体
     * @author jianglingfeng
     * @date 2007-11-9
     * @return
     * @see
     */
    public static UserSession getUserSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserSession user = (UserSession)request.getSession().getAttribute(UserSession.USER_SESSION_KEY);
        return user ;
    }

    /**
     * 清除用户实体缓存
     * @author jianglingfeng
     * @date 2007-11-9
     * @see
     */
    public static void clearUserSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().removeAttribute(UserSession.USER_SESSION_KEY);
    }


    public static long getCurrentUserId(){
        return getUserSession()!=null?getUserSession().getId():0L;
    }

}
