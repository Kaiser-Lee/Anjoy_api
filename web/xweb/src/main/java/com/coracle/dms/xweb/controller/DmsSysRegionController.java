package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.base.vo.TreeNodeVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzy on 2017/8/18
 */
@Controller
@RequestMapping(value = "/api/v1/dms/region")
@Api(description = "地区管理接口")
public class  DmsSysRegionController extends BaseController {
    @Autowired
    private DmsSysRegionService  dmsSysRegionService;

    @ResponseBody
    @ApiOperation(value = "地区列表",response = DmsSysRegion.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestBody DmsSysRegion dmsSysRegion) {
        PageHelper.Page<DmsSysRegion> dmsSysRegionPage=dmsSysRegionService.findRegionPageList(dmsSysRegion);
        return toResult(dmsSysRegionPage);
    }

    @ResponseBody
    @ApiOperation(value = "根据父id查询省、市、区、街道办",response = DmsSysRegion.class)
    @RequestMapping(value = "/listByParentId", method = RequestMethod.GET)
    public Object listByParentId(@ApiParam("父ID") @RequestParam Long id) {
        List<DmsSysRegion> dmsSysRegionList = dmsSysRegionService.findRegionByParentId(id);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for(DmsSysRegion region : dmsSysRegionList) {
            map = new HashMap<String, Object>();
            map.put("regionId", region.getRegionId());
            map.put("regionName", region.getRegionName());
            map.put("parentRegionId", region.getParentRegionId());
            list.add(map);
        }
        return toResult(list);
    }


    @ResponseBody
    @ApiOperation(value = "地区树查询--通过parentId,只查下一级节点列表", response = TreeNodeVo.class)
    @RequestMapping(value = "/treeListByParentId", method = RequestMethod.GET)
    public Object treeListByParentId(@ApiParam("父节点ID") @RequestParam(value = "id",required = false) Long id) {
        List<TreeNode> orgTree =  dmsSysRegionService.tree(id);
        return toResult(orgTree);
    }

    @ResponseBody
    @ApiOperation(value = "新增地区",response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsSysRegion dmsSysRegion) {
        Integer regionId = dmsSysRegionService.insertByPo(dmsSysRegion);
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "修改地区",response = JsonResult.class)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Object modify(@RequestBody DmsSysRegion dmsSysRegion) {
        DmsSysRegion dmsSysRegions=dmsSysRegionService.selectByPrimaryKey(dmsSysRegion.getRegionId());
        if(dmsSysRegions==null){
            throw new ControllerException("无法获取id为"+dmsSysRegion.getRegionId()+"的地区信息！");
        }
        dmsSysRegionService.updateByPo(dmsSysRegion);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "地区查看",response = DmsSysRegion.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam("地区ID") @RequestParam Integer id) {
        DmsSysRegion dmsSysRegions=dmsSysRegionService.selectByPrimaryKey(id);
        if(dmsSysRegions==null){
            throw new ControllerException("无法获取id为"+id+"的地区信息！");
        }
        return toResult(dmsSysRegions);
    }

    @ResponseBody
    @ApiOperation(value = "删除地区",response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@ApiParam("地区id") @RequestBody Integer id) {
        dmsSysRegionService.deleteByPrimaryKey(id);
        return toResult(null);
    }
}
