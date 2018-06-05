package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsFootprint;
import com.coracle.dms.service.DmsFootprintService;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(description = "我的足迹")
@RequestMapping("/api/v1/dms/footprint")
public class DmsFootprintController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsFootprintController.class);

    @Autowired
    private DmsFootprintService dmsFootprintService;

    @ResponseBody
    @ApiOperation(value = "我的足迹（最近浏览的10件产品）", response = JsonResult.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@ApiParam("分页页码") @RequestParam(value = "p", required = false) Integer p,
                       @ApiParam("每页条数") @RequestParam(value = "s", required = false) Integer s) {
        DmsFootprint param = new DmsFootprint();
        param.setP(p);
        param.setS(s);
        PageHelper.Page<DmsProductVo> list = dmsFootprintService.list(param, LoginManager.getUserSession());
        return toResult(list);
    }
}