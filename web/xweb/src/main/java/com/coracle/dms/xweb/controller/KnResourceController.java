package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsUser;
import com.coracle.dms.po.KnResource;
import com.coracle.dms.service.DmsUserService;
import com.coracle.dms.service.KnResourceService;
import com.coracle.dms.vo.DmsResourceVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.xframework.po.UserSession;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbaidong on 2017/3/22.
 */
@Controller
@RequestMapping(value = "/api/v1/mxm/knResource")
@Api(description = "Xsimple系统菜单资源接口")
public class KnResourceController extends BaseController {

    @Resource
    private KnResourceService knResourceService;

    @Resource
    private DmsUserService dmsUserService;

    @ResponseBody
    @ApiOperation(value = "菜单列表", response = KnResource.class)
    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public Object list() {
        Map<Long,List<DmsResourceVo>> map= Maps.newHashMap();
        UserSession userSession = LoginManager.getUserSession();
        //获取mxmId
        DmsUser dmsUser=dmsUserService.selectByPrimaryKey(userSession.getId());
        List<KnResource> resources = knResourceService.selectResourcesByUserId(dmsUser.getMxmId());
        for(KnResource kr : resources){
            List<DmsResourceVo> re=map.get(kr.getSupId());
            if(re==null){
                re= Lists.newArrayList();
            }
            DmsResourceVo resourceVo=new DmsResourceVo();
            resourceVo.setId(kr.getId());
            resourceVo.setCode(kr.getCode());
            resourceVo.setName(kr.getName());
            resourceVo.setIcon(kr.getIcon());
            resourceVo.setType(kr.getType());
            resourceVo.setUrl(kr.getUrl());
            resourceVo.setSupId(kr.getSupId());
            re.add(resourceVo);
            map.put(kr.getSupId(),re);
        }
        List<DmsResourceVo> list= makeResource(map,102L);
        Map<Long,DmsResourceVo> resourceVoMap=new HashMap<>();
        for(DmsResourceVo vo:list){
            resourceVoMap.put(vo.getId(),vo);
        }
        List<Long> resIds=new ArrayList<>();
        //判断有没有漏的
        for(Map.Entry entry:map.entrySet()){
            Long id=Long.parseLong(entry.getKey().toString());
            if(!resourceVoMap.containsKey(id)){
                resIds.add(id);
            }
        }
        //漏网之鱼
        if(!resIds.isEmpty()){
            List<KnResource> resourceList=knResourceService.selectResourcesByIds(resIds);
            //去重复
            for(KnResource kr:resourceList){
                List<DmsResourceVo> re=map.get(kr.getSupId());
                if(re==null){
                    re= Lists.newArrayList();
                }
                DmsResourceVo resourceVo=new DmsResourceVo();
                resourceVo.setId(kr.getId());
                resourceVo.setCode(kr.getCode());
                resourceVo.setName(kr.getName());
                resourceVo.setIcon(kr.getIcon());
                resourceVo.setType(kr.getType());
                resourceVo.setUrl(kr.getUrl());
                resourceVo.setSupId(kr.getSupId());
                if(!re.contains(resourceVo)) {
                    re.add(resourceVo);
                }
                map.put(kr.getSupId(),re);
            }
            list= makeResource(map,102L);
        }
        return toResult(list);
    }

    /*@ResponseBody
    @ApiOperation(value = "页面权限", response = KnResource.class)
    @RequestMapping(value = "/buttons", method = RequestMethod.POST)
    public Object buttons(@ApiParam("菜单ID") @RequestParam Long resId) {
        UserSession userSession = LoginManager.getUserSession();
        //获取mxmId
        DmsUser dmsUser=dmsUserService.selectByPrimaryKey(userSession.getId());
        List<String> buttons=knResourceService.selectButtonsByMxmIdAndResId(dmsUser.getMxmId(),resId);
        return toResult(buttons);
    }*/


    private List<DmsResourceVo> makeResource(Map<Long,List<DmsResourceVo>> map,Long supId){
        List<DmsResourceVo> res=Lists.newArrayList();
        List<DmsResourceVo> reList=map.get(supId);
        if(reList!=null){
            for(DmsResourceVo re : reList){
                re.setChildren(makeResource(map,re.getId()));
                res.add(re);
            }
        }
        return res;
    }
}
