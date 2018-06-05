/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsOrderProduct;
import com.coracle.dms.vo.DmsOrderProductVo;
import com.coracle.dms.vo.DmsOrderVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DmsOrderProductMapper extends IMybatisDao<DmsOrderProduct> {

    /**
     * 批量新增订单产品信息
     * @param orderProductList
     */
    void batchInsert(List<DmsOrderProduct> orderProductList);

    /**
     * 根据订单id获取待发货产品信息列表，并根据产品id和规格属性获取有库存的仓库和货位列表
     * @param orderId
     * @return
     */
    List<DmsOrderProductVo> selectStorageInfoByOrderId(Long orderId);

    /**
     * 根据条件获取待发货产品信息列表
     * @param order
     * @return
     */
    List<DmsOrderProductVo> selectUndeliveredListByOrder(DmsOrderVo order);

    /**
     * 根据条件获取订单产品列表
     * @param order
     * @return
     */
    List<DmsOrderProductVo> selectByOrder(DmsOrderVo order);

    /**
     * 根据账号id获取已完成的订单产品，已完成但全部退货的数据不显示
     * @param order
     * @return
     */
    List<DmsOrderProductVo> selectFinishedByOrder(DmsOrderVo order);

    /**
     * 根据订单产品id条件获取星级统计
     * @param productId
     * @return
     */
    Map<String,Object> selectCountByOrderProductProductId(Long productId);

    /**
     * 获取订单的未收货产品数量
     * @param orderId
     * @return
     */
    Integer selectUnreceivedCountByOrderId(Long orderId);

    /**
     * 根据订单id获取订单总金额
     * @param orderId
     * @return
     */
    BigDecimal selectAmountByOrderId(Long orderId);

    /**
     * 根据条件逻辑删除订单产品
     *
     * @param param
     */

    void removeByCondition(DmsOrderProduct param);

    BigDecimal getTotalWeightByOrderId(Long orderId);

    BigDecimal getTotalVolumeByOrderId(Long orderId);
}