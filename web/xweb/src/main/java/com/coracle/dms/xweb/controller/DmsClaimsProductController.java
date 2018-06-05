package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.service.DmsClaimsProductService;
import com.coracle.dms.vo.DmsEmployeeVo;
import com.coracle.dms.vo.DmsOrderVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/v1/dms/dmsClaimsProduct")
@Api(description = "理赔产品")
public class DmsClaimsProductController extends BaseController {

    private static final Logger logger = Logger.getLogger(DmsClaimsProductController.class);

    @Autowired
    private DmsClaimsProductService dmsClaimsProductService;



    @ResponseBody
    @ApiOperation(value = "分页获取理赔产品列表", response = DmsClaimsProduct.class)
    @RequestMapping(value = "/listByPage", method = RequestMethod.POST)
    public Object listByPageList(
            @ApiParam("条件和页码p页数s") @RequestBody DmsClaimsProduct dmsClaimsProduct) {
        PageHelper.Page<DmsClaimsProduct> dcd =
                dmsClaimsProductService.selectPageList(dmsClaimsProduct);
        return toResult(dcd);
    }

    @ResponseBody
    @ApiOperation(value = "获取关联理赔产品列表", response = DmsClaimsProduct.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object listList(@ApiParam("正常理赔单id") Long dmsClaimsId) {
        DmsClaimsProduct product = new DmsClaimsProduct();
        product.setDmsClaimsId(dmsClaimsId);
        List<DmsClaimsProduct> data =
                dmsClaimsProductService.selectByCondition(product);
        return toResult(data);
    }


    @ResponseBody
    @ApiOperation(value = "获取理赔产品", response = DmsClaimsProduct.class)
    @RequestMapping(value = "/listOne", method = RequestMethod.POST)
    public Object listOne(@ApiParam("理赔产品id") Integer id) {
        DmsClaimsProduct data =
                dmsClaimsProductService.selectByPrimaryKey(id);
        return toResult(data);
    }

    @ResponseBody
    @ApiOperation(value = "保存理赔产品", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@ApiParam("理赔产品") @RequestBody DmsClaimsProduct dmsClaimsProduct) {
        if (dmsClaimsProduct.getDmsClaimsId() == null) {
            logger.info("正常理赔单id是null");
            return toResult(null, "正常理赔单id是null");
        }
        PoDefaultValueGenerator.putDefaultValue(dmsClaimsProduct);
        dmsClaimsProductService.insertSelective(dmsClaimsProduct);
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "修改理赔产品", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@ApiParam("修改理赔产品") @RequestBody DmsClaimsProduct dmsClaimsProduct) {
        PoDefaultValueGenerator.putDefaultValue(dmsClaimsProduct);
        dmsClaimsProductService.updateByPrimaryKeySelective(dmsClaimsProduct);
        return toResult(null);
    }



    @ResponseBody
    @ApiOperation(value = "删除理赔产品", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@ApiParam("理赔产品id") Integer id) {
        dmsClaimsProductService.deleteByPrimaryKey(id);
        return toResult(null);
    }



}




