package com.coracle.dms.xweb.controller;

import com.coracle.dms.dao.mybatis.DmsBackGoodsMaterialMapper;
import com.coracle.dms.po.DmsBackGoodsMaterial;
import com.coracle.dms.po.DmsBackGoodsOrder;
import com.coracle.dms.po.DmsClaims;
import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.service.DmsBackGoodsMaterialService;
import com.coracle.dms.service.DmsBackGoodsOrderService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/dms/dmsBackGoodsOrder")
@Api(description = "退货单-天正")
public class DmsBackGoodsOrderController extends BaseController {

    private static final Logger logger = Logger.getLogger(DmsBackGoodsOrderController.class);

    @Autowired
    private DmsBackGoodsOrderService dmsBackGoodsOrderService;

    @Autowired
    private DmsBackGoodsMaterialService dmsBackGoodsMaterialService;


    @ResponseBody
    @ApiOperation(value = "生成退货单-天正", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(
            @ApiParam("退货单实体类") @RequestBody DmsBackGoodsOrder dmsBackGoodsOrder) {
        PoDefaultValueGenerator.putDefaultValue(dmsBackGoodsOrder);
        ArrayList<DmsBackGoodsMaterial> list = dmsBackGoodsOrder.getDmsBackGoodsMaterialList();
        if(list!=null){
            for (DmsBackGoodsMaterial dmsBackGoodsMaterial: list) {
                PoDefaultValueGenerator.putDefaultValue(dmsBackGoodsMaterial);
            }
        }

        dmsBackGoodsOrderService.insertObject(dmsBackGoodsOrder);

        return toResult(null);
    }



    @ResponseBody
    @ApiOperation(value = "分页获取退货单列表", response = DmsBackGoodsOrder.class)
    @RequestMapping(value = "/listByPage", method = RequestMethod.POST)
    public Object listByPageList(
            @ApiParam("条件和页码p页数s") @RequestBody DmsBackGoodsOrder dmsBackGoodsOrder) {
        PageHelper.Page<DmsBackGoodsOrder> dcd =
                dmsBackGoodsOrderService.selectPageList(dmsBackGoodsOrder);
        return toResult(dcd);
    }

//    @ResponseBody
//    @ApiOperation(value = "获取退货单列表", response = DmsBackGoodsOrder.class)
//    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    public Object listList(@RequestBody long id) {
//        DmsBackGoodsOrder obj = new DmsBackGoodsOrder();
//        obj.setId(id);
//        List<DmsBackGoodsOrder> data =
//                dmsBackGoodsOrderService.selectByCondition(obj);
//        return toResult(data);
//    }


    @ResponseBody
    @ApiOperation(value = "获取退货单", response = DmsBackGoodsOrder.class)
    @RequestMapping(value = "/listOne", method = RequestMethod.POST)
    public Object listOne(@ApiParam("主键id") Long id) {
        DmsBackGoodsOrder data =
                dmsBackGoodsOrderService.selectByPrimaryKey(id);
        //
        DmsBackGoodsMaterial obj = new DmsBackGoodsMaterial();
        obj.setBackGoodsOrderId(data.getId());
        ArrayList<DmsBackGoodsMaterial> goodsMaterials =
                (ArrayList<DmsBackGoodsMaterial>) dmsBackGoodsMaterialService.selectByCondition(obj);
        data.setDmsBackGoodsMaterialList(goodsMaterials);
        return toResult(data);
    }

}







