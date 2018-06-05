package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsStoreMapper;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.support.SystemParamerConfiguration;
import com.coracle.dms.vo.DmsRemarkVo;
import com.coracle.dms.vo.DmsSellerVo;
import com.coracle.dms.vo.DmsStorageVo;
import com.coracle.dms.vo.DmsStoreVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DmsStoreServiceImpl extends BaseServiceImpl<DmsStore> implements DmsStoreService {
    private static final Logger logger = Logger.getLogger(DmsStoreServiceImpl.class);

    @Autowired
    private DmsStoreMapper dmsStoreMapper;
    @Autowired
    private DmsAttachmentRelationService dmsAttachmentRelationService;
    @Autowired
    private DmsRemarkService dmsRemarkService;
    @Autowired
    private DmsSellerService dmsSellerService;
    @Autowired
    private DmsStorageService dmsStorageService;
    @Autowired
    private DmsChannelContactsService dmsChannelContactsService;
    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;
    @Autowired
    private DmsOrganizationService dmsOrganizationService;
    @Autowired
    private DmsChannelService dmsChannelService;

    @Override
    public IMybatisDao<DmsStore> getBaseDao() {
        return dmsStoreMapper;
    }

    /***
     * 新增门店
     * @param dmsStoreVo
     * @param userSession
     * @param type 0:PC新增门店  1：APP订货端新增门店
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addStore(DmsStoreVo dmsStoreVo,UserSession userSession,int type) {
        checkParamStore(dmsStoreVo);
        dmsStoreVo.setId(null);
        if (BlankUtil.isEmpty(dmsStoreVo.getCreatedBy())) dmsStoreVo.setCreatedBy(userSession.getId());
        if (BlankUtil.isEmpty(dmsStoreVo.getLastUpdatedBy())) dmsStoreVo.setLastUpdatedBy(userSession.getId());
        dmsStoreVo.setActive(1);
        dmsStoreVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsStoreVo.setCode(SystemParamerConfiguration.getInstance().createSerialNumStr(DmsModuleEnums.STORE_SERIAL_KEY));
        if (type == 1) {
            //订货端新增门店要默认增加所属经销商就是渠道增加
            DmsChannelContacts dmsChannelContacts = new DmsChannelContacts();
            dmsChannelContacts.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            dmsChannelContacts.setUserId(userSession.getId());
            List<DmsChannelContacts> dmsChannelContactsList = dmsChannelContactsService.selectByCondition(dmsChannelContacts);
            if (BlankUtil.isEmpty(dmsChannelContactsList) || dmsChannelContactsList.size() == 0) {
                throw new ServiceException("未获取到渠道信息！");
            } else if (dmsChannelContactsList.size() > 1) {
                throw new ServiceException("获取到多个渠道信息");
            } else {
                dmsStoreVo.setOperateWay(DmsModuleEnums.STORE_OPERATE_WAY.DISTRIBUTE.getType());
                dmsStoreVo.setBelongDealer(dmsChannelContactsList.get(0).getChannelId());
                if (BlankUtil.isEmpty(dmsStoreVo.getBelongArea())){
                    DmsChannel dmsChannel = dmsChannelService.selectByPrimaryKey(dmsChannelContactsList.get(0).getChannelId());
                    if (BlankUtil.isEmpty(dmsChannel)){
                        throw new ServiceException("无法获取渠道商信息");
                    }
                    dmsStoreVo.setBelongArea(dmsChannel.getAreaId());
                }
            }
        } else {
            if (dmsStoreVo.getOperateWay() == DmsModuleEnums.STORE_OPERATE_WAY.DISTRIBUTE.getType()) {
                if (BlankUtil.isEmpty(dmsStoreVo.getBelongDealer())) {
                    throw new ServiceException("非直营门店上级渠道不能为空！");
                }
            }
            if (BlankUtil.isEmpty(dmsStoreVo.getOperateWay())){
                throw new ServiceException("门店运营方式不能为空");
            }

            if (dmsStoreVo.getBelongArea()==null) {
                throw new ServiceException("门店归属区域不能为空");
            }
        }
        dmsStoreMapper.insert(dmsStoreVo);
        //添加店长信息
        DmsSellerVo dmsSellerVo = new DmsSellerVo();
        dmsSellerVo.setShopId(dmsStoreVo.getId());
        dmsSellerVo.setName(dmsStoreVo.getShopowner());
        List<DmsContactInfo> contactInfoList = new ArrayList<>();
        DmsContactInfo dmsContactInfo = new DmsContactInfo();
        dmsContactInfo.setType(DmsModuleEnums.CONTACT_TYPE.MOBILE.getType());
        dmsContactInfo.setContent(dmsStoreVo.getShopownerPhone());
        contactInfoList.add(dmsContactInfo);
        dmsSellerVo.setDmsContactInfoList(contactInfoList);
        dmsSellerVo.setCreatedBy(userSession.getId());
        dmsSellerVo.setLastUpdatedBy(userSession.getId());
        dmsSellerVo.setCreatedDate(new Date());
        dmsSellerVo.setLastUpdatedDate(new Date());
        dmsSellerVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsSellerVo.setActive(1);
        dmsSellerVo.setPost("1");
        dmsSellerService.saveOrUpdate(dmsSellerVo,userSession);
        //增加关联树表中数据
        if (dmsStoreVo.getOperateWay() == DmsModuleEnums.STORE_OPERATE_WAY.DISTRIBUTE.getType()){
            dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_STORE.getType(), dmsStoreVo.getId(),
                    DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), dmsStoreVo.getBelongDealer(),
                    dmsStoreVo.getName(), userSession.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType());
            String remark = dmsStoreVo.getRemark();
            if (BlankUtil.isNotEmpty(remark)){//添加备注
                DmsRemarkVo dmsRemarkVo = new DmsRemarkVo();
                dmsRemarkVo.setContent(remark);
                dmsRemarkVo.setRelatedTableType(DmsModuleEnums.REMARK_RELATED_TABLE_TYPE.STORE_SHOP.getType());
                dmsRemarkVo.setRelatedTableId(dmsStoreVo.getId());
                dmsRemarkVo.setCreatedDate(new Date());
                dmsRemarkVo.setCreatedBy(userSession.getId());
                dmsRemarkVo.setLastUpdatedDate(new Date());
                dmsRemarkVo.setLastUpdatedBy(userSession.getId());
                dmsRemarkVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                dmsRemarkService.createRemark(dmsRemarkVo,userSession);
            }
        }else {
//            dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_STORE.getType(), dmsStoreVo.getId(),dmsStoreVo.getName(), userSession.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType());
            //插入树形结构数据时，直营门店将其父节点设置为所属区域id
            dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_STORE.getType(), dmsStoreVo.getId(),
                    DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), dmsStoreVo.getBelongArea(),
                    dmsStoreVo.getName(), userSession.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType());
        }

        //新建仓库
        addStoreStorage(dmsStoreVo,userSession);
    }

    /**
     * 新建仓库
     * @param dmsStoreVo
     * @param userSession
     */
    private void addStoreStorage(DmsStoreVo dmsStoreVo,UserSession userSession){
        DmsStorageVo dmsStorageVo = new DmsStorageVo();
        dmsStorageVo.setCreatedBy(userSession.getId());
        dmsStorageVo.setCreatedDate(new Date());
        dmsStorageVo.setLastUpdatedBy(userSession.getId());
        dmsStorageVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsStorageVo.setLastUpdatedDate(new Date());
        dmsStorageVo.setName(dmsStoreVo.getName());
        dmsStorageVo.setLocalFlag(DmsModuleEnums.ACTIVE_STATUS.DIABLE.getType());
        dmsStorageVo.setCode("S"+dmsStoreVo.getCode());
        dmsStorageVo.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
        dmsStorageVo.setContacts(dmsStoreVo.getShopowner());
        dmsStorageVo.setContactsMobile(dmsStoreVo.getShopownerPhone());
        dmsStorageVo.setProvince(String.valueOf(dmsStoreVo.getProvince()));
        dmsStorageVo.setCity(String.valueOf(dmsStoreVo.getCity()));
        dmsStorageVo.setCounty(String.valueOf(dmsStoreVo.getCounty()));
        dmsStorageVo.setAddress(dmsStoreVo.getStoreAddress());
        List<Long> areaIdList = new ArrayList<>();
        areaIdList.add(dmsStoreVo.getBelongArea());
        dmsStorageVo.setAreaIdList(areaIdList);
        if(dmsStoreVo.getOperateWay() == DmsModuleEnums.STORE_OPERATE_WAY.DIRECT.getType()){
            DmsOrganization dmsOrganization = dmsOrganizationService.getRootOrganization();
            if (BlankUtil.isEmpty(dmsOrganization)){
                throw new ServiceException("未查询到根组织");
            }
            dmsStorageVo.setOrgId(dmsOrganization.getId());
        }else {
            dmsStorageVo.setOrgId(dmsStoreVo.getBelongDealer());
            dmsStorageVo.setStorageType(DmsModuleEnums.STORAGE_TYPE.STORE.getType());
        }
        dmsStorageVo.setRelationId(dmsStoreVo.getId());
        dmsStorageService.create(dmsStorageVo);
    }

    /***
     * 修改门店
     * @param dmsStoreVo
     * @param userId
     * @param type 0:PC修改门店  1：APP订货端修改门店
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void modifyStore(DmsStoreVo dmsStoreVo,long userId,int type){
        DmsStore dmsStore = dmsStoreMapper.selectByPrimaryKey(dmsStoreVo.getId());
        if (BlankUtil.isEmpty(dmsStore.getCode())) {//如果是空的，重新生成一个编码
            dmsStoreVo.setCode(SystemParamerConfiguration.getInstance().createSerialNumStr(DmsModuleEnums.STORE_SERIAL_KEY));
        }
        if (BlankUtil.isEmpty(dmsStore)){
            throw new ServiceException("无法获取当前id为"+dmsStore.getId()+"的信息！");
        }
        dmsStoreVo.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
        dmsStoreVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsStoreVo.setLastUpdatedBy(userId);
        if (type==1) {
            dmsStoreVo.setBelongDealer(dmsStore.getBelongDealer());
        }
        /*if (BlankUtil.isNotEmpty(dmsStoreVo.getCloseDate())){//关停时间不为空
            dmsStoreVo.setActive(0);
        }*/
        dmsTreeRelationService.updateName(DmsModuleEnums.TREE_RELATED_TYPE.DMS_STORE.getType(),dmsStoreVo.getId(),dmsStoreVo.getName(),userId);
        dmsStoreMapper.updateByPrimaryKeySelective(dmsStoreVo);
    }

    /**
     * 门店分页查询
     * @param dmsStoreVo
     * @return
     */
    public PageHelper.Page<DmsStoreVo> selectForPageList(DmsStoreVo dmsStoreVo) {
        try {
            PageHelper.startPage(dmsStoreVo.getP(), dmsStoreVo.getS());
            dmsStoreMapper.getPageList(dmsStoreVo);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            logger.error("获取门店分页报错信息：",e);
            throw new ServiceException("门店列表分页查询异常--->>>");
        }
    }

    /**
     * APP端门店分页查询
     * @param dmsStoreVo
     * @return
     */
    public PageHelper.Page<DmsStoreVo> selectForPageListByApp(DmsStoreVo dmsStoreVo) {
        try {
            PageHelper.startPage(dmsStoreVo.getP(), dmsStoreVo.getS());
            dmsStoreMapper.getPageListByApp(dmsStoreVo);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            logger.error("获取门店分页报错信息：",e);
            throw new ServiceException("门店列表分页查询异常--->>>");
        }
    }

    @Override
    public Map<String, Object> getStoreDetail(Long id,UserSession userSession) {
        Map<String,Object> result = new HashMap<String,Object>();
        DmsStoreVo dmsStoreVo = dmsStoreMapper.getStoreDetailByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsStoreVo)||BlankUtil.isEmpty(dmsStoreVo.getId())||dmsStoreVo.getId()==0){//未查到数据
            throw new ServiceException("无法获取id为["+id+"]的门店信息！");
        }
        result.put("dmsStore",dmsStoreVo);
        return result;
    }

    @Override
    public DmsStore getStoreInfoByUserId(Long id) {
        return dmsStoreMapper.getStoreInfoByUserId(id);
    }

    /**
     * 通过用户ID获取所拥有的门店分页查询
     * @param dmsStoreVo
     * @return
     */
    public PageHelper.Page<DmsStore> selectForPageListByUser(DmsStoreVo dmsStoreVo) {
        try {
            PageHelper.startPage(dmsStoreVo.getP(), dmsStoreVo.getS());
            dmsStoreMapper.selectByCondition(dmsStoreVo);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            logger.error("获取门店分页报错信息：",e);
            throw new ServiceException("门店列表分页查询异常--->>>");
        }
    }

    public String getStoreIdsByChannelId(Long id){
        if (BlankUtil.isEmpty(id)) id = 0L;
        return dmsStoreMapper.getStoreIdsByChannelId(id);
    }


    /**
     * 根据渠道id获取渠道下的门店id列表
     *
     * @param channelId
     * @return
     */
    @Override
    public List<Long> selectStoreIdListByChannelId(Long channelId) {
        return dmsStoreMapper.selectStoreIdListByChannelId(channelId);
    }

    /**
     * 统一检验参数
     * @param dmsStoreVo
     */
    private void checkParamStore(DmsStoreVo dmsStoreVo) {
        if (dmsStoreVo == null) {
            throw new ServiceException("参数异常");
        }
        if (BlankUtil.isEmpty(dmsStoreVo.getName())) {
            throw new ServiceException("门店名称不能为空");
        }
        if (BlankUtil.isEmpty(dmsStoreVo.getStoreType())){
            throw new ServiceException("门店类型不能为空");
        }
        if (BlankUtil.isEmpty(dmsStoreVo.getShopowner())) {
            throw new ServiceException("门店店长不能为空");
        }
        if (BlankUtil.isEmpty(dmsStoreVo.getShopownerPhone())) {
            throw new ServiceException("门店店长手机不能为空");
        }
        if (BlankUtil.isEmpty(dmsStoreVo.getProvince())||dmsStoreVo.getProvince()==0) {
            throw new ServiceException("省份不能为空");
        }
        if (BlankUtil.isEmpty(dmsStoreVo.getCity())||dmsStoreVo.getCity()==0) {
            throw new ServiceException("城市不能为空");
        }
        if (BlankUtil.isEmpty(dmsStoreVo.getCounty())||dmsStoreVo.getCounty()==0) {
            throw new ServiceException("区县不能为空");
        }
        if (BlankUtil.isEmpty(dmsStoreVo.getStoreAddress())) {
            throw new ServiceException("门店详细地址不能为空");
        }
    }
}