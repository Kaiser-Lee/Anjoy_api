package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsChannelContacts;
import com.coracle.dms.po.DmsRemark;
import com.coracle.dms.po.DmsStore;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsRemarkVo;
import com.coracle.dms.vo.DmsSellerVo;
import com.coracle.dms.vo.DmsStoreVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/19.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/store")
@Api(description = "门店管理接口")
public class DmsStoreController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsStoreController.class);
    @Autowired
    private DmsStoreService dmsStoreService;
    @Autowired
    private DmsSellerService dmsSellerService;
    @Autowired
    private DmsChannelContactsService dmsChannelContactsService;
    @Autowired
    private DmsChannelService dmsChannelService;
    @Autowired
    private DmsUserService dmsUserService;

    /**
     * 新增门店PC
     */
    @ResponseBody
    @ApiOperation(value = "新增门店PC", response = JsonResult.class)
    @RequestMapping(value ="/insertByPc",method = RequestMethod.POST)
    public Object insertByPc(@ApiParam("门店Vo")@RequestBody DmsStoreVo dmsStoreVo){
        PoDefaultValueGenerator.putDefaultValue(dmsStoreVo);
        dmsStoreService.addStore(dmsStoreVo,LoginManager.getUserSession(),0);
        return toResult(null);
    }

    /**
     * 更新门店PC
     */
    @ResponseBody
    @ApiOperation(value = "更新门店PC", response = JsonResult.class)
    @RequestMapping(value ="/modifyByPc",method = RequestMethod.POST)
    public Object modifyByPc(@ApiParam("门店Vo")@RequestBody DmsStoreVo dmsStoreVo){
        PoDefaultValueGenerator.putDefaultValue(dmsStoreVo);
        dmsStoreService.modifyStore(dmsStoreVo,LoginManager.getCurrentUserId(),0);
        return toResult(null);
    }

    /**
     * 新增门店订货端
     */
    @ResponseBody
    @ApiOperation(value = "新增门店(订货端)", response = JsonResult.class)
    @RequestMapping(value ="/insertByApp",method = RequestMethod.POST)
    public Object insertByApp(@ApiParam("门店Vo")@RequestBody DmsStoreVo dmsStoreVo){
        PoDefaultValueGenerator.putDefaultValue(dmsStoreVo);
        dmsStoreService.addStore(dmsStoreVo,LoginManager.getUserSession(),1);
        return toResult(null);
    }
    /**
     * 修改门店订货端
     */
    @ResponseBody
    @ApiOperation(value = "修改门店(订货端)", response = JsonResult.class)
    @RequestMapping(value ="/modifyByApp",method = RequestMethod.POST)
    public Object modifyByApp(@ApiParam("门店Vo")@RequestBody DmsStoreVo dmsStoreVo){
        if (dmsStoreVo.getId()==null||dmsStoreVo.getId()==0){
            throw new ControllerException("需要修改的门店ID不能为空！");
        }
        PoDefaultValueGenerator.putDefaultValue(dmsStoreVo);
        dmsStoreService.modifyStore(dmsStoreVo,LoginManager.getCurrentUserId(),1);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "按ID获取门店详情", response = DmsStore.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object selectDetail(@ApiParam("门店id")@RequestParam("id") Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("请输入要获取门店的ID！");
        }
        Map<String,Object> result = dmsStoreService.getStoreDetail(id,LoginManager.getUserSession());
        return toResult(result);
    }

    @ResponseBody
    @ApiOperation(value = "PC按条件获取门店列表", response = DmsStore.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@ApiParam("查询信息")@RequestBody DmsStoreVo dmsStoreVo){
        dmsStoreVo.setCloseDate(new Date());
        if (BlankUtil.isNotEmpty(dmsStoreVo.getDealerName())){
            List<Long> list = dmsChannelService.selectChannelId(dmsStoreVo.getDealerName().trim());
            if (BlankUtil.isNotEmpty(list)){
                dmsStoreVo.setChannelIds(list);
            }
        }
        dmsStoreVo.setOrderField(" ds.last_updated_date ");
        dmsStoreVo.setOrderString(" desc ");
        PageHelper.Page<DmsStoreVo> pageList=dmsStoreService.selectForPageList(dmsStoreVo);
        List<DmsStoreVo> list = pageList.getResult();
        for(DmsStoreVo dsv:list){
            if (BlankUtil.isEmpty(dsv.getCloseDate())||dsv.getCloseDate().getTime()>new Date().getTime()){//关停时间为空或者比当前时间大，代表还在运营中，否则已停运
                dsv.setActive(1);
            }else {
                dsv.setActive(0);
            }
        }
        pageList.setResult(list);
        return toResult(pageList, "操作成功");
    }

    /**
     * APP获取门店列表
     */
    @ResponseBody
    @ApiOperation(value = "APP获取门店列表", response = JsonResult.class)
    @RequestMapping(value ="/getListByApp",method = RequestMethod.POST)
    public Object getListByApp(@RequestBody DmsStoreVo dmsStoreVo){
        DmsChannelContacts dmsChannelContacts = new DmsChannelContacts();
        dmsChannelContacts.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsChannelContacts.setUserId(LoginManager.getCurrentUserId());
        List<DmsChannelContacts> dmsChannelContactsList = dmsChannelContactsService.selectByCondition(dmsChannelContacts);
        if (BlankUtil.isEmpty(dmsChannelContactsList)||dmsChannelContactsList.size()==0){
            throw new ControllerException("未获取到渠道信息！");
        }else if(dmsChannelContactsList.size()>1){
            throw new ControllerException("获取到多个渠道信息");
        }else{
            dmsStoreVo.setBelongDealer(dmsChannelContactsList.get(0).getChannelId());
        }
        dmsStoreVo.setOrderField(" ds.last_updated_date ");
        dmsStoreVo.setOrderString(" desc ");
        PageHelper.Page<DmsStoreVo> pageList=dmsStoreService.selectForPageListByApp(dmsStoreVo);
        List<DmsStoreVo> list = pageList.getResult();
        for(DmsStoreVo dsv:list){
            if (BlankUtil.isEmpty(dsv.getCloseDate())||dsv.getCloseDate().getTime()>new Date().getTime()){//关停时间为空或者比当前时间大，代表还在运营中，否则已停运
                dsv.setActive(1);
            }else {
                dsv.setActive(0);
            }
        }
        pageList.setResult(list);
        return toResult(pageList);
    }

    /**
     * APP获取门店列表
     */
    @ResponseBody
    @ApiOperation(value = "获取当前用户下的门店列表", response = JsonResult.class)
    @RequestMapping(value ="/getStoreListByUser",method = RequestMethod.POST)
    public Object getStoreListByUser(@RequestBody DmsStoreVo dmsStoreVo){
        DmsUser dmsUser = dmsUserService.selectByPrimaryKey(LoginManager.getCurrentUserId());
        if (dmsUser.getSource()==DmsModuleEnums.ACCOUNT_SOURCE_TYPE.CHANNEL_CONTACTS.getType()){//渠道商门店
            dmsStoreVo.setBelongDealer(LoginManager.getUserSession().getOrgId());
        }else if (dmsUser.getSource()==DmsModuleEnums.ACCOUNT_SOURCE_TYPE.SELLER.getType()) {//店员所在门店
            DmsStore dmsStore = dmsStoreService.selectByPrimaryKey(LoginManager.getUserSession().getOrgId());
            dmsStoreVo.setBelongDealer(dmsStore.getBelongDealer());
            dmsStoreVo.setOperateWay(dmsStore.getOperateWay());
        }else {
            return toResult(null);
        }
        PageHelper.Page<DmsStore> pageList = dmsStoreService.selectForPageListByUser(dmsStoreVo);
        return toResult(pageList);

    }

    /**
     * 新增和更新门店店员
     */
    @ResponseBody
    @ApiOperation(value = "新增和更新门店店员", response = JsonResult.class)
    @RequestMapping(value ="/saveOrUpdateSeller",method = RequestMethod.POST)
    public Object saveOrUpdateSeller(@ApiParam("门店店员Vo")@RequestBody DmsSellerVo dmsSellerVo){
        PoDefaultValueGenerator.putDefaultValue(dmsSellerVo);
        dmsSellerService.saveOrUpdate(dmsSellerVo, LoginManager.getUserSession());
        return toResult(null);
    }

    /**
     * 获取门店店员列表
     */
    @ResponseBody
    @ApiOperation(value = "获取门店店员列表", response = JsonResult.class)
    @RequestMapping(value ="/getSellerList",method = RequestMethod.GET)
    public Object getSellerList(@ApiParam("门店id")@RequestParam("id") Long id,
                                @ApiParam("分页页码")@RequestParam(value = "p",required = false) Integer p,
                                @ApiParam("分页条数")@RequestParam(value = "s",required = false) Integer s){
        DmsSellerVo dmsSellerVo = new DmsSellerVo();
        dmsSellerVo.setShopId(id);
        dmsSellerVo.setRemoveFlag(0);
        dmsSellerVo.setP(p);
        dmsSellerVo.setS(s);
        dmsSellerVo.setOrderField(" ds.id ");
        dmsSellerVo.setOrderString(" desc ");
        PageHelper.Page<DmsSellerVo> pageList = dmsSellerService.pageList(dmsSellerVo);
        List<DmsSellerVo> dmsSellerVoList = pageList.getResult();
        if (BlankUtil.isNotEmpty(dmsSellerVoList)) {
            for (DmsSellerVo sellerVo : dmsSellerVoList) {
                if (BlankUtil.isNotEmpty(sellerVo.getDmsContactInfoMapList())) {
                    for (Map<String, Object> map : sellerVo.getDmsContactInfoMapList()) {
                        if (BlankUtil.isNotEmpty(map.get("type"))) {
                            int type = Integer.parseInt(map.get("type").toString());
                            switch (type) {
                                case 1:
                                    if (BlankUtil.isEmpty(sellerVo.getMobileText()))
                                        sellerVo.setMobileText(map.get("content") == null ? "" : map.get("content").toString());
                                    break;
                                case 2:
                                    sellerVo.setPhoneText(map.get("content") == null ? "" : map.get("content").toString());
                                    break;
                                case 3:
                                    sellerVo.setEmailText(map.get("content") == null ? "" : map.get("content").toString());
                                    break;
                                case 4:
                                    sellerVo.setQqText(map.get("content") == null ? "" : map.get("content").toString());
                                    break;
                                case 5:
                                    sellerVo.setWeiText(map.get("content") == null ? "" : map.get("content").toString());
                                    break;
                            }
                        }
                    }
                }
            }
        }
        pageList.setResult(dmsSellerVoList);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "按ID获取门店店员详情", response = DmsStore.class)
    @RequestMapping(value = "/sellerDetail", method = RequestMethod.GET)
    public Object selectSellerDetail(@ApiParam("门店店员id")@RequestParam("id") Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("请输入要获取门店店员的ID！");
        }
        Map<String,Object> result = dmsSellerService.getSellerDetail(id,LoginManager.getUserSession());
        return toResult(result);
    }

    @ResponseBody
    @ApiOperation(value = "按ID给门店店员开通,返回账号", response = DmsStore.class)
    @RequestMapping(value = "/openAccount", method = RequestMethod.GET)
    public Object openAccount(@ApiParam("门店店员id")@RequestParam("sellerId") Long sellerId){
        if (BlankUtil.isEmpty(sellerId)){
            throw new ControllerException("请输入门店店员的ID！");
        }
        String sellerAccount = dmsSellerService.openAccount(sellerId, LoginManager.getUserSession());
        return toResult(sellerAccount);
    }

}
