package com.coracle.dms.service;

import java.util.Map;

import com.coracle.dms.po.DmsOrderDeliveryItem;
import com.coracle.dms.po.DmsOrderReturn;
import com.coracle.dms.vo.DmsOrderDeliveryVo;
import com.coracle.dms.vo.DmsOrderReturnItemVo;
import com.coracle.dms.vo.DmsOrderReturnVo;
import com.coracle.dms.vo.DmsReturnRequestVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsOrderReturnService extends IBaseService<DmsOrderReturn> {
	
	/**
	 * 分页查询退换货列表
	 * @param search
	 * @return
	 */
	Page<DmsOrderReturnVo> selectForListPage(DmsOrderReturnVo search);
	
	/**
	 * 查询退换货详情
	 * @param id
	 * @return
	 */
	Map<String,Object> detail(Long id);
	
	/**
	 * 创建退换货单
	 * @param paramVo
	 * @param session
	 */
	DmsOrderReturnVo create(DmsOrderReturnVo paramVo, UserSession session);

	DmsOrderReturnVo anjoyReturnSync(DmsReturnRequestVo requestVo);

	/**
	 * 品牌商审核退换货订单
	 * @param paramVo
	 * @param session
	 */
	void audit(DmsOrderReturnVo paramVo, UserSession session);

	/***
	 * 安井退货订单审核
	 * @param orderNo
	 * @param auit
	 * @param remark
     */
	void audit(String orderNo,Integer auit,String remark);
	/**
	 * 渠道发货
	 * @param paramVo
	 * @param session
	 */
	void updateDelivery(DmsOrderReturnVo paramVo, UserSession session);
	/**
	 * 品牌商确认收货
	 * @param paramVo
	 * @param session
	 */
	void updateReceived(DmsOrderReturnItemVo orderReturnItem, UserSession session);
	
	/**
	 * 品牌商发货
	 * @param paramVo
	 * @param session
	 */
	void updateDeliveryBrand(DmsOrderDeliveryVo orderDelivery, UserSession session); 
	/**
	 * 渠道确认收货
	 * @param paramVo
	 * @param session
	 */
	void updateReceivedChannel(DmsOrderReturnVo paramVo, UserSession session);
	/**
	 * 渠道确认收货（针对单个产品）
	 * @param paramVo
	 * @param session
	 */
    void updateReceivedChannel(DmsOrderDeliveryItem paramVo, UserSession session);
	/**
	 * 取消订单
	 * @param paramVo
	 * @param session
	 */
	void updateCancel(DmsOrderReturnVo paramVo, UserSession session);
	/**
	 * 确认退款
	 * @param paramVo
	 * @param session
	 */
	void updateReturnPay(DmsOrderReturnVo paramVo, UserSession session);
	/**
	 * 根据账号id获取用户的退换货订单数量
	 * @param userId
	 * @return
	 */
	Integer selectCountByUserId(Long userId);
	/**
	 * 根据订单id查询发货清单
	 * @param orderId
	 * @return
	 */
	Map<String, Object> selectStorageInfoByOrderId(Long orderId);
}