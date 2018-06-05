package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsDataDictionay;
import com.coracle.dms.service.DmsDataDictionayService;
import com.coracle.yk.base.vo.JsonResult;
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
 * Created by xiaobu2012 on 2017/8/21.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/dictionay")
@Api(description = "字典数据项接口")
public class DmsDataDictionayController extends BaseController{

    @Autowired
    private DmsDataDictionayService dmsDataDictionayService;

    @ResponseBody
    @ApiOperation(value = "字典数据项列表",response = DmsDataDictionay.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestBody DmsDataDictionay dmsDataDictionay) {
        dmsDataDictionay.setOrderField(" id ");
        dmsDataDictionay.setOrderString(" desc ");
        PageHelper.Page<DmsDataDictionay> dmsDataDictionayPage=dmsDataDictionayService.findDictionayPageList(dmsDataDictionay);
        return toResult(dmsDataDictionayPage);
    }

    @ResponseBody
    @ApiOperation(value = "字典从属数据项列表",response = DmsDataDictionay.class)
    @RequestMapping(value = "/listDependent", method = RequestMethod.GET)
    public Object listDependent(DmsDataDictionay dmsDataDictionay) {
        List<DmsDataDictionay> dmsDataDictionaylist=dmsDataDictionayService.findDictionayList(dmsDataDictionay);
        return toResult(dmsDataDictionaylist);
    }

    @ResponseBody
    @ApiOperation(value = "新增数据项",response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsDataDictionay dmsDataDictionay) {
        dmsDataDictionay.setRemoveFlag(0);
        dmsDataDictionayService.insertTo(dmsDataDictionay);
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "修改数据项",response = JsonResult.class)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Object modify(@RequestBody DmsDataDictionay dmsDataDictionay) {
        DmsDataDictionay dmsDataDictionays=dmsDataDictionayService.selectByPrimaryKey(dmsDataDictionay.getId());
        if(dmsDataDictionays==null){
            throw new ControllerException("无法获取id为"+dmsDataDictionay.getId()+"的数据项信息！");
        }
        dmsDataDictionayService.updateByPrimaryKeySelective(dmsDataDictionay);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "数据项查看",response = DmsDataDictionay.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam("数据项ID") @RequestParam Long id) {
        DmsDataDictionay dmsDataDictionay=dmsDataDictionayService.selectByPrimaryKey(id);
        if(dmsDataDictionay==null){
            throw new ControllerException("无法获取id为"+id+"的数据项信息！");
        }
        if(dmsDataDictionay.getDependentDataItemId()==null||dmsDataDictionay.getDependentDataItemId()==0){

        }else{
            //查询从属数据项的值
            DmsDataDictionay dmsDataDictionays = dmsDataDictionayService.selectByPrimaryKey(dmsDataDictionay.getDependentDataItemId());
            dmsDataDictionay.setDependentDataItemName(dmsDataDictionays.getName());
        }
        return toResult(dmsDataDictionay);
    }

    @ResponseBody
    @ApiOperation(value = "删除数据项",response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@ApiParam("数据项id") @RequestBody Long id) {
        dmsDataDictionayService.updateById(id);
        return toResult(null);
    }

}
