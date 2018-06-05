package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsClaims;
import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.po.DmsTianGoodsStore;
import com.coracle.dms.service.DmsTianGoodsStoreService;
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
@RequestMapping(value = "/api/v1/dms/dmsTianGoodsStore")
@Api(description = "库存-天正")
public class DmsTianGoodsStoreController extends BaseController {

    private static final Logger logger = Logger.getLogger(DmsTianGoodsStoreController.class);

    @Autowired
    private DmsTianGoodsStoreService dmsTianGoodsStoreService;


    @ResponseBody
    @ApiOperation(value = "保存库存", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@ApiParam("库存实体类") @RequestBody DmsTianGoodsStore dmsTianGoodsStore) {
        PoDefaultValueGenerator.putDefaultValue(dmsTianGoodsStore);
        dmsTianGoodsStoreService.insertSelective(dmsTianGoodsStore);
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "获取库存", response = DmsTianGoodsStore.class)
    @RequestMapping(value = "/listOne", method = RequestMethod.POST)
    public Object listOne(@ApiParam("主键id") Long id) {
        DmsTianGoodsStore data =
                dmsTianGoodsStoreService.selectByPrimaryKey(id);
        return toResult(data);
    }

    @ResponseBody
    @ApiOperation(value = "分页获取库存列表", response = DmsTianGoodsStore.class)
    @RequestMapping(value = "/listByPage", method = RequestMethod.POST)
    public Object listByPageList(
            @ApiParam("条件和页码p页数s") @RequestBody DmsTianGoodsStore dmsTianGoodsStore) {
        PageHelper.Page<DmsTianGoodsStore> dcd =
                dmsTianGoodsStoreService.selectPageList(dmsTianGoodsStore);
        return toResult(dcd);
    }


}







