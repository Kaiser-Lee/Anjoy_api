package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsStorageSaleOut;
import com.coracle.dms.service.DmsStorageInventoryService;
import com.coracle.dms.service.DmsStorageSaleOutService;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/api/v1/dms/storageSaleOut")
@Api(description = "销售出库管理接口")
public class DmsStorageSaleOutController extends BaseController {

	@Resource
	private DmsStorageSaleOutService dmsStorageSaleOutService;
	
	/**
     * 根据条件获取入库单列表
     */
    @ResponseBody
    @ApiOperation(value = "销售出库列表",response = DmsStorageSaleOut.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@RequestBody DmsStorageSaleOut dmsStorageSaleOut){

    PageHelper.Page<DmsStorageSaleOut> pageList = dmsStorageSaleOutService.selectForListPage(dmsStorageSaleOut);
        return toResult(pageList);
}
    
    @ResponseBody
    @ApiOperation(value = "销售出库新增", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsStorageSaleOut paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        UserSession userSession=LoginManager.getUserSession();
        dmsStorageSaleOutService.insertTo(paramVo,userSession);
        return toResult();
    }

}
