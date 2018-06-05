package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsUserMapper;
import com.coracle.dms.dto.DmsUserInfoDto;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsUserValueAddedTaxInvoiceVo;
import com.coracle.dms.vo.DmsUserVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.common.exception.runtime.ParamException;
import com.coracle.yk.xframework.util.BlankUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 增值税发票信息接口
 */
@Controller
@RequestMapping("/api/v1/dms/invoice")
@Api(description = "用户增值税发票接口")
public class DmsUserValueAddedTaxInvoiceController extends BaseController {
    @Autowired
    private DmsUserValueAddedTaxInvoiceService dmsUserValueAddedTaxInvoiceService;
    @Autowired
    private DmsUserService dmsUserService;
    @Autowired
    private DmsGlobalVariableService dmsGlobalVariableService;
    @Autowired
    private DmsCommonAttachFileService dmsCommonAttachFileService;

    @ResponseBody
    @ApiOperation(value = "增值税发票列表",response = DmsUserValueAddedTaxInvoiceVo.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@ApiParam("增值税发票实体")@RequestBody DmsUserValueAddedTaxInvoiceVo invoice) {
        invoice.setRemoveFlag(0);
        invoice.setOrderField(" id ");
        invoice.setOrderString(" desc ");
        PageHelper.Page<DmsUserValueAddedTaxInvoiceVo> pageList = dmsUserValueAddedTaxInvoiceService.selectForPageList(invoice);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "新增记录", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsUserValueAddedTaxInvoice invoice) {
        PoDefaultValueGenerator.putCreateDefault(invoice);
        invoice.setUserId(LoginManager.getCurrentUserId());
        dmsUserValueAddedTaxInvoiceService.insertInvoice(invoice);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "更新记录", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsUserValueAddedTaxInvoice invoice) {
        PoDefaultValueGenerator.putUpdateDefault(invoice);
        invoice.setUserId(LoginManager.getCurrentUserId());
        dmsUserValueAddedTaxInvoiceService.update(invoice);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "详情记录", response = JsonResult.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam(value = "增值税主键ID") @RequestParam Long id) {
        if (BlankUtil.isEmpty(id)||id==0){
            throw new ControllerException("ID不合法");
        }
        DmsUserValueAddedTaxInvoiceVo invoice = dmsUserValueAddedTaxInvoiceService.selectVoByPrimaryKey(id);
        if (BlankUtil.isEmpty(invoice)||invoice.getRemoveFlag()==1){
            throw new ControllerException("无法获取id为"+id+"的增值税发票信息！");
        }
        DmsUserVo dmsUserVo = dmsUserService.selectVoByPrimaryKey(invoice.getUserId());
        if (BlankUtil.isEmpty(dmsUserVo)){
            throw new ControllerException("未获取到用户信息");
        }
        invoice.setUserName(dmsUserVo.getName());
        if (BlankUtil.isNotEmpty(invoice.getApproveUserId())&&invoice.getApproveUserId()>0){
            DmsUserVo dmsUserVo2 = dmsUserService.selectVoByPrimaryKey(invoice.getApproveUserId());
            if (BlankUtil.isNotEmpty(dmsUserVo2)) {
                invoice.setApproveName(dmsUserVo2.getName());
            }else {
                invoice.setApproveName("");
            }
        }
        return toResult(invoice);
    }

    @ResponseBody
    @ApiOperation(value = "删除记录", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object delete(@ApiParam(value = "增值税主键ID") @RequestParam Long id) {
        if(id == null) {
            throw new ParamException("参数不能为空");
        }
        DmsUserValueAddedTaxInvoice invoice = dmsUserValueAddedTaxInvoiceService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(invoice)){
            throw new ControllerException("无法获取id为"+id+"的增值税发票信息！");
        }
        DmsUserValueAddedTaxInvoice dmsUserValueAddedTaxInvoice = new DmsUserValueAddedTaxInvoice();
        dmsUserValueAddedTaxInvoice.setRemoveFlag(1);
        dmsUserValueAddedTaxInvoice.setId(invoice.getId());
        dmsUserValueAddedTaxInvoiceService.updateByPrimaryKeySelective(dmsUserValueAddedTaxInvoice);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "获取用户当前增值税发票信息记录", response = JsonResult.class)
    @RequestMapping(value = "/getCurrentInvoice", method = RequestMethod.GET)
    public Object getUserCurrentInvoice() {
        DmsUserValueAddedTaxInvoiceVo invoice = dmsUserValueAddedTaxInvoiceService.getUserCurrentInvoice(LoginManager.getCurrentUserId());
        return toResult(invoice);
    }

    @ResponseBody
    @ApiOperation(value = "增值税发票审核", response = JsonResult.class)
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public Object approve(@RequestBody DmsUserValueAddedTaxInvoice dmsUserValueAddedTaxInvoice) {
        if(dmsUserValueAddedTaxInvoice.getId() == null) {
            throw new ParamException("增值税发票主键ID不能为空");
        }
        if(dmsUserValueAddedTaxInvoice.getStatus() == null) {
            throw new ParamException("审核状态不能为空");
        }
        dmsUserValueAddedTaxInvoiceService.auditInvoice(dmsUserValueAddedTaxInvoice.getId(), LoginManager.getCurrentUserId(),dmsUserValueAddedTaxInvoice.getStatus(),dmsUserValueAddedTaxInvoice.getRemark());
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "提交审批", response = JsonResult.class)
    @RequestMapping(value = "/submitToApprove", method = RequestMethod.GET)
    public Object submitToApprove(@ApiParam(value = "增值税主键ID") @RequestParam Long id) {
        if(id == null) {
            throw new ParamException("参数不能为空");
        }
        dmsUserValueAddedTaxInvoiceService.submitToApprove(id, LoginManager.getCurrentUserId());
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "获取用户审核通过增值税发票信息", response = JsonResult.class)
    @RequestMapping(value = "/getApprovedInvoice", method = RequestMethod.GET)
    public Object getApprovedInvoice() {
        DmsUserValueAddedTaxInvoiceVo invoice = dmsUserValueAddedTaxInvoiceService.getApprovedInvoice(LoginManager.getCurrentUserId());
        return toResult(invoice);
    }

    @ResponseBody
    @ApiOperation(value = "是否有增值税信息", response = JsonResult.class)
    @RequestMapping(value = "/hasApprovedInvoice", method = RequestMethod.GET)
    public Object hasApprovedInvoice() {
        DmsUserValueAddedTaxInvoice invoice = dmsUserValueAddedTaxInvoiceService.getApprovedInvoice(LoginManager.getCurrentUserId());
        DmsUser dmsUser = dmsUserService.selectByPrimaryKey(LoginManager.getCurrentUserId());
        if (BlankUtil.isEmpty(dmsUser)){
            throw new ControllerException("无法获取用户信息");
        }
        DmsUserInfoDto dmsUserInfoDto = new DmsUserInfoDto();
        dmsUserInfoDto.setSource(dmsUser.getSource());
        dmsUserInfoDto.setStaffId(dmsUser.getStaffId());
        List<Map<String,Object>> list = dmsUserService.selectUserDetail(dmsUserInfoDto);
        if (BlankUtil.isEmpty(list)||list.size()==0){
            throw new ControllerException("无法获取用户信息");
        }
        Map result = new HashMap();
        result.put("userName", list.get(0).get("userName")==null?"":list.get(0).get("userName").toString());
        if(invoice != null) {
            result.put("hasInvoice", DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
        } else {
            result.put("hasInvoice", DmsModuleEnums.ACTIVE_STATUS.DIABLE.getType());
        }
        return toResult(result);
    }


    @ResponseBody
    @ApiOperation(value = "设置委托书模板", response = JsonResult.class)
    @RequestMapping(value = "/setProxyTemplate", method = RequestMethod.GET)
    public Object setProxyTemplate(@RequestParam Long temlateId) {
        if(BlankUtil.isEmpty(temlateId)) {
            throw new ParamException("模板ID不能为空");
        }
        DmsGlobalVariable dmsGlobalVariable = new DmsGlobalVariable();
        dmsGlobalVariable.setsKey(DmsModuleEnums.PROXY_TEMPLATE_KEY);
        List<DmsGlobalVariable> list = dmsGlobalVariableService.selectByCondition(dmsGlobalVariable);
        if (BlankUtil.isEmpty(list)||list.size()==0){
            DmsGlobalVariable dmsGlobalVariable1 = new DmsGlobalVariable();
            PoDefaultValueGenerator.putDefaultValue(dmsGlobalVariable1);
            dmsGlobalVariable1.setsKey(DmsModuleEnums.PROXY_TEMPLATE_KEY);
            dmsGlobalVariable1.setValue(String.valueOf(temlateId));
            dmsGlobalVariable1.setName("增值税发票委托书模板");
            dmsGlobalVariable1.setDescription("委托书模版ID");
            dmsGlobalVariable1.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
            dmsGlobalVariableService.insert(dmsGlobalVariable1);
        }else if (list.size()==1){
            DmsGlobalVariable dmsGlobalVariable1 = new DmsGlobalVariable();
            dmsGlobalVariable1.setRemoveFlag(0);
            dmsGlobalVariable1.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
            dmsGlobalVariable1.setValue(String.valueOf(temlateId));
            dmsGlobalVariable1.setId(list.get(0).getId());
            dmsGlobalVariable1.setLastUpdatedBy(LoginManager.getCurrentUserId());
            dmsGlobalVariable1.setLastUpdatedDate(new Date());
            dmsGlobalVariableService.updateByPrimaryKeySelective(dmsGlobalVariable1);
        }else {
            throw new ControllerException("增值税发票委托书模板存在多个");
        }
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "获取委托书模板", response = JsonResult.class)
    @RequestMapping(value = "/getProxyTemplate", method = RequestMethod.GET)
    public Object getProxyTemplate() {
        String templateId = dmsGlobalVariableService.queryValueByKey(DmsModuleEnums.PROXY_TEMPLATE_KEY);
        Long id = 0L;
        try {
            id =Long.parseLong(templateId);
        } catch (NumberFormatException e) {
            return toResult(null);
        }
        DmsCommonAttachFile dmsCommonAttachFile = dmsCommonAttachFileService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsCommonAttachFile)){
            throw new ControllerException("未获取到模版信息！");
        }
        return toResult(dmsCommonAttachFile);
    }

}
