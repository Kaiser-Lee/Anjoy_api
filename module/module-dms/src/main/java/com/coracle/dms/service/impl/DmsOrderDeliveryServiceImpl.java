package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSON;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsOrderDeliveryMapper;
import com.coracle.dms.dao.mybatis.DmsOrderMapper;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.*;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.StringUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.components.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DmsOrderDeliveryServiceImpl extends BaseServiceImpl<DmsOrderDelivery> implements DmsOrderDeliveryService {
    private static final Logger logger = LoggerFactory.getLogger(DmsOrderDeliveryServiceImpl.class);

    @Autowired
    private DmsOrderDeliveryMapper dmsOrderDeliveryMapper;
    @Autowired
    private DmsOrderService dmsOrderService;
    @Autowired
    private DmsOrderMapper dmsOrderMapper;
    @Autowired
    private DmsStoreService dmsStoreService;
    @Autowired
    private DmsOrderProductService dmsOrderProductService;
    @Autowired
    private DmsOrderDeliveryItemService dmsOrderDeliveryItemService;
    @Autowired
    private DmsOrderOperationLogService dmsOrderOperationLogService;
    @Autowired
    private DmsStorageInventoryService dmsStorageInventoryService;
    @Autowired
    private DmsStorageTransportationService dmsStorageTransportationService;
    @Autowired
    private DmsProductService dmsProductService;
    @Autowired
    private DmsMessageService dmsMessageService;
    @Autowired
    private DmsDataDictionayDependentService dmsDataDictionayDependentService;

    @Override
    public IMybatisDao<DmsOrderDelivery> getBaseDao() {
        return dmsOrderDeliveryMapper;
    }

    /**
     * 创建发货单，发货操作
     */
    @Override
    @Transactional(readOnly = false)
    public void create(DmsOrderDeliveryVo orderDeliveryVo, UserSession session) {
        checkParam(orderDeliveryVo);

        Long userId = orderDeliveryVo.getCreatedBy();  //当前登录账号id
        /* 1.新增发货单信息 */
        orderDeliveryVo.setRelatedType(DmsModuleEnums.ORDER_DELIVERY_TYPE.ORDER.getType());
        int count = dmsOrderDeliveryMapper.insert(orderDeliveryVo);
        if (count <= 0) {
            throw new ServiceException("新增发货单异常");
        }

        /* 为避免用户重复点击"发货"按钮，发货操作前先获取待发货商品数量 */
        Integer unreceivedCount = dmsOrderProductService.selectUnreceivedCountByOrderId(orderDeliveryVo.getOrderId());

        if (unreceivedCount <= 0) {
            throw new ServiceException("该用户不存在待发货的产品，操作有误");
        }

        /* 2.新增发货清单 */
        List<DmsOrderDeliveryItem> itemList = orderDeliveryVo.getOrderDeliveryItemList();
        for (DmsOrderDeliveryItem item : itemList) {
            item.setOrderId(orderDeliveryVo.getOrderId());
            item.setOrderDeliveryId(orderDeliveryVo.getId());
            item.setRelatedType(orderDeliveryVo.getRelatedType());
            item.setDeliverDate(new Date());
            item.setCreatedDate(new Date());
            item.setCreatedBy(userId);
            item.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

            dmsOrderDeliveryItemService.insert(item);

            /* 发货之后减少库存 */
            updateInventory(item, session);

            /* 修改产品的销量 */
            Long orderProductId = item.getOrderProductId();
            DmsOrderProduct orderProduct = dmsOrderProductService.selectByPrimaryKey(orderProductId);
            if (orderProduct == null) {
                throw new ServiceException("参数错误，数据库中不存在id为: " + orderProductId + "的订单产品信息");
            }
            dmsProductService.addSalesVolume(orderProduct.getProductId(), item.getCount());
        }

        /* 修改订单的状态为"待收货" */
        DmsOrder order = dmsOrderService.selectByPrimaryKey(orderDeliveryVo.getOrderId());
        order.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.RECEIVE_AWAIT.getType());
        order.setLastUpdatedBy(orderDeliveryVo.getCreatedBy());
        order.setLastUpdatedDate(new Date());
        dmsOrderService.updateByPrimaryKeySelective(order);

        /* 新增订单操作日志 */
        dmsOrderOperationLogService.createOrderOperLog(order.getId(), "发货", userId);

        sendMessage(order.getId(), order.getUserId(), orderDeliveryVo.getLogisticsCompany(), orderDeliveryVo.getLogisticsCode(), orderDeliveryVo.getLastUpdatedBy(), order.getCode());
    }


    @Override
    @Transactional(readOnly = false)
    public void createAnjoy(DmsOrderDeliveryAnjoyVo orderDeliveryVo) {
        //Long userId = orderDeliveryVo.getCreatedBy();  //当前登录账号id
        logger.info("*************** 安井-发货数据：{} ***************", JSON.toJSONString(orderDeliveryVo));
        List<DmsOrderDeliveryItemAnjoyVo> deliveryItemList = orderDeliveryVo.getOrderDeliveryItemList();
        if(deliveryItemList == null || deliveryItemList.size() == 0){
            throw new ServiceException("缺少发货明细数据！");
        }

        //订单记录
        Map<Long, DmsOrder> dmsOrderMap = new HashMap<>();
        Map<Long, List<DmsOrderDeliveryItemAnjoyVo>> dmsOrderAnjoyVoMap = new HashMap<>();
        for (DmsOrderDeliveryItemAnjoyVo anjoyVo : deliveryItemList){
            //查询订单信息
            DmsOrder orderCondition = new DmsOrder();
            orderCondition.setThirdOrderNo(anjoyVo.getEasOrderNo());
            DmsOrder dmsOrder = dmsOrderMapper.selectOneByCondition(orderCondition);
            if (dmsOrder == null || dmsOrder.getId() == null) {
                throw new ServiceException("订单编号异常");
            }

            Long dmsOrderId = dmsOrder.getId();
            if(dmsOrderMap.get(dmsOrderId) == null){
                dmsOrderMap.put(dmsOrderId, dmsOrder);
            }
            List<DmsOrderDeliveryItemAnjoyVo> anjoyDeliveryVoList = null;
            if(dmsOrderAnjoyVoMap.get(dmsOrderId) == null){
                anjoyDeliveryVoList = new ArrayList<>();
                anjoyDeliveryVoList.add(anjoyVo);
                dmsOrderAnjoyVoMap.put(dmsOrderId, anjoyDeliveryVoList);
            }else {
                anjoyDeliveryVoList = dmsOrderAnjoyVoMap.get(dmsOrderId);
                anjoyDeliveryVoList.add(anjoyVo);
            }
        }

        List<Long> orderIdList = new ArrayList<>();
        orderIdList.addAll(dmsOrderMap.keySet());
        if(orderIdList.size() > 0){
            //将发货订单历史数据置为：已删除状态
            dmsOrderDeliveryMapper.deleteOrderDeliveryHistory(orderIdList);
            dmsOrderDeliveryMapper.deleteOrderDeliveryItemHistory(orderIdList);
        }

        for(Map.Entry<Long, List<DmsOrderDeliveryItemAnjoyVo>> entry : dmsOrderAnjoyVoMap.entrySet()){
            Long orderId = entry.getKey();
            DmsOrder order = dmsOrderMap.get(orderId);
            List<DmsOrderDeliveryItemAnjoyVo> itemList = entry.getValue();

            DmsOrderDelivery delivery = new DmsOrderDelivery();
            /* 1.新增发货单信息 */
            delivery.setRelatedType(DmsModuleEnums.ORDER_DELIVERY_TYPE.ORDER.getType());
            delivery.setCode(orderDeliveryVo.getCode());
            delivery.setLogisticsCode(orderDeliveryVo.getLogisticsCode());
            delivery.setLogisticsCompany(orderDeliveryVo.getLogisticsCompany());
            delivery.setDriverName(orderDeliveryVo.getDriverName());
            delivery.setDriverMobile(orderDeliveryVo.getDriverMobile());
            delivery.setPlateNumber(orderDeliveryVo.getPlateNumber());
            delivery.setAttr1(orderDeliveryVo.getAttr1());
            delivery.setAttr2(orderDeliveryVo.getAttr2());
            delivery.setAttr3(orderDeliveryVo.getAttr3());
            delivery.setAttr4(orderDeliveryVo.getAttr4());
            delivery.setAttr5(orderDeliveryVo.getAttr5());
            delivery.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            delivery.setOrderId(orderId);
            delivery.setCreatedDate(new Date());
            delivery.setCreatedBy(order.getCreatedBy());
            int count = dmsOrderDeliveryMapper.insert(delivery);
            if (count <= 0) {
                throw new ServiceException("新增发货单异常");
            }

            /* 为避免用户重复点击"发货"按钮，发货操作前先获取待发货商品数量 */
            Integer unreceivedCount = dmsOrderProductService.selectUnreceivedCountByOrderId(delivery.getOrderId());
            if (unreceivedCount <= 0) {
                throw new ServiceException("该用户不存在待发货的产品，操作有误");
            }

            //获取仓库信息
            DmsStore store = new DmsStore();
            store.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            List<DmsStore> storeList = dmsStoreService.selectByCondition(store);
            store = StringUtil.isListEmpty(storeList) ? null : storeList.get(0);

            /* 2.新增发货清单 */
            for (DmsOrderDeliveryItemAnjoyVo itemVo : itemList) {
                DmsOrderDeliveryItem item = new DmsOrderDeliveryItem();
                item.setOrderId(delivery.getOrderId());
                item.setOrderDeliveryId(delivery.getId());
                item.setRelatedType(delivery.getRelatedType());
                item.setDeliverDate(new Date());
                item.setCreatedDate(new Date());
                item.setCreatedBy(delivery.getCreatedBy());
                item.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                item.setCount(itemVo.getCount());

                DmsOrderProduct dmsOrderProduct = new DmsOrderProduct();
                dmsOrderProduct.setOrderId(delivery.getOrderId());
                dmsOrderProduct.setProductCode(itemVo.getEasProductCode());
                List<DmsOrderProduct> productList = dmsOrderProductService.selectByCondition(dmsOrderProduct);
                if (StringUtil.isListEmpty(productList)) {
                    throw new ServiceException("该产品不存在该订单中");
                }
                item.setOrderProductId(productList.get(0).getId());
                if (store != null) {
                    item.setStorageId(store.getId());
                }
                item.setDeliverDate(itemVo.getDeliverDate());
                dmsOrderDeliveryItemService.insert(item);

                /* 发货之后减少库存 */
                //updateInventory(item, session);

                /* 修改产品的销量 */
                Long orderProductId = item.getOrderProductId();
                DmsOrderProduct orderProduct = dmsOrderProductService.selectByPrimaryKey(orderProductId);
                if (orderProduct == null) {
                    throw new ServiceException("参数错误，数据库中不存在编码为: " + itemVo.getEasProductCode() + "的订单产品信息");
                }
                dmsProductService.addSalesVolume(orderProduct.getProductId(), item.getCount());
            }

            /* 修改订单的状态为"待收货" */
            order.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.RECEIVE_AWAIT.getType());
            order.setLastUpdatedDate(new Date());
            dmsOrderService.updateByPrimaryKeySelective(order);

            /* 新增订单操作日志 */
            dmsOrderOperationLogService.createOrderOperLog(order.getId(), "发货", order.getUserId());

            //sendMessage(order.getId(), order.getUserId(), orderDeliveryVo.getLogisticsCompany(), orderDeliveryVo.getLogisticsCode(), order.getUserId(), order.getCode());
        }

    }

    /**
     * 更新库存信息：订单发货后，减少品牌商的库存
     *
     * @param item
     * @param session
     */
    @Transactional(readOnly = false)
    private void updateInventory(DmsOrderDeliveryItem item, UserSession session) {
        DmsStorageInventoryVo inventory = new DmsStorageInventoryVo();

        DmsOrderProduct orderProduct = dmsOrderProductService.selectByPrimaryKey(item.getOrderProductId());
        DmsOrderDelivery orderDelivery = dmsOrderDeliveryMapper.selectByPrimaryKey(item.getOrderDeliveryId());

        /* 减少库存 */
        inventory.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.SALE_OUT.getType());
        inventory.setProductId(orderProduct.getProductId());
        inventory.setProductSpecId(orderProduct.getProductSpecId());
        inventory.setStorage(item.getStorageId());
        inventory.setStorageLocal(item.getStorageLocalId());
        inventory.setOutInType(DmsModuleEnums.STORAGE_OUTIN.OUT.getType());
        inventory.setOutOrgId(session.getOrgId());
        inventory.setOutOrgType(DmsModuleEnums.STORAGE_TYPE.BRAND.getType());
        inventory.setAddOrSubtractNum(item.getCount());
        inventory.setTransFlag(true);
        dmsStorageInventoryService.addOrSubtract(inventory, session);

        /* 新增在途库存记录 */
        DmsStorageTransportation storageTransportation = new DmsStorageTransportation();
        storageTransportation.setRelationId(item.getId());
        storageTransportation.setRelationType(DmsModuleEnums.RELATION_TYPE.SEND.getType());
        storageTransportation.setSendBill(orderDelivery.getCode());
        storageTransportation.setProductId(orderProduct.getProductId());
        storageTransportation.setProductSpecId(orderProduct.getProductSpecId());
        storageTransportation.setTransportationNum(item.getCount());
        storageTransportation.setSendStorage(item.getStorageId());
        storageTransportation.setSendStorageLocal(item.getStorageLocalId());
        dmsStorageTransportationService.create(storageTransportation, session);
    }

    private void checkParam(DmsOrderDelivery orderDelivery) {
        if (orderDelivery == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(orderDelivery.getCode())) {
            throw new ServiceException("发货单号不能为空");
        }
        if (orderDelivery.getOrderId() == null || orderDelivery.getOrderId() == 0) {
            throw new ServiceException("订单id不能为空");
        }
        if (StringUtils.isBlank(orderDelivery.getLogisticsCompany())) {
            throw new ServiceException("请选择物流公司");
        }
        if (StringUtils.isBlank(orderDelivery.getLogisticsCode())) {
            throw new ServiceException("物流单号不能为空");
        }
    }

    private void sendMessage(Long orderId, Long staffId, String expressCom, String expressNo, Long userId, String code) {
        List<Long> staffs = new ArrayList<>();
        staffs.add(staffId);
        String title = "订单已发货";
        String subject = "订单" + code + "已发货，请您耐心等待";
        String content = "商家已发货。物流公司：" + dmsDataDictionayDependentService.getDataValueName("express_company", expressCom) + "，物流单号：" + expressNo;
        dmsMessageService.sendMessage(DmsModuleEnums.MESSAGE_TYPE.MESSAGE_ORDER.getType(), DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_ORDER.getType(), orderId, title, subject, content, staffs, 1, userId);
    }
}