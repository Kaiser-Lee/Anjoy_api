package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsDynamicMapper;
import com.coracle.dms.dao.mybatis.DmsDynamicProductMapper;
import com.coracle.dms.dto.DmsRetailOutStorageDto;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsCustomersVo;
import com.coracle.dms.vo.DmsDynamicVo;
import com.coracle.dms.vo.DmsStorageInventoryVo;
import com.coracle.dms.vo.DmsStorageVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DmsDynamicServiceImpl extends BaseServiceImpl<DmsDynamic> implements DmsDynamicService {
    private static final Logger logger = Logger.getLogger(DmsDynamicServiceImpl.class);

    @Autowired
    private DmsDynamicMapper dmsDynamicMapper;
    @Autowired
    private DmsDynamicProductMapper dmsDynamicProductMapper;
    @Autowired
    private DmsCustomersService dmsCustomersService;
    @Autowired
    private DmsStorageService dmsStorageService;
    @Autowired
    private DmsSellerService dmsSellerService;
    @Autowired
    private DmsStorageInventoryService dmsStorageInventoryService;
    @Autowired
    private DmsStoreService dmsStoreService;

    @Override
    public IMybatisDao<DmsDynamic> getBaseDao() {
        return dmsDynamicMapper;
    }

    /***
     * 新增客户动态
     * @param dmsDynamicVo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void addCustomerDynamic(DmsDynamicVo dmsDynamicVo, UserSession userSession){
        checkParams(dmsDynamicVo);
        List<DmsDynamicProduct> dmsDynamicProductList = dmsDynamicVo.getDmsDynamicProductList();
        dmsDynamicMapper.insert(dmsDynamicVo);
        if (BlankUtil.isNotEmpty(dmsDynamicProductList)&&dmsDynamicProductList.size()>0){
            Date date = new Date();
            for(DmsDynamicProduct dmsDynamicProduct:dmsDynamicProductList){
                dmsDynamicProduct.setDynamicId(dmsDynamicVo.getId());
                dmsDynamicProduct.setCreatedBy(userSession.getId());
                dmsDynamicProduct.setCreatedDate(date);
                dmsDynamicProduct.setLastUpdatedBy(userSession.getId());
                dmsDynamicProduct.setLastUpdatedDate(date);
                dmsDynamicProduct.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            }
        }
        dmsDynamicProductMapper.batchInsert(dmsDynamicProductList);
        updateStorageInventory(userSession,dmsDynamicProductList);
    }

    /**
     * 分页获取客户购买记录列表
     * @param dmsDynamicVo
     * @return
     */
    public PageHelper.Page<DmsDynamicVo> selectForPage(DmsDynamicVo dmsDynamicVo){
        try {
            PageHelper.startPage(dmsDynamicVo.getP(), dmsDynamicVo.getS());
            dmsDynamicMapper.getPageList(dmsDynamicVo);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            logger.error("分页获取客户购买记录列表报错信息：",e);
            throw new ServiceException("客户购买记录分页查询异常--->>>");
        }
    }

    /**
     * 获取全部购买记录列表
     * @param dmsDynamicVo
     * @return
     */
    public List<DmsDynamicVo> getAllList(DmsDynamicVo dmsDynamicVo){
        return dmsDynamicMapper.getPageList(dmsDynamicVo);
    }

    /***
     * 新增零售出库记录
     * @param dmsRetailOutStorageDto
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void addRetailOutStorage(DmsRetailOutStorageDto dmsRetailOutStorageDto, UserSession userSession){
        Date date = new Date();
        Long customerId = 0L;
        DmsCustomersVo dmsCustomersVo = dmsRetailOutStorageDto.getDmsCustomersVo();//客户信息
        if (BlankUtil.isNotEmpty(dmsCustomersVo)&&BlankUtil.isNotEmpty(dmsCustomersVo.getName())&&BlankUtil.isNotEmpty(dmsCustomersVo.getPhone())) {
            dmsCustomersVo.setCreatedBy(userSession.getId());
            dmsCustomersVo.setLastUpdatedBy(userSession.getId());
            dmsCustomersVo.setCreatedDate(date);
            dmsCustomersVo.setLastUpdatedDate(date);
            dmsCustomersVo.setUserId(userSession.getId());
            dmsCustomersVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            customerId = dmsCustomersService.insertCustomer(dmsCustomersVo, userSession.getId());//新增客户
        }
        DmsDynamicVo dmsDynamicVo = dmsRetailOutStorageDto.getDmsDynamicVo();//动态信息
        checkParams(dmsDynamicVo);
        dmsDynamicVo.setCreatedBy(userSession.getId());
        dmsDynamicVo.setLastUpdatedBy(userSession.getId());
        dmsDynamicVo.setCreatedDate(date);
        dmsDynamicVo.setLastUpdatedDate(date);
        dmsDynamicVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        if (BlankUtil.isNotEmpty(customerId)&&customerId>0){
            dmsDynamicVo.setCustomerId(customerId);
        }else {
            dmsDynamicVo.setCustomerId(0L);
        }
        addCustomerDynamic(dmsDynamicVo,userSession);//增加客户动态记录
    }

    /**
     * 更新库存
     * @param userSession
     * @param dmsDynamicProductList
     */
    private void updateStorageInventory(UserSession userSession,List<DmsDynamicProduct> dmsDynamicProductList){
        DmsStore dmsStore = dmsStoreService.getStoreInfoByUserId(userSession.getId());
        if (BlankUtil.isEmpty(dmsStore)||BlankUtil.isEmpty(dmsStore.getId())||dmsStore.getId()==0){
            throw new ServiceException("获取所属门店异常");
        }
        Long storeId  = dmsStore.getId();
        int type = 0;
        if (dmsStore.getOperateWay() == DmsModuleEnums.STORE_OPERATE_WAY.DIRECT.getType()){
            type = DmsModuleEnums.STORAGE_TYPE.BRAND.getType();
        }else {
            type = DmsModuleEnums.STORAGE_TYPE.STORE.getType();
        }
        DmsStorage storage = dmsStorageService.selectByRelation(storeId,type);
        if (BlankUtil.isEmpty(storage)){
            throw new ServiceException("获取门店仓库异常");
        }
        for(DmsDynamicProduct dmsDynamicProduct:dmsDynamicProductList){
            DmsStorageInventoryVo paramVo = new DmsStorageInventoryVo();
            paramVo.setOutOrgId(storeId);//出库机构ID
            paramVo.setOutOrgType(DmsModuleEnums.STORAGE_TYPE.STORE.getType());
            paramVo.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.SALE_OUT.getType());//属于销售出库
            paramVo.setAddOrSubtractNum(dmsDynamicProduct.getProductNum());
            paramVo.setOutInType(DmsModuleEnums.STORAGE_OUTIN.OUT.getType());
            paramVo.setStorage(storage.getId());
            paramVo.setProductId(dmsDynamicProduct.getProductId());
            paramVo.setProductSpecId(dmsDynamicProduct.getProductSpecId());
            paramVo.setTransFlag(false);
            dmsStorageInventoryService.addOrSubtract(paramVo,userSession);
        }
    }

    /**
     * 统一检验参数
     * @param dmsDynamicVo
     */
    private void checkParams(DmsDynamicVo dmsDynamicVo) {
        if (dmsDynamicVo == null) {
            throw new ServiceException("参数异常");
        }
        if (BlankUtil.isEmpty(dmsDynamicVo.getDmsDynamicProductList())) {
            throw new ServiceException("购买产品不能为空");
        }
        if (BlankUtil.isEmpty(dmsDynamicVo.getSendDate())) {
            throw new ServiceException("发货日期不能为空");
        }
    }

    /**
     * 统一检验参数
     * @param dmsCustomersVo
     */
    private void checkParams(DmsCustomersVo dmsCustomersVo) {
        if (dmsCustomersVo == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(dmsCustomersVo.getName())) {
            throw new ServiceException("客户名称不能为空");
        }
        if (StringUtils.isBlank(dmsCustomersVo.getPhone())) {
            throw new ServiceException("客户手机不能为空");
        }
    }
}