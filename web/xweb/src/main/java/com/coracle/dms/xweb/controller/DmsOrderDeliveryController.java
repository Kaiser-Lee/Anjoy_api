package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsOrderDeliveryItem;
import com.coracle.dms.service.DmsOrderDeliveryItemService;
import com.coracle.dms.service.DmsOrderDeliveryService;
import com.coracle.dms.vo.DmsOrderDeliveryAnjoyVo;
import com.coracle.dms.vo.DmsOrderDeliveryVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
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
@Api(description = "发货单接口")
@RequestMapping("/api/v1/dms/orderDelivery")
public class DmsOrderDeliveryController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsOrderDeliveryController.class);

    @Autowired
    private DmsOrderDeliveryService dmsOrderDeliveryService;

    @Autowired
    private DmsOrderDeliveryItemService dmsOrderDeliveryItemService;

    @ResponseBody
    @ApiOperation(value = "创建发货单", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsOrderDeliveryVo orderDeliveryVo) {
        PoDefaultValueGenerator.putDefaultValue(orderDeliveryVo);
        orderDeliveryVo.setRelatedType(DmsModuleEnums.ORDER_DELIVERY_TYPE.ORDER.getType());
        dmsOrderDeliveryService.create(orderDeliveryVo, LoginManager.getUserSession());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "安井创建发货单", response = JsonResult.class)
    @RequestMapping(value = "/anjoyInsert", method = RequestMethod.POST)
    public Object anjoyInsert(@RequestBody DmsOrderDeliveryAnjoyVo orderDeliveryVo) {
        //PoDefaultValueGenerator.putDefaultValue(orderDeliveryVo);
        dmsOrderDeliveryService.createAnjoy(orderDeliveryVo);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "确认收货-发货单", response = JsonResult.class)
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public Object confirm(@RequestBody DmsOrderDeliveryItem orderDeliveryItem) {
        PoDefaultValueGenerator.putDefaultValue(orderDeliveryItem);
        dmsOrderDeliveryItemService.confirm(orderDeliveryItem, LoginManager.getUserSession());
        return toResult(null);
    }


}