/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsOrganization;
import com.coracle.dms.vo.DmsOrganizationVo;
import com.coracle.yk.base.vo.TreeNodeVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsOrganizationMapper extends IMybatisDao<DmsOrganization> {

    /**
     * 根据主键id获取组织vo
     * @param id
     * @return
     */
    DmsOrganizationVo selectVoByPrimaryKey(Long id);

    /**
     * 根据组织id获取组织的树形结构
     * @param id
     * @return
     */
    List<TreeNodeVo> selectByParentId(Long id);

    /**
     * 根据主键id逻辑删除组织
     * @param id
     */
    void removeByPrimaryKey(Long id);

    /**
     * 根据父id获取子组织
     * @param parentId
     * @return
     */
    Integer selectCountByParentId(Long parentId);

    /**
     * 批量插入
     *
     * @param organization1List
     */
    void batchInsert(List<DmsOrganization> organization1List);

    /**
     * 根据安井的父id获取组织信息
     *
     * @param list
     * @return
     */
    List<DmsOrganization> listByAnjoyParentId(List<String> list);
}