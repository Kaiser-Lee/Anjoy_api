package com.coracle.dms.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsOrderOperationLogMapper;
import com.coracle.dms.po.DmsOrderOperationLog;
import com.coracle.dms.service.DmsOrderOperationLogService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;

@Service
public class DmsOrderOperationLogServiceImpl extends BaseServiceImpl<DmsOrderOperationLog> implements DmsOrderOperationLogService {

    @Autowired
    private DmsOrderOperationLogMapper dmsOrderOperationLogMapper;

    @Override
    public IMybatisDao<DmsOrderOperationLog> getBaseDao() {
        return dmsOrderOperationLogMapper;
    }
    @Override
	public void createOrderOperLog(Long orderId,String operation,Integer relatedType,Long userId){
		DmsOrderOperationLog orderLog = new DmsOrderOperationLog();
        orderLog.setOrderId(orderId);
        orderLog.setOperation(operation);
        orderLog.setRelatedType(relatedType);
        orderLog.setCreatedBy(userId);
        orderLog.setCreatedDate(new Date());
        orderLog.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsOrderOperationLogMapper.insert(orderLog);
	}
    @Override
	public void createOrderOperLog(Long orderId,String operation,Long userId){
		this.createOrderOperLog(orderId, operation, DmsModuleEnums.ORDER_DELIVERY_TYPE.ORDER.getType(), userId);
	}
    @Override
	public void createOrderReturnOperLog(Long orderId,String operation,Long userId){
		this.createOrderOperLog(orderId, operation, DmsModuleEnums.ORDER_DELIVERY_TYPE.ORDER_RETURN.getType(), userId);
	}
	@Override
	public void createTransferOperLog(Long orderId, String operation, Long userId) {
		this.createOrderOperLog(orderId, operation, DmsModuleEnums.ORDER_DELIVERY_TYPE.TRANSFER.getType(), userId);
	}
}