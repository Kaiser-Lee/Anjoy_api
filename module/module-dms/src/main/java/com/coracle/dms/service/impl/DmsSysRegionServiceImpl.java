package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsSysRegionMapper;

import com.coracle.dms.po.DmsSysRegion;
import com.coracle.dms.service.DmsSysRegionService;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Maps;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DmsSysRegionServiceImpl extends BaseServiceImpl<DmsSysRegion> implements DmsSysRegionService {
    private static final Logger logger = Logger.getLogger(DmsSysRegionServiceImpl.class);

    @Autowired
    private DmsSysRegionMapper dmsSysRegionMapper;

    @Override
    public IMybatisDao<DmsSysRegion> getBaseDao() {
        return dmsSysRegionMapper;
    }

    @Override
    public PageHelper.Page<DmsSysRegion> findRegionPageList(DmsSysRegion dmsSysRegion) {
        try {
            PageHelper.startPage(dmsSysRegion.getP(), dmsSysRegion.getS());
            dmsSysRegionMapper.findRegionPageList(dmsSysRegion);
        } catch (Exception e) {
            System.out.println("分页查询异常" + e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }


    @Override
    public Integer insertByPo(DmsSysRegion dmsSysRegion) {
        dmsSysRegion.setDataNum(0);
        dmsSysRegionMapper.insert(dmsSysRegion);

        Integer parentId = dmsSysRegion.getParentRegionId();
        if (parentId == null || parentId == 0) {
            dmsSysRegion.setParentRegionId(1);
            dmsSysRegion.setRegionPath(dmsSysRegion.getRegionId() + "");
        } else {
            DmsSysRegion parentEntity = this.dmsSysRegionMapper.selectByPrimaryKey(parentId);
            if (parentEntity == null) {
                throw new ServiceException("获取上级数据为空！");
            }
            dmsSysRegion.setParentRegionId(parentId);
            dmsSysRegion.setRegionPath(parentEntity.getRegionPath() + "," + dmsSysRegion.getRegionId());
            dmsSysRegion.setFullPath(parentEntity.getFullPath() + "," + dmsSysRegion.getRegionName());
        }
        dmsSysRegionMapper.updateByPrimaryKeySelective(dmsSysRegion);
        return dmsSysRegion.getRegionId();
    }

    @Override
    public Integer updateByPo(DmsSysRegion dmsSysRegion) {

        Integer parentId = dmsSysRegion.getParentRegionId();
        if (parentId == null || parentId == 0) {
            dmsSysRegion.setParentRegionId(1);
            dmsSysRegion.setRegionPath(dmsSysRegion.getRegionId() + "");
        } else {
            DmsSysRegion parentEntity = this.dmsSysRegionMapper.selectByPrimaryKey(parentId);
            if (parentEntity == null) {
                throw new ServiceException("获取上级数据为空！");
            }
            dmsSysRegion.setParentRegionId(parentId);
            dmsSysRegion.setRegionPath(parentEntity.getRegionPath() + "," + dmsSysRegion.getRegionId());
            dmsSysRegion.setFullPath(parentEntity.getFullPath() + "," + dmsSysRegion.getRegionName());
        }
        dmsSysRegionMapper.updateByPrimaryKeySelective(dmsSysRegion);
        return dmsSysRegion.getRegionId();
    }

    @Override
    public List<DmsSysRegion> findRegionByParentId(Long parentId) {
        return dmsSysRegionMapper.findRegionByParentId(parentId);
    }

    /**
     * 通过名称，获取指定的地区Id
     */
    @Override
    public DmsSysRegion selectByName(String name) {
        return dmsSysRegionMapper.selectByName(name);
    }

    /**
     * 树形结构数据
     *
     * @param id
     * @return
     */
    @Override
    public List<TreeNode> tree(Long id) {
        if (id == null) {  //id为null时，默认查询根组织结构树
            id = 0L;
        }
        return dmsSysRegionMapper.selectByParentId(id);
    }

    /**
     * 根据类型获取地区名称-id映射集
     *
     * @param type
     * @return
     */
    @Override
    public Map<String, Long> getNameIdMap(String type) {
        List<DmsSysRegion> regionList = dmsSysRegionMapper.listByType(type);
        Map<String, Long> resultMap = Maps.newHashMap();
        if (regionList != null && regionList.size() > 0) {
            for (DmsSysRegion region : regionList) {
                resultMap.put(region.getRegionName(), region.getRegionId().longValue());
            }
        }
        return resultMap;
    }

    /**
     * 根据类型获取地区列表
     *
     * @param type
     * @return
     */
    public List<DmsSysRegion> listByType(String type) {
        return dmsSysRegionMapper.listByType(type);
    }
}