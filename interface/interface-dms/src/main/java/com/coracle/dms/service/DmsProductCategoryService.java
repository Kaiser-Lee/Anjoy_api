package com.coracle.dms.service;

import java.util.List;
import java.util.Map;

import com.coracle.dms.po.DmsProductCategory;
import com.coracle.dms.vo.DmsProductCategoryVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsProductCategoryService extends IBaseService<DmsProductCategory> {

    /**
     * 查询产品分类列表
     * @param category
     * @return
     */
    PageHelper.Page<DmsProductCategory> selectForListPage(DmsProductCategory search);
    
    /**
     * 产品分类树查询
     * @param cid
     * @return
     */
    List<TreeNode> selectRecursiveTree(Long cid, UserSession userSession);
    
    /**
     * 查询产品分类详情
     * @param id
     * @return
     */
    DmsProductCategoryVo detail(Long id);

    /**
     * 保存产品分类
     * @param category
     */
    void create(DmsProductCategory param);
    
    /**
     * 修改产品分类
     * @param category
     */
    void update(DmsProductCategory param);
    
    /**
     * 删除产品分类
     * @param category
     */
    void delete(DmsProductCategory param);
    /**
     * 修改产品分类为失效
     * @param category
     */
    void updateActive(DmsProductCategory param);
    
    Integer getLevelMax();

    Map<String, DmsProductCategory> getProductCategoryMap();

    void anjoySyn();


    List<DmsProductCategory> listByParentId(List<Long> indexList);

    List<DmsProductCategory> selectSon();
}