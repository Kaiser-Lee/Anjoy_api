/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.po.DmsOrderReturn;
import com.coracle.dms.vo.DmsOrderReturnVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsOrderReturnMapper extends IMybatisDao<DmsOrderReturn> {
	/**
	 * 查询订单列表
	 * @param search
	 * @return
	 */
	List<DmsOrderReturnVo> getPageList(DmsOrderReturnVo search);
	/**
	 * 查询订单详情
	 * @param id
	 * @return
	 */
	DmsOrderReturnVo selectDetailByPrimaryKey(Long id);
	
	void updateStatus(DmsOrderReturnVo paramVo);

	/**
	 * 根据账号id获取用户的退换货订单数量
	 * @param userId
	 * @return
	 */
	Integer selectCountByUserId(Long userId);
}