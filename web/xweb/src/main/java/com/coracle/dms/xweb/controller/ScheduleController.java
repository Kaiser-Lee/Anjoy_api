package com.coracle.dms.xweb.controller;

import com.coracle.dms.schedule.service.JobEntityService;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.xframework.common.constants.CommonConstants;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by huangbaidong
 * 2017/4/5.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/schedule")
@Api(description = "计划任务管理接口")
public class ScheduleController extends BaseController {

    @Resource
    private JobEntityService jobEntityService;

    @RequestMapping(value = "/reload", method = RequestMethod.GET)
    @ResponseBody
    public Object reload() {
        if(LoginManager.getUserSession().getEmployeeType() != CommonConstants.EMPLOYEE_TYPE_PPS) {
            throw new ControllerException("你没有权限操作");
        }
        jobEntityService.reloadSchedule();
        return toResult();
    }
}
