package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsDataDictionay;
import com.coracle.dms.po.DmsDataDictionayDependent;
import com.coracle.dms.service.DmsDataDictionayDependentService;
import com.coracle.dms.service.DmsDataDictionayService;
import com.coracle.yk.base.vo.JsonResult;
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
 * Created by xiaobu2012 on 2017/8/21.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/dictionayDependent")
@Api(description = "字典数据项值接口")
public class DmsDataDictionayDependentController extends BaseController{

    @Autowired
    private DmsDataDictionayService dmsDataDictionayService;

    @Autowired
    private DmsDataDictionayDependentService dmsDataDictionayDependentService;


    @ResponseBody
    @ApiOperation(value = "字典数据项值列表",response = DmsDataDictionayDependent.class)
    @RequestMapping(value = "/sKeylist", method = RequestMethod.GET)
    public Object list(@ApiParam("数据项sKey") @RequestParam String sKey) {
        DmsDataDictionay dmsDataDictionay = dmsDataDictionayService.selectBySkey(sKey);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("dictionaryId",dmsDataDictionay.getId());
        map.put("type",1);
        List<DmsDataDictionayDependent> dmsDictionayDependentList = dmsDataDictionayDependentService.findByDictionayId(map);
        return toResult(dmsDictionayDependentList);
    }

    @ResponseBody
    @ApiOperation(value = "字典数据项值列表",response = DmsDataDictionayDependent.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@ApiParam("数据项ID") @RequestParam Long id) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("dictionaryId",id);
        map.put("type",0);
        List<DmsDataDictionayDependent> dmsDictionayDependentList = dmsDataDictionayDependentService.findByDictionayId(map);
        return toResult(dmsDictionayDependentList);
    }

    @ResponseBody
    @ApiOperation(value = "字典数据项值列表",response = DmsDataDictionayDependent.class)
    @RequestMapping(value = "/dependentList", method = RequestMethod.GET)
    public Object dependentList(@ApiParam("从属数据项ID") @RequestParam Long id) {
        List<DmsDataDictionayDependent> dmsDictionayDependentList = dmsDataDictionayDependentService.findByDependentDataItemId(id);
        return toResult(dmsDictionayDependentList);
    }

    @ResponseBody
    @ApiOperation(value = "新增数据项值",response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsDataDictionayDependent dmsDataDictionayDependent) {
        dmsDataDictionayDependent.setRemoveFlag(0);
        dmsDataDictionayDependentService.insert(dmsDataDictionayDependent);
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "修改数据项值",response = JsonResult.class)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Object modify(@RequestBody DmsDataDictionayDependent dmsDataDictionayDependent) {
        DmsDataDictionayDependent dmsDataDictionayDependents=dmsDataDictionayDependentService.selectByPrimaryKey(dmsDataDictionayDependent.getId());
        if(dmsDataDictionayDependents==null){
            throw new ControllerException("无法获取id为"+dmsDataDictionayDependent.getId()+"的数据项值信息！");
        }
        dmsDataDictionayDependentService.updateByPrimaryKeySelective(dmsDataDictionayDependent);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "数据项值查看",response = DmsDataDictionay.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam("数据项值ID") @RequestParam Long id) {
        DmsDataDictionayDependent dmsDataDictionayDependents=dmsDataDictionayDependentService.selectByPrimaryKey(id);
        if(dmsDataDictionayDependents==null){
            throw new ControllerException("无法获取id为"+id+"的数据项值信息！");
        }
        return toResult(dmsDataDictionayDependents);
    }

    @ResponseBody
    @ApiOperation(value = "删除数据项值",response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@ApiParam("数据项值id") @RequestBody Long id) {
        dmsDataDictionayDependentService.updateById(id);
        return toResult(null);
    }

}
