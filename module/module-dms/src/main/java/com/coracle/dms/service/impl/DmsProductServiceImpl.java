package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.constant.DmsRoleCodeConstants;
import com.coracle.dms.dao.mybatis.*;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.dms.vo.DmsProductSpecMatrixConfigVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DmsProductServiceImpl extends BaseServiceImpl<DmsProduct> implements DmsProductService {
    private static final Logger logger = LoggerFactory.getLogger(DmsProductServiceImpl.class);
    @Autowired
    private DmsProductMapper dmsProductMapper;
    @Autowired
    private DmsProductAttachFileMapper dmsProductAttachFileMapper;
    @Autowired
    private DmsProductAreaMapper dmsProductAreaMapper;
    @Autowired
    private DmsProductSpecMatrixConfigMapper dmsProductSpecMatrixConfigMapper;
    @Autowired
    private DmsChannelAreaMapper dmsChannelAreaMapper;
    @Autowired
    private DmsChannelContactsService dmsChannelContactsService;
    @Autowired
    private DmsStoreService dmsStoreService;
    @Autowired
    private DmsPromotionProductService dmsPromotionProductService;
    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;
    @Autowired
    private DmsFootprintService dmsFootprintService;
    @Autowired
    private DmsProductCategoryService dmsProductCategoryService;
    @Autowired
    private DmsChannelMapper dmsChannelMapper;

    @Override
    public IMybatisDao<DmsProduct> getBaseDao() {
        return dmsProductMapper;
    }

    /**
     * PC端产品列表
     */
    @Override
    public PageHelper.Page<DmsProductVo> findProductPCPageList(DmsProductVo product) {
        try {
            PageHelper.startPage(product.getP(), product.getS());
            dmsProductMapper.findProductPCPageList(product);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public PageHelper.Page<DmsProductVo> findProductPCSpecPageList(DmsProductVo dmsProduct) {
        try {
            PageHelper.startPage(dmsProduct.getP(),dmsProduct.getS());
            dmsProductMapper.findProductPCSpecPageList(dmsProduct);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public PageHelper.Page<DmsProductVo> findProductPageList(DmsProductVo dmsProduct, UserSession session) {
        String roleCode = session.getRoleCode();
        Long orgId = session.getOrgId();
        Long channelId = null;
        String channelCode = null;
        List<Long> channelAreaIdList = null;
        dmsProduct.setUserId(session.getId());

        if (roleCode.equals(DmsRoleCodeConstants.CO)) {  //订货端
            //从session中获取当前登录人，根据登录人id 从channel_contact表中查出所属渠道的信息
            DmsChannelVo dmsChannelVo = dmsChannelMapper.selectByUserId(session.getId());

            channelId = dmsChannelVo.getId();
            channelCode = dmsChannelVo.getEasNum();
            //channelId = session.getOrgId();
        } else if (roleCode.equals(DmsRoleCodeConstants.SR)) {  //零售端
            DmsStore store = dmsStoreService.selectByPrimaryKey(orgId);
            if (store == null) {
                throw new ServiceException("获取当前登录人的门店出错");
            }

            /* 分销门店获取其所属渠道的id */
            if (store.getOperateWay() == DmsModuleEnums.STORE_OPERATE_WAY.DISTRIBUTE.getType()) {  //分销门店
                channelId = session.getSuperiorOrgId();
            } else {  //直营门店可以看到所有产品
                channelId = null;
            }
        }
        //设置渠道id和code
        dmsProduct.setChannelId(channelId);
        dmsProduct.setChannelCode(channelCode);

        Integer existProductWhiteList = dmsProductMapper.findCountChannelProductWhite(channelId);
        dmsProduct.setExistProductWhiteList(existProductWhiteList);

        //todo 阳光：根据当前登录账号所属的渠道的渠道等级，确定显示的价格（大、中、小客户价）
        //todo 如果对应的大、中、小客户价为空，则按原逻辑取价格
        try {
            PageHelper.startPage(dmsProduct.getP(),dmsProduct.getS());
            dmsProductMapper.findProductPageList(dmsProduct);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public PageHelper.Page<DmsProductVo> findProductPageListForStore(DmsProductVo dmsProductVo, UserSession userSession) {
        try {
            PageHelper.startPage(dmsProductVo.getP(),dmsProductVo.getS());
            if("CO".equals(userSession.getRoleCode())){//订货单---渠道
                dmsProductVo.setType("1");
            }if("SR".equals(userSession.getRoleCode())){//零售端----门店
                dmsProductVo.setType("2");
            }
            dmsProductVo.setUserId(userSession.getId());
            dmsProductMapper.findProductPageListStore(dmsProductVo);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 获取产品详情
     * @param id 产品id
     * @return
     */
    public DmsProductVo getDetails(Long id,UserSession userSession) {
        Long userId=userSession.getId();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("userId",userId);

        DmsProduct dmsProduct = dmsProductMapper.selectByPrimaryKey(id);
        if(dmsProduct == null){
            throw new ServiceException("该产品不存在！");
        }
        DmsProductVo productVo = dmsProductMapper.getProductDetails(map);



        //如果该产品有促销活动返回，并且当前登录人是渠道商，则判断该促销活动是否已结束
        //促销活动结束逻辑为：若活动时间未结束，但是有总量限制，且促销订单量已达到，则提示活动已结束
//        DmsPromotionProductVo promotionProduct = productVo.getPromotion();
//        //当前登录人是否是渠道商(订货端)
//        boolean isChannelContact = userSession.getRoleCode().equals(DmsRoleCodeConstants.CO);
//        if (promotionProduct != null && isChannelContact) {
//            DmsPromotionProductVo param = new DmsPromotionProductVo();
//            param.setId(promotionProduct.getId());
//            param.setChannelId(userSession.getOrgId());
//            Integer availableCount = dmsPromotionProductService.selectAvailableCountByCondition(param);
//
//            //-1表示没有限制；0或正数表示可用量
//            if (availableCount == 0) {
//                promotionProduct.setIsFinished(1);
//            } else {
//                promotionProduct.setIsFinished(0);
//            }
//        }
//        productVo.setPromotion(promotionProduct);
        return productVo;
    }

    @Override
    public DmsProductVo getDetailsApp(Long id, UserSession userSession) {
        Long userId=userSession.getId();
        DmsChannelVo dmsChannelVo = dmsChannelMapper.selectByUserId(userSession.getId());
        Long channelId = dmsChannelVo.getId();
        String channelCode = dmsChannelVo.getEasNum();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("userId",userId);
        map.put("channelId",channelId);
        map.put("channelCode",channelCode);

        DmsProduct dmsProduct = dmsProductMapper.selectByPrimaryKey(id);
        if(dmsProduct == null){
            throw new ServiceException("该产品不存在！");
        }
        DmsProductVo productVo = dmsProductMapper.getProductDetailsAPP(map);



        //如果该产品有促销活动返回，并且当前登录人是渠道商，则判断该促销活动是否已结束
        //促销活动结束逻辑为：若活动时间未结束，但是有总量限制，且促销订单量已达到，则提示活动已结束
//        DmsPromotionProductVo promotionProduct = productVo.getPromotion();
//        //当前登录人是否是渠道商(订货端)
//        boolean isChannelContact = userSession.getRoleCode().equals(DmsRoleCodeConstants.CO);
//        if (promotionProduct != null && isChannelContact) {
//            DmsPromotionProductVo param = new DmsPromotionProductVo();
//            param.setId(promotionProduct.getId());
//            param.setChannelId(userSession.getOrgId());
//            Integer availableCount = dmsPromotionProductService.selectAvailableCountByCondition(param);
//
//            //-1表示没有限制；0或正数表示可用量
//            if (availableCount == 0) {
//                promotionProduct.setIsFinished(1);
//            } else {
//                promotionProduct.setIsFinished(0);
//            }
//        }
//        productVo.setPromotion(promotionProduct);
        return productVo;
    }

    /**
     * 获取产品详情页规格矩阵列表（规格矩阵列表，作用于前端判断是否可以删除该规格）
     * @param id 产品id
     * @return
     */
    public List<DmsProductVo> getDetailSpecMatrix(Long id,UserSession userSession) {
        Long userId=userSession.getId();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("userId",userId);
        List<DmsProductVo> productVo=dmsProductMapper.getProductDetailSpecMatrix(map);
        return productVo;
    }


    /**
     * 新增产品
     * @param productVo 产品实体
     * @param userSession 登录信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(DmsProductVo productVo, UserSession userSession) {
        //校验产品编码唯一性
        if(isInsert(productVo.getCode(),null)){
            throw new ServiceException("产品编码重复");
        }

        if(productVo.getShowPrice().compareTo(new BigDecimal(0)) < 0 ||productVo.getSuggestedPrice().compareTo(new BigDecimal(0)) < 0){
            throw  new ServiceException("价格不可为负数");
        }
            /**
             * 保存产品基础信息
             */
            productVo.setViewCount(0l);//产品浏览量
            productVo.setSalesVolume(0l);//产品销量
            dmsProductMapper.insert(productVo);
            /**
             * 保存相关联的属性
             */
            Long productId=productVo.getId();//产品id
            List<DmsProductAttachFile> picIds=productVo.getPicIds();//产品主图id集合
            List<DmsProductAttachFile> attachFileList= Lists.newArrayList();//暂存需要保存产品主图附件实体
            List<Long> saleAreaIds=productVo.getSaleAreaIds();//产品销售区域id集合
            List<DmsProductArea> saleAreaIdList=Lists.newArrayList();//暂存产品销售区域关联关系实体集合
            List<DmsProductSpecMatrixConfigVo> productSpecMatrixs=productVo.getProductSpecMatrixs();//产品规格属性矩阵集合
            List<DmsProductSpecMatrixConfig> productSpecMatrixList=Lists.newArrayList();//暂存产品规格属性矩阵关联关系实体集合
            //保存产品与产品主图关联关系
            if(BlankUtil.isNotEmpty(picIds)){
                for(DmsProductAttachFile pic:picIds){
                    attachFileList.add(getAttachFileEntity(userSession,productId,pic.getAttachId(),true,null,1,pic.getSortOrder()));
                }
            }
            //保存产品销售区域关联关系
            for(Long saleAreaId:saleAreaIds){
                saleAreaIdList.add(getProductArea(productId,saleAreaId));
            }
            //保存产品规格属性矩阵关联关系
            for(DmsProductSpecMatrixConfig specMatrix:productSpecMatrixs){
                productSpecMatrixList.add(getProductSpecMatrixConfig(userSession,productId,specMatrix.getSpecUnionKey(),specMatrix.getProductCode(),specMatrix.getBarCode(),specMatrix.getPrice(),specMatrix.getSuggestedRetailPrice(),specMatrix.getActive(),specMatrix.getSortOrder(),true));
            }
            //批量新增附件关联关系
            if(BlankUtil.isNotEmpty(attachFileList)){
                dmsProductAttachFileMapper.batchInsert(attachFileList);
            }
            //批量新增销售区域关联关系
            if(BlankUtil.isNotEmpty(saleAreaIdList)){
                dmsProductAreaMapper.batchInsert(saleAreaIdList);
            }
            //批量新增产品规格属性矩阵关联关系
            if(BlankUtil.isNotEmpty(productSpecMatrixList)){
                dmsProductSpecMatrixConfigMapper.batchInsert(productSpecMatrixList);
            }
    }

    /**
     * 生成产品附件关联关系实体
     * @param attachType 附件类型
     * @param userSession 登录信息实体
     * @param productId 产品id
     * @param attachId 附件id
     * @param attachName 产品附件名称
     * @param isCreate 是否是新增
     * @return
     */
    private DmsProductAttachFile getAttachFileEntity(UserSession userSession,Long productId,Long attachId,boolean isCreate,String attachName,Integer attachType,Integer sort){
        DmsProductAttachFile attachFile=new DmsProductAttachFile();
        attachFile.setProductId(productId);
        attachFile.setAttachId(attachId);
        attachFile.setAttachType(attachType);
        attachFile.setAttachName(attachName);
        attachFile.setSortOrder(sort);
        attachFile.setRemoveFlag(0);
        if(isCreate){
            attachFile.setCreatedDate(new Date());
            attachFile.setCreatedBy(userSession.getId());
        }else
        {
            attachFile.setLastUpdatedDate(new Date());
            attachFile.setLastUpdatedBy(userSession.getId());
        }
        return attachFile;
    }


    /**
     * 生成产品销售区域关联关系实体
     * @param productId 产品id
     * @param areaId 区域id
     * @return
     */
    private DmsProductArea getProductArea(Long productId, Long areaId){
        DmsProductArea dmsProductArea=new DmsProductArea();
        dmsProductArea.setProductId(productId);
        dmsProductArea.setAreaId(areaId);
        return dmsProductArea;
    }


    /**
     * 生成产品规格属性矩阵关联关系实体
     * @param userSession 登录信息实体
     * @param productId 产品id
     * @param specUnionKey 规格组合键(规格名称:s;规格名称:白色)
     * @param productCode 产品编码
     * @param barcode 条形码
     * @param price 产品标价
     * @param suggestedRetailPrice 建议零售价
     * @param active 产品状态：1已上架、0已下架
     * @param sortOrder 用于矩形动态表单每行保存的数据顺序
     * @param isCreate 是否是新增
     * @return
     */
    private DmsProductSpecMatrixConfig getProductSpecMatrixConfig(UserSession userSession, Long productId, String specUnionKey, String productCode,String barcode, BigDecimal price,BigDecimal suggestedRetailPrice,Integer active,Integer sortOrder, boolean isCreate){
        DmsProductSpecMatrixConfig dmsProductSpecMatrixConfig=new DmsProductSpecMatrixConfig();
        dmsProductSpecMatrixConfig.setProductId(productId);
        dmsProductSpecMatrixConfig.setSpecUnionKey(specUnionKey);
        dmsProductSpecMatrixConfig.setProductCode(productCode);
        dmsProductSpecMatrixConfig.setBarCode(barcode);
        dmsProductSpecMatrixConfig.setPrice(price);
        dmsProductSpecMatrixConfig.setSuggestedRetailPrice(suggestedRetailPrice);
        dmsProductSpecMatrixConfig.setActive(active);
        dmsProductSpecMatrixConfig.setSortOrder(sortOrder);
        dmsProductSpecMatrixConfig.setRemoveFlag(0);
        if(isCreate){
            dmsProductSpecMatrixConfig.setCreatedDate(new Date());
            dmsProductSpecMatrixConfig.setCreatedBy(userSession.getId());
        }else{
            dmsProductSpecMatrixConfig.setLastUpdatedBy(userSession.getId());
            dmsProductSpecMatrixConfig.setLastUpdatedDate(new Date());
        }
        return  dmsProductSpecMatrixConfig;
    }




    /**
     * 修改产品
     * @param productVo 产品实体
     * @param userSession 登录信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(DmsProductVo productVo, UserSession userSession) {
        //校验产品编号唯一性
        if(isInsert(productVo.getCode(),1l)){
            throw new ServiceException("产品编号重复");
        }
        /*if(productVo.getShowPrice().compareTo(new BigDecimal(0)) < 0 ||productVo.getSuggestedPrice().compareTo(new BigDecimal(0)) < 0){
            throw  new ServiceException("价格不可为负数");
        }*/
            //更新产品主图关联关系
            updateProductPics(productVo,userSession);
            //更新产品销售区域关联关系
            updateProductAreas(productVo,userSession);
            //更新产品规格属性矩阵关联关系
            updateProductSpecMatrixConfig(productVo,userSession);
            //更新基本属性
            dmsProductMapper.updateByPrimaryKeySelective(productVo);



    }

    /**
     * 更新产品图片关联关系
     */
    @Transactional(rollbackFor = Exception.class)
    private void updateProductPics(DmsProductVo productVo,UserSession userSession){

        /**
         *  获取系统中已经存在产品图片关联记录
         */
        DmsProductAttachFile productAttachFile= new DmsProductAttachFile();
        productAttachFile.setAttachType(1);
        productAttachFile.setRemoveFlag(0);
        productAttachFile.setProductId(productVo.getId());
        List<DmsProductAttachFile> oldProductPics=dmsProductAttachFileMapper.selectByCondition(productAttachFile);
        if(!oldProductPics.isEmpty() && oldProductPics.size() > 0){
            dmsProductAttachFileMapper.batchDelete(oldProductPics);
        }

        List<DmsProductAttachFile> newPicIds=productVo.getPicIds();//前端传过来的图片id集合
        List<DmsProductAttachFile> addFiles = Lists.newArrayList(); //定义新增图片关联关系
        for(DmsProductAttachFile newFile:newPicIds){
            addFiles.add(getAttachFileEntity(userSession,productVo.getId(),newFile.getAttachId(),true,newFile.getAttachName(),1,newFile.getSortOrder()));
        }
        dmsProductAttachFileMapper.batchInsert(addFiles);

    }

    /**
     * 更新产品销售区域关联关系
     */
    @Transactional(readOnly = true)
    private void updateProductAreas(DmsProductVo productVo,UserSession userSession){

        if(productVo.getSaleAreaIds()==null||productVo.getSaleAreaIds().size()==0){

        }else {
            //获取产品旧销售区域关联记录
            DmsProductArea dmsProductArea = new DmsProductArea();
            dmsProductArea.setProductId(productVo.getId());
            List<DmsProductArea> oldProductAreaList = dmsProductAreaMapper.selectByCondition(dmsProductArea);

            List<Long> newAreaIds = productVo.getSaleAreaIds();
            List<DmsProductArea> addFiles = Lists.newArrayList();      //定义新增产品销售区域关联关系
            List<DmsProductArea> removeFiles = Lists.newArrayList();   //定义需要删除的产品销售区域关联关系

            if (BlankUtil.isNotEmpty(oldProductAreaList)) {
                //如果历史数据非空， 且最新提交数据也非空，比对更新情况
                if (BlankUtil.isNotEmpty(newAreaIds)) {
                    List<Long> oldAreaIds = Lists.newArrayList(); //用于旧原先的oldAreaIds
                    /**
                     * 记录删除的产品范围关联记录（新产品列表中不存在的旧的ID）
                     */
                    for (DmsProductArea oldArea : oldProductAreaList) {
                        if (!newAreaIds.contains(oldArea.getAreaId())) {
                            removeFiles.add(oldArea);
                        }
                        oldAreaIds.add(oldArea.getAreaId());
                    }

                    /**
                     * 记录新增的产品范围关联记录（旧产品列表中不存的新图片id）
                     */
                    for (Long newId : newAreaIds) {
                        if (!oldAreaIds.contains(newId)) {
                            DmsProductArea areaTemp = new DmsProductArea();
                            areaTemp.setProductId(productVo.getId());
                            areaTemp.setAreaId(newId);
                            addFiles.add(getProductArea(productVo.getId(), newId));
                        }
                    }
                    /**
                     * 删除产品产品发布范围关联关系
                     */
                    if (BlankUtil.isNotEmpty(removeFiles)) {
                        dmsProductAreaMapper.batchDelete(removeFiles);
                    }
                    /**
                     * 新增产品发布范围关联关系
                     */
                    if (BlankUtil.isNotEmpty(addFiles)) {
                        dmsProductAreaMapper.batchInsert(addFiles);
                    }
                } else {
                    //如果历史数据非空，而最新提交为空，将规则对应历史商品全部删除
                    List<DmsProductArea> oldProductAreaLists = dmsProductAreaMapper.selectByCondition(dmsProductArea);
                    for (DmsProductArea oldAreas : oldProductAreaLists) {
                        removeFiles.add(oldAreas);
                    }
                    dmsProductAreaMapper.batchDelete(removeFiles);
                }
            } else {
                //如果历史数据为空， 最新提交非空， 新增产品销售区域关联记录。
                for (Long orgId : newAreaIds) {
                    addFiles.add(getProductArea(productVo.getId(), orgId));
                }
                dmsProductAreaMapper.batchInsert(addFiles);
            }
        }
    }

    /**
     * //更新产品规格属性矩阵关联关系
     */
    @Transactional(readOnly = true)
    private void updateProductSpecMatrixConfig(DmsProductVo productVo,UserSession userSession){
        //获取产品旧规格属性矩阵关联记录
        DmsProductSpecMatrixConfig dmsProductSpecMatrixConfig=new DmsProductSpecMatrixConfig();
        dmsProductSpecMatrixConfig.setRemoveFlag(0);
        dmsProductSpecMatrixConfig.setProductId(productVo.getId());
        List<DmsProductSpecMatrixConfig> oldProductSpecMatrixConfigs=dmsProductSpecMatrixConfigMapper.selectByCondition(dmsProductSpecMatrixConfig);

        List<DmsProductSpecMatrixConfigVo> newProductSpecMatrixConfigs=productVo.getProductSpecMatrixs();//前端传过来的产品规格属性矩阵关联关系实体
        List<Long> newProductSpecMatrixConfigIds=Lists.newArrayList();//保存从前端传过来的关联关系实体中取出来的目标产品id

        if(BlankUtil.isNotEmpty(oldProductSpecMatrixConfigs)) {
            //如果历史数据非空， 且最新提交数据也非空，比对更新情况
            if(BlankUtil.isNotEmpty(newProductSpecMatrixConfigs)) {
                List<DmsProductSpecMatrixConfig> addFiles = Lists.newArrayList();       //定义新增产品规格属性矩阵关联关系
                List<DmsProductSpecMatrixConfig> removeFiles = Lists.newArrayList();    //定义需要删除的产品规格属性矩阵关联关系
                List<DmsProductSpecMatrixConfig> updateFiles = Lists.newArrayList();    //定义需要修改的产品规格属性矩阵关联关系
                List<Long> oldProductSpecMatrixConfigIds=Lists.newArrayList();          //保存数据库中找出来的产品规格属性矩阵实体的目标产品id

                /**
                 * 取出前端传过来的产品规格属性矩阵id
                 */
                for(DmsProductSpecMatrixConfig newProductSpecMatrixConfig:newProductSpecMatrixConfigs){
                    newProductSpecMatrixConfigIds.add(newProductSpecMatrixConfig.getId());
                }
                /**
                 * 记录需要删除的产品规格属性矩阵（前端传过来的规格属性矩阵中不包含的旧id）
                 */
                for(DmsProductSpecMatrixConfig oldProductSpecMatrixConfig:oldProductSpecMatrixConfigs){
                    if(!newProductSpecMatrixConfigIds.contains(oldProductSpecMatrixConfig.getId())){
                        removeFiles.add(oldProductSpecMatrixConfig);
                    }
                    oldProductSpecMatrixConfigIds.add(oldProductSpecMatrixConfig.getId());
                }
                /**
                 * 记录需要新增或者修改的产品规格属性矩阵
                 */
                for(DmsProductSpecMatrixConfig newPsmc:newProductSpecMatrixConfigs){
                    if(!oldProductSpecMatrixConfigIds.contains(newPsmc.getId())){
                        addFiles.add(getProductSpecMatrixConfig(userSession,productVo.getId(),newPsmc.getSpecUnionKey(),newPsmc.getProductCode(),newPsmc.getBarCode(),newPsmc.getPrice(),newPsmc.getSuggestedRetailPrice(),newPsmc.getActive(),newPsmc.getSortOrder(),true));
                    }else{
                        DmsProductSpecMatrixConfig temp=getProductSpecMatrixConfig(userSession,productVo.getId(),newPsmc.getSpecUnionKey(),newPsmc.getProductCode(),newPsmc.getBarCode(),newPsmc.getPrice(),newPsmc.getSuggestedRetailPrice(),newPsmc.getActive(),newPsmc.getSortOrder(),false);
                        temp.setId(newPsmc.getId());
                        updateFiles.add(temp);
                    }
                }
                /**
                 * 删除产品关联关系
                 */
                if(BlankUtil.isNotEmpty(removeFiles)) {
                    dmsProductSpecMatrixConfigMapper.batchDelete(removeFiles);
                }
                /**
                 * 新增产品关联关系
                 */
                if(BlankUtil.isNotEmpty(addFiles)) {
                    dmsProductSpecMatrixConfigMapper.batchInsert(addFiles);
                }
                /**
                 * 更新产品关联关系
                 */
                if(BlankUtil.isNotEmpty(updateFiles)) {
                    for(DmsProductSpecMatrixConfig link:updateFiles){
                        dmsProductSpecMatrixConfigMapper.updateByPrimaryKey(link);
                    }
                }
            } else {
                //如果历史数据非空，而最新提交为空，将产品互链关联记录全部删除
                if(oldProductSpecMatrixConfigs!=null&&oldProductSpecMatrixConfigs.size()>0){
                    dmsProductSpecMatrixConfigMapper.batchDelete(oldProductSpecMatrixConfigs);
                }
            }
        } else {
            //如果历史数据为空， 最新提交非空， 新增产品互链关联记录。
            List<DmsProductSpecMatrixConfig> tempList=Lists.newArrayList();
            for(DmsProductSpecMatrixConfig newPsmc:newProductSpecMatrixConfigs){
                tempList.add(getProductSpecMatrixConfig(userSession,productVo.getId(),newPsmc.getSpecUnionKey(),newPsmc.getProductCode(),newPsmc.getBarCode(),newPsmc.getPrice(),newPsmc.getSuggestedRetailPrice(),newPsmc.getActive(),newPsmc.getSortOrder(),true));
            }
            if(tempList.size()>0){
                dmsProductSpecMatrixConfigMapper.batchInsert(tempList);
            }
        }

    }

    /**
     * 记录用户的产品浏览历史
     *
     * @param productId 产品id
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void browseRecord(Long productId, Long userId){

        //增加产品浏览次数
        dmsProductMapper.addReadCount(productId);

        //保留用户的足迹（用户产品浏览记录）
        dmsFootprintService.insert(userId, productId);
    }


    /**
     * 上架产品
     * @param ids 产品id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void up(List<Long> ids) {
        dmsProductMapper.up(ids);
    }

    /**
     * 下架产品
     * @param ids 产品id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void down(List<Long> ids) {
        dmsProductMapper.down(ids);
    }

    private boolean isInsert(String code,Long id){
        if(code==null &&"".equals(code)){
            return true;
        }else{
            DmsProduct dmsProduct=new DmsProduct();
            dmsProduct.setCode(code);
            dmsProduct.setRemoveFlag(0);
            List<DmsProduct> list=selectByCondition(dmsProduct);
            if(list==null || list.size()<=0){
                return false;
            }else{
                if(id !=null && list.size()==1){
                    return false;
                }else {
                    return true;
                }
            }
        }
    }



    @Override
    public void updateById(Long id) {
        dmsProductMapper.updateById(id);
    }

    @Override
    public Map<String, Object> findProductNameSpecName(Map<String, Object> map) {
        return dmsProductMapper.findProductNameSpecName(map);
    }

    /**
     * 修改产品销量
     * @param productId
     * @param count
     */
    @Override
    public void addSalesVolume(Long productId, Integer count) {
        DmsProduct product = dmsProductMapper.selectByPrimaryKey(productId);
        if (product == null) {
            throw new ServiceException("参数错误，数据库中不存在id为: " + productId + "的产品信息");
        }
        product.setSalesVolume(product.getSalesVolume()==null?0:product.getSalesVolume() + count);
        dmsProductMapper.updateByPrimaryKeySelective(product);
    }

    /**
     * 根据产品规格获取产品列表
     *
     * @param param
     * @param session
     * @return
     */
    @Override
    public List<DmsProductVo> listBySpecifications(DmsProductVo param, UserSession session) {
        param.setUserId(session.getId());
        DmsChannelContacts channelContacts = dmsChannelContactsService.queryContactByUserId(session.getId());
        if (channelContacts != null) {
            param.setChannelId(channelContacts.getChannelId());
        }
        return dmsProductMapper.listBySpecifications(param);
    }

    /**
     * 同步安井产品信息
     * 调用安井的渠道同步接口，返回JSON数组格式数据
     */
    @Override
    @Transactional(readOnly = false)
    public void anjoySyn() {
        logger.info("*************************** start 开始同步安井产品数据 ***************************");
        JSONArray jsonArray = AnjoySynClient.getAnjoySynDataBySQLType(AnjoySynClient.SYN_TYPE.PRODUCT);
        if(jsonArray == null || jsonArray.size() <= 0){
            logger.info("***************** 没有安井产品数据返回 *****************");
            return;
        }

        /**
         * 同步安井渠道数据前先将DMS所有渠道数据置为：已删除状态
         */
        Integer batchDeleteResult = dmsProductMapper.deleteProductSyncAnjoy();
        logger.info("DMD，共删除：{} 条产品数据", batchDeleteResult);

        List<DmsProduct> productList = Lists.newArrayList();
        Map<String, DmsProductCategory> productCategoryMap = dmsProductCategoryService.getProductCategoryMap();

        Date currentDate = new Date();
        //处理JSON数组，将产品JSON数组的数据插入到数据库中
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            Date createDate = new Date();
            try {
                String createDateStr = jsonObject.getString("FCREATEDATE");
                createDate = DateUtils.parseDate(createDateStr);
            }catch (Exception e){
                logger.error("*********************** 日期转换失败 ***********************");
            }
            DmsProduct product = new DmsProduct();
            product.setAnjoyId(jsonObject.getString("FID"));
            product.setName(jsonObject.getString("FNAME_L2"));
            product.setCode(jsonObject.getString("FNUMBER"));
            product.setEasCode(product.getCode());
            //product.setUnit(jsonObject.getString("FBASEUNIT"));
            product.setUnit("箱");//暂时写死
            product.setCreatedDate(createDate);
            product.setCreatedBy(0L);
            product.setLastUpdatedDate(currentDate);
            product.setLastUpdatedBy(0L);
            product.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            product.setActive(Integer.parseInt(jsonObject.getString("FSTATUS")));

            String productCategoryAnjoyId = jsonObject.getString("FMATERIALGROUPID");
            DmsProductCategory productCategory = productCategoryMap.get(productCategoryAnjoyId);
            if (productCategory != null) {
                product.setCategoryId(productCategory.getId());
            }
            /** 产品品牌，默认值：安井 */
            product.setBrandId(54L);
            product.setBigCustomerPrice(BigDecimal.ZERO);
            product.setShowPrice(BigDecimal.ZERO);
            product.setSuggestedPrice(BigDecimal.ZERO);
            product.setListingTime(currentDate);

            DmsProduct dmsProductCondition = new DmsProduct();
            dmsProductCondition.setAnjoyId(product.getAnjoyId());
            DmsProduct dmsProduct = dmsProductMapper.selectOneByCondition(dmsProductCondition);

            if(dmsProduct == null || dmsProduct.getId() == null || dmsProduct.getId() < 1){
                productList.add(product);
            }else {
                product.setId(dmsProduct.getId());
                dmsProductMapper.updateByPrimaryKeySelective(product);
            }
        }

        if (productList != null && productList.size() > 0) {
            logger.info("******************* 新增："+productList.size() + " 条产品数据 *******************");
            dmsProductMapper.batchInsert(productList);
        }

        logger.info("*************************** end 结束同步安井产品信息 ***************************");
    }

    /**
     *同步产品规格型号
     */
    @Override
    @Transactional(readOnly = false)
    public void anjoySpecificationSyn() {
        JSONArray jsonArray = AnjoySynClient.getAnjoySynDataBySQLType(AnjoySynClient.SYN_TYPE.PRODUCT_SPECIFICATION);
        List<DmsProduct> productList = new ArrayList<>();

        for (Object object: jsonArray){
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            String code = jsonObject.getString("CODE");
            String spe = jsonObject.getString("SPE");
            String speModel = jsonObject.getString("SPEMODEL");

            if(StringUtils.isNotBlank(spe) &&(
                "桶装".equals(spe.trim()) || "盒装".equals(spe.trim())
            )){
                spe = "袋装";
            }

            DmsProduct dmsProduct = new  DmsProduct() ;
            dmsProduct.setCode(code);
            dmsProduct.setSpecifications(spe);
            dmsProduct.setSpecificationsModel(speModel);

            productList.add(dmsProduct);
        }

        logger.info("*************** 数据大小：{}, JSON数据：{} ***************", productList.size(), JSON.toJSONString(productList));
        if(productList != null && productList.size() > 0){
            dmsProductMapper.batchUpdate(productList);
        }

    }

    /***
     * 根据产品编码查询产品信息
     * @param code
     * @return
     */
    @Override
    public DmsProduct getByCode(String code) {
        return dmsProductMapper.selectByCode(code);
    }
}