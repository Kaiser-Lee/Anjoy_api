package com.coracle.dms.xweb.controller.dms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coracle.dms.dto.DmsRemarkDto;
import com.coracle.dms.service.DmsRemarkService;
import com.coracle.dms.vo.DmsRemarkVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.dms.xweb.controller.BaseController;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;

@Controller
@RequestMapping(value = "/api/v1/dms/remark")
@Api(description = "备注管理接口")
public class DmsRemarkController extends BaseController {

	@Resource
	private DmsRemarkService dmsRemarkService;
	
	@ResponseBody
    @ApiOperation(value = "备注列表",response = DmsRemarkDto.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@ApiParam("备注实体")@RequestBody DmsRemarkDto paramDto){
		if(paramDto == null || paramDto.getRelatedTableType() == null || paramDto.getRelatedTableId() == null){
			throw  new ControllerException("参数错误！");
		}
        paramDto.setOrderField(" b.id ");
        paramDto.setOrderString(" desc ");
        PageHelper.Page<DmsRemarkDto> pageList = dmsRemarkService.selectForListPage(paramDto);
        return toResult(pageList);
    }
	
	@ResponseBody
    @ApiOperation(value = "新增备注", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsRemarkVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsRemarkService.createRemark(paramVo, LoginManager.getUserSession());
        return toResult(null);
    }
	
	@ResponseBody
    @ApiOperation(value = "修改备注", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsRemarkVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsRemarkService.updateRemark(paramVo);
        return toResult(null);
    }
	
	@ResponseBody
    @ApiOperation(value = "删除备注", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody DmsRemarkVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsRemarkService.deleteRemark(paramVo);
        return toResult(null);
    }
}
