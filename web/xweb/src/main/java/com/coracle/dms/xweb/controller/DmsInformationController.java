package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsChannelInformationVo;
import com.coracle.dms.vo.DmsInfoCommentVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.BaseRequestParamVo;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/information")
@Api(description = "DMS渠道赋能调用接口")
public class DmsInformationController extends BaseController {
    @Autowired
    private DmsInfoCommentService dmsInfoCommentService;
    @Autowired
    private DmsChannelInformationService dmsChannelInformationService;
    @Autowired
    private DmsUserService dmsUserService;
    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;

    @ResponseBody
    @ApiOperation(value = "新增渠道赋能资讯", response = JsonResult.class)
    @RequestMapping(value = "/addChannelInfo", method = RequestMethod.POST)
    public Object addChannelInfo(@RequestBody DmsChannelInformationVo dmsChannelInformationVo){
        PoDefaultValueGenerator.putDefaultValue(dmsChannelInformationVo);
        dmsChannelInformationVo.setPublishDate(new Date());
        dmsChannelInformationVo.setPublishUserId(LoginManager.getCurrentUserId());
        if(BlankUtil.isEmpty(dmsChannelInformationVo.getIsCanForward())){
            dmsChannelInformationVo.setIsCanForward(1);
        }
        dmsChannelInformationService.insertChannelInformation(dmsChannelInformationVo);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "渠道赋能资讯列表", response = JsonResult.class)
    @RequestMapping(value = "/informationList", method = RequestMethod.POST)
    public Object getInformationList(@RequestBody DmsChannelInformationVo dmsChannelInformationVo){
        dmsChannelInformationVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        if (BlankUtil.isNotEmpty(dmsChannelInformationVo.getStartDate())&&dmsChannelInformationVo.getStartDate().length()>=11){
            throw new ControllerException("请输入合适的查询日期");
        }else if (BlankUtil.isNotEmpty(dmsChannelInformationVo.getStartDate())&&dmsChannelInformationVo.getStartDate().length()<11){
            dmsChannelInformationVo.setStartDate(dmsChannelInformationVo.getStartDate() + " 00:00:00");
        }
        if (BlankUtil.isNotEmpty(dmsChannelInformationVo.getEndDate())&&dmsChannelInformationVo.getEndDate().length()>=11){
            throw new ControllerException("请输入合适的查询日期");
        }else if (BlankUtil.isNotEmpty(dmsChannelInformationVo.getEndDate())&&dmsChannelInformationVo.getEndDate().length()<11){
            dmsChannelInformationVo.setEndDate(dmsChannelInformationVo.getEndDate() + " 23:59:59");
        }
        dmsChannelInformationVo.setOrderField(" id ");
        dmsChannelInformationVo.setOrderString(" desc ");
        PageHelper.Page<DmsChannelInformationVo> pageList = dmsChannelInformationService.selectInformationPageList(dmsChannelInformationVo);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "渠道赋能资讯详情", response = JsonResult.class)
    @RequestMapping(value = "/informationDetail", method = RequestMethod.GET)
    public Object informationDetail(@ApiParam("渠道赋能资讯id") @RequestParam Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("请输入要查询的id");
        }
        DmsChannelInformationVo dmsChannelInformationVo = dmsChannelInformationService.selectVoByPrimaryKey(id);
        return toResult(dmsChannelInformationVo);
    }

    @ResponseBody
    @ApiOperation(value = "APP渠道赋能资讯查看", response = JsonResult.class)
    @RequestMapping(value = "/informationDetailApp", method = RequestMethod.GET)
    public Object informationDetailApp(@ApiParam("渠道赋能资讯id") @RequestParam Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("请输入要查询的id");
        }
        DmsChannelInformationVo dmsChannelInformationVo = dmsChannelInformationService.selectVoByPrimaryKey(id);
        if(dmsChannelInformationVo==null){
            throw new ControllerException("无法获取id为"+id+"的渠道赋能资讯信息！");
        }
        if (BlankUtil.isEmpty(dmsChannelInformationVo.getClickCount())){
            dmsChannelInformationVo.setClickCount(0L);
        }
        //点击量+1
        DmsChannelInformation info=new DmsChannelInformation();
        info.setId(id);
        info.setClickCount(dmsChannelInformationVo.getClickCount()+1);
        dmsChannelInformationService.updateByPrimaryKeySelective(info);
        return toResult(dmsChannelInformationVo);
    }

    @ResponseBody
    @ApiOperation(value = "修改渠道赋能资讯", response = JsonResult.class)
    @RequestMapping(value = "/modifyChannelInfo", method = RequestMethod.POST)
    public Object modifyChannelInfo(@RequestBody DmsChannelInformationVo dmsChannelInformationVo){
        PoDefaultValueGenerator.putDefaultValue(dmsChannelInformationVo);
        dmsChannelInformationService.modifyChannelInformation(dmsChannelInformationVo);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "发布和撤回接口",response = JsonResult.class)
    @RequestMapping(value = "/publishOrDownInfors", method = RequestMethod.POST)
    public Object publishOrDownInfors(@ApiParam("status：0撤回，1发布")@RequestBody BaseRequestParamVo baseRequestParamVo) {
        if(BlankUtil.isEmpty(baseRequestParamVo.getStatus())){
            throw new ControllerException("请确定操作类型：status：0撤回，1发布");
        }
        DmsChannelInformation dmsChannelInformation = new DmsChannelInformation();
        dmsChannelInformation.setLastUpdatedBy(LoginManager.getCurrentUserId());
        dmsChannelInformation.setLastUpdatedDate(new Date());
        dmsChannelInformation.setIsPublish(baseRequestParamVo.getStatus().intValue());
        dmsChannelInformationService.updateByIdsSelective(dmsChannelInformation,baseRequestParamVo.getIds());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "新增渠道赋能资讯评论(含转发)",response = JsonResult.class)
    @RequestMapping(value = "/insertInforsComment", method = RequestMethod.POST)
    public Object insertInforsComment(@RequestBody DmsInfoComment dmsInfoComment) {
        UserSession userSession=LoginManager.getUserSession();
        PoDefaultValueGenerator.putDefaultValue(dmsInfoComment);
        dmsInfoComment.setUserId(userSession.getId());
        dmsInfoComment.setCommentTime(new Date());
        dmsInfoComment.setUserName("");
        dmsInfoComment.setIsTop(0);
        if(BlankUtil.isEmpty(dmsInfoComment.getType())) dmsInfoComment.setType(0);
        dmsInfoCommentService.insertNewsComment(dmsInfoComment);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "APP获取渠道赋能资讯评论列表",response = DmsInfoCommentVo.class)
    @RequestMapping(value = "/inforsCommentList", method = RequestMethod.GET)
    public Object inforsCommentList(@ApiParam("渠道赋能资讯ID") @RequestParam Long id,
                                    @ApiParam("分页页码")@RequestParam(value = "p",required = false) Integer p,
                                    @ApiParam("每页条数")@RequestParam(value = "s",required = false) Integer s) {
        if(null==id){
            throw  new ControllerException("渠道赋能资讯ID不能为空！");
        }
        DmsInfoCommentVo dmsInfoCommentVo = new DmsInfoCommentVo();
        dmsInfoCommentVo.setP(p);
        dmsInfoCommentVo.setS(s);
        dmsInfoCommentVo.setRemoveFlag(0);
        dmsInfoCommentVo.setNewsId(id);
        dmsInfoCommentVo.setOrderField(" dic.is_top desc ,dic.id ");
        dmsInfoCommentVo.setOrderString(" desc ");
        PageHelper.Page<DmsInfoCommentVo> dmsInfoCommentVoPage=dmsInfoCommentService.selectDmsInforsCommentPage(dmsInfoCommentVo);
        /*dmsInfoCommentVoPage.setResult(dmsInfoCommentService.getCommentDetailByUser(dmsInfoCommentVoPage.getResult()));*/
        return toResult(dmsInfoCommentVoPage);
    }

    @ResponseBody
    @ApiOperation(value = "PC获取评论列表",response = DmsInfoCommentVo.class)
    @RequestMapping(value = "/inforsCommentAllList", method = RequestMethod.POST)
    public Object commentAppList(@RequestBody DmsInfoCommentVo dmsInfoCommentVo) {
        dmsInfoCommentVo.setRemoveFlag(0);
        dmsInfoCommentVo.setOrderField(" dic.is_top desc ,dic.id ");
        dmsInfoCommentVo.setOrderString(" desc ");
        PageHelper.Page<DmsInfoCommentVo> dmsInfoCommentVoPage=dmsInfoCommentService.selectDmsInforsCommentPage(dmsInfoCommentVo);
        /*dmsInfoCommentVoPage.setResult(dmsInfoCommentService.getCommentDetailByUser(dmsInfoCommentVoPage.getResult()));*/
        return toResult(dmsInfoCommentVoPage);
    }

    @ResponseBody
    @ApiOperation(value = "删除渠道赋能资讯评论",response = JsonResult.class)
    @RequestMapping(value = "/deleteInforsComment", method = RequestMethod.POST)
    public Object deleteComment(@ApiParam("渠道赋能资讯评论id集合") @RequestBody BaseRequestParamVo baseRequestParamVo) {
        if (baseRequestParamVo.getIds()==null||baseRequestParamVo.getIds().size()==0){
            throw new ControllerException("请输入需要删除的渠道赋能资讯评论Id列表");
        }
        DmsInfoComment dmsInfoComment = new DmsInfoComment();
        dmsInfoComment.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.REMOVE.getType());
        dmsInfoComment.setLastUpdatedBy(LoginManager.getCurrentUserId());
        dmsInfoComment.setLastUpdatedDate(new Date());
        dmsInfoCommentService.deleteInforsCommentByDeal(dmsInfoComment,baseRequestParamVo.getIds());
        return toResult(null);
    }

   @ResponseBody
    @ApiOperation(value = "渠道赋能资讯评论置顶",response = JsonResult.class)
    @RequestMapping(value = "/inforsCommentTop", method = RequestMethod.POST)
    public Object commentTop(@ApiParam("渠道赋能资讯评论id集合") @RequestBody BaseRequestParamVo baseRequestParamVo) {
        if (baseRequestParamVo.getIds()==null||baseRequestParamVo.getIds().size()==0){
            throw new ControllerException("请输入需要置顶的评论Id列表");
        }
        DmsInfoComment dmsInfoComment = new DmsInfoComment();
       dmsInfoComment.setIsTop(baseRequestParamVo.getStatus().intValue());
       dmsInfoComment.setLastUpdatedDate(new Date());
       dmsInfoComment.setLastUpdatedBy(LoginManager.getCurrentUserId());
        dmsInfoCommentService.updateByIdsSelective(dmsInfoComment,baseRequestParamVo.getIds());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "APP渠道赋能资讯列表", response = JsonResult.class)
    @RequestMapping(value = "/getInformationListByUser", method = RequestMethod.POST)
    public Object getInformationListByUser(@RequestBody DmsChannelInformation dmsChannelInformation){
        DmsUser dmsUser = dmsUserService.selectByPrimaryKey(LoginManager.getCurrentUserId());
        if (BlankUtil.isEmpty(dmsUser)){
            throw new ControllerException("请先登录");
        }
        Long treeId = dmsTreeRelationService.getTreeRelationId(dmsUser);
        if (treeId==null){
            return toResult(null);
        }else {
            PageHelper.Page<DmsChannelInformationVo> pageList = dmsChannelInformationService.selectChannelInforsForPageList(treeId, dmsChannelInformation.getP(), dmsChannelInformation.getS(),dmsChannelInformation.getType(),dmsChannelInformation.getTitle());
            return toResult(pageList);
        }
    }

}
