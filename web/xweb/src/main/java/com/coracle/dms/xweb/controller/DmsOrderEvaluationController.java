package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsOrderLogisticsEvaluation;
import com.coracle.dms.po.DmsOrderProductEvaluation;
import com.coracle.dms.service.DmsOrderEvaluationService;
import com.coracle.dms.vo.DmsOrderEvaluationVo;
import com.coracle.dms.vo.DmsOrderProductEvaluationVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/v1/dms/orderEvaluation")
public class DmsOrderEvaluationController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsOrderEvaluationController.class);

    @Autowired
    private DmsOrderEvaluationService dmsOrderEvaluationService;

    @ResponseBody
    @ApiOperation(value = "添加订单评价", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsOrderEvaluationVo orderEvaluationVo) {
        orderEvaluationVo.setUserId(LoginManager.getCurrentUserId());
        dmsOrderEvaluationService.create(orderEvaluationVo);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "订单评价详情", response = JsonResult.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(DmsOrderProductEvaluation orderProductEvaluation) {
        DmsOrderEvaluationVo result = dmsOrderEvaluationService.detail(orderProductEvaluation);
        return toResult(result);
    }
}