/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsOrderPayment;
import com.coracle.dms.vo.DmsOrderPaymentVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.math.BigDecimal;
import java.util.List;

public interface DmsOrderPaymentMapper extends IMybatisDao<DmsOrderPayment> {

    /**
     * 根据订单id获取订单收款记录列表
     * @param orderId
     * @return
     */
    List<DmsOrderPaymentVo> selectByOrderId(Long orderId);


    /**
     * 根据订单id获取已收金额
     * @param orderId
     * @return
     */
    BigDecimal selectAmountByOrderId(Long orderId);

    /**
     * 根据主键id获取vo
     * @param id
     * @return
     */
    DmsOrderPaymentVo selectVoByPrimaryKey(Long id);
}