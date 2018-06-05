package com.coracle.dms.service;

import com.coracle.dms.po.DmsOrderProduct;
import com.coracle.dms.vo.DmsOrderProductVo;
import com.coracle.dms.vo.DmsOrderVo;
import com.coracle.dms.webservice.model.AnjoyOrderProductModel;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DmsOrderProductService extends IBaseService<DmsOrderProduct> {

    /**
     * 批量新增订单产品信息
     * @param orderProductList
     */
    void batchInsert(List<DmsOrderProduct> orderProductList);

    List<DmsOrderProductVo> selectStorageInfoByOrderId(Long orderId);

    /**
     * 根据订单产品id条件获取星级统计
     * @param productId
     * @return
     */
    Map<String,Object> selectCountByOrderProductProductId(Long productId);

    Integer selectUnreceivedCountByOrderId(Long orderId);

    PageHelper.Page<DmsOrderProductVo> finishedPageList(DmsOrderVo order);

    BigDecimal selectAmountByOrderId(Long orderId);

    /**
     * 修改订单产品信息
     * @param param
     */
    void update(DmsOrderVo param);

    List<AnjoyOrderProductModel> convertParam(List<DmsOrderProduct> orderProductList, Map<String, Object> paramMap);
}