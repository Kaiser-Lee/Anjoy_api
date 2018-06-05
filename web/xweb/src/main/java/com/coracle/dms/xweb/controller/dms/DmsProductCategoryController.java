package com.coracle.dms.xweb.controller.dms;

import com.coracle.dms.po.DmsProductCategory;
import com.coracle.dms.service.DmsProductCategoryService;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.dms.xweb.controller.BaseController;
import com.coracle.yk.base.vo.BaseRequestParamVo;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tanyb on 2017/8/18.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/category")
@Api(description = "产品分类接口")
public class DmsProductCategoryController extends BaseController {

    @Resource
    private DmsProductCategoryService dmsProductCategoryService;

	@ResponseBody
	@ApiOperation(value = "产品分类树查询", response = DmsProductCategory.class)
	@RequestMapping(value = "/listTree", method = RequestMethod.GET)
	public Object listTree(@ApiParam("父节点ID") @RequestParam(value = "id",required = false) Long id) {
        UserSession userSession = LoginManager.getUserSession();
		return toResult(dmsProductCategoryService.selectRecursiveTree(id, userSession));
	}
    
    /**
     * 根据条件获取产品分类列表
     */
    @ResponseBody
    @ApiOperation(value = "产品分类列表",response = DmsProductCategory.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@ApiParam("产品分类实体") @RequestBody DmsProductCategory param){
    	param.setOrderField("last_updated_date");
    	param.setOrderString("desc");
    	PageHelper.Page<DmsProductCategory> pageList = dmsProductCategoryService.selectForListPage(param);
        return toResult(pageList);
    }


    @ResponseBody
    @ApiOperation(value = "产品分类子集列表1",response = DmsProductCategory.class)
    @RequestMapping(value = "/selectByPid1",method = RequestMethod.POST)
    public Object selectByPid(@ApiParam("父id集合") @RequestBody BaseRequestParamVo entity){
        List indexList = entity.getIds();
        List<DmsProductCategory> pageList = dmsProductCategoryService.listByParentId(indexList);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "产品分类子集列表1",response = DmsProductCategory.class)
    @RequestMapping(value = "/selectByPid2",method = RequestMethod.POST)
    public Object selectSon(){

        List<DmsProductCategory> pageList = dmsProductCategoryService.selectSon();
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "产品分类详情", response = JsonResult.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam("分类ID") @RequestParam(value = "id",required = true) Long id){
        return toResult(dmsProductCategoryService.detail(id));
    }
    
    @ResponseBody
    @ApiOperation(value = "获取产品分类最大级别", response = JsonResult.class)
    @RequestMapping(value = "/getLevelMax", method = RequestMethod.GET)
    public Object getLevelMax(){
        return toResult(this.dmsProductCategoryService.getLevelMax());
    }
    
    @ResponseBody
    @ApiOperation(value = "新增分类", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsProductCategory category){
        PoDefaultValueGenerator.putDefaultValue(category);
        dmsProductCategoryService.create(category);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "修改分类", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsProductCategory category){
        PoDefaultValueGenerator.putDefaultValue(category);
        dmsProductCategoryService.update(category);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "删除分类", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@ApiParam("分类ID") @RequestParam(value = "id",required = true) Long id){
    	DmsProductCategory category = new DmsProductCategory();
    	category.setId(id);
        PoDefaultValueGenerator.putDefaultValue(category);
        dmsProductCategoryService.delete(category);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "失效分类", response = JsonResult.class)
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    public Object invalid(@RequestBody DmsProductCategory category){
        PoDefaultValueGenerator.putDefaultValue(category);
        dmsProductCategoryService.updateActive(category);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "安井产品分类同步", response = DmsProductCategory.class)
    @RequestMapping(value = "/anjoy-syn", method = RequestMethod.GET)
    public Object anjoySyn() {
        dmsProductCategoryService.anjoySyn();
        return toResult();
    }





}
