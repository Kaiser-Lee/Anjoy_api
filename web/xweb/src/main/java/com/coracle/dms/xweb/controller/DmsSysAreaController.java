package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsSysArea;
import com.coracle.dms.po.DmsSysAreaRegion;
import com.coracle.dms.service.DmsSysAreaRegionService;
import com.coracle.dms.service.DmsSysAreaService;
import com.coracle.dms.service.DmsTreeRelationService;
import com.coracle.dms.xweb.common.session.LoginManager;
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

import java.util.List;

/**
 * Created by zzy on 2017/8/19
 */
@Controller
@RequestMapping(value = "/api/v1/dms/area")
@Api(description = "区域管理接口")
public class DmsSysAreaController extends BaseController {


    @Autowired
    private DmsSysAreaService dmsSysAreaService;

    @Autowired
    private DmsSysAreaRegionService dmsSysAreaRegionService;

    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;

    @ResponseBody
    @ApiOperation(value = "区域树形结构数据--通过parentId,查询下面所有节点列表,", response = TreeNodeVo.class)
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public Object tree(@ApiParam("父节点ID") @RequestParam(value = "id",required = false) Long id) {
        List<TreeNode> orgTree =  dmsSysAreaService.tree(id);
        return toResult(orgTree);
    }


    @ResponseBody
    @ApiOperation(value = "区域树查询--通过parentId,只查下一级节点列表",response = DmsSysArea.class)
    @RequestMapping(value = "/treeListByParentId", method = RequestMethod.GET)
    public Object treeListByParentId(@ApiParam("父节点ID") @RequestParam Long id) {
        List<DmsSysArea> areaList = dmsSysAreaService.findAreaByParentId(id);
        return toResult(areaList);
    }

    @ResponseBody
    @ApiOperation(value = "区域列表",response = DmsSysArea.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(DmsSysArea dmsSysArea) {
        PageHelper.Page<DmsSysArea> dmsSysAreaPage=dmsSysAreaService.findAreaPageList(dmsSysArea);
        return toResult(dmsSysAreaPage);
    }

    @ResponseBody
    @ApiOperation(value = "新增区域",response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsSysArea dmsSysArea) {
        //插入区域表
        Long areaId = dmsSysAreaService.insertByPo(dmsSysArea, LoginManager.getUserSession());
        //更新areaPath

        if(dmsSysArea.getRegionId()!=null){
            //插入区域关联表
            DmsSysAreaRegion dmsSysAreaRegion =  new DmsSysAreaRegion();
            String[] regionIds = dmsSysArea.getRegionId().split(";");
            for (int i=0;i<regionIds.length;i++) {
                dmsSysAreaRegion.setAreaId(areaId);
                dmsSysAreaRegion.setRegionId(Long.valueOf(regionIds[i]));
                dmsSysAreaRegionService.insert(dmsSysAreaRegion);
            }
        }else{
            throw new ControllerException("管理地区信息不能为空！");
        }
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "修改区域",response = JsonResult.class)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Object modify(@RequestBody DmsSysArea dmsSysArea) {
        DmsSysArea dmsSysAreas=dmsSysAreaService.selectByPrimaryKey(dmsSysArea.getId());
        if(dmsSysAreas==null){
            throw new ControllerException("无法获取id为"+dmsSysArea.getId()+"的区域信息！");
        }
        //修改区域表
        dmsSysAreaService.updateByPrimaryKeySelective(dmsSysArea);

        Long userId = LoginManager.getCurrentUserId();
        //修改树形结构信息
//        dmsTreeRelationService.updateRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), dmsSysArea.getId(),
//                dmsSysArea.getParentAreaId(), dmsSysArea.getName(), LoginManager.getCurrentUserId());
        dmsTreeRelationService.updateRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), dmsSysArea.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), dmsSysArea.getParentAreaId(), dmsSysArea.getName(), userId, DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType());

        if(dmsSysArea.getRegionId()!=null&&dmsSysArea.getRegionId()!=""){
            //先删除区域关联表
            dmsSysAreaRegionService.deleteByAreaId(dmsSysArea.getId());
            //插入区域关联表
            DmsSysAreaRegion dmsSysAreaRegion =  new DmsSysAreaRegion();
            String[] regionIds = dmsSysArea.getRegionId().split(";");
            for (int i=0;i<regionIds.length;i++) {
                dmsSysAreaRegion.setAreaId(Long.valueOf(dmsSysArea.getId()));
                dmsSysAreaRegion.setRegionId(Long.valueOf(regionIds[i]));
                dmsSysAreaRegionService.insert(dmsSysAreaRegion);
            }
        }else{
            throw new ControllerException("管理地区信息不能为空！");
        }
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "区域查看",response = DmsSysArea.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam("区域ID") @RequestParam Long id) {
        DmsSysArea dmsSysAreas=dmsSysAreaService.selectById(id);
        if(dmsSysAreas==null){
            throw new ControllerException("无法获取id为"+id+"的区域信息！");
        }
        return toResult(dmsSysAreas);
    }

    @ResponseBody
    @ApiOperation(value = "删除区域",response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@ApiParam("区域id") @RequestBody Long id) {
        int count =  dmsSysAreaService.findChildById(id);
        if(count>0){
            throw new ControllerException("包含子区域，不能删除该区域信息！");
        }
        dmsSysAreaService.deleteByPrimaryKey(id);

        //从dms_tree_relation表中删除数据
        dmsTreeRelationService.deleteRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), id);

        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "同步区域",response = JsonResult.class)
    @RequestMapping(value = "/anjoySync", method = RequestMethod.GET)
    public Object synsAnjoy() {
        dmsSysAreaService.anjoySync();
        return toResult(null);
    }
}
