package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsSysAreaMapper;
import com.coracle.dms.po.DmsSysArea;
import com.coracle.dms.service.DmsSysAreaService;
import com.coracle.dms.service.DmsTreeRelationService;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DmsSysAreaServiceImpl extends BaseServiceImpl<DmsSysArea> implements DmsSysAreaService {
    private static final Logger logger = Logger.getLogger(DmsSysAreaServiceImpl.class);

    @Autowired
    private DmsSysAreaMapper dmsSysAreaMapper;

    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;

    @Override
    public IMybatisDao<DmsSysArea> getBaseDao() {
        return dmsSysAreaMapper;
    }


    @Override
    public Long insertByPo(DmsSysArea dmsSysArea, UserSession session) {
        Long userId = session.getId();

        dmsSysAreaMapper.insert(dmsSysArea);
        Long parentId = dmsSysArea.getParentAreaId();
        if (parentId == null || parentId == 0) {
            dmsSysArea.setParentAreaId(0L);
            dmsSysArea.setAreaPath(dmsSysArea.getId() + "");
            dmsSysArea.setFullPath(dmsSysArea.getName() + "");
        } else {
            DmsSysArea parentEntity = this.dmsSysAreaMapper.selectByPrimaryKey(parentId);
            if (parentEntity == null) {
                throw new ServiceException("获取上级数据为空！");
            }
            dmsSysArea.setParentAreaId(parentId);
            dmsSysArea.setAreaPath(parentEntity.getAreaPath()+ "," + dmsSysArea.getId());
            dmsSysArea.setFullPath(parentEntity.getFullPath() + "," + dmsSysArea.getName());
        }
        dmsSysAreaMapper.updateByPrimaryKeySelective(dmsSysArea);

        //往dms_tree_relation表里面插入数据
        if (dmsSysArea.getParentAreaId() == 0) {
            dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), dmsSysArea.getId(), dmsSysArea.getName(), session.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType());
        } else {
            dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), dmsSysArea.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), dmsSysArea.getParentAreaId(), dmsSysArea.getName(), userId, DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType());
        }

        return dmsSysArea.getId();
    }

    @Override
    public PageHelper.Page<DmsSysArea> findAreaPageList(DmsSysArea dmsSysArea) {
        try {
            PageHelper.startPage(dmsSysArea.getP(),dmsSysArea.getS());
            dmsSysAreaMapper.findAreaPageList(dmsSysArea);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 树形结构数据
     * @param id
     * @return
     */
    @Override
    public List<TreeNode> tree(Long id) {
        if (id == null) {  //id为null时，默认查询整个组织结构树
            id = 0L;
        }
        return dmsSysAreaMapper.selectByParentId(id);
    }

    /**
     * 返回区域code-id映射集
     *
     * @return
     */
    @Override
    public Map<String, Long> getCodeIdMap() {
        Map<String, Long> resultMap = Maps.newHashMap();

        DmsSysArea param = new DmsSysArea();
        param.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
        List<DmsSysArea> areaList = dmsSysAreaMapper.selectByCondition(param);
        for (DmsSysArea area : areaList) {
            resultMap.put(area.getAreaCode(), area.getId());
        }
        return resultMap;
    }

    @Override
    public List<DmsSysArea> findAreaByParentId(Long parentId) {
        return dmsSysAreaMapper.findAreaByParentId(parentId);
    }

    @Override
    public DmsSysArea selectByName(String name) {
        return dmsSysAreaMapper.selectByName(name);
    }

    @Override
    public DmsSysArea selectById(Long id) {
        return dmsSysAreaMapper.selectById(id);
    }

    @Override
    public Integer findChildById(Long id){
        return dmsSysAreaMapper.findChildById(id);
    }

    @Override
    public void anjoySync() {
        //调用安井的区域同步接口，返回JSON数组格式数据
        JSONArray jsonArray = AnjoySynClient.getAnjoySynDataBySQLType(AnjoySynClient.SYN_TYPE.AREA);
        List<DmsSysArea> areaList = Lists.newArrayList();

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            DmsSysArea area = new DmsSysArea();
            area.setAnjoyId(jsonObject.getString("FID"));
            area.setName(jsonObject.getString("FNAME_L2"));
            area.setAreaCode(jsonObject.getString("FNUMBER"));
            area.setActive(1);
            area.setParentAreaId(0L);
            DmsSysArea sysArea=dmsSysAreaMapper.selectByAreaCode(area.getAreaCode());
            if(sysArea==null){
                dmsSysAreaMapper.insert(area);
            }else {
                area.setId(sysArea.getId());
                dmsSysAreaMapper.updateByPrimaryKeySelective(area);
            }
        }
        DmsSysArea sysArea=new DmsSysArea();
        sysArea.setActive(1);
        areaList=dmsSysAreaMapper.selectByCondition(sysArea);
        for(DmsSysArea area:areaList){
            area.setAreaPath(area.getId()+"");
            area.setFullPath(area.getName());
            dmsSysAreaMapper.updateByPrimaryKeySelective(area);
        }

    }
}