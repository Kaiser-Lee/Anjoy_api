package com.coracle.dms.service;

import com.coracle.dms.po.DmsTreeRelation;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.vo.DmsTreeRelationVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsTreeRelationService extends IBaseService<DmsTreeRelation> {
    /**
     * 添加关联数据
     * @param relatedType
     * @param relatedId
     * @param parRelatedType 直接传入前台传入的id和类型，由这里确认父ID
     * @param parRelatedId
     * @param name
     * @param userId
     * @param type
     */
    void addRelation(Integer relatedType,Long relatedId,Integer parRelatedType,Long parRelatedId,String name,Long userId,Integer type);

    /**
     *添加关联数据（不传入父ID，属于跟节点类型）
     * @param relatedType
     * @param relatedId
     * @param name
     * @param userId
     * @param type
     */
    void addRelation(Integer relatedType,Long relatedId,String name,Long userId,Integer type);

    /**
     * 添加关联数据
     * @param relatedType 关联表类型
     * @param relatedId 关联表中对应的id
     * @param name 关联表中对应的名称（冗余）
     * @param parentId 父ID
     */
    void addRelation(Integer relatedType, Long relatedId, String name, Long parentId, Long userId, Integer type);

    /**
     * 同步更新name
     * @param relatedType
     * @param relatedId
     * @param name
     * @param userId
     */
    void updateName(Integer relatedType,Long relatedId,String name,Long userId);

    void updateRelation(Integer relatedType, Long relatedId, Integer parRelatedType, Long parRelatedId, String name, Long userId, Integer type);

    /**
     * 关联删除树关系
     * @param relatedType
     * @param relatedId
     */
    void deleteRelation(Integer relatedType,Long relatedId);

    /**
     * 获取列表
     * @param dmsTreeRelationVo
     * @return
     */
    List<DmsTreeRelation> getList(DmsTreeRelationVo dmsTreeRelationVo);

    /**
     * 获取treeID
     * @param dmsUser
     * @return
     */
    Long getTreeRelationId(DmsUser dmsUser);

    List<DmsTreeRelation> getListByIds(List<Long> ids);

    List<TreeNode> selectByParentIdForNews(Long id);

    List<Long> getAllIdsByNewsType();

    /**
     * 获取区域、渠道、门店树形结构
     *
     * @return
     */
    List<TreeNode> getAreaChannelStoreTree();

    List<TreeNode> getAreaChannelTree();

    List<TreeNode> selectByParentId(Long rootId, String level);
}