package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsOrganization;
import com.coracle.dms.service.DmsChannelService;
import com.coracle.dms.service.DmsOrganizationService;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.dms.vo.DmsOrganizationVo;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.base.vo.TreeNodeVo;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(description = "组织架构接口")
@RequestMapping("/api/v1/dms/organization")
public class DmsOrganizationController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsOrganizationController.class);

    @Autowired
    private DmsOrganizationService dmsOrganizationService;

    @Autowired
    private DmsChannelService dmsChannelService;

    @ResponseBody
    @ApiOperation(value = "组织架构列表,包含渠道", response = JsonResult.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list() {
        DmsOrganization dmsOrganization = dmsOrganizationService.getRootOrganization();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",dmsOrganization.getId());
        map.put("name",dmsOrganization.getName());
        map.put("type", DmsModuleEnums.STORAGE_TYPE.BRAND.getType());
        DmsChannelVo dmsChannelVo = new DmsChannelVo();
        dmsChannelVo.setOrderField("id");
        dmsChannelVo.setOrderString("desc");
        List<Map<String,Object>> dmsChannelList = dmsChannelService.getList(dmsChannelVo);
        dmsChannelList.add(0, map);  //将品牌商放到第一个
        return toResult(dmsChannelList);
    }

    @ResponseBody
    @ApiOperation(value = "组织架构树形结构数据", response = TreeNodeVo.class)
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Object tree(@RequestParam Long id) {
        List<TreeNodeVo> orgTree =  dmsOrganizationService.tree(id);
        return toResult(orgTree);
    }

    @ResponseBody
    @ApiOperation(value = "新增组织", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsOrganizationVo organization) {
        PoDefaultValueGenerator.putDefaultValue(organization);
        dmsOrganizationService.insertOrUpdate(organization);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "修改组织", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsOrganizationVo organization) {
        PoDefaultValueGenerator.putDefaultValue(organization);
        dmsOrganizationService.insertOrUpdate(organization);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "组织详情", response = DmsOrganization.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam("组织id") @RequestParam Long id) {
        DmsOrganizationVo organization = dmsOrganizationService.detail(id);
        if (organization == null) {
            throw new ServiceException("无法获取id为" + id + "的组织信息!");
        }
        return toResult(organization);
    }

    @ResponseBody
    @ApiOperation(value = "删除组织", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody DmsOrganization organization) {
        PoDefaultValueGenerator.putDefaultValue(organization);
        dmsOrganizationService.remove(organization);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "获取子组织列表", response = DmsOrganization.class)
    @RequestMapping(value = "/childList", method = RequestMethod.GET)
    public Object childList(@ApiParam("父组织id") @RequestParam Long id) {
        DmsOrganization param = new DmsOrganization();
        param.setParentId(id);
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

        List<DmsOrganization> organizationList = dmsOrganizationService.selectByCondition(param);
        return toResult(organizationList);
    }

    @ResponseBody
    @ApiOperation(value = "安井组织同步", response = DmsOrganization.class)
    @RequestMapping(value = "/anjoy-syn", method = RequestMethod.GET)
    public Object anjoySyn() {
        dmsOrganizationService.anjoySyn();
        return toResult();
    }
}