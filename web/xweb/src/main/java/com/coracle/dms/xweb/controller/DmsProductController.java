package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsProduct;
import com.coracle.dms.po.DmsProductSpecMatrixConfig;
import com.coracle.dms.service.DmsOrderProductEvaluationService;
import com.coracle.dms.service.DmsOrderProductService;
import com.coracle.dms.service.DmsProductService;
import com.coracle.dms.service.DmsProductSpecMatrixConfigService;
import com.coracle.dms.vo.DmsOrderProductEvaluationVo;
import com.coracle.dms.vo.DmsProductSpecMatrixConfigVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.BaseRequestParamVo;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.xiruo.medbid.util.BeanConvertHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaobu2012 on 2017/8/22
 */
@Controller
@RequestMapping(value = "/api/v1/dms/product")
@Api(description = "产品管理接口")
public class DmsProductController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(DmsProductController.class);

    @Autowired
    private DmsProductService dmsProductService;

    @Autowired
    private DmsProductSpecMatrixConfigService dmsProductSpecMatrixConfigService;

    @Autowired
    private DmsOrderProductEvaluationService dmsOrderProductEvaluationService;

    @Autowired
    private DmsOrderProductService dmsOrderProductService;


    /**
     * PC-根据条件获取产品列表
     * @param product
     */
    @ResponseBody
    @ApiOperation(value = "产品列表",response = DmsProduct.class)
    @RequestMapping(value = "/listPc", method = RequestMethod.POST)
    public Object listPc(@ApiParam("产品实体")@RequestBody DmsProductVo product) {

        product.setUserId(LoginManager.getCurrentUserId());
        PageHelper.Page<DmsProductVo> dmsProductPage=dmsProductService.findProductPCPageList(product);
        return toResult(dmsProductPage);
    }

    /**
     * PC-产品-规格列表
     */
    @ResponseBody
    @ApiOperation(value = "产品-规格列表",response = DmsProduct.class)
    @RequestMapping(value = "/listPcSpec", method = RequestMethod.POST)
    public Object listPcSpec(@ApiParam("产品实体")@RequestBody DmsProductVo dmsProduct) {
        dmsProduct.setUserId(LoginManager.getCurrentUserId());
        PageHelper.Page<DmsProductVo> dmsProductPage=dmsProductService.findProductPCSpecPageList(dmsProduct);
        return toResult(dmsProductPage);
    }

    /**
     * app-根据条件获取产品列表
     */
    @ResponseBody
    @ApiOperation(value = "产品列表",response = DmsProduct.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@ApiParam("产品实体")@RequestBody DmsProductVo dmsProduct) {
        Long time1 = System.currentTimeMillis();
        dmsProduct.setUserId(LoginManager.getCurrentUserId());
        PageHelper.Page<DmsProductVo> dmsProductPage=dmsProductService.findProductPageList(dmsProduct, LoginManager.getUserSession());
        List<DmsProductVo> result = dmsProductPage.getResult();
        if (result == null || result.isEmpty()) {
            dmsProductPage.setResult(new ArrayList<DmsProductVo>());
        }
        Long time2 = System.currentTimeMillis();
        logger.info("**********产品列表耗时:{}***************",(time2-time1)+"ms");
        return toResult(dmsProductPage);
    }

    /**
     * app-根据条件获取产品列表(新增零售时产品列表)
     */
    @ResponseBody
    @ApiOperation(value = "产品列表(新增零售时产品列表)",response = DmsProduct.class)
    @RequestMapping(value = "/listForStore", method = RequestMethod.POST)
    public Object listForStore(@ApiParam("产品实体")@RequestBody DmsProductVo dmsProductVo) {
        UserSession userSession= LoginManager.getUserSession();
        PageHelper.Page<DmsProductVo> dmsProductPage=dmsProductService.findProductPageListForStore(dmsProductVo,userSession);
        if (dmsProductPage.getResult().isEmpty()) {
            dmsProductPage.setResult(new ArrayList<DmsProductVo>());
        }
        return toResult(dmsProductPage);
    }

    /**
     * 获取产品详情
     */
    @ResponseBody
    @ApiOperation(value = "PC产品详情", response = DmsProductVo.class)
    @RequestMapping(value = "/details",method=RequestMethod.GET)
    public Object getDetails(@ApiParam("产品id")@RequestParam("id") Long id){
        UserSession userSession= LoginManager.getUserSession();
        DmsProductVo productVo=dmsProductService.getDetails(id,userSession);
        return toResult(productVo,"操作成功");
    }


    /**
     * app获取产品详情
     */
    @ResponseBody
    @ApiOperation(value = "App产品详情", response = DmsProductVo.class)
    @RequestMapping(value = "/detailsApp",method=RequestMethod.GET)
    public Object getDetailsApp(@ApiParam("产品id")@RequestParam("id") Long id){
        UserSession userSession= LoginManager.getUserSession();
        DmsProductVo productVo=dmsProductService.getDetailsApp(id,userSession);
        return toResult(productVo,"操作成功");
    }

    /**
     * 获取产品详情页规格矩阵列表（规格矩阵列表，作用于前端判断是否可以删除该规格）
     */
    @ResponseBody
    @ApiOperation(value = "获取产品详情页规格矩阵列表", response = DmsProductVo.class)
    @RequestMapping(value = "/detailsSpecMatrix",method=RequestMethod.GET)
    public Object getDetailsSpecMatrix(@ApiParam("产品id")@RequestParam("id") Long id){
        UserSession userSession= LoginManager.getUserSession();
        List<DmsProductVo> productVo=dmsProductService.getDetailSpecMatrix(id,userSession);
        return toResult(productVo,"操作成功");
    }

    /**
     * 产品评论列表
     */
    @ResponseBody
    @ApiOperation(value = "产品评论列表", response = DmsProductVo.class)
    @RequestMapping(value = "/getProductEevaluation",method=RequestMethod.GET)
    public Object getTroductEevaluation(@ApiParam("产品id")@RequestParam("productId") Long productId){
        List<DmsOrderProductEvaluationVo> dmsOrderProductEvaluation=dmsOrderProductEvaluationService.getProductEvaluation(productId);
        return toResult(dmsOrderProductEvaluation,"操作成功");
    }

    /**
     * 获取产品详情
     */
    @ResponseBody
    @ApiOperation(value = "产品评论列表--星级统计", response = DmsProductVo.class)
    @RequestMapping(value = "/getProductEevaluationCount",method=RequestMethod.GET)
    public Object getProductEevaluationCount(@ApiParam("产品id")@RequestParam("productId") Long productId){
        Map<String,Object> map=dmsOrderProductService.selectCountByOrderProductProductId(productId);
        return toResult(map,"操作成功");
    }


    /**
     * 新增产品
     */
    @ResponseBody
    @ApiOperation(value = "新增产品", response = JsonResult.class)
    @RequestMapping(value ="create",method = RequestMethod.POST)
    public Object create(@ApiParam("产品Vo")@RequestBody DmsProductVo productVo){
        PoDefaultValueGenerator.putDefaultValue(productVo);
        UserSession userSession= LoginManager.getUserSession();
        dmsProductService.create(productVo,userSession);
        return toResult(null);
    }



    /**
     * 更新产品
     */
    @ResponseBody
    @ApiOperation(value = "更新产品", response = JsonResult.class)
    @RequestMapping(value ="/update",method = RequestMethod.POST)
    public Object update(@ApiParam("产品Vo")@RequestBody DmsProductVo productVo){
        UserSession userSession= LoginManager.getUserSession();
        dmsProductService.update(productVo,userSession);
        return toResult(null);
    }


    /**
     * 增加浏览次数
     */
    @ResponseBody
    @ApiOperation(value = "增加浏览次数和产品浏览记录", response = JsonResult.class)
    @RequestMapping(value = "/read",method=RequestMethod.GET)
    public Object addViewCount(@ApiParam("产品id")@RequestParam("id") Long id){
        dmsProductService.browseRecord(id, LoginManager.getUserSession().getId());
        return toResult(null);
    }


    /**
     * 批量上架产品
     */
    @ResponseBody
    @ApiOperation(value = "批量上架产品", response = JsonResult.class)
    @RequestMapping(value = "/up",method = RequestMethod.POST)
    public Object down(@RequestBody()BaseRequestParamVo entity){
        if(BlankUtil.isEmpty(entity.getIds())){
            throw new ControllerException("请选择需要上架的产品");
        }
        dmsProductService.up(entity.getIds());
        return toResult(null);
    }

    /**
     * 批量下架产品
     */
    @ResponseBody
    @ApiOperation(value = "批量下架产品", response = JsonResult.class)
    @RequestMapping(value = "/down",method = RequestMethod.POST)
    public Object up(@RequestBody BaseRequestParamVo entity){
        if(BlankUtil.isEmpty(entity.getIds())){
            throw new ControllerException("请选择需要下架的产品");
        }
        dmsProductService.down(entity.getIds());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "删除产品",response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@ApiParam("产品id") @RequestBody Long id) {
        dmsProductService.updateById(id);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "获取产品规格属性矩阵",response = JsonResult.class)
    @RequestMapping(value = "/specMatrix", method = RequestMethod.GET)
    public Object specConfigMatrix(@ApiParam("产品id") @RequestParam Long id) {

        DmsProduct product = dmsProductService.selectByPrimaryKey(id);
        if (product == null) {
            throw new ServiceException("数据库中不存在id为: " + id + "的产品");
        }
        DmsProductVo productVo = BeanConvertHelper.copyProperties(product, DmsProductVo.class);

        DmsProductVo param = new DmsProductVo();
        param.setId(id);
        param.setUserId(LoginManager.getCurrentUserId());
        //产品规格树形矩阵
        List<DmsProductSpecMatrixConfigVo> specMatrix = dmsProductSpecMatrixConfigService.selectVoByProductId(param);
        productVo.setProductSpecMatrixs(specMatrix);
        return toResult(productVo);
    }

    /*@ResponseBody
    @ApiOperation(value = "根据产品id和规格获取产品库存",response = JsonResult.class)
    @RequestMapping(value = "/specInventory", method = RequestMethod.POST)
    public Object specInventory(@ApiParam("产品实体") @RequestBody DmsProductSpecMatrixConfig dmsProductSpecMatrixConfig) {

        DmsProduct product = dmsProductService.selectByPrimaryKey(dmsProductSpecMatrixConfig.getProductId());
        if (product == null) {
            throw new ServiceException("数据库中不存在id为: " + dmsProductSpecMatrixConfig.getProductId() + "的产品");
        }

        //产品库存对象
        List<DmsProductSpecMatrixConfigVo> specMatrix = dmsProductSpecMatrixConfigService.selectVoByProductIdSpec(dmsProductSpecMatrixConfig);
        return toResult(specMatrix);
    }*/

    @ResponseBody
    @ApiOperation(value = "根据产品id和规格获取产品库存",response = DmsProductSpecMatrixConfig.class)
    @RequestMapping(value = "/specInventory", method = RequestMethod.POST)
    public Object specInventory(@ApiParam("产品实体") @RequestBody DmsProductSpecMatrixConfigVo dmsProductSpecMatrixConfigVo) {
        UserSession userSession= LoginManager.getUserSession();
        List<DmsProductSpecMatrixConfigVo> specMatrix=dmsProductSpecMatrixConfigService.selectVoByProductIdSpec(dmsProductSpecMatrixConfigVo,userSession);
        return toResult(specMatrix);
    }

    /**
     * 产品的评论列表
     * 参数：
     * productId - 产品id
     * evaluation - 评论内容
     * evaluator - 评论人
     */
    @ResponseBody
    @ApiOperation(value = "产品评论列表", response = DmsProductVo.class)
    @RequestMapping(value = "/evaluationList", method = RequestMethod.POST)
    public Object evaluationList(@RequestBody DmsOrderProductEvaluationVo param) {
        PageHelper.Page<DmsOrderProductEvaluationVo> dmsOrderProductEvaluation = dmsOrderProductEvaluationService.selectVoByParam(param);
        return toResult(dmsOrderProductEvaluation, "操作成功");
    }

    @ResponseBody
    @ApiOperation(value = "根据规格获取产品", response = DmsProductVo.class)
    @RequestMapping(value = "/listBySpecifications", method = RequestMethod.POST)
    public Object listBySpecifications(@RequestBody DmsProductVo param) {
        List<DmsProductVo> result = dmsProductService.listBySpecifications(param, LoginManager.getUserSession());
        return toResult(result, "操作成功");
    }

    @ResponseBody
    @ApiOperation(value = "安井产品同步")
    @RequestMapping(value = "/anjoy-syn", method = RequestMethod.GET)
    public Object anjoySyn() {
        dmsProductService.anjoySyn();
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "安井产品规格同步")
    @RequestMapping(value = "/anjoy-syn-spec", method = RequestMethod.GET)
    public Object anjoySpecificationSyn(){
        dmsProductService.anjoySpecificationSyn();
        return toResult(null);
    }

}
