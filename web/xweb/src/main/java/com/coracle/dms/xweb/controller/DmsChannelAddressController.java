package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsChannelAddress;
import com.coracle.dms.service.DmsChannelAddressService;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.base.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(description = "收货地址接口")
@RequestMapping("dmsChannelAddress")
public class DmsChannelAddressController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsChannelAddressController.class);

    @Autowired
    private DmsChannelAddressService dmsChannelAddressService;

    @ResponseBody
    @ApiOperation(value = "渠道收货地址列表", response = JsonResult.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list() {
        return toResult(dmsChannelAddressService.list(LoginManager.getUserSession()));
    }

    @ResponseBody
    @ApiOperation(value = "设置为默认收货地址", response = JsonResult.class)
    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    public Object setDefault(@RequestBody DmsChannelAddress param) {
        dmsChannelAddressService.setDefault(param, LoginManager.getUserSession());
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "安井渠道-收货地址同步", response = DmsChannelAddress.class)
    @RequestMapping(value = "/anjoy-syn", method = RequestMethod.GET)
    public Object anjoySyn() {
        dmsChannelAddressService.anjoySyn();
        return toResult();
    }

}