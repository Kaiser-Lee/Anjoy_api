package com.coracle.dms.xweb.controller.dms;

import com.coracle.dms.vo.*;
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

import com.coracle.dms.constant.DmsRoleCodeConstants;
import com.coracle.dms.po.DmsOrderDeliveryItem;
import com.coracle.dms.po.DmsOrderReturn;
import com.coracle.dms.po.DmsOrderReturnCart;
import com.coracle.dms.service.DmsOrderReturnCartService;
import com.coracle.dms.service.DmsOrderReturnService;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.dms.xweb.controller.BaseController;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;

@Controller
@RequestMapping(value = "/api/v1/dms/orderReturn")
@Api(description = "退货管理接口")
public class DmsOrderReturnController extends BaseController {

	@Resource
	private DmsOrderReturnService dmsOrderReturnService;
	@Resource
	private DmsOrderReturnCartService dmsOrderReturnCartService;
	
	@ResponseBody
    @ApiOperation(value = "退换货清单",response = DmsOrderReturn.class)
    @RequestMapping(value = "/listCart",method = RequestMethod.POST)
    public Object getListCart(@RequestBody DmsOrderReturnCartVo paramVo){
		paramVo.setUserId(LoginManager.getCurrentUserId());
    	PageHelper.Page<DmsOrderReturnCartVo> pageList = dmsOrderReturnCartService.selectForListPage(paramVo);
		if (pageList != null) {
			for (DmsOrderReturnCartVo vo : pageList.getResult()) {
				Integer returnCount = this.dmsOrderReturnCartService.selectReturnCount(vo);
				vo.setOldOrderNum(returnCount == null ? 0 : returnCount);
			}
		}
        return toResult(pageList);
    }
	
	@ResponseBody
    @ApiOperation(value = "添加退换货清单", response = JsonResult.class)
    @RequestMapping(value = "/saveCart", method = RequestMethod.POST)
    public Object saveCart(@RequestBody DmsOrderReturnCart paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        paramVo.setUserId(LoginManager.getCurrentUserId());
        this.dmsOrderReturnCartService.create(paramVo);
        return toResult(null);
    }
	
	@ResponseBody
    @ApiOperation(value = "删除退换货清单", response = JsonResult.class)
    @RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
    public Object deleteCart(@RequestBody DmsOrderReturnCart paramVo){
        paramVo.setUserId(LoginManager.getCurrentUserId());
        this.dmsOrderReturnCartService.deleteByIdSoft(paramVo.getIds());
        return toResult(null);
    }
	
	@ResponseBody
    @ApiOperation(value = "批量修改退换货清单", response = JsonResult.class)
    @RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
    public Object batchUpdate(@RequestBody List<DmsOrderReturnCart> paramList){
        this.dmsOrderReturnCartService.batchUpdate(paramList, LoginManager.getUserSession());
        return toResult(null);
    }
	
	@ResponseBody
    @ApiOperation(value = "退换货清单数量", response = JsonResult.class)
    @RequestMapping(value = "/getCartCount", method = RequestMethod.GET)
    public Object getCartCount(){
		DmsOrderReturnCart paramVo = new DmsOrderReturnCart();
        paramVo.setUserId(LoginManager.getCurrentUserId());
        return toResult(this.dmsOrderReturnCartService.getOrderReturnCartCount(paramVo));
    }
	
	@ResponseBody
    @ApiOperation(value = "退货列表",response = DmsOrderReturn.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@RequestBody DmsOrderReturnVo paramVo){
		String roleCode = LoginManager.getUserSession().getRoleCode();
        if (roleCode.equals(DmsRoleCodeConstants.CO)) {  //订货端用户只能查看自己的订单
        	paramVo.setUserId(LoginManager.getCurrentUserId());
        }
        paramVo.setOrderField(" id ");
		paramVo.setOrderString(" desc ");
    	PageHelper.Page<DmsOrderReturnVo> pageList = dmsOrderReturnService.selectForListPage(paramVo);
        return toResult(pageList);
    }
	
	@ResponseBody
    @ApiOperation(value = "退换货详情",response = DmsOrderReturn.class)
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(@ApiParam("订单id") @RequestParam(value = "id",required = true) Long id){
        return toResult(this.dmsOrderReturnService.detail(id));
    }
    
    @ResponseBody
    @ApiOperation(value = "创建退换货单", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@RequestBody DmsOrderReturnVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        DmsOrderReturnVo result = this.dmsOrderReturnService.create(paramVo,LoginManager.getUserSession());
        return toResult(result);
    }
    
    @ResponseBody
    @ApiOperation(value = "审核退换货订单", response = JsonResult.class)
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public Object audit(@RequestBody DmsOrderReturnVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsOrderReturnService.audit(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }


    @ResponseBody
    @ApiOperation(value = "安井审核退换货订单", response = JsonResult.class)
    @RequestMapping(value = "/anjoyAudit", method = RequestMethod.POST)
    public Object audit(@RequestParam @ApiParam("EAS退货订单编码") String orderNo,
                        @RequestParam @ApiParam("是否审核通过") Integer audit,
                        @RequestParam @ApiParam("审核意见") String remark){
        this.dmsOrderReturnService.audit(orderNo,audit,remark);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "渠道发货", response = JsonResult.class)
    @RequestMapping(value = "/updateDelivery", method = RequestMethod.POST)
    public Object updateDelivery(@RequestBody DmsOrderReturnVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsOrderReturnService.updateDelivery(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "品牌商收货", response = JsonResult.class)
    @RequestMapping(value = "/updateReceived", method = RequestMethod.POST)
    public Object updateReceived(@RequestBody DmsOrderReturnItemVo orderReturnItem){
        PoDefaultValueGenerator.putDefaultValue(orderReturnItem);
        this.dmsOrderReturnService.updateReceived(orderReturnItem,LoginManager.getUserSession());
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "品牌商发货", response = JsonResult.class)
    @RequestMapping(value = "/updateDeliveryBrand", method = RequestMethod.POST)
    public Object updateDeliveryBrand(@RequestBody DmsOrderDeliveryVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsOrderReturnService.updateDeliveryBrand(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "渠道收货", response = JsonResult.class)
    @RequestMapping(value = "/updateReceivedChannel", method = RequestMethod.POST)
    public Object updateReceivedChannel(@RequestBody DmsOrderReturnVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsOrderReturnService.updateReceivedChannel(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
    @ResponseBody
    @ApiOperation(value = "渠道收货（针对单个产品收货）", response = JsonResult.class)
    @RequestMapping(value = "/updateReceivedByProduct", method = RequestMethod.POST)
    public Object updateReceivedByProduct(@RequestBody DmsOrderDeliveryItem paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsOrderReturnService.updateReceivedChannel(paramVo, LoginManager.getUserSession());
        return toResult(null);
    }
    @ResponseBody
    @ApiOperation(value = "取消订单", response = JsonResult.class)
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public Object cancel(@RequestBody DmsOrderReturnVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsOrderReturnService.updateCancel(paramVo, LoginManager.getUserSession());
        return toResult(null);
    }
    @ResponseBody
    @ApiOperation(value = "确认退款", response = JsonResult.class)
    @RequestMapping(value = "/confirmReturnPay", method = RequestMethod.POST)
    public Object confirmReturnPay(@RequestBody DmsOrderReturnVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsOrderReturnService.updateReturnPay(paramVo, LoginManager.getUserSession());
        return toResult(null);
    }
    @ResponseBody
    @ApiOperation(value = "根据订单id获取待发货产品的仓库、货位等信息", response = JsonResult.class)
    @RequestMapping(value = "/productStorage", method = RequestMethod.GET)
    public Object productStorage(@RequestParam Long id) {
        return toResult(this.dmsOrderReturnService.selectStorageInfoByOrderId(id));
    }



    @ResponseBody
    @ApiOperation(value = "安井创建退货单", response = JsonResult.class)
    @RequestMapping(value = "/anjoyReturn", method = RequestMethod.POST)
    public Object saveAnjoy(@RequestBody DmsReturnRequestVo requestVo){
        //PoDefaultValueGenerator.putDefaultValue(requestVo);
        this.dmsOrderReturnService.anjoyReturnSync(requestVo);
        return toResult(null);
    }
}
