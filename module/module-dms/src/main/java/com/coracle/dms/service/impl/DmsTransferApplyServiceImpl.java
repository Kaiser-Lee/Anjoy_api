package com.coracle.dms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coracle.dms.constant.DmsRoleCodeConstants;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsTransferApplyMapper;
import com.coracle.dms.dao.mybatis.DmsTransferDeliveryMapper;
import com.coracle.dms.dao.mybatis.DmsTransferStatusMapper;
import com.coracle.dms.dto.DmsTransferApplyDto;
import com.coracle.dms.support.SystemParamerConfiguration;
import com.coracle.dms.vo.DmsStorageInventoryVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.util.BeanConvertHelper;
/**
 * 调货业务实现类
 * @author tanyb
 *
 */
@Service
public class DmsTransferApplyServiceImpl extends BaseServiceImpl<DmsTransferApply> implements DmsTransferApplyService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DmsTransferApplyMapper dmsTransferApplyMapper;
    @Autowired
    private DmsTransferDeliveryMapper dmsTransferDeliveryMapper;
    @Autowired
    private DmsTransferStatusMapper dmsTransferStatusMapper;
    @Autowired
    private DmsStorageOutInRecordService dmsStorageOutInRecordService;
    @Autowired
    private DmsStorageInventoryService dmsStorageInventoryService;
    @Autowired
    private DmsStorageTransportationService dmsStorageTransportationService;
    @Autowired
    private DmsStorageService dmsStorageService;
    @Autowired
    private DmsProductService dmsProductService;
	@Autowired
	private DmsMessageService dmsMessageService;
	@Autowired
	private DmsSellerService dmsSellerService;
	@Autowired
	private DmsChannelContactsService dmsChannelContactsService;
	@Autowired
	private DmsDataDictionayDependentService dmsDataDictionayDependentService;
	@Autowired
	private DmsChannelService dmsChannelService;
	@Autowired
	private DmsStoreService dmsStoreService;
	@Autowired
	private DmsOrderOperationLogService dmsOrderOperationLogService;
	 
    @Override
    public IMybatisDao<DmsTransferApply> getBaseDao() {
        return dmsTransferApplyMapper;
    }

    @SuppressWarnings("unchecked")
	@Override
	public Page<DmsTransferApplyDto> selectForListPage(DmsTransferApplyDto search) {
		try {
            PageHelper.startPage(search.getP(), search.getS());
            this.dmsTransferApplyMapper.getPageList(search);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            this.logger.error("分页查询调货列表失败！",e);
            throw new ServiceException("分页查询异常--->>>");
        }
	}
    
    @Override
	public DmsTransferApplyDto detail(Long id, UserSession session) {
        Long orgId = session.getOrgId();
    	if(id == null || orgId == null || orgId == 0){
    		throw new ServiceException("参数错误");
    	}
    	DmsTransferApplyDto transferDto = this.dmsTransferApplyMapper.selectDetailByPrimaryKey(id, orgId);
    	if(transferDto == null){
    		throw new ServiceException("获取调货详情数据不存在");
    	}


    	Integer storageType = 0;  //仓库类型参数，只获取自己的库存(渠道商只获取渠道商的，门店只获取门店的)
    	String roleCode = session.getRoleCode();
    	if (roleCode.equals(DmsRoleCodeConstants.CO)) {  //渠道
            storageType = 2;
		} else if (roleCode.equals(DmsRoleCodeConstants.SR)) {  //门店
            storageType = 3;
		}
    	Integer inventory = this.dmsTransferApplyMapper.selectTransferInventory(transferDto.getBillProductId(), transferDto.getProductSpecId(), storageType, orgId);
    	transferDto.setInventory(inventory);
		return transferDto;
	}
    
    /**
     * 调货申请.创建调货单
     * @author tanyb
     */
	@Override
	public void create(DmsTransferApplyDto paramVo,UserSession session) {
		paramVo.setApplyNo(SystemParamerConfiguration.getInstance().createSerialNumStr(DmsModuleEnums.TRANSFER_SERIAL_KEY));
		paramVo.setApplyDate(paramVo.getApplyDate() == null ? new Date() : paramVo.getApplyDate());
		this.checkParam(paramVo,true);
		DmsProduct product = this.dmsProductService.selectByPrimaryKey(paramVo.getBillProductId());
		if(product == null){
			 throw new ServiceException("产品不存在！");
		}
		/**
		 * 根据调货类型判断状态和数据记录创建
		 */
		if(paramVo.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.CHANNEL_PH.getType()){
			//查询渠道仓库信息
			DmsStorage storage = this.dmsStorageService.selectByRelation(paramVo.getDeliveryStoreId(), DmsModuleEnums.STORAGE_TYPE.CHANNEL.getType());
			if (storage == null) {
				throw new ServiceException("所在组织无仓库，请维护仓库数据");
			}
			paramVo.setProcessStatus(DmsModuleEnums.PROCESS_STATUS.UNPROCESS.getType());
			paramVo.setDeliveryStatus(DmsModuleEnums.DELIVERY_STATUS.WAIT_DELIVERY.getType());
			paramVo.setReceiveStatus(DmsModuleEnums.RECEIVE_STATUS.RECEIVED.getType());
			DmsTransferApply applyEntity = BeanConvertHelper.copyProperties(paramVo, DmsTransferApply.class);
			this.dmsTransferApplyMapper.insert(applyEntity);
			Long applyId = applyEntity.getId();
			DmsTransferDelivery deliver = BeanConvertHelper.copyProperties(paramVo, DmsTransferDelivery.class);
			deliver.setApplyId(applyId);
			deliver.setCreatedBy(session.getId());
			deliver.setCreatedDate(new Date());
			deliver.setRemoveFlag(0);
			this.dmsTransferDeliveryMapper.insert(deliver);
			//保存调货状态
			this.createTransferStatus(applyId, paramVo.getDeliveryStoreId(), DmsModuleEnums.PROCESS_STATUS.PROCESSED.getType(), session); //渠道已处理
			this.createTransferStatus(applyId, paramVo.getReceiveStoreId(), DmsModuleEnums.PROCESS_STATUS.UNPROCESS.getType(), session); //门店未处理
			this.createInventory(applyEntity, paramVo, storage, session);
			this.dmsOrderOperationLogService.createTransferOperLog(applyId, "发货", session.getId());
			DmsProduct dmsProduct = dmsProductService.selectByPrimaryKey(applyEntity.getBillProductId());
			sendMessage(1,applyId,paramVo.getDeliveryStoreId(),paramVo.getReceiveStoreId(),deliver.getDeliveryType(),session.getId(),applyEntity.getApplyNo(),BlankUtil.isNotEmpty(dmsProduct)?dmsProduct.getName():"");//铺货发货消息type =1
		} else if (paramVo.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.STORE_DH.getType()
				|| paramVo.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.CHANNEL_DH.getType()) {
			// 门店之间调货（或者门店向渠道调货）只会产生调货申请记录，发货记录为空
			paramVo.setProcessStatus(DmsModuleEnums.PROCESS_STATUS.UNPROCESS.getType());
			paramVo.setDeliveryStatus(DmsModuleEnums.DELIVERY_STATUS.WAIT.getType());
			paramVo.setReceiveStatus(DmsModuleEnums.DELIVERY_STATUS.WAIT.getType());
			DmsTransferApply applyEntity = BeanConvertHelper.copyProperties(paramVo, DmsTransferApply.class);
			this.dmsTransferApplyMapper.insert(applyEntity);
			this.createTransferStatus(applyEntity.getId(), paramVo.getDeliveryStoreId(), DmsModuleEnums.PROCESS_STATUS.UNPROCESS.getType(), session); // 渠道未处理
			this.createTransferStatus(applyEntity.getId(), paramVo.getReceiveStoreId(), DmsModuleEnums.PROCESS_STATUS.UNPROCESS.getType(), session); // 门店未处理
			this.dmsOrderOperationLogService.createTransferOperLog(applyEntity.getId(), "申请", session.getId());
			int type = 0;
			if (paramVo.getApplyType()==DmsModuleEnums.TRANSFER_APPLY_TYPE.STORE_DH.getType()) type = 2;
			else if (paramVo.getApplyType()==DmsModuleEnums.TRANSFER_APPLY_TYPE.CHANNEL_DH.getType()) type = 3;
			DmsProduct dmsProduct = dmsProductService.selectByPrimaryKey(applyEntity.getBillProductId());
			sendMessage(type,applyEntity.getId(),paramVo.getDeliveryStoreId(),paramVo.getReceiveStoreId(),"",session.getId(),applyEntity.getApplyNo(),BlankUtil.isNotEmpty(dmsProduct)?dmsProduct.getName():"");//门店门店申请type =2，门店渠道申请type=3
		} else {
			throw new ServiceException("参数异常");
		}
	}
	
	private void createTransferStatus(Long transferId,Long storeId,Integer processStatus,UserSession session){
		DmsTransferStatus transferStatus = new DmsTransferStatus();
		transferStatus.setTransferId(transferId);
		transferStatus.setStoreId(storeId);
		transferStatus.setProcessStatus(processStatus);
		transferStatus.setCreatedBy(session.getId());
		transferStatus.setCreatedDate(new Date());
		transferStatus.setRemoveFlag(0);
		this.dmsTransferStatusMapper.insert(transferStatus);
	}

	private void updateTransferStatus(Long transferId,Long storeId,Integer processStatus,UserSession session){
		DmsTransferStatus statusSearch = new DmsTransferStatus();
		statusSearch.setStoreId(storeId);
		statusSearch.setTransferId(transferId);
		List<DmsTransferStatus> statusList = this.dmsTransferStatusMapper.selectByCondition(statusSearch);
		if(BlankUtil.isEmpty(statusList)){
			throw new ServiceException("修改处理状态异常！");
		}
		DmsTransferStatus transferStatus = statusList.get(0);
		transferStatus.setProcessStatus(processStatus);
		transferStatus.setLastUpdatedDate(new Date());
		transferStatus.setLastUpdatedBy(session.getId());
		this.dmsTransferStatusMapper.updateByPrimaryKeySelective(transferStatus);
	}
	/**
	 * 发货-增加出库记录，扣库存，增加在途
	 * @param paramVo
	 * @param session
	 */
	@Override
	public void createDelivery(DmsTransferApplyDto paramVo,UserSession session) {
		this.checkParam(paramVo,false);
		DmsTransferApply entity = this.dmsTransferApplyMapper.selectByPrimaryKey(paramVo.getId());
        if(entity == null){  
        	throw new ServiceException("数据库获取数据不存在！");
        }
        //查询门店或者渠道仓库信息发货
        int storageRelationType = 0;
        int type = 0;
        if(entity.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.STORE_DH.getType() ){
			type = 9;
			// storageRelationType = DmsModuleEnums.STORAGE_TYPE.STORE.getType();
			// 需要判断直营门店
			DmsStore dmsStore = dmsStoreService.selectByPrimaryKey(paramVo.getDeliveryStoreId());
			if (BlankUtil.isEmpty(dmsStore) || BlankUtil.isEmpty(dmsStore.getId()) || dmsStore.getId() == 0) {
				throw new ServiceException("获取门店数据为空");
			}
			if (dmsStore.getOperateWay().intValue() == DmsModuleEnums.STORE_OPERATE_WAY.DIRECT.getType()) {
				storageRelationType = DmsModuleEnums.STORAGE_TYPE.BRAND.getType();
			} else {
				storageRelationType = DmsModuleEnums.STORAGE_TYPE.STORE.getType();
			}
        }else if(entity.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.CHANNEL_DH.getType()){
			type = 8;
        	storageRelationType=DmsModuleEnums.STORAGE_TYPE.CHANNEL.getType();
        } else {
        	throw new ServiceException("获取调货类型参数异常");
        }
		DmsStorage storage = this.dmsStorageService.selectByRelation(paramVo.getDeliveryStoreId(), storageRelationType);
		if (storage == null) {
			throw new ServiceException("获取仓库数据不存在！");
		}
        entity.setDeliveryStatus(DmsModuleEnums.DELIVERY_STATUS.WAIT_DELIVERY.getType());
        entity.setReceiveStatus(DmsModuleEnums.RECEIVE_STATUS.RECEIVED.getType());
        entity.setLastUpdatedBy(session.getId());
        entity.setLastUpdatedDate(new Date());
		this.dmsTransferApplyMapper.updateByPrimaryKeySelective(entity);
		//创建发货记录
		Long applyId = paramVo.getId();
		DmsTransferDelivery deliver = BeanConvertHelper.copyProperties(paramVo, DmsTransferDelivery.class);
		DmsTransferDelivery deliverEntity = this.dmsTransferDeliveryMapper.selectByApplyId(applyId);
		if(deliverEntity == null){
			deliver.setApplyId(applyId);
			deliver.setCreatedBy(session.getId());
			deliver.setCreatedDate(new Date());
			deliver.setRemoveFlag(0);
			this.dmsTransferDeliveryMapper.insert(deliver);
			this.createInventory(entity, paramVo, storage, session);
		} else {
			deliver.setId(deliverEntity.getId());
			deliver.setApplyId(applyId);
			deliver.setLastUpdatedBy(session.getId());
			deliver.setLastUpdatedDate(new Date());
			this.dmsTransferDeliveryMapper.updateByPrimaryKeySelective(deliver);
		}
		//修改处理状态-修改发货单位为已处理
		this.updateTransferStatus(applyId, paramVo.getDeliveryStoreId(), DmsModuleEnums.PROCESS_STATUS.PROCESSED.getType(), session);
		this.dmsOrderOperationLogService.createTransferOperLog(applyId, "发货", session.getId());
		DmsProduct dmsProduct = dmsProductService.selectByPrimaryKey(entity.getBillProductId());
		sendMessage(type,entity.getId(),entity.getDeliveryStoreId(),entity.getReceiveStoreId(),deliver.getDeliveryType(),session.getId(),entity.getApplyNo(),BlankUtil.isNotEmpty(dmsProduct)?dmsProduct.getName():"");//门店发货type =9，渠道发货type=8
	}
	
	/**
	 * 发货调用库存接口.库存数量减少，创建库存记录，在途库存
	 * @param entity
	 * @param paramVo
	 * @param storage
	 * @param session
	 */
	private void createInventory(DmsTransferApply entity,DmsTransferApplyDto paramVo,DmsStorage storage,UserSession session){
		//减少出库记录，扣库存
		DmsStorageInventoryVo inventory = new DmsStorageInventoryVo();
		inventory.setInOrgId(entity.getReceiveStoreId());
		int outType = 0;
		if(entity.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.STORE_DH.getType() ){
			outType = DmsModuleEnums.STORAGE_TYPE.STORE.getType();
        }else if(entity.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.CHANNEL_PH.getType()
        		|| entity.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.CHANNEL_DH.getType()){
        	outType = DmsModuleEnums.STORAGE_TYPE.CHANNEL.getType();
        }
		inventory.setOutOrgType(outType);
		inventory.setOutOrgId(entity.getDeliveryStoreId());
		inventory.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.ALLOCATION_OUT.getType());
		inventory.setProductId(entity.getBillProductId());
		inventory.setProductSpecId(entity.getProductSpecId());
		inventory.setStorage(storage.getId());
		inventory.setOutInType(DmsModuleEnums.STORAGE_OUTIN.OUT.getType());
		inventory.setAddOrSubtractNum(paramVo.getDeliveryNum());
		inventory.setTransFlag(true);
		this.dmsStorageInventoryService.addOrSubtract(inventory, session);
		//创建在途记录
		DmsStorageTransportation trans = new DmsStorageTransportation();
		trans.setRelationId(entity.getId());
		trans.setRelationType(DmsModuleEnums.RELATION_TYPE.TRANSFER.getType());
		trans.setSendBill(entity.getApplyNo());
		trans.setProductId(entity.getBillProductId());
		trans.setProductSpecId(entity.getProductSpecId());
		trans.setTransportationNum(paramVo.getDeliveryNum());
		trans.setSendStorage(storage.getId());
		this.dmsStorageTransportationService.create(trans,session);
	}
	/**
	 * 确认收货-增加入库记录，加库存，减少在途
	 * @param paramVo
	 * @param session
	 */
	@Override
	public void updateReceived(DmsTransferApplyDto paramVo,UserSession session) {
		if(paramVo == null || paramVo.getId() == null){
    		throw new ServiceException("参数异常");
    	}
		DmsTransferApplyDto entityDto = this.dmsTransferApplyMapper.selectDtoByPrimaryKey(paramVo.getId());
        if(entityDto == null){  
        	throw new ServiceException("数据库获取数据不存在！");
        }
        //收货仓库（肯定是门店）.需要判断直营门店
        int storageRelationType = 0;
		DmsStore dmsStore = dmsStoreService.selectByPrimaryKey(entityDto.getReceiveStoreId());
		if (BlankUtil.isEmpty(dmsStore) || BlankUtil.isEmpty(dmsStore.getId()) || dmsStore.getId() == 0) {
			throw new ServiceException("获取门店数据为空");
		}
		if (dmsStore.getOperateWay().intValue() == DmsModuleEnums.STORE_OPERATE_WAY.DIRECT.getType()) {
			storageRelationType = DmsModuleEnums.STORAGE_TYPE.BRAND.getType();
		} else {
			storageRelationType = DmsModuleEnums.STORAGE_TYPE.STORE.getType();
		}
		DmsStorage storage = this.dmsStorageService.selectByRelation(entityDto.getReceiveStoreId(), storageRelationType);
		if (storage == null) {
			throw new ServiceException("收货无仓库，请维护仓库数据");
		}
        DmsTransferApply entity = new DmsTransferApply();
        entity.setId(entityDto.getId());
        entity.setProcessStatus(DmsModuleEnums.PROCESS_STATUS.PROCESSED.getType());
        entity.setDeliveryStatus(DmsModuleEnums.DELIVERY_STATUS.FINISH.getType());
        entity.setReceiveStatus(DmsModuleEnums.RECEIVE_STATUS.FINISH.getType());
        entity.setLastUpdatedBy(session.getId());
        entity.setLastUpdatedDate(new Date());
		this.dmsTransferApplyMapper.updateByPrimaryKeySelective(entity);
        
        int type = 0;
        if(entityDto.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.STORE_DH.getType() ){
//        	storageRelationType = DmsModuleEnums.STORAGE_TYPE.STORE.getType();
			type = 5;
        }else if(entityDto.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.CHANNEL_PH.getType()
        		|| entityDto.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.CHANNEL_DH.getType()){  //渠道铺货或者门店向渠道调货发货单位就是渠道
//        	storageRelationType=DmsModuleEnums.STORAGE_TYPE.CHANNEL.getType();
			type = 4;
        } else {
        	throw new ServiceException("获取调货类型参数异常");
        }
//		DmsStorage deliStorage = this.dmsStorageService.selectByRelation(entityDto.getDeliveryStoreId(), storageRelationType);
//		if (deliStorage == null) {
//			throw new ServiceException("发货无仓库，请维护仓库数据");
//		}
		//增加库存
		DmsStorageInventoryVo inventoryAdd = new DmsStorageInventoryVo();
		inventoryAdd.setInOrgId(entityDto.getReceiveStoreId());
		inventoryAdd.setInOrgType(DmsModuleEnums.STORAGE_TYPE.STORE.getType());
		inventoryAdd.setOutOrgId(entityDto.getDeliveryStoreId());
		inventoryAdd.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.ALLOCATION_IN.getType());
		inventoryAdd.setProductId(entityDto.getBillProductId());
		inventoryAdd.setProductSpecId(entityDto.getProductSpecId());
		inventoryAdd.setStorage(storage.getId());
		inventoryAdd.setOutInType(DmsModuleEnums.STORAGE_OUTIN.IN.getType()); //入库
		inventoryAdd.setAddOrSubtractNum(entityDto.getDeliveryNum().intValue());
		this.dmsStorageInventoryService.addOrSubtract(inventoryAdd, session);
		//删除在途库存
		DmsStorageTransportation trans = new DmsStorageTransportation();
		trans.setRelationId(entityDto.getId());
		trans.setRelationType(DmsModuleEnums.RELATION_TYPE.TRANSFER.getType());
		this.dmsStorageTransportationService.updateTrans(trans);
		//修改收货单位状态-已处理
		this.updateTransferStatus(entityDto.getId(), entityDto.getReceiveStoreId(), DmsModuleEnums.PROCESS_STATUS.PROCESSED.getType(), session);
		this.dmsOrderOperationLogService.createTransferOperLog(entityDto.getId(), "确认收货", session.getId());
		this.dmsOrderOperationLogService.createTransferOperLog(entityDto.getId(), "完成", session.getId());
		DmsProduct dmsProduct = dmsProductService.selectByPrimaryKey(entityDto.getBillProductId());
		sendMessage(type,entityDto.getId(),entityDto.getDeliveryStoreId(),entityDto.getReceiveStoreId(),"",session.getId(),entityDto.getApplyNo(),BlankUtil.isNotEmpty(dmsProduct)?dmsProduct.getName():"");//门店发货type =5，渠道发货type=4
	}
    /**
     * 取消调货申请
     */
	@Override
	public void updateCancel(DmsTransferApplyDto paramVo,UserSession session) {
		if(paramVo == null || paramVo.getId() == null){
    		throw new ServiceException("参数异常");
    	}
		DmsTransferApply entity = this.dmsTransferApplyMapper.selectByPrimaryKey(paramVo.getId());
        if(entity == null){  
        	throw new ServiceException("数据库获取数据不存在！");
        }
        //取消调货只能取消待发货的单
        if(!entity.getReceiveStatus().equals(DmsModuleEnums.DELIVERY_STATUS.WAIT.getType())){
        	throw new ServiceException("调货申请已经发货，不能取消");
        }
        entity.setDeliveryStatus(DmsModuleEnums.DELIVERY_STATUS.CANCEL.getType());
        entity.setReceiveStatus(DmsModuleEnums.RECEIVE_STATUS.CANCEL.getType());
        entity.setLastUpdatedBy(session.getId());
        entity.setLastUpdatedDate(new Date());
		this.dmsTransferApplyMapper.updateByPrimaryKeySelective(entity);
		// 已取消-收货发货单位修改已处理
		this.updateTransferStatus(entity.getId(), entity.getDeliveryStoreId(), DmsModuleEnums.PROCESS_STATUS.PROCESSED.getType(), session);
		this.updateTransferStatus(entity.getId(), entity.getReceiveStoreId(), DmsModuleEnums.PROCESS_STATUS.PROCESSED.getType(), session);
		this.dmsOrderOperationLogService.createTransferOperLog(entity.getId(), "取消", session.getId());
		this.dmsOrderOperationLogService.createTransferOperLog(entity.getId(), "完成", session.getId());
		int type = 0;
		if(entity.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.STORE_DH.getType() ){
			type = 7;
		}else if(entity.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.CHANNEL_PH.getType()
				|| entity.getApplyType() == DmsModuleEnums.TRANSFER_APPLY_TYPE.CHANNEL_DH.getType()){  //渠道铺货或者门店向渠道调货发货单位就是渠道
			type = 6;
		}
		DmsProduct dmsProduct = dmsProductService.selectByPrimaryKey(entity.getBillProductId());
		sendMessage(type,entity.getId(),entity.getDeliveryStoreId(),entity.getReceiveStoreId(),"",session.getId(),entity.getApplyNo(),BlankUtil.isNotEmpty(dmsProduct)?dmsProduct.getName():"");//门店发货type =5，渠道发货type=4
	}
	
	private void checkParam(DmsTransferApplyDto paramVo,boolean addFlag) {
		if (paramVo == null) {
			throw new ServiceException("参数异常");
		}
		if(addFlag){
			if(paramVo.getApplyType() == null || paramVo.getApplyType() == 0){
				throw new ServiceException("调货类型不能为空");
			}
			if (paramVo.getBillProductId() == null || paramVo.getBillProductId() == 0) {
				throw new ServiceException("产品ID不能为空");
			}
//			if (paramVo.getProductSpecId() == null || paramVo.getProductSpecId() == 0) {
//				throw new ServiceException("产品规格ID不能为空");
//			}
			if (paramVo.getApplyNum() == null || paramVo.getApplyNum() == 0) {
				throw new ServiceException("调货数量不能为空或者不能为零");
			}
			if (paramVo.getDeliveryStoreId() == null || paramVo.getDeliveryStoreId() == 0) {
				throw new ServiceException("出货单位不能为空");
			}
			if (paramVo.getReceiveStoreId() == null || paramVo.getReceiveStoreId() == 0) {
				throw new ServiceException("收货单位不能为空");
			}
		} else {
			if(paramVo == null || paramVo.getId() == null){
	    		throw new ServiceException("参数异常");
	    	}
			if(paramVo.getDeliveryNum() == null || paramVo.getDeliveryNum() == 0){
				throw new ServiceException("发货数量不能为空或者为零");
			}
			if(BlankUtil.isEmpty(paramVo.getReceiver())){
				throw new ServiceException("收货人不能为空");
			}
			if(BlankUtil.isEmpty(paramVo.getMobile())){
				throw new ServiceException("收货人手机不能为空");
			}
			if(BlankUtil.isEmpty(paramVo.getAddress())){
				throw new ServiceException("收货地址不能为空");
			}
		}
		
	}

	private void sendMessage(Integer type,Long transferId,Long outId,Long inId,String expressWay,Long userId,String code,String productName){
		List<Long> staffIds = new ArrayList<>();
		List<Long> staffIds2 = new ArrayList<>();
		String title = "";
		String subject = "";
		String content = "";
		int messageType = DmsModuleEnums.MESSAGE_TYPE.MESSAGE_GOODS.getType();
		int entityType =DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_TRANSFER_APPLY.getType();
		if (type==1){//渠道铺货发货
			staffIds = getChannelContacts(outId);//出货单位，渠道商
			staffIds2 = getStoreContacts(inId);//入货单位，门店
			String orgName = getOrgName(outId,1);//发货组织名称
			title = (BlankUtil.isEmpty(orgName)?"":orgName)+" 已经发货";
			if (BlankUtil.isEmpty(expressWay)) content = "已发货。";
			else {
				String getStr = dmsDataDictionayDependentService.getDataValueName("deliver_type",expressWay);
				if(BlankUtil.isEmpty(getStr)) content = "已发货。";
				else content = "已发货。配送方式："+getStr+"。";
			}
			subject = "商品："+productName;
		}else if(type==2){//门店门店调货
			staffIds = getStoreContacts(inId);//入货单位，门店
			staffIds2 = getStoreContacts(outId);//出货单位，门店
			String orgName = getOrgName(inId,2);//申请组织名称
			title = "调货申请";
			subject = (BlankUtil.isEmpty(orgName)?"":orgName)+" 提交了新的调货申请 商品："+productName;
			content = "发起了新的调货申请。";
		}else if(type==3){//门店渠道调货
			staffIds = getStoreContacts(inId);//入货单位，门店
			staffIds2 = getChannelContacts(outId);//出货单位，渠道
			String orgName = getOrgName(inId,2);//申请组织名称
			title = "调货申请";
			subject = (BlankUtil.isEmpty(orgName)?"":orgName)+" 提交了新的调货申请 商品："+productName;
			content = "发起了新的调货申请。";
		}else if(type==4){//确认收货（渠道发货）
			staffIds = getStoreContacts(inId);//入货单位，门店
			staffIds2 = getChannelContacts(outId);//出货单位，渠道
			title = "商品已签收";
			subject = "调货单号："+code+" 商品："+productName;
			content = "已确认收货。";
		}else if(type==5){//确认收货（门店发货）
			staffIds = getStoreContacts(inId);//入货单位，门店
			staffIds2 = getStoreContacts(outId);//出货单位，门店
			title = "商品已签收";
			subject = "调货单号："+code+" 商品："+productName;
			content = "已确认收货。";
		}else if(type==6){//取消调货（渠道发货）
			staffIds = getStoreContacts(inId);//入货单位，门店
			staffIds2 = getChannelContacts(outId);//出货单位，渠道
			String orgName = getOrgName(inId,2);//申请组织名称
			title = "调货申请已取消";
			subject = (BlankUtil.isEmpty(orgName)?"":orgName)+"门店取消了调货申请 商品："+productName;
			content = "申请已取消，交易关闭。";
		}else if(type==7){//取消调货（门店发货）
			staffIds = getStoreContacts(inId);//入货单位，门店
			staffIds2 = getStoreContacts(outId);//出货单位，门店
			String orgName = getOrgName(inId,2);//申请组织名称
			title = "调货申请已取消";
			subject = (BlankUtil.isEmpty(orgName)?"":orgName)+"门店取消了调货申请 商品："+productName;
			content = "申请已取消，交易关闭。";
		}else if(type==8){//调货发货（渠道发货）
			staffIds2 = getStoreContacts(inId);//入货单位，门店
			staffIds = getChannelContacts(outId);//出货单位，渠道
			//String orgName = getOrgName(outId,1);//申请组织名称
			title = "您的商品已发货，请耐心等待";
			subject = "商品："+productName;
			content = "已发货。";
		}else if(type==9){//调货发货（门店发货）
			staffIds2 = getStoreContacts(inId);//入货单位，门店
			staffIds = getStoreContacts(outId);//出货单位，门店
			//String orgName = getOrgName(outId,2);//申请组织名称
			title = "您的商品已发货，请耐心等待";
			subject = "商品："+productName;
			content = "已发货。";
		}
		dmsMessageService.sendMessage(messageType, entityType, transferId,title,subject, content, staffIds,0,userId);
		dmsMessageService.sendMessage(messageType, entityType, transferId,title,subject, content, staffIds2,1,userId);
	}

	private List<Long> getChannelContacts(Long channelId){
		List<Long> staffIds = new ArrayList<>();
		DmsChannelContacts dmsChannelContacts = new DmsChannelContacts();
		dmsChannelContacts.setChannelId(channelId);
		dmsChannelContacts.setRemoveFlag(0);
		List<DmsChannelContacts> dmsChannelContactsList = dmsChannelContactsService.selectByCondition(dmsChannelContacts);
		if (BlankUtil.isNotEmpty(dmsChannelContactsList)){
			for(DmsChannelContacts dcc:dmsChannelContactsList){
				if (BlankUtil.isNotEmpty(dcc.getUserId())&&dcc.getUserId()>0) staffIds.add(dcc.getUserId());
			}
		}
		return staffIds;
	}
	private List<Long> getStoreContacts(Long storeId){
		List<Long> staffIds = new ArrayList<>();
		DmsSeller dmsSeller = new DmsSeller();
		dmsSeller.setShopId(storeId);
		dmsSeller.setRemoveFlag(0);
		List<DmsSeller> dmsSellerList = dmsSellerService.selectByCondition(dmsSeller);
		if (BlankUtil.isNotEmpty(dmsSellerList)){
			for(DmsSeller ds:dmsSellerList){
				if (BlankUtil.isNotEmpty(ds.getUserId())&&ds.getUserId()>0) staffIds.add(ds.getUserId());
			}
		}
		return staffIds;
	}
	private String getOrgName(Long id,Integer type){
		if (type==1){//渠道
			DmsChannel dmsChannel = dmsChannelService.selectByPrimaryKey(id);
			return BlankUtil.isEmpty(dmsChannel) ? "" : dmsChannel.getName();
		}else {//门店
			DmsStore dmsStore = dmsStoreService.selectByPrimaryKey(id);
			return BlankUtil.isEmpty(dmsStore) ? "" : dmsStore.getName();
		}
	}
}