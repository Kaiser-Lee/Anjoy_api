package com.coracle.dms.service;

import com.coracle.dms.po.DmsSysArea;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsSysAreaService extends IBaseService<DmsSysArea> {

    /**
     *
     * 插入对象，并返回Id
     */
    Long insertByPo(DmsSysArea dmsSysArea, UserSession session);

    /***
     * 区域列表查询
     * @return
     */
    public PageHelper.Page<DmsSysArea> findAreaPageList(DmsSysArea dmsSysArea);


    /***
     * 通过parentId,区域树查询--查询下面所有节点列表
     * @return
     */

    List<TreeNode> tree(Long id);

    Map<String, Long> getCodeIdMap();

    /**
     *
     * 通过parentId，区域树查询--只查下一级节点列表
     */
    List<DmsSysArea> findAreaByParentId(Long parentId);

    /**
     *
     * 通过名称，获取指定的区域Id
     */
    DmsSysArea selectByName(String name);

    /**
     *
     * 通过id，获取指定的区域
     */
    DmsSysArea selectById(Long id);

    /**
     *
     * 通过id，获取下面是否存在子节点
     */
    Integer findChildById(Long id);


    void anjoySync();
}