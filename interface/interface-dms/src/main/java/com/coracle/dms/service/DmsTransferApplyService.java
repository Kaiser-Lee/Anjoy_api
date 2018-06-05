package com.coracle.dms.service;

import com.coracle.dms.dto.DmsTransferApplyDto;
import com.coracle.dms.po.DmsTransferApply;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsTransferApplyService extends IBaseService<DmsTransferApply> {
	
	/**
	 * 分页查询调货列表数据
	 * @param search
	 * @return
	 */
	Page<DmsTransferApplyDto> selectForListPage(DmsTransferApplyDto search);
	
	/**
	 * 查询调货详情
	 * @param id
	 * @param session
     * @return
	 */
	DmsTransferApplyDto detail(Long id, UserSession session);
	/**
	 * 创建调货申请单
	 * @param pareamVo
	 */
	void create(DmsTransferApplyDto pareamVo , UserSession session);
	
	/**
	 * 创建发货单
	 * @param paramVo
	 * @param session
	 */
	void createDelivery (DmsTransferApplyDto paramVo,UserSession session);
	
	/**
	 * 确认收货
	 * @param paramVo
	 * @param session
	 */
	void updateReceived(DmsTransferApplyDto paramVo,UserSession session);
	/**
	 * 取消调货
	 * @param paramVo
	 * @param session
	 */
	void updateCancel(DmsTransferApplyDto paramVo,UserSession session);
}