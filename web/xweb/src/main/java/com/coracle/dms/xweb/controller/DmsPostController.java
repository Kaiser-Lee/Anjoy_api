package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsPost;
import com.coracle.dms.service.DmsPostService;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.base.vo.TreeNodeVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(description = "岗位管理接口")
@RequestMapping("/api/v1/dms/post")
public class DmsPostController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsPostController.class);

    @Autowired
    private DmsPostService dmsPostService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "岗位列表", response = JsonResult.class)
    public Object pageList(@RequestBody DmsPost post) {
        PageHelper.Page<DmsPost> listPage = dmsPostService.pageList(post);
        return toResult(listPage);
    }

    @ResponseBody
    @ApiOperation(value = "岗位树形结构数据", response = TreeNodeVo.class)
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Object tree(@RequestParam Long id) {
        List<TreeNodeVo> postTree = dmsPostService.tree(id);
        return toResult(postTree);
    }

    @ResponseBody
    @ApiOperation(value = "新增岗位", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsPost post) {
        PoDefaultValueGenerator.putDefaultValue(post);
        dmsPostService.insertOrUpdate(post);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "修改岗位", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsPost post) {
        PoDefaultValueGenerator.putDefaultValue(post);
        dmsPostService.insertOrUpdate(post);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "岗位详情", response = DmsPost.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam("岗位id") @RequestParam Long id) {
        DmsPost post = dmsPostService.selectByPrimaryKey(id);
        if (post == null) {
            throw new ServiceException("无法获取id为" + id + "的岗位信息!");
        }
        return toResult(post);
    }

    @ResponseBody
    @ApiOperation(value = "删除岗位", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody DmsPost post) {
        dmsPostService.remove(post);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "获取子岗位列表", response = DmsPost.class)
    @RequestMapping(value = "/childList", method = RequestMethod.GET)
    public Object childList(@ApiParam("父岗位id") @RequestParam Long id) {
        DmsPost param = new DmsPost();
        param.setParentId(id);
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

        List<DmsPost> postList = dmsPostService.selectByCondition(param);
        return toResult(postList);
    }
}