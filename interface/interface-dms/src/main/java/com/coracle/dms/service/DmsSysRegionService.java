package com.coracle.dms.service;

import com.coracle.dms.po.DmsSysRegion;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsSysRegionService extends IBaseService<DmsSysRegion> {

    /***
     *地区列表查询
     * @return
     */
    public PageHelper.Page<DmsSysRegion> findRegionPageList(DmsSysRegion dmsSysRegion);

    /**
     *
     * 插入对象，并返回Id
     */
    Integer insertByPo(DmsSysRegion dmsSysRegion);

    /**
     *
     * 插入对象，并返回Id
     */
    Integer updateByPo(DmsSysRegion dmsSysRegion);

    /**
     *
     * 通过parentId，获取指定的区域集合
     */
    List<DmsSysRegion> findRegionByParentId(Long parentId);

    /**
     *
     * 通过名称，获取指定的地区Id
     */
    DmsSysRegion selectByName(String name);

    /***
     * 通过parentId,区域树查询--查询下面节点列表
     * @return
     */

    List<TreeNode> tree(Long id);

    Map<String, Long> getNameIdMap(String type);
}