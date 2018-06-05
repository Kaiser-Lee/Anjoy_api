package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.po.DmsExceptionClaims;
import com.coracle.dms.service.DmsExceptionClaimsService;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/v1/dms/dmsExceptionClaims")
@Api(description                 = "异常理赔")
public class DmsExceptionClaimsController extends BaseController {

    private static final Logger logger = Logger.getLogger(DmsExceptionClaimsController.class);

    @Autowired
    private DmsExceptionClaimsService dmsExceptionClaimsService;

    @ResponseBody
    @ApiOperation(value = "保存异常理赔", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsExceptionClaims dmsExceptionClaims) {
        PoDefaultValueGenerator.putDefaultValue(dmsExceptionClaims);
        dmsExceptionClaimsService.insertSelective(dmsExceptionClaims);
        // 提交单据，数据传输到BPM走审批流，最后返回审批结果
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "获取异常理赔", response = DmsExceptionClaims.class)
    @RequestMapping(value = "/listOne", method = RequestMethod.POST)
    public Object listOne(Long id) {
        DmsExceptionClaims data =
                dmsExceptionClaimsService.selectByPrimaryKey(id);
        return toResult(data);
    }


    @ResponseBody
    @ApiOperation(value = "修改异常理赔", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@ApiParam("修改异常理赔") @RequestBody DmsExceptionClaims dmsExceptionClaims) {
        PoDefaultValueGenerator.putDefaultValue(dmsExceptionClaims);
        dmsExceptionClaimsService.updateByPrimaryKeySelective(dmsExceptionClaims);
        return toResult(null);
    }

}





