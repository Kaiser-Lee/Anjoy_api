package com.coracle.dms.xweb.controller;

import com.coracle.dms.dto.DmsRetailOutStorageDto;
import com.coracle.dms.po.DmsCustomers;
import com.coracle.dms.service.DmsCustomersService;
import com.coracle.dms.service.DmsDynamicService;
import com.coracle.dms.vo.DmsCustomersVo;
import com.coracle.dms.vo.DmsDynamicVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/customers")
@Api(description = "零售客户接口")
public class DmsCustomersController extends BaseController {
    @Autowired
    private DmsCustomersService dmsCustomersService;
    @Autowired
    private DmsDynamicService dmsDynamicService;

    @ResponseBody
    @ApiOperation(value = "新增客户信息",response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insertCustomer(@RequestBody DmsCustomersVo dmsCustomersVo){
        PoDefaultValueGenerator.putDefaultValue(dmsCustomersVo);
        dmsCustomersVo.setUserId(LoginManager.getCurrentUserId());
        dmsCustomersService.insertCustomer(dmsCustomersVo, LoginManager.getCurrentUserId());
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "修改客户信息",response = JsonResult.class)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Object modifyCustomer(@RequestBody DmsCustomersVo dmsCustomersVo){
        if (BlankUtil.isEmpty(dmsCustomersVo)){
            throw new ControllerException("请输入信息！");
        }
        if (dmsCustomersVo.getId()==null||dmsCustomersVo.getId()==0){
            throw new ControllerException("客户信息Id不能为空！");
        }
        PoDefaultValueGenerator.putDefaultValue(dmsCustomersVo);
        dmsCustomersService.updateCustomer(dmsCustomersVo, LoginManager.getCurrentUserId());
        return toResult();
    }

    /**
     * 分页获取客户列表
     */
    @ResponseBody
    @ApiOperation(value = "分页获取客户列表", response = JsonResult.class)
    @RequestMapping(value ="/getList",method = RequestMethod.POST)
    public Object getCustomerList(@RequestBody DmsCustomersVo dmsCustomersVo){
        dmsCustomersVo.setOrderField(" id ");
        dmsCustomersVo.setOrderString(" desc ");
        dmsCustomersVo.setUserId(LoginManager.getCurrentUserId());
        PageHelper.Page<DmsCustomersVo> pageList = dmsCustomersService.selectForPage(dmsCustomersVo);
        return toResult(pageList);
    }

    /**
     * 获取客户基本信息详情
     */
    @ResponseBody
    @ApiOperation(value = "获取客户基本信息详情", response = JsonResult.class)
    @RequestMapping(value ="/getDetail",method = RequestMethod.GET)
    public Object getCustomerDetail(@ApiParam("客户ID") @RequestParam("id") Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("请输入客户ID");
        }
        DmsCustomersVo dmsCustomersVo = dmsCustomersService.selectVoByPrimaryKey(id);
        dmsCustomersVo.setOrgName(LoginManager.getUserSession().getOrgName());
        if (BlankUtil.isEmpty(dmsCustomersVo)){
            throw new ControllerException("无法获取客户ID为【"+id+"】的客户信息");
        }
        return toResult(dmsCustomersVo);
    }

    @ResponseBody
    @ApiOperation(value = "分页获取客户购买记录列表", response = JsonResult.class)
    @RequestMapping(value ="/getShopPageList",method = RequestMethod.GET)
    public Object getShopPageList(@ApiParam("客户ID") @RequestParam("id") Long id,
                                  @ApiParam("分页页码") @RequestParam(value = "p",required = false) Integer p,
                                  @ApiParam("每页条数") @RequestParam(value = "s",required = false) Integer s){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("请输入客户ID");
        }
        DmsCustomers dmsCustomers = dmsCustomersService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsCustomers)){
            throw new ControllerException("无法获取客户ID为【"+id+"】的客户信息");
        }
        DmsDynamicVo dmsDynamicVo = new DmsDynamicVo();
        dmsDynamicVo.setCustomerId(dmsCustomers.getId());
        dmsDynamicVo.setP(p);
        dmsDynamicVo.setS(s);
        dmsDynamicVo.setOrderField(" send_date ");
        dmsDynamicVo.setOrderString(" desc ");
        PageHelper.Page<DmsDynamicVo> pageList = dmsDynamicService.selectForPage(dmsDynamicVo);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "获取客户全部购买记录列表", response = JsonResult.class)
    @RequestMapping(value ="/getShopAllList",method = RequestMethod.GET)
    public Object getShopAllList(@ApiParam("客户ID") @RequestParam("id") Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("请输入客户ID");
        }
        DmsCustomers dmsCustomers = dmsCustomersService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsCustomers)){
            throw new ControllerException("无法获取客户ID为【"+id+"】的客户信息");
        }
        DmsDynamicVo dmsDynamicVo = new DmsDynamicVo();
        dmsDynamicVo.setCustomerId(dmsCustomers.getId());
        dmsDynamicVo.setOrderField(" send_date ");
        dmsDynamicVo.setOrderString(" desc ");
        List<DmsDynamicVo> list = dmsDynamicService.getAllList(dmsDynamicVo);
        return toResult(list);
    }

    @ResponseBody
    @ApiOperation(value = "给客户增加购买记录", response = JsonResult.class)
    @RequestMapping(value ="/addShopRecord",method = RequestMethod.POST)
    public Object addShopRecord(@ApiParam("客户购买详细信息") @RequestBody DmsDynamicVo dmsDynamicVo){
        dmsDynamicVo.setShippingUnit(LoginManager.getUserSession().getOrgId());
        dmsDynamicService.addCustomerDynamic(dmsDynamicVo,LoginManager.getUserSession());
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "零售出库增加记录", response = JsonResult.class)
    @RequestMapping(value ="/addRetailOutStorage",method = RequestMethod.POST)
    public Object addRetailOutStorage(@ApiParam("零售出库客户及零售信息")@RequestBody DmsRetailOutStorageDto dmsRetailOutStorageDto){
        dmsRetailOutStorageDto.getDmsDynamicVo().setShippingUnit(LoginManager.getUserSession().getOrgId());
        dmsDynamicService.addRetailOutStorage(dmsRetailOutStorageDto,LoginManager.getUserSession());
        return toResult();
    }

}
