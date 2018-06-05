package com.coracle.dms.xweb.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by huangbaidong
 * 2017/4/5.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/test")
@Api(description = "测试接口")
public class TestController extends BaseController {


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Object test(HttpSession session, HttpServletRequest requet) {
        String url = requet.getScheme() + "://" + requet.getServerName() + ":" + requet.getServerPort();
        System.out.println(url);
        System.out.println(session.getId());
        return url;
    }

    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public Object exit (HttpSession session){
        System.out.println(session.getId());
        session.invalidate();
        return session;
    }
}
