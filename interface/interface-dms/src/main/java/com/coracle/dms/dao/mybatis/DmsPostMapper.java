/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsPost;
import com.coracle.yk.base.vo.TreeNodeVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsPostMapper extends IMybatisDao<DmsPost> {


    /**
     * 根据主键id逻辑删除组织
     * @param id
     */
    void removeByPrimaryKey(Long id);

    /**
     * 获取岗位的树形结构
     * @param id
     * @return
     */
    List<TreeNodeVo> selectByParentId(Long id);

    /**
     * 根据父岗位id获取子岗位id
     * @param parentId
     * @return
     */
    Integer selectCountByParentId(Long parentId);
}