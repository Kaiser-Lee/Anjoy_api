package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsTianGoodsStore;
import com.coracle.dms.po.DmsTianSubStore;
import com.coracle.dms.service.DmsTianSubStoreService;
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
@RequestMapping(value = "/api/v1/dms/dmsTianSubStore")
@Api(description = "子库存-天正")
public class DmsTianSubStoreController extends BaseController {

    private static final Logger logger = Logger.getLogger(DmsTianSubStoreController.class);

    @Autowired
    private DmsTianSubStoreService dmsTianSubStoreService;


    @ResponseBody
    @ApiOperation(value = "保存子库存", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@ApiParam("子库存实体类") @RequestBody DmsTianSubStore dmsTianSubStore) {
        PoDefaultValueGenerator.putDefaultValue(dmsTianSubStore);
        dmsTianSubStoreService.insertSelective(dmsTianSubStore);
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "获取子库存", response = DmsTianSubStore.class)
    @RequestMapping(value = "/listOne", method = RequestMethod.POST)
    public Object listOne(@ApiParam("子库存id") Integer id) {
        DmsTianSubStore data =
                dmsTianSubStoreService.selectByPrimaryKey(id);
        return toResult(data);
    }



    @ResponseBody
    @ApiOperation(value = "分页获取子库存列表", response = DmsTianGoodsStore.class)
    @RequestMapping(value = "/listByPage", method = RequestMethod.POST)
    public Object listByPageList(
            @ApiParam("条件和页码p页数s") @RequestBody DmsTianSubStore dmsTianSubStore) {
        PageHelper.Page<DmsTianSubStore> dcd =
                dmsTianSubStoreService.selectPageList(dmsTianSubStore);
        return toResult(dcd);
    }


}


