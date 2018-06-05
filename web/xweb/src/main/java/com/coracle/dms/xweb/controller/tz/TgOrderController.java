package com.coracle.dms.xweb.controller.tz;

import com.coracle.dms.po.tz.TgOrder;
import com.coracle.dms.po.tz.TgOrderProduct;
import com.coracle.dms.po.tz.TgOrderProductPart;
import com.coracle.dms.service.tz.TgOrderProductService;
import com.coracle.dms.service.tz.TgOrderService;
import com.coracle.dms.vo.tz.TgOrderVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.dms.xweb.controller.BaseController;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Api(description = "天正订单管理接口")
@RequestMapping("/api/v1/dms/tgOrder")
public class TgOrderController extends BaseController {
    private static final Logger logger = Logger.getLogger(TgOrderController.class);

    @Autowired
    private TgOrderService tgOrderService;
    @Autowired
    private TgOrderProductService tgOrderProductService;

    @ResponseBody
    @ApiOperation(value = "订单列表", response = JsonResult.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestBody TgOrder order) {
        PageHelper.Page<TgOrder> pageList = tgOrderService.pageList(order, LoginManager.getUserSession());
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "新增订单产品", response = JsonResult.class)
    @RequestMapping(value = "/insterOrderProduct", method = RequestMethod.POST)
    public Object insterOrderProduct(@RequestBody TgOrderProduct pro) {
        return tgOrderProductService.insert(pro);
    }

    @ResponseBody
    @ApiOperation(value = "添加订单产品分批发运", response = JsonResult.class)
    @RequestMapping(value = "/insterOrderProPart", method = RequestMethod.POST)
    public Object insterOrderProductPart(@RequestBody TgOrderProductPart po) {
        int code = tgOrderService.insterOrderProPart(po);
        return code;
    }

    @ResponseBody
    @ApiOperation(value = "删除订单产品", response = JsonResult.class)
    @RequestMapping(value = "/deleteOrderProduct", method = RequestMethod.POST)
    public Object deleteOrderProduct(String ids) {
        Map<String,Object> map = Maps.newHashMap();
        String[] arr = ids.split(",");
        List<String> idList = new ArrayList<>();
        for (String id:arr) {
            idList.add(id);
        }
        map.put("idList",idList);
        tgOrderService.deleteOrderPro(map);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "删除订单产品分批发运信息", response = JsonResult.class)
    @RequestMapping(value = "/deleteOrderProPart", method = RequestMethod.POST)
    public Object deleteOrderProPart(String ids) {
        Map<String,Object> map = Maps.newHashMap();
        String[] arr = ids.split(",");
        List<String> idList = new ArrayList<>();
        for (String id:arr) {
            idList.add(id);
        }
        map.put("idList",idList);
        tgOrderService.deleteOrderProPart(map);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "批量设置提货地点", response = JsonResult.class)
    @RequestMapping(value = "/updatePickAddress", method = RequestMethod.POST)
    public Object updatePickAddress(String ids,String pickAddress) {
        tgOrderService.updatePickAddress(ids,pickAddress);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "新增订单", response = JsonResult.class)
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public Object createOrder(@RequestBody TgOrderVo orderVo) {
        PoDefaultValueGenerator.putDefaultValue(orderVo);
        TgOrder result = tgOrderService.create(orderVo, LoginManager.getUserSession());
        return toResult(result);
    }

    @ResponseBody
    @ApiOperation(value = "订单详情", response = JsonResult.class)
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(@RequestBody TgOrderVo orderVo) {
        PoDefaultValueGenerator.putDefaultValue(orderVo);
        TgOrder result = tgOrderService.detail(orderVo, LoginManager.getUserSession());
        return toResult(result);
    }


}