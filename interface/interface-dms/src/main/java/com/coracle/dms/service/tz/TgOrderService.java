package com.coracle.dms.service.tz;

import com.coracle.dms.po.tz.TgOrder;
import com.coracle.dms.po.tz.TgOrderProductPart;
import com.coracle.dms.vo.tz.TgOrderVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.Map;

public interface TgOrderService extends IBaseService<TgOrder> {

    /**
     * 天正订单列表
     * @param order
     * @param userSession
     * @return
     */
    PageHelper.Page<TgOrder> pageList(TgOrder order, UserSession userSession);

    /**
     * 天正添加订单产品分批发运信息
     * @param po
     * @return
     */
    int insterOrderProPart(TgOrderProductPart po);

    /**
     * 天正订单,删除订单产品
     * @param map
     * @return
     */
    void deleteOrderPro(Map<String, Object> map);

    /**
     * 天正订单,删除订单产品分批发运信息
     * @param map
     * @return
     */
    void deleteOrderProPart(Map<String, Object> map);

    /**
     * 天正批量设置提货地点
     * @param ids
     */
    void updatePickAddress(String ids,String pickAddress);

    /**
     * 新增订单
     * @param orderVo
     * @param userSession
     * @return
     */
    TgOrder create(TgOrderVo orderVo, UserSession userSession);

    /**
     * 订单详情
     * @param orderVo
     * @param userSession
     * @return
     */
    TgOrder detail(TgOrderVo orderVo, UserSession userSession);
}