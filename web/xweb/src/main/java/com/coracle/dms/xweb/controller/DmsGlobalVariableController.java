package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsGlobalVariable;
import com.coracle.dms.service.DmsGlobalVariableService;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/18.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/globalVariable")
@Api(description = "DMS全局变量调用接口")
public class DmsGlobalVariableController extends BaseController {
    @Autowired
    private DmsGlobalVariableService globalVariableService;

    @ResponseBody
    @ApiOperation(value = "新增或者修改全局变量", response = JsonResult.class)
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsGlobalVariable dmsGlobalVariable){
        PoDefaultValueGenerator.putDefaultValue(dmsGlobalVariable);
        UserSession userSession= LoginManager.getUserSession();
        if(BlankUtil.isEmpty(userSession)){
            throw  new ControllerException("用户会话失效，请重新登录！");
        }
        globalVariableService.saveOrUpdate(dmsGlobalVariable,userSession);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "删除全局变量", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@ApiParam("全局变量id")@RequestParam("id") Long id){
        UserSession userSession= LoginManager.getUserSession();
        if(BlankUtil.isEmpty(userSession)){
            throw  new ControllerException("用户会话失效，请重新登录！");
        }
        globalVariableService.delete(id,userSession);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "按ID获取全局变量详情", response = DmsGlobalVariable.class)
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object selectDetail(@ApiParam("全局变量id")@RequestParam("id") Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("请输入要获取全局变量的ID！");
        }
        DmsGlobalVariable dmsGlobalVariable = globalVariableService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsGlobalVariable)||BlankUtil.isEmpty(dmsGlobalVariable.getId())||dmsGlobalVariable.getId()==0||dmsGlobalVariable.getRemoveFlag()==1){//未查到数据，或者状态为删除则未查询到
            throw new ControllerException("无法获取id为["+id+"]的全局变量信息！");
        }
        return toResult(dmsGlobalVariable);
    }
    @ResponseBody
    @ApiOperation(value = "按条件获取全局变量列表", response = DmsGlobalVariable.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestBody DmsGlobalVariable dmsGlobalVariable){
        dmsGlobalVariable.setOrderField(" id ");
        dmsGlobalVariable.setOrderString(" desc ");
        PageHelper.Page<DmsGlobalVariable> pageList=globalVariableService.getPageList(dmsGlobalVariable,LoginManager.getUserSession());
        return toResult(pageList, "操作成功");
    }
}
