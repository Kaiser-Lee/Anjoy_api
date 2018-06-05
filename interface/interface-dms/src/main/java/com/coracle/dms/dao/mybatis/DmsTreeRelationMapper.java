/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsTreeRelation;
import com.coracle.dms.vo.DmsTreeRelationVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DmsTreeRelationMapper extends IMybatisDao<DmsTreeRelation> {
    /**
     * 更新记录
     * @param dmsTreeRelation
     */
    void updateByRelation(DmsTreeRelation dmsTreeRelation);

    /**
     * 获取列表
     * @param dmsTreeRelationVo
     * @return
     */
    List<DmsTreeRelation> getListByRelatedTypeList(DmsTreeRelationVo dmsTreeRelationVo);

    /**
     * 渠道联系人用户对应表中dms_tree_relation的id
     * @param id
     * @return
     */
    Long getTreeRelationIdByChannel(Long id);
    /**
     * 门店联系人用户对应表中dms_tree_relation的id
     * @param id
     * @return
     */
    Long getTreeRelationIdBySeller(Long id);

    /**
     * 根据id列表获取列表
     * @param ids
     * @return
     */
    List<DmsTreeRelation> getListByIds(@Param("ids") List<Long> ids);

    List<TreeNode> selectByParentIdForNews(Long id);

    List<Long> getIdsByType();

    /**
     * 根据父id获取区域、渠道、门店树形结构
     *
     * @param rootId
     * @return
     */
    List<TreeNode> getAreaChannelStoreTree(Long rootId);

    /**
     * 根据父id获取区域、渠道树形结构
     *
     * @param rootId
     * @return
     */
    List<TreeNode> getAreaChannelTree(Long rootId);

    List<TreeNode> selectByParentId(Map<String, Object> param);

    DmsTreeRelation selectByRelatedId(Long relatedId);

    DmsTreeRelation selectChannelByRelatedId(Long relatedId);

    DmsTreeRelation selectOneByCondition(DmsTreeRelation dmsTreeRelation);



}