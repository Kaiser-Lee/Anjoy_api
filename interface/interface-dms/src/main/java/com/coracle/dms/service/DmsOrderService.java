package com.coracle.dms.service;

import com.coracle.dms.po.DmsOrder;
import com.coracle.dms.vo.DmsOrderVo;
import com.coracle.dms.webservice.model.AnjoyOrderModel;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.math.BigDecimal;
import java.util.Map;

public interface DmsOrderService extends IBaseService<DmsOrder> {

    /**
     * 创建订单
     * @param orderVo
     * @param session
     */
    DmsOrder create(DmsOrderVo orderVo, UserSession session);

    DmsOrder audit(DmsOrderVo order, UserSession userSession);

    void audit(String orderNo,Integer auit,String remark);

    void cancel(DmsOrder order);

    void updateOrderStatusByItem(DmsOrder order);

    void confirm(DmsOrder order, UserSession session);

    /**
     * 订单列表(分页)
     * @param orderVo
     * @param session
     * @return
     */
    PageHelper.Page<DmsOrderVo> pageList(DmsOrderVo orderVo, UserSession session);

    PageHelper.Page<DmsOrderVo> pageListPC(DmsOrderVo orderVo, UserSession session);

    DmsOrderVo again(DmsOrderVo param);

    /**
     * 订单详情
     * @param param
     * @param session
     * @return
     */
    DmsOrderVo detail(DmsOrderVo param, UserSession session);


    Map<String, Integer> count(Long userId);




    /**
     * 获取用户可用额度
     * @param userId
     * @return
     */
    BigDecimal getAvailableLimit(Long userId);

    /**
     * 效验订单金额是否超出可用额度
     */
    void checkAvailableLimit(DmsOrderVo orderVo);

    /**
     * 将订单同步到安井
     * @param order
     */
    void pushOrderToAnjoy(DmsOrder order, Map<String, Object> paramMap);

    /**
     * 将DMS订单数据转化成 Anjoy需要的数据格式
     * @param order
     * @return
     */
    AnjoyOrderModel convertParam(DmsOrder order);
    //AnJoyOrder convertParam(DmsOrder order);

}