package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsNewsCommentVo;
import com.coracle.dms.vo.DmsNewsVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.BaseRequestParamVo;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by yjr on 2017/3/7.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/news")
@Api(description = "Dms资讯新闻接口")
public class DmsNewsController extends BaseController {
    @Autowired
    private DmsNewsService dmsNewsService;
    @Autowired
    private DmsNewsCommentService dmsNewsCommentService;
    @Autowired
    private DmsUserService dmsUserService;
    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;

    @ResponseBody
    @ApiOperation(value = "新闻列表",response = DmsNewsVo.class)
    @RequestMapping(value = "/getNewsList", method = RequestMethod.POST)
    public Object newsList(@RequestBody DmsNewsVo dmsNewsVo) {
        //此处默认查新闻类型
        dmsNewsVo.setType(DmsModuleEnums.NEWS_TYPE.NEWS.getType());
        if (BlankUtil.isNotEmpty(dmsNewsVo.getStartDate())&&dmsNewsVo.getStartDate().length()>=11){
            throw new ControllerException("请输入合适的查询日期");
        }else if (BlankUtil.isNotEmpty(dmsNewsVo.getStartDate())&&dmsNewsVo.getStartDate().length()<11){
            dmsNewsVo.setStartDate(dmsNewsVo.getStartDate() + " 00:00:00");
        }
        if (BlankUtil.isNotEmpty(dmsNewsVo.getEndDate())&&dmsNewsVo.getEndDate().length()>=11){
            throw new ControllerException("请输入合适的查询日期");
        }else if (BlankUtil.isNotEmpty(dmsNewsVo.getEndDate())&&dmsNewsVo.getEndDate().length()<11){
            dmsNewsVo.setEndDate(dmsNewsVo.getEndDate() + " 23:59:59");
        }
        dmsNewsVo.setOrderField(" dn.publish_date ");
        dmsNewsVo.setOrderString(" desc ");
        PageHelper.Page<DmsNewsVo> dmsNewsVoPage = dmsNewsService.findNewsPageList(dmsNewsVo);
        return toResult(dmsNewsVoPage);
    }

    @ResponseBody
    @ApiOperation(value = "新增资讯新闻",response = JsonResult.class)
    @RequestMapping(value = "/insertNews", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsNewsVo dmsNewsVo) {
        PoDefaultValueGenerator.putDefaultValue(dmsNewsVo);
        dmsNewsVo.setPublishDate(new Date());
        dmsNewsVo.setPublishUserId(LoginManager.getCurrentUserId());
        dmsNewsVo.setType(DmsModuleEnums.NEWS_TYPE.NEWS.getType());
        if(BlankUtil.isEmpty(dmsNewsVo.getIsCanForward())){
            dmsNewsVo.setIsCanForward(1);
        }
        Long id= dmsNewsService.insertNews(dmsNewsVo);
        //插入推送信息,

        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "修改资讯新闻",response = JsonResult.class)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Object modify(@RequestBody DmsNewsVo dmsNewsVo) {
        PoDefaultValueGenerator.putDefaultValue(dmsNewsVo);
        dmsNewsService.updateNews(dmsNewsVo);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "资讯新闻详情",response = DmsNews.class)
    @RequestMapping(value = "/newsDetail", method = RequestMethod.GET)
    public Object newsDetail(@ApiParam("资讯新闻ID") @RequestParam Long id) {
        if (id==null){
            throw new ControllerException("请输入资讯新闻ID");
        }
        DmsNewsVo dmsNewsVo = dmsNewsService.selectNewByPrimaryKey(id);

        return toResult(dmsNewsVo);
    }

    @ResponseBody
    @ApiOperation(value = "资讯查看",response = DmsNews.class)
    @RequestMapping(value = "/detailNoIntercept ", method = RequestMethod.GET)
    public Object detailNoIntercept(@ApiParam("资讯ID") @RequestParam Long id) {
        DmsNewsVo dmsNewsVo = dmsNewsService.selectNewByPrimaryKey(id);
        if(dmsNewsVo==null){
            throw new ControllerException("无法获取id为"+id+"的新闻资讯信息！");
        }
        if (BlankUtil.isEmpty(dmsNewsVo.getClickCount())){
            dmsNewsVo.setClickCount(0L);
        }
        //点击量+1
        DmsNews news=new DmsNews();
        news.setId(id);
        news.setClickCount(dmsNewsVo.getClickCount()+1);
        dmsNewsService.updateByPrimaryKeySelective(news);
        return toResult(dmsNewsVo);
    }

    @ResponseBody
    @ApiOperation(value = "发布和撤回接口",response = JsonResult.class)
    @RequestMapping(value = "/publishOrDownNews", method = RequestMethod.POST)
    public Object publishOrDownNews(@ApiParam("status：0撤回，1发布")@RequestBody BaseRequestParamVo baseRequestParamVo) {
        if(BlankUtil.isEmpty(baseRequestParamVo.getStatus())){
            throw new ControllerException("请确定操作类型：status：0撤回，1发布");
        }
        DmsNews dmsNews = new DmsNews();
        dmsNews.setLastUpdatedBy(LoginManager.getCurrentUserId());
        dmsNews.setLastUpdatedDate(new Date());
        dmsNews.setIsPublish(baseRequestParamVo.getStatus().intValue());
        if(baseRequestParamVo.getStatus()==1){//发布重新设置发布日期
            dmsNews.setPublishDate(new Date());
        }
        dmsNewsService.updateByIdsSelective(dmsNews,baseRequestParamVo.getIds());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "首页展示",response = JsonResult.class)
    @RequestMapping(value = "/showFirstPage", method = RequestMethod.POST)
    public Object showFirstPage(@ApiParam("资讯id集合:status:0-取消首页头条，1-首页头条") @RequestBody BaseRequestParamVo baseRequestParamVo) {
        if(BlankUtil.isEmpty(baseRequestParamVo.getStatus())){
            throw new ControllerException("请确定status操作类型值");
        }
        DmsNews dmsNews = new DmsNews();
        dmsNews.setLastUpdatedBy(LoginManager.getCurrentUserId());
        dmsNews.setLastUpdatedDate(new Date());
        dmsNews.setIsShow(baseRequestParamVo.getStatus().intValue());
        dmsNewsService.updateByIdsSelective(dmsNews,baseRequestParamVo.getIds());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "新增新闻评论(含转发)",response = JsonResult.class)
    @RequestMapping(value = "/insertNewsComment", method = RequestMethod.POST)
    public Object insertNewsComment(@RequestBody DmsNewsComment dmsNewsComment) {
        PoDefaultValueGenerator.putDefaultValue(dmsNewsComment);
        dmsNewsComment.setUserName("");
        dmsNewsComment.setUserId(LoginManager.getCurrentUserId());
        dmsNewsComment.setCommentTime(new Date());
        dmsNewsComment.setIsTop(0);
        if(BlankUtil.isEmpty(dmsNewsComment.getType())) dmsNewsComment.setType(0);
        dmsNewsCommentService.insertNewsComment(dmsNewsComment);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "PC查询新闻的评论列表",response = DmsNewsCommentVo.class)
    @RequestMapping(value = "/commentListByPC", method = RequestMethod.POST)
    public Object commentListByPC(@RequestBody DmsNewsCommentVo dmsNewsCommentVo) {
        dmsNewsCommentVo.setRemoveFlag(0);
        dmsNewsCommentVo.setOrderField(" c.is_top desc,c.id ");
        dmsNewsCommentVo.setOrderString(" desc ");
        PageHelper.Page<DmsNewsCommentVo> dmsNewsCommentVoPage=dmsNewsCommentService.selectDmsNewsCommentPage(dmsNewsCommentVo);
        return toResult(dmsNewsCommentVoPage);
    }


    @ResponseBody
    @ApiOperation(value = "App查询评论列表",response = DmsNewsCommentVo.class)
    @RequestMapping(value = "/commentList", method = RequestMethod.GET)
    public Object commentAppList(@ApiParam("资讯新闻ID") @RequestParam Long id,
                                 @ApiParam("分页页码")@RequestParam(value = "p",required = false) Integer p,
                                 @ApiParam("每页条数")@RequestParam(value = "s",required = false) Integer s) {
        if(null==id){
            throw  new ControllerException("资讯新闻ID不能为空！");
        }
        DmsNewsCommentVo dmsNewsCommentVo = new DmsNewsCommentVo();
        dmsNewsCommentVo.setP(p);
        dmsNewsCommentVo.setS(s);
        dmsNewsCommentVo.setRemoveFlag(0);
        dmsNewsCommentVo.setNewsId(id);
        dmsNewsCommentVo.setOrderField(" c.is_top desc,c.id ");
        dmsNewsCommentVo.setOrderString(" desc ");
        PageHelper.Page<DmsNewsCommentVo> dmsNewsCommentVoPage=dmsNewsCommentService.selectDmsNewsCommentPage(dmsNewsCommentVo);
        /*dmsNewsCommentVoPage.setResult(dmsNewsCommentService.getCommentDetailByUser(dmsNewsCommentVoPage.getResult()));*/
        return toResult(dmsNewsCommentVoPage);
    }

    @ResponseBody
    @ApiOperation(value = "删除评论",response = JsonResult.class)
    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    public Object deleteComment(@ApiParam("评论id集合") @RequestBody BaseRequestParamVo baseRequestParamVo) {
        if (baseRequestParamVo.getIds()==null||baseRequestParamVo.getIds().size()==0){
            throw new ControllerException("请输入需要删除的评论Id列表");
        }
        DmsNewsComment dmsNewsComment = new DmsNewsComment();
        dmsNewsComment.setRemoveFlag(1);
        dmsNewsComment.setLastUpdatedBy(LoginManager.getCurrentUserId());
        dmsNewsComment.setLastUpdatedDate(new Date());
        dmsNewsCommentService.deleteCommentByDeal(dmsNewsComment,baseRequestParamVo.getIds());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "评论置顶",response = JsonResult.class)
    @RequestMapping(value = "/commentTop", method = RequestMethod.POST)
    public Object commentTop(@ApiParam("评论id集合") @RequestBody BaseRequestParamVo baseRequestParamVo) {
        if (baseRequestParamVo.getIds()==null||baseRequestParamVo.getIds().size()==0){
            throw new ControllerException("请输入需要置顶的评论Id列表");
        }
        DmsNewsComment dmsNewsComment = new DmsNewsComment();
        dmsNewsComment.setIsTop(baseRequestParamVo.getStatus().intValue());
        dmsNewsComment.setLastUpdatedDate(new Date());
        dmsNewsComment.setLastUpdatedBy(LoginManager.getCurrentUserId());
        dmsNewsCommentService.updateByIdsSelective(dmsNewsComment,baseRequestParamVo.getIds());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "APP端查看新闻列表",response = JsonResult.class)
    @RequestMapping(value = "/newsListByApp", method = RequestMethod.GET)
    public Object newsListByApp(@ApiParam("分页页码")@RequestParam(value = "p",required = false) Integer p,
                                @ApiParam("每页条数")@RequestParam(value = "s",required = false) Integer s){
        DmsUser dmsUser = dmsUserService.selectByPrimaryKey(LoginManager.getCurrentUserId());
        if (BlankUtil.isEmpty(dmsUser)){
            throw new ControllerException("请先登录");
        }
        Long treeId = dmsTreeRelationService.getTreeRelationId(dmsUser);
        if (treeId==null){
            List<DmsNewsVo> result = Lists.newArrayList();
            return toResult(result);
        }else {
            PageHelper.Page<DmsNewsVo> pageList = dmsNewsService.selectNewsPageByUser(treeId,null, p, s);
            return toResult(pageList);
        }
    }
    @ResponseBody
    @ApiOperation(value = "APP端查看首页置顶的新闻列表",response = JsonResult.class)
    @RequestMapping(value = "/newsTopListByApp", method = RequestMethod.GET)
    public Object newsTopListByApp(@ApiParam("分页页码")@RequestParam(value = "p",required = false) Integer p,
                                   @ApiParam("每页条数")@RequestParam(value = "s",required = false) Integer s){
        DmsUser dmsUser = dmsUserService.selectByPrimaryKey(LoginManager.getCurrentUserId());
        if (BlankUtil.isEmpty(dmsUser)){
            throw new ControllerException("请先登录");
        }
        Long treeId = dmsTreeRelationService.getTreeRelationId(dmsUser);
        if (treeId==null){
            return toResult(null);
        }else {
            PageHelper.Page<DmsNewsVo> pageList = dmsNewsService.selectNewsPageByUser(treeId,1, p, s);
            return toResult(pageList);
        }
    }
}
