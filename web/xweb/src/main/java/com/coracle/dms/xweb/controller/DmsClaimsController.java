package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsClaims;
import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.service.DmsClaimsProductService;
import com.coracle.dms.service.DmsClaimsService;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/api/v1/dms/dmsClaims")
@Api(description = "正常理赔")
public class DmsClaimsController extends BaseController {

    private static final Logger logger = Logger.getLogger(DmsClaimsController.class);

    @Autowired
    private DmsClaimsService dmsClaimsService;

    @Autowired
    private DmsClaimsProductService dmsClaimsProductService;


    @ResponseBody
    @ApiOperation(value = "保存理赔", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@ApiParam("理赔实体类") @RequestBody DmsClaims dmsClaims) {

        //1待审批2已审批3已驳回
        dmsClaims.setState(1);

        PoDefaultValueGenerator.putDefaultValue(dmsClaims);
        ArrayList<DmsClaimsProduct> list = dmsClaims.getProductsList();
        if(list != null) {
            for (DmsClaimsProduct dmsClaimsProduct: list) {
                PoDefaultValueGenerator.putDefaultValue(dmsClaimsProduct);
            }
        }
        dmsClaimsService.insertObject(dmsClaims);
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "获取理赔", response = DmsClaims.class)
    @RequestMapping(value = "/listOne", method = RequestMethod.GET)
    public Object listOne(@ApiParam("理赔id") Long id) {
        DmsClaims data = dmsClaimsService.selectByPrimaryKey(id);
        if(data!=null){
            DmsClaimsProduct product = new DmsClaimsProduct();
            product.setDmsClaimsId(data.getId());
            ArrayList<DmsClaimsProduct> dataList =
                    (ArrayList<DmsClaimsProduct>) dmsClaimsProductService.selectByCondition(product);
            data.setProductsList(dataList);
        }
        return toResult(data);
    }


    @ResponseBody
    @ApiOperation(value = "条件分页获取理赔列表", response = DmsClaims.class)
    @RequestMapping(value = "/listByPage", method = RequestMethod.POST)
    public Object listByPageList(@ApiParam("条件和页码p页数s") @RequestBody DmsClaims dmsClaims) {
        PageHelper.Page<DmsClaims> dcd =
                dmsClaimsService.selectPageList(dmsClaims);
        return toResult(dcd);
    }



    @ResponseBody
    @ApiOperation(value = "修改理赔", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@ApiParam("理赔实体类") @RequestBody DmsClaims dmsClaims) {
        PoDefaultValueGenerator.putDefaultValue(dmsClaims);
        dmsClaimsService.updateByPrimaryKeySelective(dmsClaims);
        return toResult(null);
    }


}

