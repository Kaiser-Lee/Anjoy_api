package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsPromotion;
import com.coracle.dms.po.DmsPromotionProductRecord;
import com.coracle.dms.service.DmsPromotionProductRecordService;
import com.coracle.dms.service.DmsPromotionService;
import com.coracle.dms.vo.DmsPromotionProductRecordVo;
import com.coracle.dms.vo.DmsPromotionVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(description = "促销活动管理接口")
@RequestMapping("/api/v1/dms/promotion")
public class DmsPromotionController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsPromotionController.class);

    @Autowired
    private DmsPromotionService dmsPromotionService;

    @Autowired
    private DmsPromotionProductRecordService dmsPromotionProductRecordService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "促销活动列表", response = JsonResult.class)
    public Object pageList(@RequestBody DmsPromotionVo promotionVo) {
        PageHelper.Page<DmsPromotionVo> pageList = dmsPromotionService.pageList(promotionVo);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "新增促销活动", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsPromotionVo promotionVo) {
        PoDefaultValueGenerator.putDefaultValue(promotionVo);
        dmsPromotionService.insertOrUpdate(promotionVo, LoginManager.getUserSession());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "修改促销活动", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsPromotionVo promotionVo) {
        PoDefaultValueGenerator.putDefaultValue(promotionVo);
        dmsPromotionService.insertOrUpdate(promotionVo, LoginManager.getUserSession());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "根据促销活动id判断是否可以进行编辑", response = JsonResult.class)
    @RequestMapping(value = "/modifiable", method = RequestMethod.GET)
    public Object modifiable(@RequestParam Long id) {
        Boolean result = dmsPromotionService.modifiable(id);
        return toResult(result);
    }

    @ResponseBody
    @ApiOperation(value = "促销活动详情", response = DmsPromotion.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam("促销活动id") @RequestParam Long id) {
        DmsPromotionVo promotionVo = dmsPromotionService.detail(id);
        return toResult(promotionVo);
    }

    @ResponseBody
    @ApiOperation(value = "删除促销活动", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete() {
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "促销产品销售记录列表", response = JsonResult.class)
    @RequestMapping(value = "/soldRecord", method = RequestMethod.POST)
    public Object soldRecord(@RequestBody DmsPromotionProductRecordVo promotionProductRecord) {
        PageHelper.Page<DmsPromotionProductRecordVo> pageList = dmsPromotionProductRecordService.pageList(promotionProductRecord);
        return toResult(pageList);
    }
}