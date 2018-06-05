package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsTreeRelation;
import com.coracle.dms.service.DmsTreeRelationService;
import com.coracle.yk.base.vo.TreeNode;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
@Controller
@RequestMapping(value = "/api/v1/dms/treeRelation")
@Api(description = "渠道，门店，组织接口结构")
public class DmsTreeRelationController extends BaseController {
    private static  final Logger logger = LoggerFactory.getLogger(DmsTreeRelationController.class);

    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;

    @ResponseBody
    @ApiOperation(value = "获取资讯发布的范围接口",response = DmsTreeRelation.class)
    @RequestMapping(value = "/infoPublishList", method = RequestMethod.GET)
    public Object infoPublishList() {
        return toResult(dmsTreeRelationService.selectByParentIdForNews(0L));
    }

    @ResponseBody
    @ApiOperation(value = "区域、渠道、门店树形结构",response = TreeNode.class)
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Object tree() {
        return toResult(dmsTreeRelationService.getAreaChannelStoreTree());
    }

    @ResponseBody
    @ApiOperation(value = "区域、渠道树形结构",response = TreeNode.class)
    @RequestMapping(value = "/areaStoreTree", method = RequestMethod.GET)
    public Object areaStoreTree() {
        Long time1 =System.currentTimeMillis();
        List<TreeNode>  treeList = dmsTreeRelationService.getAreaChannelTree();
        Long time2 = System.currentTimeMillis();
        logger.info("***************渠道树形结构耗时:{}**************",(time2-time1)+"ms");
        return toResult(treeList);


    }
}
