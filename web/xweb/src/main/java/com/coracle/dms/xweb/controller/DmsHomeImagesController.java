package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsHomeImages;
import com.coracle.dms.service.DmsHomeImagesService;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
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

@Controller
@RequestMapping(value = "/api/v1/dms/index/images")
@Api(description = "首页轮播图设置接口")
public class DmsHomeImagesController extends BaseController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private DmsHomeImagesService dmsHomeImagesService;

    /**
     * 获取首页轮播图片集合
     */
    @ResponseBody
    @ApiOperation(value = "获取首页轮播图片列表接口",response = JsonResult.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(DmsHomeImages dmsHomeImages){
        dmsHomeImages.setImageType(DmsModuleEnums.HOME_IMAGES_TYPE.BANNER.getType());
        List<Map<String,Object>> resultList=dmsHomeImagesService.getList(dmsHomeImages);
        return toResult(resultList);
    }

    /**
     * 保存首页轮播图片
     */
    @ResponseBody
    @ApiOperation(value = "保存首页轮播图片列表接口",response = JsonResult.class)
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Object create(@ApiParam("首页轮播图片实体集合")@RequestBody List<DmsHomeImages> indexImages){
        dmsHomeImagesService.save(indexImages,LoginManager.getCurrentUserId());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "删除首页轮播图片接口",response = JsonResult.class)
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Object deleteBanner(@ApiParam("轮播图片id")@RequestParam Long id){
        DmsHomeImages dmsHomeImages = dmsHomeImagesService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsHomeImages)) {
            throw new ControllerException("id为【"+id+"】的轮播图片不存在");
        }
        if (dmsHomeImages.getRemoveFlag()==DmsModuleEnums.REMOVE_FLAG_STATUS.REMOVE.getType()){
            throw new ControllerException("id为【"+id+"】的轮播图片已删除");
        }
        DmsHomeImages dmsHomeImages1 = new DmsHomeImages();
        dmsHomeImages1.setId(dmsHomeImages.getId());
        dmsHomeImages1.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.REMOVE.getType());
        dmsHomeImages1.setLastUpdatedBy(LoginManager.getCurrentUserId());
        dmsHomeImages1.setLastUpdatedDate(new Date());
        dmsHomeImagesService.updateByPrimaryKeySelective(dmsHomeImages1);
        return toResult();
    }


}
