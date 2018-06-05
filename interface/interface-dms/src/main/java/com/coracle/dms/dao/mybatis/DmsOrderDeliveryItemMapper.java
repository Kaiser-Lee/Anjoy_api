/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsOrderDeliveryItem;
import com.coracle.dms.vo.DmsOrderDeliveryItemVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsOrderDeliveryItemMapper extends IMybatisDao<DmsOrderDeliveryItem> {

    /**
     * 批量插入
     * @param itemList
     */
    void batchInsert(List<DmsOrderDeliveryItem> itemList);

    /**
     * 根据订单id获取已发货产品信息列表
     * @param orderId
     * @return
     */
    List<DmsOrderDeliveryItemVo> selectDeliveredListByOrderId(Long orderId);
    /**
     * 退换货使用
     * @param orderId
     * @return
     */
    List<DmsOrderDeliveryItemVo> selectDeliveredListByOrderIdRT(Long orderId); 

    /**
     * 获取已发货，但是还未确认收货的订单产品数量
     * @param orderId
     * @return
     */
    Integer selectUnconfirmedCount(Long orderId);
    /**
     * 退换货使用（后续统一修改公用方法）
     * @param orderId
     * @return
     */
    Integer selectUnconfirmedCountRT(Long orderId);
}