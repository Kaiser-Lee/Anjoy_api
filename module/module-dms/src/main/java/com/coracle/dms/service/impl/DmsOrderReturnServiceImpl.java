package com.coracle.dms.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coracle.dms.dao.mybatis.*;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;

import com.coracle.dms.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.support.SystemParamerConfiguration;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiruo.medbid.components.StringUtils;
import com.xiruo.medbid.util.BeanConvertHelper;

/**
 * 退换货业务层
 * @author tanyb
 */
@Service
public class DmsOrderReturnServiceImpl extends BaseServiceImpl<DmsOrderReturn> implements DmsOrderReturnService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DmsOrderMapper dmsOrderMapper;
    @Autowired
    private DmsOrderReturnMapper dmsOrderReturnMapper;
    @Autowired
    private DmsOrderReturnItemMapper dmsOrderReturnItemMapper;
    @Autowired
    private DmsOrderReturnDeliveryMapper dmsOrderReturnDeliveryMapper;
    @Autowired
    private DmsOrderReturnCartMapper dmsOrderReturnCartMapper;
    @Autowired
    private DmsOrderReceiveItemMapper dmsOrderReceiveItemMapper;
    @Autowired
    private DmsChannelContactsService dmsChannelContactsService;
    @Autowired
    private DmsChannelService dmsChannelService;
    @Autowired
    private DmsProductService dmsProductService;
	@Autowired
	private DmsContactInfoService dmsContactInfoService;
    @Autowired
    private DmsOrderDeliveryService dmsOrderDeliveryService;
    @Autowired
    private DmsOrderDeliveryItemService dmsOrderDeliveryItemService;
    @Autowired
    private DmsOrderOperationLogService dmsOrderOperationLogService;
    @Autowired
    private DmsOrderProductService dmsOrderProductService;
    @Autowired
    private DmsStorageOutInRecordService dmsStorageOutInRecordService;
    @Autowired
    private DmsStorageInventoryService dmsStorageInventoryService;
    @Autowired
    private DmsStorageTransportationService dmsStorageTransportationService;
    @Autowired
    private DmsStorageService dmsStorageService;
	@Autowired
	private DmsMessageService dmsMessageService;
	@Autowired
	private DmsDataDictionayDependentService dmsDataDictionayDependentService;
	@Autowired
	private DmsSysRegionService dmsSysRegionService;
    
    @Override
    public IMybatisDao<DmsOrderReturn> getBaseDao() {
        return dmsOrderReturnMapper;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public Page<DmsOrderReturnVo> selectForListPage(DmsOrderReturnVo search) {
    	try {
            PageHelper.startPage(search.getP(), search.getS());
            this.dmsOrderReturnMapper.getPageList(search);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            this.logger.error("分页查询退换货列表失败！",e);
            throw new ServiceException("分页查询异常--->>>");
        }
	}
	@Override
	public Map<String, Object> detail(Long id) {
		Map<String, Object> retData = new HashMap<String, Object>();
		if (id == null || id == 0) {
			throw new ServiceException("参数错误");
		}
		DmsOrderReturnVo entity = this.dmsOrderReturnMapper.selectDetailByPrimaryKey(id);
		if (entity == null) {
			throw new ServiceException("数据库获取数据不存在！");
		}
		retData.put("orderReturn", entity);
		return retData;
	}
	
    @Override
	public DmsOrderReturnVo create(DmsOrderReturnVo paramVo, UserSession session) {
    	DmsOrderReturnVo resultData = new DmsOrderReturnVo();
		paramVo.setUserId(session.getId());
		paramVo.setCode(SystemParamerConfiguration.getInstance().createSerialNumStr(DmsModuleEnums.ORDER_SERIAL_KEY));
		paramVo.setApplyDate(new Date());
		this.checkParam(paramVo);
		//设置客户联系人信息.查询渠道表关联联系人信息
		DmsChannelContactsVo channelContact = this.dmsChannelContactsService.queryContactByUserId(session.getId());
		if(channelContact == null){
			throw new ServiceException("查询渠道联系人不存在！");
		}
		DmsChannel channel = this.dmsChannelService.selectByPrimaryKey(channelContact.getChannelId());
		if(channel == null){
			throw new ServiceException("查询订货端不存在！");
		}
		paramVo.setCustomerName(channel.getName());
		paramVo.setContacts(channelContact.getName());
		paramVo.setPhone(channelContact.getMobile());
		paramVo.setAreaId(channel.getAreaId().intValue());
		
		paramVo.setAuditStatus(DmsModuleEnums.RETURN_AUDIT_STATUS.WAIT_PEND.getType());
		paramVo.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.WAIT_PEND.getType());
		paramVo.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.WAIT_PEND.getType());
		this.dmsOrderReturnMapper.insert(paramVo);
		Long orderReturnId = paramVo.getId();
		//获取退换货商品
		List<DmsOrderReturnItem> returnItemList = new ArrayList<DmsOrderReturnItem>();
		List<Long> returnCartIdList = paramVo.getReturnCartIdList();
		if (BlankUtil.isNotEmpty(returnCartIdList)) {
			for (Long cartId : returnCartIdList) {
				DmsOrderReturnCart returnCart = dmsOrderReturnCartMapper.selectByPrimaryKey(cartId);
				if (returnCart == null) {
					throw new ServiceException("获取退换清单数据为空");
				}
				DmsProduct product = dmsProductService.selectByPrimaryKey(returnCart.getProductId());
				if (product == null) {
					throw new ServiceException("产品数据不存在！");
				}
				DmsOrderReturnItem returnItem = BeanConvertHelper.copyProperties(returnCart, DmsOrderReturnItem.class);
				returnItem.setProductName(product.getName());
				returnItem.setOrderReturnId(orderReturnId);
				returnItem.setCreatedBy(session.getId());
				returnItem.setCreatedDate(new Date());
				returnItem.setRemoveFlag(0);
				returnItemList.add(returnItem);
			}
		}
		if(BlankUtil.isNotEmpty(returnItemList)){
			this.dmsOrderReturnItemMapper.batchInsert(returnItemList);
		}
		//如果是换货需要保存收货人信息
		if(paramVo.getOrderType() == DmsModuleEnums.RETURN_ORDER_TYPE.CHANGE.getType()){
			if(paramVo.getReturnDelivery() == null){
				throw new ServiceException("操作类型为换货，请录入收货人信息");
			}
			DmsOrderReturnDelivery returnDelivery = BeanConvertHelper.copyProperties(paramVo.getReturnDelivery(), DmsOrderReturnDelivery.class);
			returnDelivery.setOrderReturnId(orderReturnId);
			returnDelivery.setDeliveryType(DmsModuleEnums.RETURN_ORDER_DELIVERY_TYPE.CHANNEL_RECEIVERY.getType());
			returnDelivery.setCreatedBy(session.getId());
			returnDelivery.setCreatedDate(new Date());
			returnDelivery.setRemoveFlag(0);
			this.dmsOrderReturnDeliveryMapper.insert(returnDelivery);
		}
		this.dmsOrderOperationLogService.createOrderReturnOperLog(orderReturnId, "订单提交", session.getId());
		this.updateReturnCount(returnItemList,true); //修改订单产品退换货数量
		this.dmsOrderReturnCartMapper.batchRemove(returnCartIdList); //删除购物车数据
		resultData.setId(orderReturnId);
		resultData.setCode(paramVo.getCode());
		String productName ="";
		if (BlankUtil.isNotEmpty(returnItemList)){
			for(DmsOrderReturnItem dmsOrderReturnItem:returnItemList){
				productName = productName +dmsOrderReturnItem.getProductName()+ ";";
				if (productName.length()>30) break;
			}
		}
		//发送消息
		sendMessage(1,orderReturnId,paramVo.getCode(),session.getId(),paramVo.getOrderType(),channel.getName(),paramVo.getAmount(),session.getId(),"",productName);

		return resultData;
	}

	private DmsChannel getChannelByCode(String code){
		//获取经销商信息
		DmsChannel channel=new DmsChannel();
		channel.setCode(code);
		List<DmsChannel> list =  dmsChannelService.selectByCondition(channel);
		return list.isEmpty()?null:list.get(0);
	}

	private DmsChannelContacts getChannelContactsByChannelId(Long channelId){
		DmsChannelContacts channelContacts=new DmsChannelContacts();
		channelContacts.setChannelId(channelId);
		List<DmsChannelContacts> contactsList=dmsChannelContactsService.selectByCondition(channelContacts);
		return contactsList.isEmpty()?null:contactsList.get(0);
	}


	@Override
	public DmsOrderReturnVo anjoyReturnSync(DmsReturnRequestVo requestVo) {
		if (BlankUtil.isEmpty(requestVo.getProductItems())) {
			throw new ServiceException("退货清单不能为空！");
		}
		//获取渠道信息
		DmsChannel channel=getChannelByCode(requestVo.getEasCustomerNo());
		if(channel==null){
			throw new ServiceException("经销商编码错误，经销商不存在");
		}
		DmsOrderReturnVo resultData = new DmsOrderReturnVo();
		//设置客户联系人信息.查询渠道表关联联系人信息
		DmsChannelContacts channelContact = getChannelContactsByChannelId(channel.getId());
		if(channelContact == null){
			throw new ServiceException("查询渠道联系人不存在！");
		}
		// 从联系方式表中获取手机号码
		DmsContactInfo contactInfoSearch = new DmsContactInfo();
		contactInfoSearch.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.CHANNEL_CONTACT.getType());
		contactInfoSearch.setRelatedTableId(channelContact.getId());
		contactInfoSearch.setType(DmsModuleEnums.CONTACT_TYPE.MOBILE.getType());
		List<DmsContactInfo> contactInfoList = dmsContactInfoService.selectByCondition(contactInfoSearch);
		if (BlankUtil.isEmpty(contactInfoList)) {
			throw new ServiceException("渠道联系人手机号码为空！");
		}
		String mobile=contactInfoList.get(0).getContent();
		DmsOrderReturn orderReturn=new DmsOrderReturn();
		orderReturn.setUserId(channelContact.getUserId());
		orderReturn.setOrderType(1);
		orderReturn.setAmount(requestVo.getAmount());
		orderReturn.setCustomerName(channel.getName());
		orderReturn.setContacts(channelContact.getName());
		orderReturn.setPhone(mobile);
		orderReturn.setAreaId(channel.getAreaId().intValue());
		orderReturn.setApplyDate(requestVo.getApplyDate());
		orderReturn.setCode(requestVo.getEasReturnNo());
		orderReturn.setAuditStatus(DmsModuleEnums.RETURN_AUDIT_STATUS.WAIT_PEND.getType());
		orderReturn.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.WAIT_PEND.getType());
		orderReturn.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.WAIT_PEND.getType());
		orderReturn.setCreatedBy(orderReturn.getUserId());
		orderReturn.setCreatedDate(new Date());
		orderReturn.setLastUpdatedDate(new Date());
		orderReturn.setRemoveFlag(0);
		this.dmsOrderReturnMapper.insert(orderReturn);
		Long orderReturnId = orderReturn.getId();
		//获取退换货商品
		List<DmsOrderReturnItem> returnItemList = new ArrayList<DmsOrderReturnItem>();
		List<DmsReturnItemVo> returnItemVoList = requestVo.getProductItems();
		if (BlankUtil.isNotEmpty(returnItemVoList)) {
			for (DmsReturnItemVo itemVo : returnItemVoList) {
				//获取订单编号、产品信息
				DmsOrder order=dmsOrderMapper.selectByThirdOrderNo(itemVo.getEasOrderNo());
				if(order==null){
					throw new ServiceException("订单编号"+itemVo.getEasOrderNo()+"有误");
				}
				DmsProduct product=dmsProductService.getByCode(itemVo.getEasProductNo());
				if(product==null){
					throw new ServiceException("产品编码"+itemVo.getEasProductNo()+"有误");
				}
				DmsOrderProduct orderProduct=new DmsOrderProduct();
				orderProduct.setOrderId(order.getId());
				orderProduct.setProductId(product.getId());
				List<DmsOrderProduct> dmsOrderProductList=dmsOrderProductService.selectByCondition(orderProduct);
				if(BlankUtil.isEmpty(dmsOrderProductList)){
					throw new ServiceException("订单编号"+itemVo.getEasOrderNo()+"下不存在产品编码为"+itemVo.getEasProductNo()+"的产品");
				}
				DmsOrderProduct dmsOrderProduct=dmsOrderProductList.get(0);
				DmsOrderReturnItem returnItem = new DmsOrderReturnItem();
				returnItem.setUserId(channelContact.getUserId());
				returnItem.setProductName(product.getName());
				returnItem.setOrderReturnId(orderReturnId);
				returnItem.setOldOrderId(order.getId());
				returnItem.setProductSpecId(dmsOrderProduct.getProductSpecId());
				returnItem.setProductId(product.getId());
				returnItem.setSpecUnionKey(dmsOrderProduct.getSpecUnionKey());
				returnItem.setUnitId(dmsOrderProduct.getUnit());
				returnItem.setPrice(dmsOrderProduct.getPrice());
				returnItem.setReturnNum(itemVo.getCount());
				returnItem.setDeliveryNum(dmsOrderProduct.getCount());
				//returnItem.setReceiverDate();
				returnItem.setCreatedBy(channelContact.getUserId());
				returnItem.setCreatedDate(new Date());
				returnItem.setRemoveFlag(0);
				returnItemList.add(returnItem);
			}
		}
		if(BlankUtil.isNotEmpty(returnItemList)){
			this.dmsOrderReturnItemMapper.batchInsert(returnItemList);
		}

		this.dmsOrderOperationLogService.createOrderReturnOperLog(orderReturnId, "订单提交", channelContact.getUserId());
		this.updateReturnCount(returnItemList,true); //修改订单产品退换货数量
		resultData.setId(orderReturnId);
		resultData.setCode(orderReturn.getCode());
		String productName ="";
		if (BlankUtil.isNotEmpty(returnItemList)){
			for(DmsOrderReturnItem dmsOrderReturnItem:returnItemList){
				productName = productName +dmsOrderReturnItem.getProductName()+ ";";
				if (productName.length()>30) break;
			}
		}
		//发送消息
		sendMessage(1,orderReturnId,orderReturn.getCode(),channelContact.getUserId(),orderReturn.getOrderType(),channel.getName(),requestVo.getAmount(),channelContact.getUserId(),"",productName);

		return resultData;
	}

    
	private void updateReturnCount(List<DmsOrderReturnItem> returnItemList, boolean addFlag) {
		if (BlankUtil.isNotEmpty(returnItemList)) {
			for (DmsOrderReturnItem returnItem : returnItemList) {
				DmsOrderProduct proSearch = new DmsOrderProduct();
				proSearch.setOrderId(returnItem.getOldOrderId());
				proSearch.setProductId(returnItem.getProductId());
				proSearch.setProductSpecId(returnItem.getProductSpecId());
				List<DmsOrderProduct> proEntityList = this.dmsOrderProductService.selectByCondition(proSearch);
				if (BlankUtil.isEmpty(proEntityList)) {
					throw new ServiceException("获取原订单产品数据不存在");
				}
				DmsOrderProduct proEntity = proEntityList.get(0);
				if (addFlag) {
					proEntity.setReturnCount((proEntity.getReturnCount() == null ? 0 : proEntity.getReturnCount()) + returnItem.getReturnNum());
				} else {
					proEntity.setReturnCount(proEntity.getReturnCount() - returnItem.getReturnNum());
				}
				proEntity.setLastUpdatedDate(new Date());
				this.dmsOrderProductService.updateByPrimaryKeySelective(proEntity);
			}
		}
	}
	@Override
	public void audit(DmsOrderReturnVo paramVo, UserSession session) {
		DmsOrderReturn entity = this.getEntity(paramVo);
		Integer auditStatus = paramVo.getAuditStatus();
		if (auditStatus == null || auditStatus == 0) {
			throw new ServiceException("请输入审核意见");
		}
		// 如果拒绝，订单取消
		DmsOrderReturnDelivery returnDelivery = null;
		if (auditStatus == DmsModuleEnums.RETURN_AUDIT_STATUS.REFUSE.getType()) {
			paramVo.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.CANCEL.getType());
			paramVo.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.CANCEL.getType());
			//取消订单退换货数量还原
			List<DmsOrderReturnItemVo> totalItems = this.dmsOrderReturnItemMapper.getProductItemList(entity.getId());
			if(BlankUtil.isNotEmpty(totalItems)){
				List<DmsOrderReturnItem> returnItemList = BeanConvertHelper.copyListProperties(totalItems, DmsOrderReturnItem.class);
				this.updateReturnCount(returnItemList,false);
			}
			this.dmsOrderOperationLogService.createOrderReturnOperLog(entity.getId(), "订单拒绝", session.getId());
			this.dmsOrderOperationLogService.createOrderReturnOperLog(entity.getId(), "订单取消", session.getId());
		} else {
			paramVo.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.WAIT_RETURN.getType());
			paramVo.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.WAIT_CUSTOMER_DELIVERY.getType());
			// 保存收货人信息.这里保存的是为了方便渠道商将商品发回给品牌商
			if(paramVo.getReturnDelivery() == null){
				throw new ServiceException("审核操作请输入收件人信息！");
			}
			if(BlankUtil.isEmpty(paramVo.getReturnDelivery().getReceiver()) 
					|| BlankUtil.isEmpty(paramVo.getReturnDelivery().getMobile()) 
					|| BlankUtil.isEmpty(paramVo.getReturnDelivery().getAddress())){
				throw new ServiceException("审核操作请输入收件人信息！");
			}
			returnDelivery = BeanConvertHelper.copyProperties(paramVo.getReturnDelivery(), DmsOrderReturnDelivery.class);
			returnDelivery.setOrderReturnId(entity.getId());
			returnDelivery.setDeliveryType(DmsModuleEnums.RETURN_ORDER_DELIVERY_TYPE.BRAND_RECEIVERY.getType());
			returnDelivery.setCreatedBy(session.getId());
			returnDelivery.setCreatedDate(new Date());
			returnDelivery.setRemoveFlag(0);
			this.dmsOrderReturnDeliveryMapper.insert(returnDelivery);
			this.dmsOrderOperationLogService.createOrderReturnOperLog(entity.getId(), "审核", session.getId());
		}
		paramVo.setAuditStatus(auditStatus);
		paramVo.setAuditBy(session.getId());
		paramVo.setAuditDate(new Date());
		paramVo.setLastUpdatedBy(session.getId());
		paramVo.setLastUpdatedDate(new Date());
		this.dmsOrderReturnMapper.updateByPrimaryKeySelective(paramVo);

		int type = 0;
		if (auditStatus==1) type = 2;
		else if (auditStatus==2) type = 3;
		List<DmsOrderReturnItemVo> totalItems = this.dmsOrderReturnItemMapper.getProductItemList(paramVo.getId());
		String productName = "";
		if (BlankUtil.isNotEmpty(totalItems)) {
			for (DmsOrderReturnItemVo orderItem : totalItems) {
				productName = productName + orderItem.getProductName()+";";
				if (productName.length()>30) break;
			}
		}

		sendMessage(type,entity.getId(),entity.getCode(),entity.getUserId(),entity.getOrderType(),entity.getCustomerName(),entity.getAmount(),session.getId(),"",paramVo.getRemark(), returnDelivery,productName);
	}

	/***
	 *
	 * @param orderNo
	 * @param auit 0为拒绝，1为同意
	 * @param remark
     */
	@Override
	public void audit(String orderNo, Integer auit, String remark) {
		DmsOrderReturn orderReturn=new DmsOrderReturn();
		orderReturn.setCode(orderNo);
		List<DmsOrderReturn> entitys = dmsOrderReturnMapper.selectByCondition(orderReturn);
		if(BlankUtil.isEmpty(entitys)){
			throw new ServiceException("退货订单编码错误");
		}
		DmsOrderReturn entity=entitys.get(0);
		entity.setRemark(remark);
		// 如果拒绝，订单取消
		if (auit == 0) {
			entity.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.CANCEL.getType());
			entity.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.CANCEL.getType());
			//取消订单退换货数量还原
			List<DmsOrderReturnItemVo> totalItems = this.dmsOrderReturnItemMapper.getProductItemList(entity.getId());
			if(BlankUtil.isNotEmpty(totalItems)){
				List<DmsOrderReturnItem> returnItemList = BeanConvertHelper.copyListProperties(totalItems, DmsOrderReturnItem.class);
				this.updateReturnCount(returnItemList,false);
			}
			this.dmsOrderOperationLogService.createOrderReturnOperLog(entity.getId(), "订单拒绝", 1L);
			this.dmsOrderOperationLogService.createOrderReturnOperLog(entity.getId(), "订单取消", 1L);
		} else {
			entity.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.WAIT_RETURN.getType());
			entity.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.WAIT_CUSTOMER_DELIVERY.getType());
			// 保存收货人信息.这里保存的是为了方便渠道商将商品发回给品牌商
			/*if(paramVo.getReturnDelivery() == null){
				throw new ServiceException("审核操作请输入收件人信息！");
			}*/
			/*if(BlankUtil.isEmpty(paramVo.getReturnDelivery().getReceiver())
					|| BlankUtil.isEmpty(paramVo.getReturnDelivery().getMobile())
					|| BlankUtil.isEmpty(paramVo.getReturnDelivery().getAddress())){
				throw new ServiceException("审核操作请输入收件人信息！");
			}*/
			DmsOrderReturnDelivery returnDelivery = new DmsOrderReturnDelivery();
			returnDelivery.setOrderReturnId(entity.getId());
			returnDelivery.setDeliveryType(DmsModuleEnums.RETURN_ORDER_DELIVERY_TYPE.BRAND_RECEIVERY.getType());
			returnDelivery.setCreatedBy(1L);
			returnDelivery.setCreatedDate(new Date());
			returnDelivery.setRemoveFlag(0);
			this.dmsOrderReturnDeliveryMapper.insert(returnDelivery);
			this.dmsOrderOperationLogService.createOrderReturnOperLog(entity.getId(), "审核", 1L);
		}
		entity.setAuditStatus(auit==1?1:2);
		entity.setAuditBy(1L);
		entity.setAuditDate(new Date());
		entity.setLastUpdatedBy(1L);
		entity.setLastUpdatedDate(new Date());
		this.dmsOrderReturnMapper.updateByPrimaryKeySelective(entity);

		int type = 0;
		if (auit==1) type = 2;
		else if (auit==0) type = 3;
		List<DmsOrderReturnItemVo> totalItems = this.dmsOrderReturnItemMapper.getProductItemList(entity.getId());
		String productName = "";
		if (BlankUtil.isNotEmpty(totalItems)) {
			for (DmsOrderReturnItemVo orderItem : totalItems) {
				productName = productName + orderItem.getProductName()+";";
				if (productName.length()>30) break;
			}
		}

		sendMessage(type,entity.getId(),entity.getCode(),entity.getUserId(),entity.getOrderType(),entity.getCustomerName(),entity.getAmount(),1L,"",remark, null,productName);

	}

	/**
	 * 渠道发货-增加出库记录，扣库存，增加在途
	 */
	@Override
	public void updateDelivery(DmsOrderReturnVo paramVo, UserSession session) {
		DmsOrderReturn entity = this.getEntity(paramVo);
		//发货需要填写物流信息回写配送信息
		DmsOrderReturnDelivery deliverySearch = new DmsOrderReturnDelivery();
		deliverySearch.setOrderReturnId(entity.getId());
		deliverySearch.setDeliveryType(DmsModuleEnums.RETURN_ORDER_DELIVERY_TYPE.BRAND_RECEIVERY.getType());
		List<DmsOrderReturnDelivery> returnDeliveryList = this.dmsOrderReturnDeliveryMapper.selectByCondition(deliverySearch);
		if(BlankUtil.isEmpty(returnDeliveryList)){
			throw new ServiceException("数据库获取品牌商收货信息为空");
		}
		DmsOrderReturnDelivery returnDelivery = returnDeliveryList.get(0);
		returnDelivery.setDeliverWayId(paramVo.getReturnDelivery().getDeliverWayId());
		returnDelivery.setExpressCompany(paramVo.getReturnDelivery().getExpressCompany());
		returnDelivery.setExpressNo(paramVo.getReturnDelivery().getExpressNo());
		returnDelivery.setRemark(paramVo.getReturnDelivery().getRemark());
		returnDelivery.setLastUpdatedBy(session.getId());
		returnDelivery.setLastUpdatedDate(new Date());
		this.dmsOrderReturnDeliveryMapper.updateByPrimaryKeySelective(returnDelivery);
		//修改退换货状态
		paramVo.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.RETURNING.getType());
		paramVo.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.WAIT_RECEIVERY.getType());
		paramVo.setLastUpdatedBy(session.getId());
		paramVo.setLastUpdatedDate(new Date());
		this.dmsOrderReturnMapper.updateByPrimaryKeySelective(paramVo);
		this.dmsOrderOperationLogService.createOrderReturnOperLog(entity.getId(), "客户已发货", session.getId());

		List<DmsOrderReturnItemVo> totalItems = this.dmsOrderReturnItemMapper.getProductItemList(paramVo.getId());
		if (BlankUtil.isEmpty(totalItems)) {
			throw new ServiceException("渠道发货获取订单产品数据为空，调整库存失败！");
		}
		String productName = "";
		for (DmsOrderReturnItemVo orderItem : totalItems) {
			this.updateInventoryChannelToBrand(entity,orderItem, entity.getUserId(), session, true, null, null);
			if (productName.length()<30){
				productName = productName + orderItem.getProductName()+";";
			}
		}

		sendMessage(4,entity.getId(),entity.getCode(),entity.getUserId(),entity.getOrderType(),entity.getCustomerName(),entity.getAmount(),session.getId(),paramVo.getReturnDelivery().getDeliverWayId(),productName);
	}

	/**
	 * 品牌商确认收货.先做保存收货记录
	 */
	@Override
	public void updateReceived(DmsOrderReturnItemVo orderReturnItem, UserSession session) {
		if (orderReturnItem == null || orderReturnItem.getId() == null || orderReturnItem.getOrderReturnId() == null) {
			throw new ServiceException("参数异常");
		}
		DmsOrderReturn entity = this.dmsOrderReturnMapper.selectByPrimaryKey(orderReturnItem.getOrderReturnId());
		if (entity == null) {
			throw new ServiceException("数据库获取数据不存在！");
		}
		DmsOrderReturnItem orderReturnItemEntity = this.dmsOrderReturnItemMapper.selectByPrimaryKey(orderReturnItem.getId());
		if(orderReturnItemEntity == null){
			throw new ServiceException("退换货记录数据库不存在！");
		}
		if(orderReturnItem.getDeliveryNum() == null || orderReturnItem.getDeliveryNum() == 0){
			throw new ServiceException("请输入实收数量");
		}
		if(orderReturnItem.getReceiverDate() == null){
			throw new ServiceException("请输入收货日期");
		}
		//请求的收货数量加上数据库已经收货的数量
		int deliveryCount = (orderReturnItemEntity.getDeliveryNum() == null ? 0 : orderReturnItemEntity.getDeliveryNum()) + orderReturnItem.getDeliveryNum();
		int returnCount = orderReturnItemEntity.getReturnNum();// 实际数据库退换数量
		if (deliveryCount > returnCount) {
			throw new ServiceException("输入的数量不符合");
		}
		if(deliveryCount <= returnCount){
			DmsOrderReturnItem orderReturnParam = new DmsOrderReturnItem();
			orderReturnParam.setId(orderReturnItem.getId());
			orderReturnParam.setReceiverDate(orderReturnItem.getReceiverDate());
			orderReturnParam.setDeliveryNum(deliveryCount);
			orderReturnParam.setLastUpdatedBy(session.getId());
			orderReturnParam.setLastUpdatedDate(new Date());
			this.dmsOrderReturnItemMapper.updateByPrimaryKeySelective(orderReturnParam);
		}
		//判断总数
		boolean eqFlag = deliveryCount == returnCount;
		DmsOrderReturnItemVo orderItem = this.dmsOrderReturnItemMapper.selectOrderReturnTotal(orderReturnItem.getOrderReturnId());
		Integer	totalReturnNum = orderItem.getTotalReturnNum();
		Integer	totalDeliveryNum = orderItem.getTotalDeliveryNum();
		if(totalReturnNum.equals(totalDeliveryNum)){
			//修改退换货状态.针对于退换，确认收货后已经完成
			if(entity.getOrderType() == DmsModuleEnums.RETURN_ORDER_TYPE.RETURN.getType()){
				entity.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.WAIT_PAY.getType());
				entity.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.WAIT_PAY.getType());
			} else {
				entity.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.WAIT_DELIVERY.getType());
				entity.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.WAIT_DELIVERY.getType());
			}
			entity.setLastUpdatedBy(session.getId());
			entity.setLastUpdatedDate(new Date());
			this.dmsOrderReturnMapper.updateByPrimaryKeySelective(entity);
			this.dmsOrderOperationLogService.createOrderReturnOperLog(entity.getId(), "已收货", session.getId());
		}
		
		orderReturnItem.setProductId(orderReturnItemEntity.getProductId());
		orderReturnItem.setProductSpecId(orderReturnItemEntity.getProductSpecId());
		this.createOrderReceiveItem(orderReturnItem, session);
		this.updateInventoryChannelToBrand(entity,orderReturnItem, entity.getUserId(), session, false, eqFlag, orderReturnItem.getDeliveryNum());
		sendMessage(5,entity.getId(),entity.getCode(),entity.getUserId(),entity.getOrderType(),entity.getCustomerName(),entity.getAmount(),session.getId(),"",orderReturnItemEntity.getProductName());
	}
	/**
	 * 保存收货记录
	 * @param orderReturnItem
	 * @param session
	 */
	private void createOrderReceiveItem(DmsOrderReturnItemVo orderReturnItem,UserSession session){
		DmsOrderReceiveItem receiveItem = new DmsOrderReceiveItem();
		receiveItem.setOrderId(orderReturnItem.getOrderReturnId());
		receiveItem.setRelatedType(DmsModuleEnums.ORDER_DELIVERY_TYPE.ORDER_RETURN.getType());
		receiveItem.setOrderProductId(orderReturnItem.getId());
		receiveItem.setCount(orderReturnItem.getDeliveryNum());
		receiveItem.setStorageId(orderReturnItem.getStorageId());
		receiveItem.setStorageLocalId(orderReturnItem.getStorageLocalId());
		receiveItem.setReceiveDate(new Date());
		receiveItem.setCreatedDate(new Date());
		receiveItem.setCreatedBy(session.getId());
		receiveItem.setRemoveFlag(0);
		this.dmsOrderReceiveItemMapper.insert(receiveItem);
	}
	/**
	 * 退货库存调整（渠道到品牌商）
	 * @param orderItem 订单产品对象
	 * @param session
	 * @param addFlag 是否是出入
	 * @param eqFlag 确认收货使用（判断收货数量和总数是否相等）
	 * @param uptCount 修改数量
	 */
	private void updateInventoryChannelToBrand(DmsOrderReturn order,DmsOrderReturnItemVo orderItem, Long userId, 
			UserSession session, boolean addFlag,Boolean eqFlag,Integer uptCount) {
		if(orderItem == null || userId == null){
			throw new ServiceException("参数异常");
		}
		// 获取渠道仓库
		DmsStorage storage = dmsStorageService.selectByChannelContactUserId(userId);
		if (storage == null) {
			throw new ServiceException("获取渠道仓库不存在！");
		}
		if (addFlag) {
			//渠道发货-减少渠道可用量库存
			DmsStorageInventoryVo inventory = new DmsStorageInventoryVo();
			inventory.setOutOrgId(session.getOrgId());
			inventory.setOutOrgType(DmsModuleEnums.STORAGE_TYPE.CHANNEL.getType());
			inventory.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.PUT_OUT.getType());
			inventory.setProductId(orderItem.getProductId());
			inventory.setProductSpecId(orderItem.getProductSpecId());
			inventory.setStorage(storage.getId());
			inventory.setOutInType(DmsModuleEnums.STORAGE_OUTIN.OUT.getType());
			inventory.setAddOrSubtractNum(orderItem.getReturnNum());
			inventory.setTransFlag(true);
			this.dmsStorageInventoryService.addOrSubtract(inventory, session);
			// 创建在途记录
			DmsStorageTransportation trans = new DmsStorageTransportation();
			trans.setRelationId(orderItem.getId());
			trans.setRelationType(DmsModuleEnums.RELATION_TYPE.RETURN_CHANNEL.getType());
			trans.setSendBill(order.getCode());
			trans.setProductId(orderItem.getProductId());
			trans.setProductSpecId(orderItem.getProductSpecId());
			trans.setTransportationNum(orderItem.getReturnNum());
			trans.setSendStorage(storage.getId());
			this.dmsStorageTransportationService.create(trans, session);
		} else {
			//品牌商收货-增加品牌商库存
			DmsStorageInventoryVo inventoryAdd = new DmsStorageInventoryVo();
			inventoryAdd.setInOrgId(session.getOrgId());
			inventoryAdd.setInOrgType(DmsModuleEnums.STORAGE_TYPE.BRAND.getType());
			inventoryAdd.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.SALE_RETURN.getType());
			inventoryAdd.setProductId(orderItem.getProductId());
			inventoryAdd.setProductSpecId(orderItem.getProductSpecId());
			inventoryAdd.setStorage(orderItem.getStorageId());
			inventoryAdd.setStorageLocal(orderItem.getStorageLocalId());
			inventoryAdd.setOutInType(DmsModuleEnums.STORAGE_OUTIN.IN.getType()); // 入库
			inventoryAdd.setAddOrSubtractNum(orderItem.getDeliveryNum());
			this.dmsStorageInventoryService.addOrSubtract(inventoryAdd, session);
			// 删除在途库存.需要判断收货的数量是否已经全部收货
			if(eqFlag != null && eqFlag){
				DmsStorageTransportation trans = new DmsStorageTransportation();
				trans.setRelationId(orderItem.getId());
				trans.setRelationType(DmsModuleEnums.RELATION_TYPE.RETURN_CHANNEL.getType());
				this.dmsStorageTransportationService.updateTrans(trans);
			} else {
				DmsStorageTransportation search = new DmsStorageTransportation();
				search.setRelationId(orderItem.getId());
				search.setRelationType(DmsModuleEnums.RELATION_TYPE.RETURN_CHANNEL.getType());
				List<DmsStorageTransportation> transList = this.dmsStorageTransportationService.selectByCondition(search);
				if (BlankUtil.isEmpty(transList)) {
					throw new ServiceException("获取在途数据不存在");
				}
				DmsStorageTransportation trans = transList.get(0);
				// 计算在途数量
				Integer transNumber = trans.getTransportationNum() - uptCount;
				trans.setTransportationNum(transNumber);
				trans.setLastUpdatedBy(session.getId());
				trans.setLastUpdatedDate(new Date());
				this.dmsStorageTransportationService.updateByPrimaryKeySelective(trans);
			}
		}
	}
	/**
	 * 退货库存调整（品牌商到渠道）
	 * @param deliveryItem
	 * @param session
	 * @param addFlag
	 */
	private void updateInventoryBrandToChannel(DmsOrderDeliveryVo orderDelivery,DmsOrderDeliveryItem deliveryItem, 
			Long userId, UserSession session, boolean addFlag) {
		if (deliveryItem == null || userId == null || deliveryItem.getOrderProductId() == null) {
			throw new ServiceException("参数异常");
		}
		DmsOrderReturnItem orderProduct = this.dmsOrderReturnItemMapper.selectByPrimaryKey(deliveryItem.getOrderProductId());
		if (orderProduct == null) {
			throw new ServiceException("库存修改失败，不存在产品数据！");
		}
		if (addFlag) {
			/* 品牌商发货-减少可用量 */
			DmsStorageInventoryVo inventory = new DmsStorageInventoryVo();
			inventory.setOutOrgId(session.getOrgId());
			inventory.setOutOrgType(DmsModuleEnums.STORAGE_TYPE.BRAND.getType());
			inventory.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.SALE_OUT.getType());
			inventory.setProductId(orderProduct.getProductId());
			inventory.setProductSpecId(orderProduct.getProductSpecId());
			inventory.setStorage(deliveryItem.getStorageId());
			inventory.setStorageLocal(deliveryItem.getStorageLocalId());
			inventory.setOutInType(DmsModuleEnums.STORAGE_OUTIN.OUT.getType());
			inventory.setAddOrSubtractNum(deliveryItem.getCount());
			inventory.setTransFlag(true);
			dmsStorageInventoryService.addOrSubtract(inventory, session);

			/* 新增在途库存记录 */
			DmsStorageTransportation storageTransportation = new DmsStorageTransportation();
			storageTransportation.setRelationId(deliveryItem.getId());
			storageTransportation.setRelationType(DmsModuleEnums.RELATION_TYPE.RETURN_BRAND.getType());
			storageTransportation.setSendBill(orderDelivery.getCode());
			storageTransportation.setProductId(orderProduct.getProductId());
			storageTransportation.setProductSpecId(orderProduct.getProductSpecId());
			storageTransportation.setTransportationNum(deliveryItem.getCount());
			storageTransportation.setSendStorage(deliveryItem.getStorageId());
			storageTransportation.setSendStorageLocal(deliveryItem.getStorageLocalId());
			dmsStorageTransportationService.create(storageTransportation, session);
		} else {
			// 获取渠道仓库
			DmsStorage storage = dmsStorageService.selectByChannelContactUserId(userId);
			if (storage == null) {
				throw new ServiceException("获取仓库不存在！");
			}
			DmsStorageInventoryVo inventory = new DmsStorageInventoryVo();
			/* 渠道收货-增加渠道商的库存 */
			inventory.setInOrgId(session.getOrgId());
			inventory.setInOrgType(DmsModuleEnums.STORAGE_TYPE.CHANNEL.getType());
			inventory.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.PUT_IN.getType());
			inventory.setProductId(orderProduct.getProductId());
			inventory.setProductSpecId(orderProduct.getProductSpecId());
			inventory.setStorage(storage.getId());
			inventory.setOutInType(DmsModuleEnums.STORAGE_OUTIN.IN.getType());
			inventory.setAddOrSubtractNum(deliveryItem.getCount());
			inventory.setTransFlag(false);
			dmsStorageInventoryService.addOrSubtract(inventory, session);

			/* 删除在途库存 */
			DmsStorageTransportation storageTransportation = new DmsStorageTransportation();
			storageTransportation.setRelationType(DmsModuleEnums.RELATION_TYPE.RETURN_BRAND.getType());
			storageTransportation.setRelationId(deliveryItem.getId());
			dmsStorageTransportationService.updateTrans(storageTransportation);
		}
	}
	/**
	 * 品牌商发货.需要创建发货单
	 */
	@Override
	public void updateDeliveryBrand(DmsOrderDeliveryVo orderDelivery, UserSession session) {
		//创建发货单
		if (orderDelivery == null) {
            throw new ServiceException("参数异常");
        }
		if (orderDelivery.getOrderId() == null || orderDelivery.getOrderId() == 0) {
            throw new ServiceException("订单id不能为空");
        }
        if (StringUtils.isBlank(orderDelivery.getCode())) {
            throw new ServiceException("发货单号不能为空");
        }
        if (StringUtils.isBlank(orderDelivery.getLogisticsCode())) {
            throw new ServiceException("物流单号不能为空");
        }
        DmsOrderReturn order = this.dmsOrderReturnMapper.selectByPrimaryKey(orderDelivery.getOrderId());
		if (order == null) {
			throw new ServiceException("数据库获取数据不存在！");
		}
         /*1.新增发货单信息 */
        orderDelivery.setRelatedType(DmsModuleEnums.ORDER_DELIVERY_TYPE.ORDER_RETURN.getType());
        dmsOrderDeliveryService.insert(orderDelivery);
         /*2.新增发货清单 */
        List<DmsOrderDeliveryItem> tempItemList = Lists.newArrayList();
        List<DmsOrderDeliveryItem> itemList = orderDelivery.getOrderDeliveryItemList();
        for (DmsOrderDeliveryItem item : itemList) {
            item.setOrderId(orderDelivery.getOrderId());
            item.setOrderDeliveryId(orderDelivery.getId());
            item.setRelatedType(DmsModuleEnums.ORDER_DELIVERY_TYPE.ORDER_RETURN.getType());
            item.setDeliverDate(new Date());
            item.setCreatedDate(new Date());
            item.setCreatedBy(session.getId());
            item.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            dmsOrderDeliveryItemService.insert(item);
            tempItemList.add(item);
        }
//        if (!itemList.isEmpty()) {
//            dmsOrderDeliveryItemService.batchInsert(itemList);
//        }
        //修改订单状态
        order.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.WAIT_RECEIVERY.getType());
        order.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.WAIT_CUSTOMER_RECEIVERY.getType());
        order.setLastUpdatedBy(session.getId());
        order.setLastUpdatedDate(new Date());
		this.dmsOrderReturnMapper.updateByPrimaryKeySelective(order);
		this.dmsOrderOperationLogService.createOrderReturnOperLog(order.getId(), "已发货", session.getId());
		if(BlankUtil.isNotEmpty(tempItemList)){
			for (DmsOrderDeliveryItem deliveryItem : tempItemList) {
				this.updateInventoryBrandToChannel(orderDelivery,deliveryItem, order.getUserId(), session, true);
			}
		}
		sendMessage(1,order.getId(),order.getUserId(),order.getCustomerName(),session.getId(),orderDelivery,order.getCode());
	}
	/**
	 * 渠道确认收货（针对单个产品）
	 */
	@Override
	public void updateReceivedChannel(DmsOrderDeliveryItem paramVo, UserSession session) {
		if (paramVo == null || paramVo.getId() == null || paramVo.getId() == 0) {
			throw new ServiceException("参数异常");
		}
		DmsOrderDeliveryItem entity = this.dmsOrderDeliveryItemService.selectByPrimaryKey(paramVo.getId());
		if (entity == null) {
			throw new ServiceException("数据库中不存在id为: " + "的产品发货信息");
		}
		paramVo.setReceiveDate(new Date());
		paramVo.setLastUpdatedBy(session.getId());
		paramVo.setLastUpdatedDate(new Date());
		dmsOrderDeliveryItemService.updateByPrimaryKeySelective(paramVo);
		DmsOrderReturn order = this.dmsOrderReturnMapper.selectByPrimaryKey(entity.getOrderId());
		if (order == null) {
			throw new ServiceException("数据库获取订单数据不存在！");
		}
		this.updateOrderStatusReceived(order, session);
		this.updateInventoryBrandToChannel(null, entity, order.getUserId(), session, false);
		sendMessage(2,order.getId(),order.getUserId(),order.getCustomerName(),session.getId(),null,order.getCode());
	}
	/**
	 * 渠道确认收货.流程完成
	 */
	@Override
	public void updateReceivedChannel(DmsOrderReturnVo paramVo, UserSession session) {
		DmsOrderReturn order = this.getEntity(paramVo);
		DmsOrderDeliveryItem param = new DmsOrderDeliveryItem();
		param.setOrderId(order.getId());
		param.setRelatedType(DmsModuleEnums.ORDER_DELIVERY_TYPE.ORDER_RETURN.getType());
		param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
		List<DmsOrderDeliveryItem> itemList = dmsOrderDeliveryItemService.selectByCondition(param);
		/* 修改订单下的所有发货单的收货日期 */
		for (DmsOrderDeliveryItem item : itemList) {
			item.setReceiveDate(new Date());
			item.setLastUpdatedBy(session.getId());
			item.setLastUpdatedDate(new Date());
			dmsOrderDeliveryItemService.updateByPrimaryKeySelective(item);
		}
		this.updateOrderStatusReceived(order, session);
		if(BlankUtil.isNotEmpty(itemList)){
			for (DmsOrderDeliveryItem deliveryItem : itemList) {
				this.updateInventoryBrandToChannel(null, deliveryItem, order.getUserId(), session, false);
			}
		}
		sendMessage(2,order.getId(),order.getUserId(),order.getCustomerName(),session.getId(),null,order.getCode());
	}
	/**
	 * 确认收货修改订单状态
	 */
	private void updateOrderStatusReceived(DmsOrderReturn order, UserSession session) {
		/* 判断订单是否已经全部收货完成，是则修改订单状态为"已完成" */
		/* 否则，判断订单是否还有未确认收货的产品：若无，则订单状态为"待厂商发货"；否则，订单状态未"待收货" */
		Integer unreceivedCount = this.dmsOrderReturnItemMapper.selectUnreceivedCountByOrderId(order.getId());
		if (unreceivedCount == 0) {
			order.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.FINISHED.getType());
			order.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.FINISHED.getType());
		} else {
			Integer unconfirmedCount = dmsOrderDeliveryItemService.selectUnconfirmedCountRT(order.getId());
			if (unconfirmedCount > 0) {
				order.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.WAIT_RECEIVERY.getType());
				order.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.WAIT_CUSTOMER_RECEIVERY.getType());
			} else {
				order.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.WAIT_DELIVERY.getType());
				order.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.WAIT_DELIVERY.getType());
			}
		}
		order.setLastUpdatedBy(session.getId());
		order.setLastUpdatedDate(new Date());
		this.dmsOrderReturnMapper.updateByPrimaryKeySelective(order);
		this.dmsOrderOperationLogService.createOrderReturnOperLog(order.getId(), "客户收货", session.getId());
		if(order.getBuyerStatus() == DmsModuleEnums.RETURN_BUYER_STATUS.FINISHED.getType()){
			this.dmsOrderOperationLogService.createOrderReturnOperLog(order.getId(), "订单完成", session.getId());
		}
	}
	@Override
	public void updateCancel(DmsOrderReturnVo paramVo, UserSession session){
		DmsOrderReturn entity = this.getEntity(paramVo);
		if(!entity.getBuyerStatus().equals(DmsModuleEnums.RETURN_BUYER_STATUS.WAIT_PEND.getType())){
			throw new ServiceException("审核后的订单不允许取消");
		}
		entity.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.CANCEL.getType());
		entity.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.CANCEL.getType());
		entity.setLastUpdatedBy(session.getId());
		entity.setLastUpdatedDate(new Date());
		this.dmsOrderReturnMapper.updateByPrimaryKeySelective(entity);
		this.dmsOrderOperationLogService.createOrderReturnOperLog(entity.getId(), "订单取消", session.getId());
		//取消订单退换货数量还原
		List<DmsOrderReturnItemVo> totalItems = this.dmsOrderReturnItemMapper.getProductItemList(entity.getId());
		if(BlankUtil.isNotEmpty(totalItems)){
			List<DmsOrderReturnItem> returnItemList = BeanConvertHelper.copyListProperties(totalItems, DmsOrderReturnItem.class);
			this.updateReturnCount(returnItemList,false);
		}
		String productName = "";
		for(DmsOrderReturnItemVo dmsOrderReturnItemVo : totalItems){
			productName = productName + dmsOrderReturnItemVo.getProductName()+";";
			if (productName.length()>30) break;;
		}
		sendMessage(7,entity.getId(),entity.getCode(),entity.getUserId(),entity.getOrderType(),entity.getCustomerName(),entity.getAmount(),session.getId(),"",productName);
	}
	
	@Override
	public void updateReturnPay(DmsOrderReturnVo paramVo, UserSession session) {
		DmsOrderReturn entity = this.getEntity(paramVo);
		entity.setBuyerStatus(DmsModuleEnums.RETURN_BUYER_STATUS.FINISHED.getType());
		entity.setSellerStatus(DmsModuleEnums.RETURN_SELLER_STATUS.FINISHED.getType());
		entity.setLastUpdatedBy(session.getId());
		entity.setLastUpdatedDate(new Date());
		this.dmsOrderReturnMapper.updateByPrimaryKeySelective(entity);
		this.dmsOrderOperationLogService.createOrderReturnOperLog(entity.getId(), "订单完成", session.getId());

		List<DmsOrderReturnItemVo> list = dmsOrderReturnItemMapper.getProductItemList(entity.getId());
		String productName = "";
		if (BlankUtil.isNotEmpty(list)) {
			for (DmsOrderReturnItemVo dmsOrderReturnItemVo : list) {
				productName = productName + dmsOrderReturnItemVo.getProductName() + ";";
				if (productName.length() > 30) break;
				;
			}
		}
		sendMessage(6,entity.getId(),entity.getCode(),entity.getUserId(),entity.getOrderType(),entity.getCustomerName(),entity.getAmount(),session.getId(),"",productName);
	}
	private DmsOrderReturn getEntity(DmsOrderReturnVo paramVo){
		if (paramVo == null || paramVo.getId() == null) {
			throw new ServiceException("参数异常");
		}
		DmsOrderReturn entity = this.dmsOrderReturnMapper.selectByPrimaryKey(paramVo.getId());
		if (entity == null) {
			throw new ServiceException("数据库获取数据不存在！");
		}
		return entity;
	}
	
    private void checkParam(DmsOrderReturnVo paramVo) {
		if (paramVo == null) {
			throw new ServiceException("参数异常");
		}
		if(paramVo.getUserId() == null || paramVo.getUserId() == 0){
			throw new ServiceException("用户ID不能为空");
		}
		if (paramVo.getOrderType() == null || paramVo.getOrderType() == 0) {
			throw new ServiceException("退换货类型不能为空");
		}
		if(paramVo.getAmount() == null || paramVo.getAmount().equals(BigDecimal.ZERO)){
			throw new ServiceException("订单总额不能为空或者不能为零");
		}
		if(BlankUtil.isEmpty(paramVo.getReturnCartIdList())){
			throw new ServiceException("退换货商品不能为空");
		}
		//换货需要录入收货人信息
		if(paramVo.getOrderType() == DmsModuleEnums.RETURN_ORDER_TYPE.CHANGE.getType()){
			if(paramVo.getReturnDelivery() == null){
				throw new ServiceException("换货收货人信息不能为空");
			} else {
				if(BlankUtil.isEmpty(paramVo.getReturnDelivery().getReceiver())
						|| BlankUtil.isEmpty(paramVo.getReturnDelivery().getMobile())
						|| BlankUtil.isEmpty(paramVo.getReturnDelivery().getAddress())){
					throw new ServiceException("换货收货人信息不能为空");
				}
			}
		}
		
	}

	/**
	 * 根据账号id获取用户的退换货订单数量
	 * @param userId
	 * @return
	 */
	@Override
	public Integer selectCountByUserId(Long userId) {
		return this.dmsOrderReturnMapper.selectCountByUserId(userId);
	}

	@Override
	public Map<String, Object> selectStorageInfoByOrderId(Long orderId) {
		Map<String, Object> result = Maps.newHashMap();
		List<DmsOrderReturnItemVo> orderProductList = this.dmsOrderReturnItemMapper.selectStorageInfoByOrderId(orderId);
        result.put("productList", orderProductList);
        result.put("code", SystemParamerConfiguration.getInstance().createSerialNumStr(DmsModuleEnums.SELL_OUT_SERIAL_KEY));  //生成的发货单号
		return result;
	}

	/**
	 * 发送消息
	 * @param type 1-新建退换货，2-审核通过，3-审核不通过，4-渠道商已发货,5-商家确认收货，6-商家已退款，7-交易取消
	 * @param orderId
	 * @param code
	 * @param staffId
	 * @param dealType
	 * @param channelName
	 * @param money
	 * @param userId
	 */
	private void sendMessage(Integer type,Long orderId,String code,Long staffId,Integer dealType,String channelName,BigDecimal money,Long userId,String way,String productNameStr){
		sendMessage(type,orderId,code,staffId,dealType,channelName,money,userId,way,"",null,productNameStr);
	}
	private void sendMessage(Integer type,Long orderId,String code,Long staffId,Integer dealType,String channelName,BigDecimal money,Long userId,String way,String remark,DmsOrderReturnDelivery returnDelivery,String productNameStr){
		String title = "";
		String subject = "";
		String content = "";
		int messageType = DmsModuleEnums.MESSAGE_TYPE.MESSAGE_RETURN.getType();
		int entityType = DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_ORDER_RETURN.getType();
		List<Long> staffs = new ArrayList<>();
		if (type==1){//创建退换货订单
			staffs.add(0L);//发给品牌商
			List<Long> staffs2 = new ArrayList<>();
			staffs2.add(staffId);
			title = "新的订单需要您审核";
			subject = channelName + "提交了新的订单￥"+money+"，需要您审核";
			content = "发起了退换货申请"+code+"，金额￥"+money.doubleValue()+"。";
			dmsMessageService.sendMessage(messageType, entityType, orderId,title,subject, content, staffs2,0,userId);
		}else if (type==2){//审核通过
			staffs.add(staffId);
			title = "您的退换货订单已确认";
			subject = "退换货订单号："+code+" 商品："+productNameStr;
			content = "商家同意了本次退换货申请。";
		}else if (type==3){//审核不通过
			staffs.add(staffId);
			title = "您的退换货订单审核未通过，已关闭";
			subject = "退换货订单号："+code+" 商品："+productNameStr;
			content = "商家拒绝了本次退换货申请。"+ (BlankUtil.isEmpty(remark)?"":"备注："+remark);
		}else if (type==4){//渠道商已发货
			String deliverType = dmsDataDictionayDependentService.getDataValueName("deliver_type",way);
			staffs.add(0L);
			List<Long> staffs2 = new ArrayList<>();
			staffs2.add(staffId);
			title = channelName + " 已经退货";
			subject = "退换货订单号："+code+" 商品："+productNameStr;
			content = "已发货。"+(BlankUtil.isEmpty(deliverType)?"":"配送方式："+deliverType);
			dmsMessageService.sendMessage(messageType, entityType, orderId,title,subject, content, staffs2,0,userId);
		}else  if(type==5){//商家确认收货
			staffs.add(staffId);
			title = "商品已签收";
			subject = "退换货订单号："+code+" 商品："+productNameStr;
			content = "商家已确认收货。";
		}else  if(type==6){//商家已退款
			staffs.add(staffId);
			title = "退款提醒";
			subject = "退换货订单号："+code+"退款已经成功 商品："+productNameStr;
			content = "商家已进行退款，退款金额：￥"+money+"，请注意查收。";
		}else  if(type==7){//交易取消
			staffs.add(0L);
			List<Long> staffs2 = new ArrayList<>();
			staffs2.add(staffId);
			title = "订单已关闭";
			subject = channelName + "取消了退换货订单"+code+"，金额￥"+money;
			content = "申请已取消，交易关闭。";
			dmsMessageService.sendMessage(messageType, entityType, orderId,title,subject, content, staffs2,0,userId);
		}

		dmsMessageService.sendMessage(messageType, entityType, orderId,title,subject, content, staffs,1,userId);
		if (type==2){
			title = "商家已确认退货地址";
			/*DmsSysRegion dmsSysRegion = dmsSysRegionService.selectByPrimaryKey(Integer.parseInt(BlankUtil.isEmpty(returnDelivery.getProvince())?"0":returnDelivery.getProvince()));
			String province = BlankUtil.isEmpty(dmsSysRegion)?"":dmsSysRegion.getRegionName();
			DmsSysRegion dmsSysRegion1 = dmsSysRegionService.selectByPrimaryKey(Integer.parseInt(BlankUtil.isEmpty(returnDelivery.getCity())?"0":returnDelivery.getCity()));
			String city =BlankUtil.isEmpty(dmsSysRegion1)?"":dmsSysRegion1.getRegionName();
			DmsSysRegion dmsSysRegion2 =  dmsSysRegionService.selectByPrimaryKey(Integer.parseInt(BlankUtil.isEmpty(returnDelivery.getCounty())?"0":returnDelivery.getCounty()));
			String county =BlankUtil.isEmpty(dmsSysRegion2)?"":dmsSysRegion2.getRegionName();
			content = "商家确认退货地址："+province+" "+city+" "+county+" "+returnDelivery.getAddress()+"（"+returnDelivery.getReceiver()+" 收） "+returnDelivery.getMobile();
			subject = "商家确认退货地址："+province+" "+city+" "+county+" "+returnDelivery.getAddress()+"（"+returnDelivery.getReceiver()+" 收） "+returnDelivery.getMobile();*/
			content="请按照商家提示地址进行退货";
			subject="请按照商家提示地址进行退货";
			dmsMessageService.sendMessage(messageType, entityType, orderId,title,subject, content, staffs,1,userId);
		}
	}

	private void sendMessage(Integer type,Long orderId,Long staffId,String channelName,Long userId,DmsOrderDeliveryVo orderDelivery,String code){
		String title = "";
		String subject = "";
		String content = "";
		int messageType = DmsModuleEnums.MESSAGE_TYPE.MESSAGE_RETURN.getType();
		int entityType = DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_ORDER_RETURN.getType();
		List<Long> staffs = new ArrayList<>();
		if (type==1){//商家发货
			staffs.add(staffId);
			title = "订单已发货";
			subject = "退（换）货订单"+code+"已发货，请您耐心等待";
			content = "商家已发货。物流公司："+dmsDataDictionayDependentService.getDataValueName("express_company",orderDelivery.getLogisticsCompany())+"，物流单号："+orderDelivery.getLogisticsCode();
		}else if (type==2){
			staffs.add(0L);
			List<Long> staffs2 = new ArrayList<>();
			staffs2.add(staffId);
			title = channelName + "商品已签收";
			subject ="退（换）货订单"+code+"已签收 商品：";
			content = "已确认收货。";
			dmsMessageService.sendMessage(messageType, entityType, orderId,title,subject, content, staffs2,0,userId);
		}
		dmsMessageService.sendMessage(messageType, entityType, orderId,title,subject, content, staffs,1,userId);
	}
}