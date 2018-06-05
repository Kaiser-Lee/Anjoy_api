package com.coracle.dms.xweb.controller.dms;

import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.enums.PlatformEnum;
import com.coracle.dms.po.DmsChannel;
import com.coracle.dms.service.DmsChannelAddressService;
import com.coracle.dms.service.DmsChannelEmployeeService;
import com.coracle.dms.service.DmsChannelService;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.dms.xweb.controller.BaseController;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by tanyb on 2017/8/18.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/channel")
@Api(description = "渠道管理接口")
public class DmsChannelController extends BaseController {
	
	@Resource
	private DmsChannelService dmsChannelService;
    @Resource
    private DmsChannelAddressService dmsChannelAddressService;
    @Resource
    private DmsChannelEmployeeService dmsChannelEmployeeService;
	
	@ResponseBody
    @ApiOperation(value = "渠道列表",response = DmsChannelVo.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@ApiParam("渠道实体") @RequestBody DmsChannelVo searchVo){
		searchVo.setOrderField(" id ");
		searchVo.setOrderString(" desc ");
        PageHelper.Page<DmsChannelVo> pageList = dmsChannelService.selectForListPage(searchVo,LoginManager.getUserSession());
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "安井渠道信用额度查询",response = DmsChannelVo.class)
    @RequestMapping(value = "/getCreditLimitByChannelAnjoy",method = RequestMethod.POST)
    public Object getCreditLimitByChannelAnjoy(@ApiParam("渠道ID") @RequestParam Long channelId){
        DmsChannel dmsChannel=dmsChannelService.selectByPrimaryKey(channelId);
        JSONObject jsonObject= AnjoySynClient.getCreditLimitByChannelAnjoyId(dmsChannel.getAnjoyId());
        return toResult(jsonObject);
    }

	@ResponseBody
    @ApiOperation(value = "渠道列表（提供给下拉框）",response = DmsChannelVo.class)
    @RequestMapping(value = "/getListSelect",method = RequestMethod.GET)
    public Object getListSelect(){
		DmsChannelVo searchVo = new DmsChannelVo();
		searchVo.setOrderField("id");
		searchVo.setOrderString("desc");
        return toResult(dmsChannelService.getList(searchVo));
    }
	@ResponseBody
    @ApiOperation(value = "渠道详情", response = JsonResult.class)
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(@ApiParam("渠道id")@RequestParam("id") Long id){
		if (BlankUtil.isEmpty(id)){
            throw new ControllerException("参数异常！");
        }
        return toResult(this.dmsChannelService.detail(id, LoginManager.getUserSession()));
    }
    @ResponseBody
    @ApiOperation(value = "安井渠道详情", response = JsonResult.class)
    @RequestMapping(value = "/ajdetail", method = RequestMethod.POST)
    public Object ajdetail(@ApiParam("渠道id")@RequestParam("id") Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("参数异常！");
        }
        return toResult(this.dmsChannelService.ajdetail(id, LoginManager.getUserSession()));
    }
	@ResponseBody
    @ApiOperation(value = "新增渠道", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsChannelVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsChannelService.create(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
    @ResponseBody
    @ApiOperation(value = "安井新增渠道", response = JsonResult.class)
    @RequestMapping(value = "/ajsave", method = RequestMethod.POST)
    public Object ajinsert(@RequestBody DmsChannelVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsChannelService.ajcreate(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
    @ResponseBody
    @ApiOperation(value = "阳光新增渠道", response = JsonResult.class)
    @RequestMapping(value = "/ygsave", method = RequestMethod.POST)
    public Object ygInsert(@RequestBody DmsChannelVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        paramVo.setPlatformEnum(PlatformEnum.YANGGUANG);
        this.dmsChannelService.create(paramVo,LoginManager.getUserSession());
        return toResult(null);
    }
	@ResponseBody
    @ApiOperation(value = "修改渠道", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsChannelVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsChannelService.update(paramVo, LoginManager.getUserSession());
        return toResult(null);
    }
    @ResponseBody
    @ApiOperation(value = "安井修改渠道", response = JsonResult.class)
    @RequestMapping(value = "/ajupdate", method = RequestMethod.POST)
    public Object ajupdate(@RequestBody DmsChannelVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        this.dmsChannelService.ajupdate(paramVo);
        return toResult(null);
    }
    /**
     * 根据userid 查询渠道详情
     */
    @ResponseBody
    @ApiOperation(value = "app获取渠道详情", response = JsonResult.class)
    @RequestMapping(value = "/selectByUserId", method = RequestMethod.POST)
    public Object selectByUserId(){

        Long userId =LoginManager.getUserSession().getId();

        return toResult(this.dmsChannelService.selectByUserId(userId));

    }

    @ResponseBody
    @ApiOperation(value="根据channelid 和条件获取产品列表",response =DmsProductVo.class )
    @RequestMapping(value ="/findProductForMinimum",method = RequestMethod.POST)
    public Object findProductForMinimum(@ApiParam("产品实体")@RequestBody DmsProductVo productVo){

        PageHelper.Page<DmsProductVo> pageList= dmsChannelService.findProductForMinimum(productVo);
        return toResult(pageList);

    }

    @ResponseBody
    @ApiOperation(value = "渠道树形查询", response = DmsChannelVo.class)
    @RequestMapping(value = "/listTree", method = RequestMethod.GET)
    public Object listTree(@ApiParam("父节点ID") @RequestParam(value = "id",required = false) Long id) {
        return toResult(dmsChannelService.selectRecursiveTree(id));
    }

    @ResponseBody
    @ApiOperation(value = "删除收货地址", response = JsonResult.class)
    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    public Object delectAddress(@ApiParam("收货地址id") @RequestParam("id")Long id){
        dmsChannelAddressService.deleteByAddressID(id);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "删除业务员", response = JsonResult.class)
    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
    public Object deleteEmployee(@ApiParam("业务员id") @RequestParam("id")Long id){
        dmsChannelEmployeeService.deleteByEmployeeID(id);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "安井渠道同步", response = DmsChannel.class)
    @RequestMapping(value = "/anjoy-syn", method = RequestMethod.GET)
    public Object anjoySyn() {
        dmsChannelService.anjoySyn();
        return toResult();
    }


}
