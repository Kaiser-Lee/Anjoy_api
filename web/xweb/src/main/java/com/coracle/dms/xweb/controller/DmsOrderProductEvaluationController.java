package com.coracle.dms.xweb.controller;

import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import com.coracle.dms.po.DmsOrderProductEvaluation;
import com.coracle.dms.service.DmsOrderProductEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Api(description = "订单产品评价管理接口")
@RequestMapping("/api/v1/dms/orderProductEvaluation")
public class DmsOrderProductEvaluationController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsOrderProductEvaluation.class);

    @Autowired
    private DmsOrderProductEvaluationService dmsOrderProductEvaluationService;

    @ResponseBody
    @ApiOperation(value = "删除评论", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody DmsOrderProductEvaluation param) {
        dmsOrderProductEvaluationService.remove(param);
        return toResult(null);
    }
}
