package com.coracle.dms.xweb.controller;

import com.coracle.dms.service.DmsReportFormsService;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.base.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/9/20.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/reportForms")
@Api(description = "报表查询接口")
public class DmsReportFormsController extends BaseController {

    @Autowired
    private DmsReportFormsService dmsReportFormsService;

    @ResponseBody
    @ApiOperation(value = "APP分类产品销量top5", response = JsonResult.class)
    @RequestMapping(value = "/getTopByDateType", method = RequestMethod.GET)
    public Object getTopByDateType(@ApiParam("1-周，2-月，3-季，其他默认月") @RequestParam(value = "searchType",required = false) Integer searchType){
        return toResult(dmsReportFormsService.getTop5ForProductCategoryByAPP(LoginManager.getUserSession(),searchType));
    }

    @ResponseBody
    @ApiOperation(value = "首页获取产品销量排行", response = JsonResult.class)
    @RequestMapping(value = "/getTop5ByPC", method = RequestMethod.GET)
    public Object getTop5ByPC(@ApiParam("2-本月，3-本季，4-本年，其他默认本季") @RequestParam(value = "searchType",required = false) Integer searchType){
        return toResult(dmsReportFormsService.getTop5ByPC(searchType));
    }
    @ResponseBody
    @ApiOperation(value = "首页获取订单折线图", response = JsonResult.class)
    @RequestMapping(value = "/getOrderInfoByPC", method = RequestMethod.GET)
    public Object getOrderInfoByPC(@ApiParam("2-本月，3-本季，4-本年，其他默认本季") @RequestParam("dateType") Integer dateType,
                                   @ApiParam("1-销售额，2-数量") @RequestParam("searchType") Integer searchType){
        return toResult(dmsReportFormsService.getOrderTopByPC(dateType,searchType));
    }

    @ResponseBody
    @ApiOperation(value = "APP库龄TOP5", response = JsonResult.class)
    @RequestMapping(value = "/getTopByOldLibrary", method = RequestMethod.GET)
    public Object getTopByOldLibrary(){
        return toResult(dmsReportFormsService.getTopByOldLibrary(LoginManager.getUserSession()));
    }
}
