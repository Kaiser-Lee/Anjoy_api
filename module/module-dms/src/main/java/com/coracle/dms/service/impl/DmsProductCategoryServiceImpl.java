package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsChannelMapper;
import com.coracle.dms.dao.mybatis.DmsProductCategoryMapper;
import com.coracle.dms.dao.mybatis.DmsProductMapper;
import com.coracle.dms.po.DmsCommonAttachFile;
import com.coracle.dms.po.DmsProductCategory;
import com.coracle.dms.service.DmsCommonAttachFileService;
import com.coracle.dms.service.DmsProductCategoryService;
import com.coracle.dms.vo.DmsProductCategoryVo;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Maps;
import com.xiruo.medbid.components.StringUtils;
import com.xiruo.medbid.util.BeanConvertHelper;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DmsProductCategoryServiceImpl extends BaseServiceImpl<DmsProductCategory> implements DmsProductCategoryService {
    private static final Logger logger = LoggerFactory.getLogger(DmsProductCategoryServiceImpl.class);

    @Autowired
    private DmsProductCategoryMapper dmsProductCategoryMapper;
    @Autowired
    private DmsCommonAttachFileService dmsCommonAttachFileService;
    @Autowired
    private DmsProductMapper dmsProductMapper;
    @Autowired
    private DmsChannelMapper dmsChannelMapper;

    @Override
    public IMybatisDao<DmsProductCategory> getBaseDao() {
        return dmsProductCategoryMapper;
    }

    /**
     * 递归算法解析成树形结构
     *
     * @param cid
     * @return
     * @author tanyb
     */
    public List<TreeNode> selectRecursiveTree(Long cid, UserSession userSession) {
        if (cid == null) {
            cid = 0L;
        }

        return this.dmsProductCategoryMapper.selectByParentId(cid);
    }

    @Override
    public DmsProductCategoryVo detail(Long id) {
        if (id == null) {
            throw new ServiceException("参数错误");
        }
        DmsProductCategory entity = this.dmsProductCategoryMapper.selectByPrimaryKey(id);
        if (entity == null) {
            throw new ServiceException("查询产品分类为空！");
        }
        DmsProductCategoryVo entityVo = BeanConvertHelper.copyProperties(entity, DmsProductCategoryVo.class);
        if (entity.getAttachId() != null && entity.getAttachId() != 0) {
            DmsCommonAttachFile dmsCommonAttachFile = dmsCommonAttachFileService.selectByPrimaryKey(entity.getAttachId());
            entityVo.setDmsCommonAttachFile(dmsCommonAttachFile);
        }
        return entityVo;
    }

    /**
     * 产品分类分页查询
     *
     * @param category
     * @return
     */
    @SuppressWarnings("unchecked")
    public PageHelper.Page<DmsProductCategory> selectForListPage(DmsProductCategory category) {
        try {
            PageHelper.startPage(category.getP(), category.getS());
            dmsProductCategoryMapper.selectByCondition(category);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            throw new ServiceException("产品分类分页查询异常--->>>");
        }
    }

    /**
     * 保存产品分类
     *
     * @param category
     */
    @Override
    public void create(DmsProductCategory category) {
        this.checkParam(category);
        this.dmsProductCategoryMapper.insert(category);
        Long parentId = category.getParentId();
        if (parentId == null || parentId == 0) {
            category.setParentId(1L);
            category.setLevel(1);
            category.setPath(category.getId() + "");
            category.setFullPath(category.getName());
        } else {
            DmsProductCategory parentEntity = this.dmsProductCategoryMapper.selectByPrimaryKey(parentId);
            if (parentEntity == null) {
                throw new ServiceException("获取上级数据为空！");
            }
            category.setParentId(parentId);
            category.setLevel(parentEntity.getLevel() + 1);
            category.setPath(parentEntity.getPath() + "," + category.getId());
            category.setFullPath(parentEntity.getFullPath() + "," + category.getName());
        }
        this.dmsProductCategoryMapper.updateByPrimaryKeySelective(category);
    }

    /**
     * 修改分类
     */
    @Override
    public void update(DmsProductCategory category) {
        this.checkParam(category);
        if (category == null || category.getId() == null) {
            throw new ServiceException("参数异常");
        }
        DmsProductCategory entity = this.dmsProductCategoryMapper.selectByPrimaryKey(category.getId());
        if (entity == null) {
            throw new ServiceException("数据库获取分类为空，不能修改！");
        }
        if (ObjectUtils.notEqual(entity.getCode(), category.getCode())) {
            throw new ServiceException("分类编号不能修改");
        }
        //如果分类名称修改需要同步路径名称
        this.updateCategoryName(entity, category);
        this.dmsProductCategoryMapper.updateByPrimaryKeySelective(category);
        Long parentId = category.getParentId();
        if (ObjectUtils.notEqual(category.getParentId(), entity.getParentId())) {
            if (category.getParentId() == null || category.getParentId() == 0) {
                category.setParentId(1L);
                category.setLevel(1);
                category.setPath(category.getId() + "");
                category.setFullPath(category.getName());
            } else {
                DmsProductCategory parentEntity = this.dmsProductCategoryMapper.selectByPrimaryKey(parentId);
                if (parentEntity == null) {
                    throw new ServiceException("获取上级数据为空！");
                }
                category.setParentId(parentId);
                category.setLevel(parentEntity.getLevel() + 1);
                category.setPath(parentEntity.getPath() + "," + category.getId());
                category.setFullPath(parentEntity.getFullPath() + "," + category.getName());
            }
            this.dmsProductCategoryMapper.updateByPrimaryKeySelective(category);
        }
    }

    private void updateCategoryName(DmsProductCategory entity, DmsProductCategory category) {
        if (ObjectUtils.notEqual(entity.getName(), category.getName())) {
            List<DmsProductCategory> pathList = this.dmsProductCategoryMapper.selectByPathList(category.getId());
            if (BlankUtil.isNotEmpty(pathList)) {
                for (DmsProductCategory pathEntity : pathList) {
                    String newFullPath = "";
                    String fullPath = pathEntity.getFullPath();
                    if (BlankUtil.isNotEmpty(fullPath)) {
                        String[] fullPathArray = fullPath.split(",");
                        for (String fullPathT : fullPathArray) {
                            if (fullPathT.equals(entity.getName())) {
                                newFullPath += category.getName() + ",";
                            } else {
                                newFullPath += fullPathT + ",";
                            }
                        }
                    }
                    DmsProductCategory paramEntity = new DmsProductCategory();
                    paramEntity.setId(pathEntity.getId());
                    paramEntity.setFullPath(newFullPath.substring(0, newFullPath.lastIndexOf(",")));
                    this.dmsProductCategoryMapper.updateByPrimaryKeySelective(paramEntity);
                }
            }
        }
    }

    /**
     * 统一检验参数
     *
     * @param category
     */
    private void checkParam(DmsProductCategory category) {
        if (category == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(category.getName())) {
            throw new ServiceException("分类名称不能为空");
        }
        if (StringUtils.isBlank(category.getCode())) {
            throw new ServiceException("分类编号不能为空");
        }
        if (category.getActive() == null) {
            throw new ServiceException("有效性不能为空");
        }
    }

    @Override
    public void delete(DmsProductCategory category) {
        if (category == null || category.getId() == null) {
            throw new ServiceException("参数错误");
        }
        DmsProductCategory entity = this.dmsProductCategoryMapper.selectByPrimaryKey(category.getId());
        if (entity == null) {
            throw new ServiceException("数据库获取分类为空！");
        }
        List<DmsProductCategory> parentList = this.dmsProductCategoryMapper.selectByPid(category.getId());
        if (BlankUtil.isNotEmpty(parentList)) {
            throw new ServiceException("该分类存在子类不能删除！");
        }
        this.dmsProductCategoryMapper.deleteByIdSoft(category.getId());
    }

    /**
     * 修改产品分类失效 update单独出来修改不然会影响效率
     */
    @Override
    public void updateActive(DmsProductCategory category) {
        if (category == null || category.getId() == null) {
            throw new ServiceException("参数错误");
        }
        DmsProductCategory entity = this.dmsProductCategoryMapper.selectByPrimaryKey(category.getId());
        if (entity == null) {
            throw new ServiceException("数据库获取分类为空！");
        }
        List<DmsProductCategory> parentList = this.dmsProductCategoryMapper.selectByPid(category.getId());
        if (BlankUtil.isNotEmpty(parentList)) {
            throw new ServiceException("该分类存在子类不能失效！");
        }
        category.setLastUpdatedDate(new Date());
        this.dmsProductCategoryMapper.updateInvalidCategory(category);
    }

    @Override
    public Integer getLevelMax() {
        return this.dmsProductCategoryMapper.getLevelMax();
    }

    /**
     * 返回 产品分类的anjoyId-产品分类实体 映射集
     *
     * @return
     */
    @Override
    public Map<String, DmsProductCategory> getProductCategoryMap() {
        DmsProductCategory param = new DmsProductCategory();
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        List<DmsProductCategory> productCategoryList = dmsProductCategoryMapper.selectByCondition(param);

        Map<String, DmsProductCategory> productCategoryMap = Maps.newHashMap();
        for (DmsProductCategory pc : productCategoryList) {
            productCategoryMap.put(pc.getAnjoyId(), pc);
        }
        return productCategoryMap;
    }

    /**
     * 同步安井产品类别信息
     * 调用安井的产品类别同步接口，返回JSON数组格式数据
     */
    @Override
    @Transactional(readOnly = false)
    public void anjoySyn() {
        logger.info("*************************** start 开始同步安井-产品分类数据 ***************************");
        JSONArray jsonArray = AnjoySynClient.getAnjoySynDataBySQLType(AnjoySynClient.SYN_TYPE.PRODUCT_CATEGORY);
        if(jsonArray == null || jsonArray.size() <= 0){
            logger.info("***************** 没有安井-产品分类数据返回 *****************");
            return;
        }

        /**
         * 同步安井-产品分类据前先将DMS所有产品分类数据置为：已删除状态
         */
        Integer batchDeleteResult = dmsProductCategoryMapper.deleteProductCategorySyncAnjoy();
        logger.info("DMD，共删除：{} 条产品分类数据", batchDeleteResult);

        Date currentDate = new Date();
        Set<String> rootSet = new LinkedHashSet<>();
        List<DmsProductCategory> productCategoryList = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            DmsProductCategory productCategory = new DmsProductCategory();
            String fid = jsonObject.getString("FID");//分类ID
            String fstatus = jsonObject.getString("FDELETEDSTATUS");//分类状态
            String frootName = jsonObject.getString("FDESCRIPTION_L2");//大类名称
            String fparentid = jsonObject.getString("FPARENTID");//分类名称

            rootSet.add(frootName);

            productCategory.setRootName(frootName);
            productCategory.setName(fparentid);
            productCategory.setLevel(2);
            productCategory.setAnjoyId(fid);

            productCategory.setCreatedBy(0L);
            productCategory.setCreatedDate(currentDate);
            productCategory.setLastUpdatedBy(0L);
            productCategory.setLastUpdatedDate(currentDate);
            productCategory.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
            productCategory.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

            productCategoryList.add(productCategory);
        }

        String keyMianmi = "面米";//放在第二位
        String keyOther = "其他";//放在最后一位

        if(rootSet.contains(keyMianmi)){
            rootSet.remove(keyMianmi);
            rootSet.add(keyMianmi);
        }
        if(rootSet.contains(keyOther)){
            rootSet.remove(keyOther);
            rootSet.add(keyOther);
        }

        String splitChar = ".";
        List<DmsProductCategory> rootProductCategoryList = new ArrayList();
        for (String rootName : rootSet){
            DmsProductCategory productCategoryCondition = new DmsProductCategory();
            productCategoryCondition.setLevel(1);
            productCategoryCondition.setName(rootName);
            //productCategoryCondition.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            DmsProductCategory rootProductCategory = dmsProductCategoryMapper.selectOneByCondition(productCategoryCondition);

            if(rootProductCategory == null){
                rootProductCategory = new DmsProductCategory();
                rootProductCategory.setName(rootName);
                rootProductCategory.setFullPath(rootName + splitChar);
                rootProductCategory.setLevel(1);
                rootProductCategory.setParentId(0L);

                rootProductCategory.setCreatedBy(0L);
                rootProductCategory.setCreatedDate(currentDate);
                rootProductCategory.setLastUpdatedBy(0L);
                rootProductCategory.setLastUpdatedDate(currentDate);
                rootProductCategory.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
                rootProductCategory.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

                dmsProductCategoryMapper.insert(rootProductCategory);

                rootProductCategory.setPath(rootProductCategory.getId() +  splitChar);
                dmsProductCategoryMapper.updateByPrimaryKeySelective(rootProductCategory);

                logger.info("*************** 新增产品大类数据：{} ***************", JSON.toJSONString(rootProductCategory));
            }else {
                rootProductCategory.setLastUpdatedBy(0L);
                rootProductCategory.setLastUpdatedDate(currentDate);
                rootProductCategory.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
                rootProductCategory.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                dmsProductCategoryMapper.updateByPrimaryKey(rootProductCategory);

                logger.info("*************** 修改产品大类数据：{} ***************", JSON.toJSONString(rootProductCategory));
            }
            rootProductCategoryList.add(rootProductCategory);
        }

        for(DmsProductCategory productCategory : productCategoryList){
            DmsProductCategory productCategoryCondition = new DmsProductCategory();
            productCategoryCondition.setAnjoyId(productCategory.getAnjoyId());
            //productCategoryCondition.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            DmsProductCategory category = dmsProductCategoryMapper.selectOneByCondition(productCategoryCondition);

            Long parentId = 0L;String parentPath = "", parentFullPath = "", anjoyParentId = "";
            for(DmsProductCategory rootProductCategory : rootProductCategoryList){
                if(StringUtils.isNotBlank(productCategory.getRootName())
                    && StringUtils.isNotBlank(rootProductCategory.getName())
                    && productCategory.getRootName().equals(rootProductCategory.getName())
                ){
                    parentId = rootProductCategory.getId();
                    parentPath = parentId + splitChar;
                    parentFullPath = rootProductCategory.getFullPath();
                    anjoyParentId = rootProductCategory.getAnjoyId();
                    break;
                }
            }
            if(parentId == null || parentId < 1){
                throw new ServiceException("未找到父产品类别ID");
            }

            if(category != null && category.getId() != null && category.getId() > 0){
                category.setName(productCategory.getName());
                category.setPath(parentPath + category.getId() + splitChar);
                category.setFullPath(parentFullPath + category.getName() + splitChar);
                category.setAnjoyParentId(anjoyParentId);
                category.setParentId(parentId);
                category.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
                category.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

                dmsProductCategoryMapper.updateByPrimaryKey(category);
            }else{
                productCategory.setFullPath(parentFullPath + productCategory.getName() + splitChar);
                productCategory.setAnjoyParentId(anjoyParentId);
                productCategory.setParentId(parentId);
                productCategory.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
                productCategory.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

                dmsProductCategoryMapper.insert(productCategory);
                productCategory.setPath(parentPath + productCategory.getId() + splitChar);
                dmsProductCategoryMapper.updateByPrimaryKeySelective(productCategory);
            }
        }


        //调用安井的产品分类同步接口，返回JSON数组格式数据
        /*JSONArray jsonArray = AnjoySynClient.getAnjoySynDataBySQLType(AnjoySynClient.SYN_TYPE.PRODUCT_CATEGORY);
        List<DmsProductCategory> productCategoryList = Lists.newArrayList();

        List<String> nodeList = Lists.newLinkedList();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            DmsProductCategory productCategory = new DmsProductCategory();
            productCategory.setAnjoyId(jsonObject.getString("FID"));
            productCategory.setName(jsonObject.getString("FNAME_L2"));
            if (jsonObject.get("FPARENTID") == null) {
                nodeList.add(jsonObject.getString("FID"));
            }
            productCategory.setAnjoyParentId(jsonObject.getString("FPARENTID"));
            productCategory.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
            productCategory.setCode(jsonObject.getString("FNUMBER"));
            productCategory.setCreatedBy(0L);
            productCategory.setCreatedDate(new Date());
            productCategory.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

            DmsProductCategory category=dmsProductCategoryMapper.selectByCode(productCategory.getCode());
            if(category!=null){
                productCategory.setId(category.getId());
                dmsProductCategoryMapper.updateByPrimaryKey(category);
            }else{
                productCategoryList.add(productCategory);
            }
        }
        if (productCategoryList.size() > 0) {
            dmsProductCategoryMapper.batchInsert(productCategoryList);
        }

        Map<String, DmsProductCategory> productCategoryMap = this.getProductCategoryMap();
        for (String anjoyId : nodeList) {
            DmsProductCategory rootProductCategory = productCategoryMap.get(anjoyId);
            rootProductCategory.setLevel(1);
            rootProductCategory.setPath(rootProductCategory.getId() + ",");
            rootProductCategory.setFullPath(rootProductCategory.getName() + ",");
            rootProductCategory.setParentId(0L);
        }

        while (nodeList.size() > 0) {
            List<String> tmpNodeList = Lists.newLinkedList();
            List<DmsProductCategory> pcList = dmsProductCategoryMapper.listByAnjoyParentId(nodeList);
            for (DmsProductCategory pc : pcList) {
                DmsProductCategory productCategory = productCategoryMap.get(pc.getAnjoyId());
                DmsProductCategory parentProductCategory = productCategoryMap.get(pc.getAnjoyParentId());

                if (parentProductCategory != null) {
                    productCategory.setLevel(parentProductCategory.getLevel() + 1);
                    productCategory.setParentId(parentProductCategory.getId());
                    if (parentProductCategory.getPath() != null) {
                        productCategory.setPath(parentProductCategory.getPath() + productCategory.getId() + ",");
                    }
                    if (parentProductCategory.getFullPath() != null) {
                        productCategory.setFullPath(parentProductCategory.getFullPath() + productCategory.getName() + ",");
                    }
                    tmpNodeList.add(pc.getAnjoyId());
                }
                logger.info("id:" + productCategory.getId() + ",parentId:" + productCategory.getParentId() + ",path:" + productCategory.getPath());
            }
            nodeList.clear();
            nodeList.addAll(tmpNodeList);
        }

        for (DmsProductCategory pc : productCategoryMap.values()) {
            dmsProductCategoryMapper.updateByPrimaryKey(pc);
        }*/

        logger.info("*************************** end 结束同步安井产品分类数据 ***************************");
    }

    @Override
    public List<DmsProductCategory> listByParentId(List<Long> indexList) {
        return dmsProductCategoryMapper.listByParentId(indexList);
    }

    @Override
    public List<DmsProductCategory> selectSon() {
        return dmsProductCategoryMapper.selectSon();
    }
}