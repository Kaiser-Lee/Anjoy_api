package com.coracle.dms.service;

import java.util.List;

import com.coracle.dms.po.DmsOrderReturnCart;
import com.coracle.dms.vo.DmsOrderReturnCartVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsOrderReturnCartService extends IBaseService<DmsOrderReturnCart> {
	/**
	 * 分页条件查询
	 * @param search
	 * @return
	 */
	PageHelper.Page<DmsOrderReturnCartVo> selectForListPage(DmsOrderReturnCartVo search);
	/**
	 * 创建退换货清单
	 * @param paramVo
	 */
	void create(DmsOrderReturnCart paramVo);
	/**
	 * 删除退换货清单
	 * @param id
	 */
	void deleteByIdSoft(List<Long> ids);
	/**
	 * 获取退换货清单数量
	 * @param paramVo
	 * @return
	 */
	Integer getOrderReturnCartCount(DmsOrderReturnCart paramVo);
	/**
	 * 批量修改购物清单
	 * @param cartList
	 * @param session
	 */
	public void batchUpdate(List<DmsOrderReturnCart> cartList,UserSession session);
	
	public Integer selectReturnCount(DmsOrderReturnCart paramVo);
}