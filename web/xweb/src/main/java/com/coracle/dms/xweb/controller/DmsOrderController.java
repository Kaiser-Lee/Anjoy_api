package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsOrder;
import com.coracle.dms.po.DmsOrderOperationLog;
import com.coracle.dms.po.DmsOrderPayment;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsOrderPaymentVo;
import com.coracle.dms.vo.DmsOrderProductVo;
import com.coracle.dms.vo.DmsOrderVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.google.common.collect.Maps;
import com.xiruo.medbid.components.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Api(description = "订单管理接口")
@RequestMapping("/api/v1/dms/order")
public class DmsOrderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DmsOrderController.class);

    @Autowired
    private DmsOrderService dmsOrderService;

    @Autowired
    private DmsOrderProductService dmsOrderProductService;

    @Autowired
    private DmsOrderPaymentService dmsOrderPaymentService;

    @Autowired
    private DmsSerialNumService dmsSerialNumService;

    @Autowired
    private DmsOrderOperationLogService dmsOrderOperationLogService;

    @ResponseBody
    @ApiOperation(value = "商城-下订单", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsOrderVo orderVo) {
        Long time1 = System.currentTimeMillis();
        PoDefaultValueGenerator.putDefaultValue(orderVo);
        DmsOrder result = dmsOrderService.create(orderVo, LoginManager.getUserSession());
        Long time2 = System.currentTimeMillis();
        logger.info("**********提交订单耗时:{}*************",(time2-time1)/1000);
        return toResult(result);
    }

    @ResponseBody
    @ApiOperation(value = "订单列表", response = JsonResult.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestBody DmsOrderVo orderVo) {
        PageHelper.Page<DmsOrderVo> pageList = dmsOrderService.pageList(orderVo, LoginManager.getUserSession());
        return toResult(pageList);
    }


    @ResponseBody
    @ApiOperation(value = "订单列表", response = JsonResult.class)
    @RequestMapping(value = "/list2", method = RequestMethod.POST)
    public Object listPC(@RequestBody DmsOrderVo orderVo) {
        PageHelper.Page<DmsOrderVo> pageList = dmsOrderService.pageListPC(orderVo, LoginManager.getUserSession());
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "订单详情", response = JsonResult.class)
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(@RequestBody DmsOrderVo orderVo) {
        PoDefaultValueGenerator.putDefaultValue(orderVo);
        DmsOrderVo result = dmsOrderService.detail(orderVo, LoginManager.getUserSession());
        result.setIsAgain(DmsModuleEnums.ACTIVE_STATUS.DIABLE.getType());
        return toResult(result);
    }

    @ResponseBody
    @ApiOperation(value = "订单审核", response = JsonResult.class)
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public Object audit(@RequestBody DmsOrderVo order) {
        PoDefaultValueGenerator.putDefaultValue(order);
        Date startDate = new Date();
        DmsOrder dmsOrder = dmsOrderService.audit(order, LoginManager.getUserSession());
        Date endDate = new Date();
        Long differSeconds = (endDate.getTime() - startDate.getTime()) / 1000;
        logger.info("************ 审核订单：{}，耗时：{}，开始时间：{}，结束时间：{} ************"
                , dmsOrder.getCode()
                , differSeconds
                , DateUtils.parseToTimeText(startDate)
                , DateUtils.parseToTimeText(endDate)
        );
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "订单审核", response = JsonResult.class)
    @RequestMapping(value = "/anjoyAudit", method = RequestMethod.POST)
    public Object anjoyAudit(@RequestParam @ApiParam("EAS订单编码") String orderNo,
                             @RequestParam @ApiParam("是否审核通过") Integer audit,
                             @RequestParam @ApiParam("审核意见") String remark) {
        //PoDefaultValueGenerator.putDefaultValue(order);
        dmsOrderService.audit(orderNo,audit,remark);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "取消订单", response = JsonResult.class)
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public Object cancel(@RequestBody DmsOrder order) {
        PoDefaultValueGenerator.putDefaultValue(order);
        dmsOrderService.cancel(order);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "订单付款", response = JsonResult.class)
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public Object pay(@RequestBody DmsOrderPaymentVo orderPaymentVo) {
        PoDefaultValueGenerator.putDefaultValue(orderPaymentVo);
        dmsOrderPaymentService.create(orderPaymentVo);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "收款确认", response = JsonResult.class)
    @RequestMapping(value = "/payConfirm", method = RequestMethod.POST)
    public Object payConfirm(@RequestBody DmsOrderPayment orderPayment) {
        PoDefaultValueGenerator.putDefaultValue(orderPayment);
        dmsOrderPaymentService.confirm(orderPayment);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "收款详情", response = JsonResult.class)
    @RequestMapping(value = "/payDetail", method = RequestMethod.GET)
    public Object payDetail(@RequestParam Long id) {
        DmsOrderPaymentVo orderPayment = dmsOrderPaymentService.detail(id);
        return toResult(orderPayment);
    }

    @ResponseBody
    @ApiOperation(value = "确认收货-订单", response = JsonResult.class)
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public Object confirm(@RequestBody DmsOrder order) {
        PoDefaultValueGenerator.putDefaultValue(order);
        dmsOrderService.confirm(order, LoginManager.getUserSession());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "订单操作日志", response = JsonResult.class)
    @RequestMapping(value = "/operationLog", method = RequestMethod.GET)
    public Object operationLog(@RequestParam("id") Long id, @RequestParam("relatedType") Integer relatedType,Integer sort) {
    	DmsOrderOperationLog param = new DmsOrderOperationLog();
        param.setRelatedType(relatedType);
        param.setOrderId(id);
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        param.setOrderField(" id ");
		if (sort == 1) {
			param.setOrderString(" desc ");
		}else{
			param.setOrderString(" asc ");
		}
        List<DmsOrderOperationLog> orderOperationLogList = dmsOrderOperationLogService.selectByCondition(param);
        return toResult(orderOperationLogList);
    }

    @ResponseBody
    @ApiOperation(value = "根据订单id获取待发货产品的仓库、货位等信息", response = JsonResult.class)
    @RequestMapping(value = "/productStorage", method = RequestMethod.GET)
    public Object productStorage(@RequestParam Long id) {
        Map<String, Object> result = Maps.newHashMap();

        List<DmsOrderProductVo> orderProductList = dmsOrderProductService.selectStorageInfoByOrderId(id);
        /* 生成发货单号 */
        String orderDeliveryCode = dmsSerialNumService.createSerialNumStr(DmsModuleEnums.SELL_OUT_SERIAL_KEY);

        result.put("productList", orderProductList);
        result.put("code", orderDeliveryCode);
        return toResult(result);
    }

    @ResponseBody
    @ApiOperation(value = "获取各个状态的订单数量", response = JsonResult.class)
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Object count() {
        Long userId = LoginManager.getCurrentUserId();
        Map<String, Integer> result = dmsOrderService.count(userId);
        return toResult(result);
    }

    @ResponseBody
    @ApiOperation(value = "获取已完成订单的产品列表", response = JsonResult.class)
    @RequestMapping(value = "/getFinished", method = RequestMethod.POST)
    public Object getFinished(@RequestBody DmsOrderVo order){
        order.setUserId(LoginManager.getCurrentUserId());
        PageHelper.Page<DmsOrderProductVo> finishedPageList= dmsOrderProductService.finishedPageList(order);
        return toResult(finishedPageList);
    }

    @ResponseBody
    @ApiOperation(value = "再来一单功能", response = JsonResult.class)
    @RequestMapping(value = "/again", method = RequestMethod.GET)
    public Object again(@ApiParam("订单id") @RequestParam Long id){
        DmsOrderVo param = new DmsOrderVo();
        param.setLoginId(LoginManager.getCurrentUserId());
        param.setId(id);
        DmsOrderVo result = dmsOrderService.again(param);

        result.setIsAgain(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
        return toResult(result);
    }

    @ResponseBody
    @ApiOperation(value = "修改订单产品数据", response = JsonResult.class)
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public Object updateProduct(@RequestBody DmsOrderVo order){
        order.setUserId(LoginManager.getCurrentUserId());
        dmsOrderProductService.update(order);
        return toResult(null);
    }

}