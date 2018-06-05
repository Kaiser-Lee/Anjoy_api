package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsChannel;
import com.coracle.dms.service.DmsChannelEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/v1/dms/channelEmployee")
@Api(description = "渠道业务员接口")
public class DmsChannelEmployeeController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsChannelEmployeeController.class);

    @Autowired
    private DmsChannelEmployeeService dmsChannelEmployeeService;

    @ResponseBody
    @ApiOperation(value = "安井渠道-业务员同步", response = DmsChannel.class)
    @RequestMapping(value = "/anjoy-syn", method = RequestMethod.GET)
    public Object anjoySyn() {
        dmsChannelEmployeeService.anjoySyn();
        return toResult();
    }

}