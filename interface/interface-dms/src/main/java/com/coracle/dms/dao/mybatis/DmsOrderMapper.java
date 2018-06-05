/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsOrder;
import com.coracle.dms.vo.DmsOrderVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.math.BigDecimal;
import java.util.List;

public interface DmsOrderMapper extends IMybatisDao<DmsOrder> {

    /**
     * 订单详情
     * @param order
     * @return
     */
    DmsOrderVo selectVoByOrder(DmsOrderVo order);

    /**
     * 订单列表
     * @param orderVo
     * @return
     */
    List<DmsOrderVo> selectVoByCondition(DmsOrderVo orderVo);


    List<DmsOrderVo> selectVoByConditionPC(DmsOrderVo orderVo);

    /**
     * 根据条件订单数量
     * @param order
     * @return
     */
    Integer selectCountByCondition(DmsOrder order);

    /**
     * 根据条件订单数量
     * @param order
     * @return
     */
    BigDecimal selectSumByCondition(DmsOrder order);

    /**
     * 获取"待评价"的订单数量
     * @param userId
     * @return
     */
    Integer selectUnEvaluatedCountByUserId(Long userId);


    /**
     * 通过EAS订货单编码查询
     * @param thirdOrderNo
     * @return
     */
    DmsOrder selectByThirdOrderNo(String thirdOrderNo);

    DmsOrder selectOneByCondition(DmsOrder dmsOrder);

}