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

import com.coracle.dms.service.DmsChannelContactsService;
import com.coracle.dms.vo.DmsChannelContactsVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.dms.xweb.controller.BaseController;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.util.BlankUtil;

/**
 * Created by tanyb on 2017/8/22.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/channelContact")
@Api(description = "渠道联系人管理接口")
public class DmsChannelContactController extends BaseController {
	
	@Resource
	private DmsChannelContactsService dmsChannelContactsService;

	@ResponseBody
    @ApiOperation(value = "渠道联系人列表",response = DmsChannelContactsVo.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@ApiParam("渠道联系人实体")@RequestBody DmsChannelContactsVo paramVo){
		if(paramVo == null || paramVo.getChannelId() == null){
			throw  new ControllerException("参数错误！");
		}
        PageHelper.Page<DmsChannelContactsVo> pageList = dmsChannelContactsService.selectForListPage(paramVo);
        return toResult(pageList);
    }
	
	@ResponseBody
    @ApiOperation(value = "渠道联系人详情", response = JsonResult.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam("联系人id")@RequestParam("id") Long id){
		if (BlankUtil.isEmpty(id)){
            throw new ControllerException("参数异常！");
        }
        return toResult(this.dmsChannelContactsService.detail(id, LoginManager.getUserSession()));
	}
	@ResponseBody
    @ApiOperation(value = "新增渠道联系人", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsChannelContactsVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsChannelContactsService.create(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
	
	@ResponseBody
    @ApiOperation(value = "修改渠道联系人", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsChannelContactsVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsChannelContactsService.update(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
	
	@ResponseBody
    @ApiOperation(value = "开通账号", response = JsonResult.class)
    @RequestMapping(value = "/createAccount", method = RequestMethod.GET)
    public Object createAccount(@ApiParam("联系人id") @RequestParam(value = "id",required = true) Long contactId){
		if(contactId == null || contactId == 0){
			throw new ControllerException("联系人id不能为空！");
		}
		this.dmsChannelContactsService.createAccount(contactId, LoginManager.getUserSession());
		return toResult(null);
    }
	
}
