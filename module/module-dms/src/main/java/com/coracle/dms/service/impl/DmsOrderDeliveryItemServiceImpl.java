package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsOrderDeliveryItemMapper;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsStorageInventoryVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DmsOrderDeliveryItemServiceImpl extends BaseServiceImpl<DmsOrderDeliveryItem> implements DmsOrderDeliveryItemService {
    private static final Logger logger = Logger.getLogger(DmsOrderDeliveryItemServiceImpl.class);

    @Autowired
    private DmsOrderDeliveryItemMapper dmsOrderDeliveryItemMapper;

    @Autowired
    private DmsOrderService dmsOrderService;

    @Autowired
    private DmsOrderProductService dmsOrderProductService;

    @Autowired
    private DmsOrderOperationLogService dmsOrderOperationLogService;

    @Autowired
    private DmsStorageInventoryService dmsStorageInventoryService;

    @Autowired
    private DmsStorageService dmsStorageService;

    @Autowired
    private DmsStorageTransportationService dmsStorageTransportationService;

    @Autowired
    private DmsMessageService dmsMessageService;

    @Override
    public IMybatisDao<DmsOrderDeliveryItem> getBaseDao() {
        return dmsOrderDeliveryItemMapper;
    }

    /**
     * 批量插入
     * @param itemList
     */
    @Override
    public void batchInsert(List<DmsOrderDeliveryItem> itemList) {
        dmsOrderDeliveryItemMapper.batchInsert(itemList);
    }

    /**
     * 确认收货
     * @param item
     * @param session
     */
    @Override
    public void confirm(DmsOrderDeliveryItem item, UserSession session) {
        Long userId = session.getId();

        DmsOrderDeliveryItem entity = dmsOrderDeliveryItemMapper.selectByPrimaryKey(item.getId());
        if (entity == null) {
            throw new ServiceException("数据库中不存在id为: " + "的产品发货信息");
        }

        /* 修改"确认收货日期" */
        item.setReceiveDate(new Date());
        dmsOrderDeliveryItemMapper.updateByPrimaryKeySelective(item);

        /* 收货之后增加仓库的库存 */
        updateInventory(item, session);

        /* 根据发货、收货情况修改订单状态 */
        DmsOrder order = dmsOrderService.selectByPrimaryKey(entity.getOrderId());
        order.setLastUpdatedBy(userId);
        dmsOrderService.updateOrderStatusByItem(order);
        sendMessage(order.getId(),order.getUserId(),order.getCustomerName(),session.getId(),order.getCode());
    }

    /**
     * 更新库存信息：订单收货之后，增加渠道商的库存
     * @param
     * @param session
     */
    @Override
    public void updateInventory(DmsOrderDeliveryItem orderDeliveryItem, UserSession session) {
        DmsStorageInventoryVo inventory = new DmsStorageInventoryVo();

        /* 获取渠道商的仓库 */
        DmsOrderDeliveryItem item = dmsOrderDeliveryItemMapper.selectByPrimaryKey(orderDeliveryItem.getId());
        DmsOrder order = dmsOrderService.selectByPrimaryKey(item.getOrderId());
        DmsStorage storage = dmsStorageService.selectByChannelContactUserId(order.getUserId());

        DmsOrderProduct orderProduct = dmsOrderProductService.selectByPrimaryKey(item.getOrderProductId());

        /* 增加渠道商的库存 */
        inventory.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.PUT_IN.getType());
        inventory.setProductId(orderProduct.getProductId());
        inventory.setProductSpecId(orderProduct.getProductSpecId());
        inventory.setStorage(storage.getId());
        inventory.setOutInType(DmsModuleEnums.STORAGE_OUTIN.IN.getType());
        inventory.setInOrgType(DmsModuleEnums.STORAGE_TYPE.CHANNEL.getType());
        inventory.setInOrgId(session.getOrgId());
        inventory.setAddOrSubtractNum(item.getCount());
        inventory.setTransFlag(false);
        dmsStorageInventoryService.addOrSubtract(inventory, session);

        /* 删除在途库存 */
        DmsStorageTransportation storageTransportation = new DmsStorageTransportation();
        storageTransportation.setRelationType(DmsModuleEnums.RELATION_TYPE.SEND.getType());
        storageTransportation.setRelationId(item.getId());
        dmsStorageTransportationService.updateTrans(storageTransportation);
    }

    /**
     * 获取已发货但还未确认收货的订单产品数量
     * @param orderId
     * @return
     */
    @Override
    public Integer selectUnconfirmedCount(Long orderId) {
        return dmsOrderDeliveryItemMapper.selectUnconfirmedCount(orderId);
    }
    @Override
    public Integer selectUnconfirmedCountRT(Long orderId){
    	return this.dmsOrderDeliveryItemMapper.selectUnconfirmedCountRT(orderId);
    }

    private void sendMessage(Long orderId,Long staffId,String channelName,Long userId,String code){
        List<Long> staffs = new ArrayList<>();
        staffs.add(0L);
        List<Long> staffs2 = new ArrayList<>();
        staffs2.add(staffId);
        String title = "订单已签收";
        String subject = "订单"+code+"已签收成功（点击查看详情）";
        String content = "已确认收货。";
        dmsMessageService.sendMessage(DmsModuleEnums.MESSAGE_TYPE.MESSAGE_ORDER.getType(), DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_ORDER.getType(), orderId,title,subject, content, staffs2,0,userId);
        dmsMessageService.sendMessage(DmsModuleEnums.MESSAGE_TYPE.MESSAGE_ORDER.getType(), DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_ORDER.getType(), orderId,title,subject, content, staffs,1,userId);
    }
}