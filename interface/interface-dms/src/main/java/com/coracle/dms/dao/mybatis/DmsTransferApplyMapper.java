/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coracle.dms.dto.DmsTransferApplyDto;
import com.coracle.dms.po.DmsTransferApply;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsTransferApplyMapper extends IMybatisDao<DmsTransferApply> {
	/**
	 * 分页查询调货列表
	 * @param search
	 * @return
	 */
	List<DmsTransferApplyDto> getPageList(DmsTransferApplyDto search);
	/**
	 * 查询调货详情.包括产品信息等
	 * @param id
	 * @return
	 */
	DmsTransferApplyDto selectDetailByPrimaryKey(@Param("id") Long id,@Param("storeId") Long storeId);
	/**
	 * 查询调货详情.只查询调货数据
	 * @param id
	 * @return
	 */
	DmsTransferApplyDto selectDtoByPrimaryKey(Long id);
	
	/**
	 * 查询库存量
	 * @param productId
	 * @param productSpecId
	 * @param orgId
	 * @return
	 */
	Integer selectTransferInventory(@Param("productId") Long productId, @Param("productSpecId") Long productSpecId,
									@Param("storageType") Integer storageType, @Param("orgId") Long orgId);
}