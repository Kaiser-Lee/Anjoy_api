package com.coracle.dms.xweb.controller;

import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.xframework.common.constants.CommonConstants;
import com.coracle.yk.xframework.common.constants.RedisKeyConstants;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.common.exception.runtime.ParamException;
import com.coracle.yk.xframework.redis.RedisUtil;
import com.coracle.yk.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "/api/v1/dms/cache")
@Api(description = "Redis缓存管理接口")
public class CacheManagerController extends BaseController {

    @Resource
    private RedisUtil redisUtil;

    /*@Resource
    private BsdSystemConfigService bsdSystemConfigService;

    @Resource
    private BsdCommonDictionaryService bsdCommonDictionaryService;*/

    @RequestMapping(value = "/getCacheByKey", method = RequestMethod.GET)
    @ApiOperation(value = "通过缓存Key获取缓存数据")
    @ResponseBody
    public Object getCacheByKey(String redisKey) {
        if(LoginManager.getUserSession().getEmployeeType() != CommonConstants.EMPLOYEE_TYPE_PPS) {
            throw new ControllerException("你没有权限操作");
        }
        if(BlankUtil.isEmpty(redisKey)) {
            throw new ParamException("参数不能为空");
        }
        Object o = redisUtil.get(redisKey);
        return o;
    }

    @RequestMapping(value = "/getCacheByKeyFromMap", method = RequestMethod.GET)
    @ApiOperation(value = "通过缓存Key获取Map缓存数据")
    @ResponseBody
    public Object getCacheByKeyFromMap(String redisKey) {
        if(LoginManager.getUserSession().getEmployeeType() != CommonConstants.EMPLOYEE_TYPE_PPS) {
            throw new ControllerException("你没有权限操作");
        }
        if(BlankUtil.isEmpty(redisKey)) {
            throw new ParamException("参数不能为空");
        }
        Object o = redisUtil.getAllFromMap(redisKey);
        return o;
    }

    @RequestMapping(value = "/getDFKOrders", method = RequestMethod.GET)
    @ApiOperation(value = "获取待付款订单缓存数据")
    @ResponseBody
    public Object getDFKOrders() {
        if(LoginManager.getUserSession().getEmployeeType() != CommonConstants.EMPLOYEE_TYPE_PPS) {
            throw new ControllerException("你没有权限操作");
        }
        Object o = redisUtil.getAllFromMap(RedisKeyConstants.DZF_ORDER_REDIS_KEY);
        return o;
    }


    /*@RequestMapping(value = "/reloadSystemConfig", method = RequestMethod.GET)
    @ApiOperation(value = "重新加载系统配置")
    @ResponseBody
    public void reloadSystemConfig() {
        if(LoginManager.getUserSession().getEmployeeType() != CommonConstants.EMPLOYEE_TYPE_PPS) {
            throw new ControllerException("你没有权限操作");
        }
        bsdSystemConfigService.loadIntoCache();
    }*/

    /*@RequestMapping(value = "/reloadDictionarys", method = RequestMethod.GET)
    @ApiOperation(value = "重新加载数据字典")
    @ResponseBody
    public void reloadDictionarys() {
        if(LoginManager.getUserSession().getEmployeeType() != CommonConstants.EMPLOYEE_TYPE_PPS) {
            throw new ControllerException("你没有权限操作");
        }
        bsdCommonDictionaryService.loadIntoCache();
    }*/

}
