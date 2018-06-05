package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsOrganizationMapper;
import com.coracle.dms.po.DmsOrganization;
import com.coracle.dms.service.DmsOrganizationAreaService;
import com.coracle.dms.service.DmsOrganizationService;
import com.coracle.dms.service.DmsTreeRelationService;
import com.coracle.dms.vo.DmsOrganizationVo;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.yk.base.vo.TreeNodeVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiruo.medbid.components.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsOrganizationServiceImpl extends BaseServiceImpl<DmsOrganization> implements DmsOrganizationService {
    private static final Logger logger = Logger.getLogger(DmsOrganizationServiceImpl.class);

    @Autowired
    private DmsOrganizationMapper dmsOrganizationMapper;

    @Autowired
    private DmsOrganizationAreaService dmsOrganizationAreaService;

    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;

    @Override
    public IMybatisDao<DmsOrganization> getBaseDao() {
        return dmsOrganizationMapper;
    }

    /**
     * 新增/修改组织
     * @param organizationVo
     */
    @Override
    @Transactional
    public void insertOrUpdate(DmsOrganizationVo organizationVo) {
        checkParam(organizationVo);

        boolean isInsert = organizationVo.getId() == null ? true : false;
        if (isInsert) {  //新增操作
            int count = dmsOrganizationMapper.insert(organizationVo);
            if (count <= 0) {
                throw new ServiceException("新增组织失败!");
            }
            /* 设置组织路径、深度：路径、深度在修改时不会改变，所以只在新增处做处理 */
            Long parentId = organizationVo.getParentId();
            if (parentId == null || parentId == 0) {  //顶层组织
                organizationVo.setParentId(0L);
                organizationVo.setDepth(1L);
                organizationVo.setPath(organizationVo.getId() + ".");
                dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_ORGANIZATION.getType(), organizationVo.getId(), organizationVo.getName(), organizationVo.getCreatedBy(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_ORGANIZATION.getType());
            } else {
                DmsOrganization parentOrganization = dmsOrganizationMapper.selectByPrimaryKey(parentId);
                String path = parentOrganization.getPath();
                organizationVo.setDepth(parentOrganization.getDepth() + 1);  //层级深度=父层级深度+1
                organizationVo.setPath(path + organizationVo.getId() + ".");
                dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_ORGANIZATION.getType(), organizationVo.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_ORGANIZATION.getType(), organizationVo.getParentId(), organizationVo.getName(), organizationVo.getCreatedBy(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_ORGANIZATION.getType());
            }
        } else {  //修改操作
            Long organizationId = organizationVo.getId();
            DmsOrganization entity = dmsOrganizationMapper.selectByPrimaryKey(organizationId);
            if (entity == null) {
                throw new ServiceException("不存在id为" + organizationId + "的组织!");
            }
            dmsTreeRelationService.updateRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_ORGANIZATION.getType(), organizationVo.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_ORGANIZATION.getType(), organizationVo.getParentId(), organizationVo.getName(), organizationVo.getCreatedBy(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType());
        }
        dmsOrganizationMapper.updateByPrimaryKeySelective(organizationVo);  //保存修改

        /* 2.设置组织-区域管理关系 */
        dmsOrganizationAreaService.insertOrUpdate(organizationVo);
    }

    /**
     * 组织详情
     * @param id
     * @return
     */
    @Override
    public DmsOrganizationVo detail(Long id) {
        return dmsOrganizationMapper.selectVoByPrimaryKey(id);
    }

    /**
     * 组织架构树形结构数据
     * @param id
     * @return
     */
    @Override
    public List<TreeNodeVo> tree(Long id) {
        if (id == null) {  //id为null时，默认查询整个组织结构树
            id = 0L;
        }
        return dmsOrganizationMapper.selectByParentId(id);
    }

    /**
     * 逻辑删除组织
     * @param organization
     */
    @Override
    public void remove(DmsOrganization organization) {
        if (organization == null || organization.getId() == null) {
            throw new ServiceException("参数错误");
        }
        Long organizationId = organization.getId();
        DmsOrganization entity = dmsOrganizationMapper.selectByPrimaryKey(organizationId);
        if (entity == null) {
            throw new ServiceException("不存在id为" + organization.getId() + "的组织!");
        }

        /* 如果组织下存在自组织，则不允许删除 */
        Integer childCount = dmsOrganizationMapper.selectCountByParentId(organizationId);
        if (childCount > 0) {
            throw new ServiceException("该组织下存在子组织，不允许删除!");
        }

        dmsOrganizationMapper.removeByPrimaryKey(organizationId);  //逻辑删除组织信息
        dmsOrganizationAreaService.removeByOrganizationId(organizationId);  //逻辑删除组织-区域管理信息
    }

    /**
     * 获取根品牌商，但新增组织时未做限制
     * @return
     */
    public DmsOrganization getRootOrganization(){
        DmsOrganization dmsOrganization = new DmsOrganization();
        dmsOrganization.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsOrganization.setParentId(0L);
        dmsOrganization.setOrderField(" id ");
        dmsOrganization.setOrderString(" asc ");
        List<DmsOrganization> dmsOrganizationList = dmsOrganizationMapper.selectByCondition(dmsOrganization);
        if (BlankUtil.isEmpty(dmsOrganizationList)){
            throw new ServiceException("未查询到根组织");
        }
        return dmsOrganizationList.get(0);
    }

    /**
     * 参数检查
     * @param organization
     */
    private void checkParam(DmsOrganization organization) {
        if (organization == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(organization.getName())) {
            throw new ServiceException("组织名称不能为空");
        }
        if (StringUtils.isBlank(organization.getCode())) {
            throw new ServiceException("组织编码不能为空");
        }
        if (organization.getType() == null) {
            throw new ServiceException("组织类型不能为空");
        }
        if (organization.getActive() == null) {
            throw new ServiceException("有效性不能为空");
        }
    }

    /**
     * 返回 组织的anjoyId-组织实体 映射集
     *
     * @return
     */
    @Override
    public Map<String, DmsOrganization> getOrganizationMap() {
        DmsOrganization param = new DmsOrganization();
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        List<DmsOrganization> organizationList = dmsOrganizationMapper.selectByCondition(param);

        Map<String, DmsOrganization> orgMap = Maps.newHashMap();
        for (DmsOrganization org : organizationList) {
            orgMap.put(org.getAnjoyId(), org);
        }
        return orgMap;
    }

    /**
     * 同步安井组织信息
     * 调用安井的组织同步接口，返回JSON数组格式数据
     */
    @Override
    @Transactional(readOnly = false)
    public void anjoySyn() {
        logger.info("*************************** start 开始同步安井组织信息 ***************************");

        //调用安井的组织同步接口，返回JSON数组格式数据
        JSONArray jsonArray = AnjoySynClient.getAnjoySynDataBySQLType(AnjoySynClient.SYN_TYPE.ORG);
        List<DmsOrganization> organizationList = Lists.newArrayList();

        Map<String, DmsOrganization> orgMap = this.getOrganizationMap();
        List<String> nodeList = Lists.newLinkedList();
        //处理JSON数组，将组织JSON数组的数据插入到数据库中
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            String anjoyId = jsonObject.getString("FID");
            String anjoyParentId = jsonObject.getString("FPARENTID");

            boolean isInsert = !orgMap.containsKey(anjoyId);  //如果数据库中已存在该组织，则进行修改操作；否则，进行新增操作
            Date now = new Date();

            DmsOrganization organization;
            if (isInsert) {
                organization = new DmsOrganization();
                organization.setCreatedBy(0L);
                organization.setCreatedDate(now);
            } else {
                organization = orgMap.get(anjoyId);
                organization.setLastUpdatedBy(0L);
                organization.setLastUpdatedDate(now);
            }
            organization.setActive(jsonObject.getInteger("STATE"));
            organization.setCode(jsonObject.getString("FNUMBER"));
            organization.setName(jsonObject.getString("FNAME_L2"));
            organization.setAnjoyId(anjoyId);
            organization.setAnjoyParentId(anjoyParentId);
            organization.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

            //因为设置path、parentId等字段值的时候需要获取组织的id，所以新增的数据需要先插入
            //修改操作统一在下面的设置path、parentId等字段时再进行
            if (isInsert) {
                dmsOrganizationMapper.insert(organization);
            }

            //根节点的anjoyId
            if (anjoyParentId == null) {
                nodeList.add(anjoyId);
            }
        }

        //首先设置根节点的parentId、depth、path字段
        for (String anjoyId : nodeList) {
            DmsOrganization rootOrg = orgMap.get(anjoyId);
            rootOrg.setParentId(0L);
            rootOrg.setDepth(1L);
            rootOrg.setPath(rootOrg.getId() + ".");
        }

        //设置完根节点的parentId、depth、path字段后，再从上往下，按树形结构依次设置各节点的这几个字段的值
        while (nodeList.size() > 0) {
            List<String> tmpNodeList = Lists.newLinkedList();
            List<DmsOrganization> orgList = dmsOrganizationMapper.listByAnjoyParentId(nodeList);
            for (DmsOrganization o : orgList) {
                DmsOrganization org = orgMap.get(o.getAnjoyId());
                DmsOrganization parentOrg = orgMap.get(o.getAnjoyParentId());

                if (parentOrg != null) {
                    org.setParentId(parentOrg.getId());
                    if (parentOrg.getPath() != null) {
                        org.setPath(parentOrg.getPath() + org.getId() + ".");
                    }
                    if (parentOrg.getDepth() != null) {
                        org.setDepth(parentOrg.getDepth() + 1);
                    }
                    tmpNodeList.add(o.getAnjoyId());
                }
                logger.info("id:" + org.getId() + ",path:" + org.getPath() + ",depth:" + org.getDepth());
            }
            nodeList.clear();
            nodeList.addAll(tmpNodeList);
        }

        //修改操作
        for(DmsOrganization org : orgMap.values()) {
            dmsOrganizationMapper.updateByPrimaryKey(org);
        }

        logger.info("*************************** end 结束同步安井组织信息 ***************************");
    }

}