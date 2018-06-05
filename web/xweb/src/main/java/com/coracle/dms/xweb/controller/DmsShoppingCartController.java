package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsShoppingCart;
import com.coracle.dms.service.DmsShoppingCartService;
import com.coracle.dms.vo.DmsShoppingCartInfoVo;
import com.coracle.dms.vo.DmsShoppingCartVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xframework.po.UserSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(description = "购物车接口")
@RequestMapping("/api/v1/dms/shoppingCart")
public class DmsShoppingCartController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DmsShoppingCartController.class);
    @Autowired
    private DmsShoppingCartService dmsShoppingCartService;

    @ResponseBody
    @ApiOperation(value = "工作区-购物车(列表)", response = JsonResult.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object pageList() {
        Long time1 = System.currentTimeMillis();
        DmsShoppingCartInfoVo detail = dmsShoppingCartService.detail(LoginManager.getUserSession());
        Long time2 = System.currentTimeMillis();
        logger.info("***********购物车耗时:{}**********",(time2-time1)/1000);
        return toResult(detail);
    }

    @ResponseBody
    @ApiOperation(value = "商城-加入购物车", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsShoppingCartVo shoppingCart) {

        Long time1 = System.currentTimeMillis();
        Long userId = LoginManager.getCurrentUserId();
        shoppingCart.setUserId(userId);
        PoDefaultValueGenerator.putDefaultValue(shoppingCart);
        dmsShoppingCartService.add(shoppingCart);
        Long time2 = System.currentTimeMillis();
        logger.info("*****************添加购物车耗时:{}****************",(time2-time1)+"ms");
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "修改数量-单个", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsShoppingCart shoppingCart) {
        Long time1= System.currentTimeMillis();
        PoDefaultValueGenerator.putDefaultValue(shoppingCart);
        dmsShoppingCartService.updateCount(shoppingCart);
        Long time2 = System.currentTimeMillis();
        logger.info("*************修改购物车数量耗时:{}*************",(time2-time1)+"ms");
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "修改数量-批量", response = JsonResult.class)
    @RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
    public Object batchUpdate(@RequestBody List<DmsShoppingCart> shoppingCartList) {
        for (DmsShoppingCart shoppingCart : shoppingCartList) {
            PoDefaultValueGenerator.putDefaultValue(shoppingCart);
            dmsShoppingCartService.updateCount(shoppingCart);
        }
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "商城-从购物车中删除产品", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object remove(@RequestBody DmsShoppingCart shoppingCart) {
        dmsShoppingCartService.batchRemove(shoppingCart.getIds());
        return toResult(null);
    }
    @ResponseBody
    @ApiOperation(value = "根据产品id删除购物车商品", response = JsonResult.class)
    @RequestMapping(value = "/removeShopCart", method = RequestMethod.GET)
    public Object removeShopCart(@ApiParam("产品id")@RequestParam("productId") Long productId) {
        UserSession userSession = LoginManager.getUserSession();
        DmsShoppingCart dmsShoppingCart = new DmsShoppingCart();
        dmsShoppingCart.setProductId(productId);
        dmsShoppingCartService.removeShopCart(dmsShoppingCart,userSession);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "商城-提交订单", response = JsonResult.class)
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Object submit() {
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "添加常购商品", response = JsonResult.class)
    @RequestMapping(value = "/addOftenBuyProduct", method = RequestMethod.POST)
    public Object addOftenBuyProduct(@RequestBody List<DmsShoppingCartVo> param) {
        Long userId = LoginManager.getCurrentUserId();
        dmsShoppingCartService.addOftenBuyProduct(param, LoginManager.getUserSession());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "常购商品列表", response = JsonResult.class)
    @RequestMapping(value = "/oftenBuyProductList", method = RequestMethod.POST)
    public Object oftenBuyProductList(@RequestBody DmsShoppingCartVo param) {
        param.setUserId(LoginManager.getCurrentUserId());
        DmsShoppingCartInfoVo result = dmsShoppingCartService.listOftenBuyProduct(param);
        return toResult(result);
    }

    @ResponseBody
    @ApiOperation(value = "删除常购产品", response = JsonResult.class)
    @RequestMapping(value = "/deleteOftenBuyProduct", method = RequestMethod.POST)
    public Object deleteOftenBuyProduct(@RequestBody DmsShoppingCartVo shoppingCartVo) {
        dmsShoppingCartService.deleteOftenBuyProduct(shoppingCartVo);
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "添加历史常购商品", response = JsonResult.class)
    @RequestMapping(value = "/addHistoryOfftenBuy", method = RequestMethod.POST)
    public Object historyOfftenBuy(@RequestBody DmsShoppingCartVo shoppingCartVo){

        dmsShoppingCartService.historyOfftenBuy(shoppingCartVo);

        return toResult(null);
    }

}