package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsChannelMinimum;
import com.coracle.dms.service.DmsChannelMinimumService;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.base.vo.ExtraJsonResult;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/dms/dmsChannelMinimum")
@Api(description = "起订量接口")
public class DmsChannelMinimumController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsChannelMinimumController.class);

    @Autowired
    private DmsChannelMinimumService dmsChannelMinimumService;

    @ResponseBody
    @ApiOperation(value = "创建起订量",response = JsonResult.class)
    @RequestMapping(value = "/insert", method= RequestMethod.POST)
    public Object insert(@ApiParam("创建起订量及整板下单量")@RequestBody DmsChannelVo dmsChannelVo){
        UserSession userSession= LoginManager.getUserSession();
        dmsChannelMinimumService.insert(dmsChannelVo,userSession);
        return toResult(null);

    }

    @ResponseBody
    @ApiOperation(value = "修改起订量",response = JsonResult.class)
    @RequestMapping(value = "/update", method= RequestMethod.POST)
    public Object update(@ApiParam("修改起订量及整板下单量")@RequestBody DmsChannelVo dmsChannelVo){
        UserSession userSession= LoginManager.getUserSession();
        dmsChannelMinimumService.insert(dmsChannelVo,userSession);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "根据渠道id及条件查询起订量",response = DmsProductVo.class)
    @RequestMapping(value = "/findProductForMinimum", method= RequestMethod.POST)
    public Object productList(@ApiParam("产品实体类") @RequestBody DmsProductVo dmsProductVo){

        PageHelper.Page<DmsProductVo> ProductVoPageList = dmsChannelMinimumService.findProductForMinimum(dmsProductVo);
        return toResult(ProductVoPageList);


    }
    @ResponseBody
    @ApiOperation(value = "根据渠道id及条件查询起订量",response = DmsProductVo.class)
    @RequestMapping(value = "/findProductForMinimum2", method= RequestMethod.POST)
    public Object productList2(@ApiParam("实体类") @RequestBody DmsChannelVo dmsChannelVo ){

        DmsChannelVo dmsChannelVo2 = dmsChannelMinimumService.findProductForMinimum2(dmsChannelVo);
        PageHelper.Page<DmsProductVo> data = dmsChannelVo2.getDmsProductPage();
        JsonResult jsonResult = (JsonResult) toResult(data);
        ExtraJsonResult extraJsonResult = new ExtraJsonResult();
        BeanUtils.copyProperties(jsonResult, extraJsonResult);
        extraJsonResult.setMinOrderQuantity(dmsChannelVo2.getMinOrderQuantity());

        extraJsonResult.setCid(dmsChannelVo2.getId());
        extraJsonResult.setS((long) 10);
        extraJsonResult.setPathIds(dmsChannelVo2.getPathIds());

        return extraJsonResult;
    }


    @ResponseBody
    @ApiOperation(value = "根据渠道id查询起订量",response = DmsProductVo.class)
    @RequestMapping(value = "/findProductById", method= RequestMethod.GET)
    public Object selectProduct(@ApiParam("渠道id")@RequestParam("id") Long id){

        DmsChannelVo dmsChannelVo= dmsChannelMinimumService.findProductByID(id);
        return toResult(dmsChannelVo);


    }
    @ResponseBody
    @ApiOperation(value = "修改起订量",response = DmsProductVo.class)
    @RequestMapping(value = "/updateMinOrderQuantity", method= RequestMethod.GET)
    public Object updateMinOrderQuantity(Long minOrderQuantity, Long id){

        dmsChannelMinimumService.updateMinOrderQuantity(minOrderQuantity, id);

        return toResult(null);

    }
    @ResponseBody
    @ApiOperation(value = "批量删除",response =  JsonResult.class)
    @RequestMapping(value = "/batchDelete", method= RequestMethod.POST)
    public Object testBatchDel(@RequestBody List<DmsChannelMinimum> list){

        dmsChannelMinimumService.batchDelete(list);
        return  toResult("成功");
    }

    @ResponseBody
    @ApiOperation(value = "批量插入",response = JsonResult.class)
    @RequestMapping(value = "/batchInsert", method= RequestMethod.POST)
    public Object testBatchInsert(@RequestBody List<DmsChannelMinimum> list){

        dmsChannelMinimumService.batchInsert(list);
        return  toResult("成功");
    }



}