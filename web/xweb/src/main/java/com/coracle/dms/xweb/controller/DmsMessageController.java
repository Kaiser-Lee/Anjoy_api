package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsMessage;
import com.coracle.dms.service.DmsMessageService;
import com.coracle.dms.vo.DmsMessageVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/message")
@Api(description = "Dms消息接口")
public class DmsMessageController extends BaseController {
    @Autowired
    private DmsMessageService dmsMessageService;

    @ResponseBody
    @ApiOperation(value = "App消息列表",response = DmsMessage.class)
    @RequestMapping(value = "/listByApp", method = RequestMethod.POST)
    public Object getListByApp(@ApiParam("消息类型列表") @RequestBody DmsMessageVo dmsMessageVo){
        dmsMessageVo.setStaffId(LoginManager.getCurrentUserId());
        dmsMessageVo.setOrderField(" t.id ");
        dmsMessageVo.setOrderString(" desc ");
        PageHelper.Page<DmsMessageVo> pageList = dmsMessageService.selectForListPage(dmsMessageVo);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "PC消息列表",response = DmsMessage.class)
    @RequestMapping(value = "/listByPC", method = RequestMethod.GET)
    public Object getListByPC(@ApiParam("分页页码")@RequestParam(value = "p",required = false) Integer p,
                              @ApiParam("每页条数")@RequestParam(value = "s",required = false) Integer s){
        DmsMessageVo messageVo = new DmsMessageVo();
        messageVo.setP(p);
        messageVo.setS(s);
        messageVo.setStaffId(0L);//0代表是品牌商可以看到消息
        messageVo.setOrderField(" t.id ");
        messageVo.setOrderString(" desc ");
        PageHelper.Page<DmsMessageVo> pageList = dmsMessageService.selectForListPage(messageVo);
        return toResult(pageList);
    }

    /**
     * 设置消息已读
     */
    @ResponseBody
    @ApiOperation(value = "设置消息已读",response = JsonResult.class)
    @RequestMapping(value = "/read",method = RequestMethod.POST)
    public Object read(@RequestBody List<Long> ids){
        /*List<Long> ids = new ArrayList<Long>();
        ids.add(id);*/
        dmsMessageService.read(ids);
        return toResult(null);
    }

    /**
     * 获取消息详情
     */
    @ResponseBody
    @ApiOperation(value = "消息详情(单纯的消息详情)",response = DmsMessage.class)
    @RequestMapping(value = "/details",method = RequestMethod.GET)
    public Object getDetails(@ApiParam("消息ID")@RequestParam("id")Long id){
        DmsMessage message=dmsMessageService.selectByPrimaryKey(id);
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        dmsMessageService.read(ids);
        return toResult(message);
    }

    /**
     * 获取消息详情
     */
    @ResponseBody
    @ApiOperation(value = "消息详情(APP获取消息详情是一个列表，根据entityId，entityType，用户ID确定)",response = DmsMessage.class)
    @RequestMapping(value = "/detailList",method = RequestMethod.POST)
    public Object getDetailList(@RequestBody DmsMessageVo dmsMessageVo){
        DmsMessage message = new DmsMessage();
        message.setEntityId(dmsMessageVo.getEntityId());
        message.setEntityType(dmsMessageVo.getEntityType());
        message.setStaffId(LoginManager.getCurrentUserId());
        message.setOrderField(" id ");
        message.setOrderString(" desc ");
        List<DmsMessage> list = dmsMessageService.selectByCondition(message);
        /*if (BlankUtil.isNotEmpty(list)){
            List<Long> ids = new ArrayList();
            for(DmsMessage dmsMessage:list){
                ids.add(dmsMessage.getId());
            }
            dmsMessageService.read(ids);
        }*/
        return toResult(list);
    }


    /**
     * 获取未读消息数量
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取未读消息数量",response = JsonResult.class)
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public Object getCount(@ApiParam("获取未读消息数量，类型：1：pc,其他APP")@RequestParam(value = "type",required = false)Integer type){
        long count = 0;
        if (BlankUtil.isNotEmpty(type)&&type==1){
            count = dmsMessageService.getCount(0L);
        }else {
            count = dmsMessageService.getCount(LoginManager.getCurrentUserId());
        }
        return toResult(count);
    }
}
