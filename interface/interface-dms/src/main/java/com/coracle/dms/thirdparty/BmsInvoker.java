package com.coracle.dms.thirdparty;

/**
 * Created by huangbaidong
 * 2017/5/31.
 */
public interface BmsInvoker {
    /**
     * 同步安井产品信息
     */
    void anjoySyn();

    /**
     * 接口05：BMS库存数据
     * 每晚21:00定时同步
     */
    //void findStock();

    /**
     * 接口22：条码合法性校验
     * APP扫码发货是验证合法性
     * @param barCode  条码
     * @param type      1、服务商->零售商，2、销售出库
     * @param userSession
     * @return
     */
    //Object sendOutCheck(String barCode,String type, UserSession userSession);


    /**
     * 接口26：同步APP订单SO
     * 如果是PDA发货, 同步订单到BMS
     * @param orderId
     */
    //boolean sendOrder(Long orderId);

    /**
     * 接口23：APP发货单
     * 发货方式为APP扫码发货时，APP完成扫码发货会实时调用此接口同步扫码记录到BMS
     * @param orderId
     */
    //boolean sendOut(Long orderId);

    /**
     * 接口23：APP发货单（销售出库专用）
     * 销售出库时调用
     * @param groupId
     */
    //boolean sendOutForSalesOut(String groupId);

    /**
     * 接口25：零售商完成收货
     * APP零售商确认收货会实时调用25接口，同步状态到BMS
     * @param orderCode
     * @return
     */
    //boolean recvIn(String orderCode);


    /**
     * 接口51：盘点条码合法性检查
     * @param cBarCode
     * @return
     */
    //Object STKCheck(String cBarCode);

    /**
     * 接口52：同步APP盘点单
     * @return
     */
    //boolean STKOrder(Long id);
}
