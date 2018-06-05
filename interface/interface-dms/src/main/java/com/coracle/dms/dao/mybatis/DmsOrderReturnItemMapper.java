/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.po.DmsOrderReturnItem;
import com.coracle.dms.vo.DmsOrderReturnItemVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsOrderReturnItemMapper extends IMybatisDao<DmsOrderReturnItem> {
	
	/**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsOrderReturnItem> list);
    
    /**
     * 根据订单id查询订单列表
     * @param id
     * @return
     */
    List<DmsOrderReturnItemVo> getProductItemList(Long id);
    
    /**
     * 根据订单id查询发货清单列表
     * @param orderId
     * @return
     */
    List<DmsOrderReturnItemVo> selectStorageInfoByOrderId(Long orderId);
    
    /**
     * 根据订单id查询收货数量
     * @param orderId
     * @return
     */
    DmsOrderReturnItemVo selectOrderReturnTotal(Long orderId);
    /**
     * 根据订单id查询待发货产品列表
     * @param orderId
     * @return
     */
    List<DmsOrderReturnItemVo> selectUndeliveredListByOrderId(Long orderId);
    
    Integer selectUnreceivedCountByOrderId(Long orderId);
}