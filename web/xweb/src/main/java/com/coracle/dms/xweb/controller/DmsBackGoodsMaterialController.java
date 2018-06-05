package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsBackGoodsMaterial;
import com.coracle.dms.po.DmsBackGoodsOrder;
import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.service.DmsBackGoodsMaterialService;
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

@Controller
@RequestMapping(value = "/api/v1/dms/dmsBackGoodsMaterial")
@Api(description = "退货物料-天正")
public class DmsBackGoodsMaterialController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsBackGoodsMaterialController.class);

    @Autowired
    private DmsBackGoodsMaterialService dmsBackGoodsMaterialService;

    @ResponseBody
    @ApiOperation(value = "生成退货物料-天正", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(
            @ApiParam("退货物料实体类") @RequestBody DmsBackGoodsMaterial dmsBackGoodsMaterial) {
        if (dmsBackGoodsMaterial.getBackGoodsOrderId() == null) {
            logger.info("退货单BackGoodsOrderId是null");
            return toResult(null, "退货单BackGoodsOrderId是null");
        }
        PoDefaultValueGenerator.putDefaultValue(dmsBackGoodsMaterial);
        dmsBackGoodsMaterialService.insertSelective(dmsBackGoodsMaterial);
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "分页退货物料单列表", response = DmsClaimsProduct.class)
    @RequestMapping(value = "/listByPage", method = RequestMethod.POST)
    public Object listByPageList(
            @ApiParam("条件和页码p页数s") @RequestBody DmsBackGoodsMaterial dmsBackGoodsMaterial) {
        PageHelper.Page<DmsBackGoodsMaterial> dcd =
                dmsBackGoodsMaterialService.selectPageList(dmsBackGoodsMaterial);
        return toResult(dcd);
    }


}