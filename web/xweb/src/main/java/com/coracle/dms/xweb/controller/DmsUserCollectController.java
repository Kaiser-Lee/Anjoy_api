package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsProduct;
import com.coracle.dms.po.DmsUserCollect;
import com.coracle.dms.service.DmsProductService;
import com.coracle.dms.service.DmsUserCollectService;
import com.coracle.dms.vo.DmsUserCollectVo;
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
@RequestMapping(value = "/api/v1/dms/collect")
@Api(description = "用户收藏接口")
public class DmsUserCollectController extends BaseController {
    @Autowired
    private DmsUserCollectService dmsUserCollectService;
    @Autowired
    private DmsProductService dmsProductService;

    @ResponseBody
    @ApiOperation(value = "新增用户收藏记录",response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public Object insertCollect(@ApiParam("产品ID")@RequestParam(value = "id",required = false) Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("请输入产品ID");
        }
        DmsProduct dmsProduct = dmsProductService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsProduct)){
            throw new ControllerException("产品ID为【"+id+"】的产品未查询到！");
        }
        DmsUserCollect dmsUserCollect0 = new DmsUserCollect();
        dmsUserCollect0.setUserId(LoginManager.getCurrentUserId());
        dmsUserCollect0.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsUserCollect0.setProductId(dmsProduct.getId());
        List<DmsUserCollect> list = dmsUserCollectService.selectByCondition(dmsUserCollect0);
        if (BlankUtil.isNotEmpty(list)){
            throw new ControllerException("该产品已经收藏了！");
        }
        DmsUserCollect dmsUserCollect = new DmsUserCollect();
        PoDefaultValueGenerator.putDefaultValue(dmsUserCollect);
        dmsUserCollect.setProductId(dmsProduct.getId());
        dmsUserCollect.setUserId(LoginManager.getCurrentUserId());
        dmsUserCollectService.insert(dmsUserCollect);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "新增用户收藏记录包含规格组合键",response = JsonResult.class)
    @RequestMapping(value = "/insertCollectHaveSpec", method = RequestMethod.POST)
    public Object insertCollectHaveSpec(@RequestBody List<DmsUserCollect> collectList){
        if (BlankUtil.isEmpty(collectList)){
            throw new ControllerException("收藏信息");
        }
        Long userId = LoginManager.getCurrentUserId();
        for(DmsUserCollect dmsUserCollect:collectList){
            dmsUserCollect.setUserId(userId);
            List<DmsUserCollect> list = dmsUserCollectService.selectByCondition(dmsUserCollect);
            if (BlankUtil.isEmpty(list)){
                PoDefaultValueGenerator.putDefaultValue(dmsUserCollect);
                dmsUserCollectService.insert(dmsUserCollect);
            }
        }
        return toResult();
    }


    @ResponseBody
    @ApiOperation(value = "删除用户收藏记录",response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object insertCollect(@ApiParam("要删除的收藏ID列表") @RequestBody List<DmsUserCollect> collectList){
        if (BlankUtil.isEmpty(collectList)){
            throw new ControllerException("请输入要删除的列表");
        }
        for (DmsUserCollect dmsUserCollect:collectList){
            //这里直接删除算了，没必要用软删除了
            dmsUserCollectService.deleteCollectByInfo(LoginManager.getCurrentUserId(),dmsUserCollect.getProductId(),dmsUserCollect.getSpecUnionKey());
        }
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "获取用户收藏记录列表没有库存",response = JsonResult.class)
    @RequestMapping(value = "/getListNoNum", method = RequestMethod.GET)
    public Object getListNoNum(@ApiParam("用户收藏记录分页页码")@RequestParam(value = "p",required = false) Integer p,
                               @ApiParam("用户收藏记录每页条数")@RequestParam(value = "s",required = false) Integer s){
        DmsUserCollect dmsUserCollect = new DmsUserCollect();
        dmsUserCollect.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsUserCollect.setUserId(LoginManager.getCurrentUserId());
        dmsUserCollect.setP(p);
        dmsUserCollect.setS(s);
        PageHelper.Page<DmsUserCollectVo> pageList = dmsUserCollectService.selectForNoNumList(dmsUserCollect);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "获取用户收藏记录列表有库存",response = JsonResult.class)
    @RequestMapping(value = "/getListByNum", method = RequestMethod.GET)
    public Object getListByNum(@ApiParam("用户收藏记录分页页码")@RequestParam(value = "p",required = false) Integer p,
                               @ApiParam("用户收藏记录每页条数")@RequestParam(value = "s",required = false) Integer s){
        DmsUserCollect dmsUserCollect = new DmsUserCollect();
        dmsUserCollect.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsUserCollect.setUserId(LoginManager.getCurrentUserId());
        dmsUserCollect.setP(p);
        dmsUserCollect.setS(s);
        PageHelper.Page<DmsUserCollectVo> pageList = dmsUserCollectService.selectForByNumList(dmsUserCollect);
        return toResult(pageList);
    }
}
