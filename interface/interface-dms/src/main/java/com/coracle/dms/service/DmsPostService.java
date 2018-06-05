package com.coracle.dms.service;

import com.coracle.dms.po.DmsPost;
import com.coracle.yk.base.vo.TreeNodeVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsPostService extends IBaseService<DmsPost> {

    /**
     * 新增/修改岗位
     * @param post
     */
    void insertOrUpdate(DmsPost post);

    PageHelper.Page<DmsPost> pageList(DmsPost post);

    List<TreeNodeVo> tree(Long id);


    /**
     * 逻辑删除岗位
     * @param post
     */
    void remove(DmsPost post);
}