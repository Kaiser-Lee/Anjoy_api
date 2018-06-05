package com.coracle.dms.xweb.controller;

import com.coracle.dms.service.DmsNoticeService;
import com.coracle.dms.vo.DmsNoticeVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/25.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/notice")
@Api(description = "DMS通知公告调用接口")
public class DmsNoticeController extends BaseController {
    @Autowired
    private DmsNoticeService dmsNoticeService;

    @ResponseBody
    @ApiOperation(value = "新增通知公告", response = JsonResult.class)
    @RequestMapping(value = "/addNotice", method = RequestMethod.POST)
    public Object addNotice(@RequestBody DmsNoticeVo dmsNoticeVo){
        PoDefaultValueGenerator.putDefaultValue(dmsNoticeVo);
        dmsNoticeVo.setPublishDate(new Date());
        dmsNoticeVo.setPublishUserId(LoginManager.getCurrentUserId());
        dmsNoticeService.insertNotice(dmsNoticeVo);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "通知公告列表", response = JsonResult.class)
    @RequestMapping(value = "/noticeList", method = RequestMethod.POST)
    public Object getNoticeList(@RequestBody DmsNoticeVo dmsNoticeVo){
        dmsNoticeVo.setRemoveFlag(0);
        if (BlankUtil.isNotEmpty(dmsNoticeVo.getStartDate())&&dmsNoticeVo.getStartDate().length()>=11){
            throw new ControllerException("请输入合适的查询日期");
        }else if (BlankUtil.isNotEmpty(dmsNoticeVo.getStartDate())&&dmsNoticeVo.getStartDate().length()<11){
            dmsNoticeVo.setStartDate(dmsNoticeVo.getStartDate() + " 00:00:00");
        }
        if (BlankUtil.isNotEmpty(dmsNoticeVo.getEndDate())&&dmsNoticeVo.getEndDate().length()>=11){
            throw new ControllerException("请输入合适的查询日期");
        }else if (BlankUtil.isNotEmpty(dmsNoticeVo.getEndDate())&&dmsNoticeVo.getEndDate().length()<11){
            dmsNoticeVo.setEndDate(dmsNoticeVo.getEndDate() + " 23:59:59");
        }
        dmsNoticeVo.setOrderField(" id ");
        dmsNoticeVo.setOrderString(" desc ");
        PageHelper.Page<DmsNoticeVo> pageList = dmsNoticeService.selectForPcList(dmsNoticeVo);
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "通知公告详情", response = JsonResult.class)
    @RequestMapping(value = "/noticeDetail", method = RequestMethod.GET)
    public Object noticeDetail(@ApiParam("通知公告id") @RequestParam Long id){
        if (BlankUtil.isEmpty(id)){
            throw new ControllerException("公告id不能为空");
        }
        return toResult(dmsNoticeService.getNoticeDetail(id));
    }
}
