package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dto.DmsUserDetailDto;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.po.DmsUserAddress;
import com.coracle.dms.service.DmsUserAddressService;
import com.coracle.dms.service.DmsUserService;
import com.coracle.dms.vo.DmsUserVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(description = "账号管理接口")
@RequestMapping("/api/v1/dms/user")
public class DmsUserController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsUserController.class);

    @Autowired
    private DmsUserService dmsUserService;
    @Autowired
    private DmsUserAddressService dmsUserAddressService;

    @ResponseBody
    @ApiOperation(value = "账号列表", response = DmsUserVo.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestBody DmsUserVo userVo) {
        if (userVo == null) {
            throw new ControllerException("参数异常");
        }
        userVo.setOrderField(" u.id ");
        userVo.setOrderString(" desc ");
        PageHelper.Page<DmsUserVo> pageList = dmsUserService.pageList(userVo);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "启用账号", response = JsonResult.class)
    @RequestMapping(value = "/valid", method = RequestMethod.GET)
    public Object valid(@RequestParam Long id) {
        DmsUser user = new DmsUser();
        user.setId(id);
        user.setStatus(DmsModuleEnums.ACCOUNT_STATUS_TYPE.VALID.getType());
        PoDefaultValueGenerator.putDefaultValue(user);
        dmsUserService.updateStatus(user);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "禁用账号", response = JsonResult.class)
    @RequestMapping(value = "/invalid", method = RequestMethod.GET)
    public Object invalid(@RequestParam Long id) {
        DmsUser user = new DmsUser();
        user.setId(id);
        user.setStatus(DmsModuleEnums.ACCOUNT_STATUS_TYPE.INVALID.getType());
        PoDefaultValueGenerator.putDefaultValue(user);
        dmsUserService.updateStatus(user);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "账号审核", response = JsonResult.class)
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public Object audit(@RequestBody DmsUser user) {
        PoDefaultValueGenerator.putDefaultValue(user);
        dmsUserService.audit(user);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "重置密码", response = JsonResult.class)
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public Object resetPassword(@RequestParam Long id) {
        dmsUserService.updatePassword(id, "", LoginManager.getUserSession());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "根据mxmId修改用户密码", response = JsonResult.class)
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public Object updatePassword(@RequestParam Long id, @RequestParam("old") String oldPassword, @RequestParam("new") String newPassword) {
        dmsUserService.updatePassword(id, oldPassword, newPassword, LoginManager.getUserSession());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "添加或者修改收货地址", response = JsonResult.class)
    @RequestMapping(value = "/saveOrModifyAddress", method = RequestMethod.POST)
    public Object addAddress(@RequestBody DmsUserAddress dmsUserAddress) {
        PoDefaultValueGenerator.putDefaultValue(dmsUserAddress);
        dmsUserAddress.setUserId(LoginManager.getCurrentUserId());
        dmsUserAddressService.saveOrUpdate(dmsUserAddress);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "收货地址列表", response = DmsUserAddress.class)
    @RequestMapping(value = "/addressList", method = RequestMethod.POST)
    public Object addressList() {
        DmsUserAddress dmsUserAddress = new DmsUserAddress();
        dmsUserAddress.setUserId(LoginManager.getCurrentUserId());
        dmsUserAddress.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsUserAddress.setOrderField(" is_default desc,id");
        dmsUserAddress.setOrderString(" asc ");
        List<DmsUserAddress> list = dmsUserAddressService.selectByCondition(dmsUserAddress);
        return toResult(list);
    }

    @ResponseBody
    @ApiOperation(value = "获取默认收货地址", response = DmsUserAddress.class)
    @RequestMapping(value = "/getDefaultAddress", method = RequestMethod.POST)
    public Object getDefaultAddress(){
        DmsUserAddress dmsUserAddress = new DmsUserAddress();
        /*dmsUserAddress.setIsDefault(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());*/
        dmsUserAddress.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsUserAddress.setUserId(LoginManager.getCurrentUserId());
        dmsUserAddress.setOrderField(" id ");
        dmsUserAddress.setOrderString(" desc ");
        List<DmsUserAddress> list = dmsUserAddressService.selectByCondition(dmsUserAddress);
        if (BlankUtil.isEmpty(list)||list.size()==0){
            return toResult(null);
        } else if (list.size()==1) {
            return toResult(list.get(0));
        }else {
            DmsUserAddress address = null;
            for (DmsUserAddress userAddress:list){
                if(userAddress.getIsDefault()==DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType()){
                    address = userAddress;
                    break;
                }
            }
            if (BlankUtil.isEmpty(address)){
                return toResult(list.get(0));
            }else {
                return toResult(address);
            }
        }
    }

    @ResponseBody
    @ApiOperation(value = "设置默认收货地址", response = JsonResult.class)
    @RequestMapping(value = "/defaultAddress", method = RequestMethod.POST)
    public Object defaultAddress(@ApiParam("收货地址id") @RequestParam Long id) {
        DmsUserAddress dmsUserAddress = dmsUserAddressService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsUserAddress)){
            throw new ControllerException("无法获取id为"+id+"的收货地址信息！");
        }
        if (dmsUserAddress.getIsDefault()==0){
            dmsUserAddressService.dealDefaultAddress(dmsUserAddress);
        }
        dmsUserAddress.setIsDefault(1);
        dmsUserAddressService.updateByPrimaryKey(dmsUserAddress);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "删除收货地址", response = DmsUserVo.class)
    @RequestMapping(value = "/deleteAddress", method = RequestMethod.GET)
    public Object deleteAddress(@ApiParam("收货地址id") @RequestParam Long id){
        DmsUserAddress dmsUserAddress = dmsUserAddressService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsUserAddress)){
            throw new ControllerException("无法获取id为"+id+"的收货地址信息！");
        }
        dmsUserAddress.setRemoveFlag(1);
        dmsUserAddressService.updateByPrimaryKeySelective(dmsUserAddress);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "获取用户信息", response = DmsUserDetailDto.class)
    @RequestMapping(value = "/getUserDetail", method = RequestMethod.GET)
    public Object getUserDetail(){
        if (LoginManager.getCurrentUserId()==0){
            throw new ControllerException("请先登录！");
        }
        DmsUserDetailDto dmsUserDetailDto = dmsUserService.getUserContactsDetail(LoginManager.getUserSession());
        return toResult(dmsUserDetailDto);
    }


    @ResponseBody
    @ApiOperation(value = "账号审核获取用户信息", response = DmsUserDetailDto.class)
    @RequestMapping(value = "/getUserDetailById", method = RequestMethod.GET)
    public Object getUserDetailById(@ApiParam("用户id") @RequestParam Long id){
        if (BlankUtil.isEmpty(id)||id==0){
            throw new ControllerException("请传入合法ID！");
        }
        DmsUserDetailDto dmsUserDetailDto = dmsUserService.getUserAccountAuditDetail(id);
        return toResult(dmsUserDetailDto);
    }


    @ResponseBody
    @ApiOperation(value = "修改用户信息", response = JsonResult.class)
    @RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST)
    public Object modifyUserInfo(@RequestBody DmsUserDetailDto dmsUserDetailDto){
        if (LoginManager.getCurrentUserId()==0){
            throw new ControllerException("请先登录！");
        }
        dmsUserService.updateUserInfo(dmsUserDetailDto,LoginManager.getCurrentUserId());
        return toResult();
    }


    @ResponseBody
    @ApiOperation(value = "消息推送测试", response = JsonResult.class)
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public Object push() {
        dmsUserService.push();
        return toResult();
    }
}