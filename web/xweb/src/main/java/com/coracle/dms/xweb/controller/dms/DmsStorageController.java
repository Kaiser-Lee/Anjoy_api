package com.coracle.dms.xweb.controller.dms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coracle.dms.po.DmsStorageLocal;
import com.coracle.dms.service.DmsStorageService;
import com.coracle.dms.vo.DmsStorageVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.dms.xweb.controller.BaseController;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;

@Controller
@RequestMapping(value = "/api/v1/dms/storage")
@Api(description = "仓库管理接口")
public class DmsStorageController extends BaseController {

	@Resource
	private DmsStorageService dmsStorageService;
	
	/**
     * 根据条件获取仓库列表
     */
    @ResponseBody
    @ApiOperation(value = "仓库列表",response = DmsStorageVo.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@RequestBody DmsStorageVo paramVo){
    	paramVo.setOrderField("created_date");
    	paramVo.setOrderString("desc");
    	PageHelper.Page<DmsStorageVo> pageList = dmsStorageService.selectForListPage(paramVo);
        return toResult(pageList);
    }

    /**
     * 根据条件获取仓库列表（入库单只能选择品牌商仓库）
     */
    @ResponseBody
    @ApiOperation(value = "入库单仓库列表",response = DmsStorageVo.class)
    @RequestMapping(value = "/listForBill",method = RequestMethod.POST)
    public Object getListForBill(@RequestBody DmsStorageVo paramVo){
    	paramVo.setOrderField("created_date");
    	paramVo.setOrderString("desc");
    	//只查询属于自己的仓库
        paramVo.setOrgId(LoginManager.getUserSession().getOrgId());
    	PageHelper.Page<DmsStorageVo> pageList = dmsStorageService.selectForBillListPage(paramVo);
        return toResult(pageList);
    }
    
    @ResponseBody
    @ApiOperation(value = "获取品牌商仓库列表",response = DmsStorageVo.class,notes="品牌商确认收货时需要选择仓库树结构")
    @RequestMapping(value = "/listTreeBrand",method = RequestMethod.GET)
    public Object listTreeBrand(){
    	DmsStorageVo paramVo = new DmsStorageVo();
    	paramVo.setStorageType(1);
    	List<TreeNode> list = dmsStorageService.selectStorageTree(paramVo);
        return toResult(list);
    }
    
    @ResponseBody
    @ApiOperation(value = "仓库详情", response = JsonResult.class)
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(@ApiParam("id") @RequestParam(value = "id",required = true) Long id){
        return toResult(this.dmsStorageService.detail(id));
    }
    
    
    @ResponseBody
    @ApiOperation(value = "新增仓库", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsStorageVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        paramVo.setRelationId(paramVo.getOrgId());
        this.dmsStorageService.create(paramVo);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "修改仓库", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsStorageVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsStorageService.update(paramVo);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "货位列表", response = JsonResult.class)
    @RequestMapping(value = "/getLocalList", method = RequestMethod.POST)
    public Object getLocalList(@RequestBody DmsStorageLocal paramVo){
    	paramVo.setRemoveFlag(0);
    	PageHelper.Page<DmsStorageLocal> pageList =  this.dmsStorageService.selectLocalForListPage(paramVo);
        return toResult(pageList);
    }
    
    @ResponseBody
    @ApiOperation(value = "新增仓库货位", response = JsonResult.class)
    @RequestMapping(value = "/saveLocal", method = RequestMethod.POST)
    public Object saveLocal(@RequestBody DmsStorageLocal paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsStorageService.createLocal(paramVo);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "修改仓库货位", response = JsonResult.class)
    @RequestMapping(value = "/updateLocal", method = RequestMethod.POST)
    public Object updateLocal(@RequestBody DmsStorageLocal paramVo){
        this.dmsStorageService.updateLocal(paramVo);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "删除仓库货位", response = JsonResult.class)
    @RequestMapping(value = "/deleteLocal", method = RequestMethod.GET)
    public Object deleteLocal(@ApiParam("仓库货位id") @RequestParam(value = "id",required = true) Long id){
        this.dmsStorageService.deleteLocal(id);
        return toResult(null);
    }
}
