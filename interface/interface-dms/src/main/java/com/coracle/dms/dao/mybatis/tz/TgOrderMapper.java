/**
 * create by apple
 * @date 2018-01
 */
package com.coracle.dms.dao.mybatis.tz;

import com.coracle.dms.po.tz.TgOrder;
import com.coracle.dms.vo.tz.TgOrderVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;
import java.util.Map;

public interface TgOrderMapper extends IMybatisDao<TgOrder> {

    /**
     * 天正订单列表
     * @param order
     */
    List<TgOrder> tgOrderList(TgOrder order);

    /**
     * 删除订单产品
     * @param map
     */
    void deleteOrderPro(Map<String, Object> map);

    /**
     * 删除订单产品发运信息
     * @param map
     */
    void deleteOrderProPart(Map<String, Object> map);

    /**
     * 天正订单详情
     * @param param
     * @return
     */
    TgOrderVo selectVoByOrder(TgOrderVo param);
}