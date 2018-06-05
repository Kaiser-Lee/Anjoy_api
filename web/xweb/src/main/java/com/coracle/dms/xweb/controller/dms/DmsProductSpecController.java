package com.coracle.dms.xweb.controller.dms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coracle.dms.po.DmsProductSpec;
import com.coracle.dms.service.DmsProductSpecService;
import com.coracle.dms.vo.DmsProductSpecVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.dms.xweb.controller.BaseController;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;

/**
 * Created by tanyb on 2017/8/18.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/spec")
@Api(description = "产品规格接口")
public class DmsProductSpecController extends BaseController {

    @Resource
    private DmsProductSpecService dmsProductSpecService;

    /**
     * 根据条件获取产品规格列表
     */
    @ResponseBody
    @ApiOperation(value = "产品规格列表",response = DmsProductSpecVo.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@ApiParam("产品规格实体")@RequestBody DmsProductSpecVo spec){
        PageHelper.Page<DmsProductSpecVo> pageList = dmsProductSpecService.selectForListPage(spec);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "产品规格列表-新建产品选择产品类别使用",response = DmsProductSpecVo.class)
    @RequestMapping(value = "/specList",method = RequestMethod.POST)
    public Object getSpecListByProductTypeId(@ApiParam("产品规格实体")@RequestBody DmsProductSpecVo spec){
        return toResult(dmsProductSpecService.selectSpecList(spec));
    }
    
    @ResponseBody
    @ApiOperation(value = "规格详情", response = JsonResult.class)
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(@ApiParam("规格ID") @RequestParam(value = "id",required = true) Long id){
        return toResult(this.dmsProductSpecService.detail(id));
    }
    
    @ResponseBody
    @ApiOperation(value = "新增规格", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsProductSpecVo param){
        PoDefaultValueGenerator.putDefaultValue(param);
        this.dmsProductSpecService.create(param);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "修改规格", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsProductSpecVo param){
        PoDefaultValueGenerator.putDefaultValue(param);
        dmsProductSpecService.update(param);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "删除规格", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@ApiParam("规格ID") @RequestParam(value = "id",required = true) Long id){
    	DmsProductSpec spec = new DmsProductSpec();
    	spec.setId(id);
        PoDefaultValueGenerator.putDefaultValue(spec);
        this.dmsProductSpecService.delete(spec);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "失效规格", response = JsonResult.class)
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    public Object invalid(@RequestBody DmsProductSpecVo spec){
        spec.setLastUpdatedBy(LoginManager.getCurrentUserId());
        spec.setLastUpdatedDate(new Date());
        dmsProductSpecService.updateByPrimaryKeySelective(spec);
        return toResult(null);
    }
}
