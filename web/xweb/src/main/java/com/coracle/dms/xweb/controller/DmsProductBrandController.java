package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsProductBrand;
import com.coracle.dms.service.DmsProductBrandService;
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

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/productBrand")
@Api(description = "DMS产品品牌调用接口")
public class DmsProductBrandController extends BaseController {
    @Autowired
    private DmsProductBrandService dmsProductBrandService;

    @ResponseBody
    @ApiOperation(value = "新增或者修改产品品牌", response = JsonResult.class)
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsProductBrand dmsProductBrand){
        PoDefaultValueGenerator.putDefaultValue(dmsProductBrand);
        UserSession userSession= LoginManager.getUserSession();
        if(BlankUtil.isEmpty(userSession)){
            throw  new ControllerException("用户会话失效，请重新登录！");
        }
        dmsProductBrandService.saveOrUpdate(dmsProductBrand,userSession);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "删除产品品牌", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody DmsProductBrand dmsProductBrand){
        UserSession userSession= LoginManager.getUserSession();
        if(BlankUtil.isEmpty(userSession)){
            throw  new ControllerException("用户会话失效，请重新登录！");
        }
        PoDefaultValueGenerator.putDefaultValue(dmsProductBrand);
        dmsProductBrandService.delete(dmsProductBrand,userSession);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "按ID获取产品品牌详情", response = DmsProductBrand.class)
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object selectDetail(@ApiParam("产品品牌id")@RequestParam("id") Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("请输入要获取产品品牌的ID！");
        }
        DmsProductBrand dmsProductBrand = dmsProductBrandService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsProductBrand)||BlankUtil.isEmpty(dmsProductBrand.getId())||dmsProductBrand.getId()==0||dmsProductBrand.getRemoveFlag()==1){//未查到数据，或者状态为删除则未查询到
            throw new ControllerException("无法获取id为["+id+"]的产品品牌信息！");
        }
        return toResult(dmsProductBrand);
    }

    @ResponseBody
    @ApiOperation(value = "按条件获取产品品牌列表", response = DmsProductBrand.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestBody DmsProductBrand dmsProductBrand){
        dmsProductBrand.setOrderString(" desc ");
        dmsProductBrand.setOrderField(" last_updated_date ");
        PageHelper.Page<DmsProductBrand> pageList=dmsProductBrandService.selectForPageList(dmsProductBrand,LoginManager.getUserSession());
        return toResult(pageList, "操作成功");
    }

    @ResponseBody
    @ApiOperation(value = "获取全部产品品牌列表", response = DmsProductBrand.class)
    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    public Object getAllList(){
        DmsProductBrand dmsProductBrand = new DmsProductBrand();
        dmsProductBrand.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
        List<DmsProductBrand> list = dmsProductBrandService.selectByCondition(dmsProductBrand);
       return toResult(list);
    }
}
