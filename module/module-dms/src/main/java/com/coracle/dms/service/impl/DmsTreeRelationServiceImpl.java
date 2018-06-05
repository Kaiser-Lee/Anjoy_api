package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsTreeRelationMapper;
import com.coracle.dms.po.DmsTreeRelation;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.service.DmsTreeRelationService;
import com.coracle.dms.vo.DmsTreeRelationVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsTreeRelationServiceImpl extends BaseServiceImpl<DmsTreeRelation> implements DmsTreeRelationService {
    private static final Logger logger = Logger.getLogger(DmsTreeRelationServiceImpl.class);

    @Autowired
    private DmsTreeRelationMapper dmsTreeRelationMapper;

    @Override
    public IMybatisDao<DmsTreeRelation> getBaseDao() {
        return dmsTreeRelationMapper;
    }

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
    public void addRelation(Integer relatedType, Long relatedId, Integer parRelatedType, Long parRelatedId, String name, Long userId, Integer type) {
        DmsTreeRelation dmsTreeRelation = new DmsTreeRelation();
        dmsTreeRelation.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsTreeRelation.setRelatedType(parRelatedType);
        dmsTreeRelation.setRelatedId(parRelatedId);
        List<DmsTreeRelation> dmsTreeRelations = dmsTreeRelationMapper.selectByCondition(dmsTreeRelation);
        if (BlankUtil.isEmpty(dmsTreeRelations) || dmsTreeRelations.size() > 1) {
            throw new ServiceException("父ID类型无法确定");
        }
        long parentId = dmsTreeRelations.get(0).getId();
        addRelation(relatedType, relatedId, name, parentId, userId, type);
    }

    /**
     *添加关联数据（不传入父ID，属于根节点类型）
     * @param relatedType
     * @param relatedId
     * @param name
     * @param userId
     * @param type
     */
    public void addRelation(Integer relatedType,Long relatedId,String name,Long userId,Integer type){
        addRelation(relatedType,relatedId,name,0L,userId,type);
    }

    /**
     * 添加关联数据
     *
     * @param relatedType 关联表类型
     * @param relatedId   关联表中对应的id
     * @param name        关联表中对应的名称（冗余）
     * @param parentId    父ID
     */
    public void addRelation(Integer relatedType, Long relatedId, String name, Long parentId, Long userId, Integer type) {
        if (relatedType == null || relatedId == null || parentId == null || userId == null || BlankUtil.isEmpty(name)) {
            throw new ServiceException("添加失败！");
        }
        if (type == null) {
            type = 0;
        }

        DmsTreeRelation dmsTreeRelation = new DmsTreeRelation();
        dmsTreeRelation.setName(name);
        dmsTreeRelation.setParentId(parentId);
        dmsTreeRelation.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsTreeRelation.setCreatedBy(userId);
        dmsTreeRelation.setCreatedDate(new Date());
        dmsTreeRelation.setLastUpdatedBy(userId);
        dmsTreeRelation.setLastUpdatedDate(new Date());
        dmsTreeRelation.setRelatedType(relatedType);
        dmsTreeRelation.setRelatedId(relatedId);
        dmsTreeRelation.setType(type);
        insert(dmsTreeRelation);

        //设置path的值
        if (parentId != null && parentId != 0) {
            DmsTreeRelation tr = dmsTreeRelationMapper.selectByPrimaryKey(parentId);
            dmsTreeRelation.setPath(tr.getPath() + dmsTreeRelation.getId() + ".");
        } else {
            dmsTreeRelation.setPath(dmsTreeRelation.getId() + ".");
        }
        dmsTreeRelationMapper.updateByPrimaryKey(dmsTreeRelation);

    }

    /**
     * 同步更新name
     * @param relatedType
     * @param relatedId
     * @param name
     * @param userId
     */
    public void updateName(Integer relatedType,Long relatedId,String name,Long userId){
        DmsTreeRelation dmsTreeRelation = new DmsTreeRelation();
        dmsTreeRelation.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsTreeRelation.setRelatedType(relatedType);
        dmsTreeRelation.setRelatedId(relatedId);
        List<DmsTreeRelation> dmsTreeRelations = dmsTreeRelationMapper.selectByCondition(dmsTreeRelation);
        if (BlankUtil.isEmpty(dmsTreeRelations)||dmsTreeRelations.size()>1){
            throw new ServiceException("关联数据无法确定");
        }
        DmsTreeRelation dtr = new DmsTreeRelation();
        dtr.setLastUpdatedDate(new Date());
        dtr.setLastUpdatedBy(userId);
        dtr.setId(dmsTreeRelations.get(0).getId());
        dtr.setName(name);
        dmsTreeRelationMapper.updateByPrimaryKeySelective(dtr);
    }

    /**
     * 修改树形结构信息
     *
     * @param relatedType
     * @param relatedId
     * @param parentRelatedType
     * @param parentRelatedId
     * @param name
     * @param userId
     * @param type
     */
    @Override
    public void updateRelation(Integer relatedType, Long relatedId, Integer parentRelatedType, Long parentRelatedId, String name, Long userId, Integer type) {
        //先删除掉原来的树形结构信息
        DmsTreeRelation tr = new DmsTreeRelation();
        tr.setRelatedType(relatedType);
        tr.setRelatedId(relatedId);
        tr.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        List<DmsTreeRelation> treeRelationList = dmsTreeRelationMapper.selectByCondition(tr);
        if ( treeRelationList.size() > 1) {
            throw new ServiceException("关联数据无法确定");
        } else {
            DmsTreeRelation treeRelation =treeRelationList.isEmpty()? new DmsTreeRelation(): treeRelationList.get(0);
            if (parentRelatedId != null && parentRelatedId != 0) {
              // DmsTreeRelation parent = dmsTreeRelationMapper.selectByPrimaryKey(parRelatedId);
                //2/27修改
                //DmsTreeRelation parent = dmsTreeRelationMapper.selectByRelatedId(parentRelatedId);

                DmsTreeRelation dmsTreeRelationCondition = new DmsTreeRelation();
                dmsTreeRelationCondition.setRelatedType(parentRelatedType);
                dmsTreeRelationCondition.setRelatedId(parentRelatedId);
                DmsTreeRelation parent = dmsTreeRelationMapper.selectOneByCondition(dmsTreeRelationCondition);
                if (parent != null) {
                    treeRelation.setParentId(parent.getId());
                    // 2/27修改
                    treeRelation.setPath(parent.getPath()+treeRelation.getId()+".");
                }
            } else {
                treeRelation.setParentId(0L);
                // 2/27修改
                treeRelation.setPath( treeRelation.getId() + ".");
            }

            treeRelation.setRelatedType(relatedType);
            treeRelation.setRelatedId(relatedId);
            treeRelation.setName(name);
            treeRelation.setLastUpdatedBy(userId);
            treeRelation.setLastUpdatedDate(new Date());
            treeRelation.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            if(treeRelationList.isEmpty()){
                treeRelation.setCreatedBy(userId);
                treeRelation.setCreatedDate(new Date());
                dmsTreeRelationMapper.insert(treeRelation);
            }else {
                dmsTreeRelationMapper.updateByPrimaryKey(treeRelation);
            }
        }
    }

    /**
     * 关联删除树关系
     * @param relatedType
     * @param relatedId
     */
    public void deleteRelation(Integer relatedType,Long relatedId){
        if (relatedType==null||relatedId==null){
            throw new ServiceException("请传入关联的信息！");
        }
        DmsTreeRelation dmsTreeRelation = new DmsTreeRelation();
        dmsTreeRelation.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.REMOVE.getType());
        dmsTreeRelation.setRelatedType(relatedType);
        dmsTreeRelation.setRelatedId(relatedId);
        dmsTreeRelationMapper.updateByRelation(dmsTreeRelation);
    }

    /**
     * 获取树形列表，通过想获取的数据类型来确定
     * @param dmsTreeRelationVo
     * @return
     */
    public List<DmsTreeRelation> getList(DmsTreeRelationVo dmsTreeRelationVo){
        List<DmsTreeRelation> list = new ArrayList<>();
        List<DmsTreeRelation> list1 = dmsTreeRelationMapper.getListByRelatedTypeList(dmsTreeRelationVo);
        for (DmsTreeRelation tree:list1){
            if (tree.getParentId()==0){
                DmsTreeRelation rootTree = dealChild(list1,tree);
                list.add(rootTree);
            }
        }
        return list;
    }

    private DmsTreeRelation dealChild(List<DmsTreeRelation> list,DmsTreeRelation tree){
        if (BlankUtil.isNotEmpty(list)){
            for(DmsTreeRelation tree1:list){
                if (tree.getId().longValue()==tree1.getParentId().longValue()){
                    DmsTreeRelation childTree = dealChild(list,tree1);
                    List<DmsTreeRelation> childList = tree.getDmsTreeRelationList();
                    if (BlankUtil.isEmpty(childList)) childList = new ArrayList<>();
                    childList.add(childTree);
                    tree.setDmsTreeRelationList(childList);
                }
            }
        }
        return tree;
    }

    /**
     * 获取treeID
     * @param dmsUser
     * @return
     */
    public Long getTreeRelationId(DmsUser dmsUser){
        if (BlankUtil.isEmpty(dmsUser)||dmsUser.getId()==null||dmsUser.getId()==0){
            throw new ServiceException("用户信息异常！");
        }
        Long treeId = null;
        if (dmsUser.getSource()==DmsModuleEnums.ACCOUNT_SOURCE_TYPE.CHANNEL_CONTACTS.getType()){//渠道用户
            treeId = dmsTreeRelationMapper.getTreeRelationIdByChannel(dmsUser.getId());
        }else if (dmsUser.getSource()==DmsModuleEnums.ACCOUNT_SOURCE_TYPE.SELLER.getType()){//门店用户
            treeId = dmsTreeRelationMapper.getTreeRelationIdBySeller(dmsUser.getId());
        }else if (dmsUser.getSource()==DmsModuleEnums.ACCOUNT_SOURCE_TYPE.EMPLOYEE.getType()){//系统用户，就是品牌商的用户
            //系统用户暂时未处理，因为一个用户会对应多个组织
        }
        return treeId;
    }

    public List<DmsTreeRelation> getListByIds(List<Long> ids){
        return dmsTreeRelationMapper.getListByIds(ids);
    }

    public List<TreeNode> selectByParentIdForNews(Long id){
        return dmsTreeRelationMapper.selectByParentIdForNews(id);
    }
    public List<Long> getAllIdsByNewsType(){
        return dmsTreeRelationMapper.getIdsByType();
    }

    /**
     * 获取区域、渠道、门店树形结构
     *
     * @return
     */
    @Override
    public List<TreeNode> getAreaChannelStoreTree() {
        return this.selectByParentId(0L, DmsModuleEnums.TREE_LEVEL_STORE);
    }

    /**
     * 获取区域、渠道树形结构
     *
     * @return
     */
    @Override
    public List<TreeNode> getAreaChannelTree() {




        return this.selectByParentId(0L, DmsModuleEnums.TREE_LEVEL_CHANNEL);
    }

    /**
     * 根据父节点id、层级等级 获取区域、渠道、门店树形结构
     *
     * @param rootId
     * @param level
     * @return
     */
    @Override
    public List<TreeNode> selectByParentId(Long rootId, String level) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("id", rootId);
        param.put("level", level);
        return dmsTreeRelationMapper.selectByParentId(param);
    }

}