package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsLadderPriceProduct;

import com.coracle.dms.service.DmsLadderPriceProductService;
import com.coracle.dms.vo.DmsLadderPriceProductVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/dms/productLadder")
@Api(description = "阶梯价格管理接口")
public class DmsLadderPriceProductController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsLadderPriceProductController.class);

    @Autowired
    private DmsLadderPriceProductService dmsLadderPriceProductService ;
    /**
     * pc端根据条件查询阶梯价格列表
     *
     * */
    @ResponseBody
    @ApiOperation(value="阶梯价格列表",response = JsonResult.class)
    @RequestMapping(value="/list",method= RequestMethod.POST)
    public Object  pcList(@RequestBody DmsLadderPriceProductVo dmsLadderPriceProductVo){

        PageHelper.Page<DmsLadderPriceProductVo> pageList  =dmsLadderPriceProductService.pageList(dmsLadderPriceProductVo);

        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "阶梯价格详情", response = DmsLadderPriceProduct.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object selectVoById(@ApiParam("阶梯价格项 id") @RequestParam Long id){

        return toResult(dmsLadderPriceProductService.selectDetailById(id));
    }


    @ResponseBody
    @ApiOperation(value = "新增阶梯价格项", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsLadderPriceProductVo dmsLadderPriceProductVo){

        PoDefaultValueGenerator.putDefaultValue(dmsLadderPriceProductVo);
        dmsLadderPriceProductService.insertOrUpdate(dmsLadderPriceProductVo, LoginManager.getUserSession());

        return toResult(null);
    }



}