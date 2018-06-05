package com.coracle.dms.xweb.controller.dms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dto.DmsTransferApplyDto;
import com.coracle.dms.service.DmsTransferApplyService;
import com.coracle.dms.vo.DmsStorageVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.dms.xweb.controller.BaseController;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;

@Controller
@RequestMapping(value = "/api/v1/dms/transfer")
@Api(description = "调货管理接口")
public class DmsTransferApplyController extends BaseController {

	@Resource
	private DmsTransferApplyService dmsTransferApplyService;
	
    @ResponseBody
	@ApiOperation(value = "调货列表", response = DmsStorageVo.class, notes = "渠道、门店调货列表显示，只需要传处理状态（1-已处理；0-未处理）")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object getList(@RequestBody DmsTransferApplyDto search){
    	search.setOrderField(" id ");
    	search.setOrderString(" desc ");
    	search.setStoreId(LoginManager.getUserSession().getOrgId());
    	PageHelper.Page<DmsTransferApplyDto> pageList = dmsTransferApplyService.selectForListPage(search);
        return toResult(pageList);
    }
    
    @ResponseBody
	@ApiOperation(value = "调货申请列表", response = DmsStorageVo.class, notes = "查询我申请的列表，可以根据条件查询，处理状态无需传")
	@RequestMapping(value = "/applyList", method = RequestMethod.POST)
    public Object applyList(@RequestBody DmsTransferApplyDto search){
    	search.setOrderField(" id ");
    	search.setOrderString(" desc ");
    	search.setApplyType(DmsModuleEnums.TRANSFER_APPLY_TYPE.STORE_DH.getType());
    	search.setReceiveStoreId(LoginManager.getUserSession().getOrgId());
    	PageHelper.Page<DmsTransferApplyDto> pageList = dmsTransferApplyService.selectForListPage(search);
        return toResult(pageList);
    }
    
    @ResponseBody
    @ApiOperation(value = "调货详情",response = DmsStorageVo.class)
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(@ApiParam("调货ID") @RequestParam(value = "id",required = true) Long id){
        return toResult(dmsTransferApplyService.detail(id,LoginManager.getUserSession()));
    }
    
    @ResponseBody
    @ApiOperation(value = "创建调货申请单", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsTransferApplyDto paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsTransferApplyService.create(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "创建发货单", response = JsonResult.class)
    @RequestMapping(value = "/createDelivery", method = RequestMethod.POST)
    public Object createDelivery(@RequestBody DmsTransferApplyDto paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsTransferApplyService.createDelivery(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "确认收货", response = JsonResult.class)
    @RequestMapping(value = "/updateReceived", method = RequestMethod.POST)
    public Object updateReceived(@RequestBody DmsTransferApplyDto paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsTransferApplyService.updateReceived(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "取消调货申请", response = JsonResult.class)
    @RequestMapping(value = "/updateCancel", method = RequestMethod.POST)
    public Object updateCancel(@RequestBody DmsTransferApplyDto paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsTransferApplyService.updateCancel(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
}
