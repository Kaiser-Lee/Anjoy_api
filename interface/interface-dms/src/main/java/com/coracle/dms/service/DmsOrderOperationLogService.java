package com.coracle.dms.service;

import com.coracle.dms.po.DmsOrderOperationLog;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsOrderOperationLogService extends IBaseService<DmsOrderOperationLog> {
	
	void createOrderOperLog(Long orderId,String operation,Integer relatedType,Long userId);
	/**
	 * 创建订单记录日志
	 * @param orderId
	 * @param operation
	 * @param userId
	 */
	void createOrderOperLog(Long orderId,String operation,Long userId);
	/**
	 * 创建退换货记录日志
	 * @param orderId
	 * @param operation
	 * @param userId
	 */
	void createOrderReturnOperLog(Long orderId,String operation,Long userId);
	/**
	 * 创建调货记录日志
	 * @param orderId
	 * @param operation
	 * @param userId
	 */
	void createTransferOperLog(Long orderId,String operation,Long userId);
}