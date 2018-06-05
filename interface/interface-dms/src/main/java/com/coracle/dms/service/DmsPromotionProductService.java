package com.coracle.dms.service;

import com.coracle.dms.po.DmsPromotionProduct;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.vo.DmsPromotionProductVo;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsPromotionProductService extends IBaseService<DmsPromotionProduct> {

    /**
     * 新增/修改促销适用产品信息
     *
     * @param promotionId
     * @param promotionProductList
     * @param session
     */
    void insertOrUpdate(Long promotionId, List<DmsPromotionProductVo> promotionProductList, UserSession session);

    /**
     * 根据条件获取折扣最高的一条促销信息
     * 用于订单下单时选择最佳的一条促销
     *
     * @param product
     * @return
     */
    DmsPromotionProductVo bestChoice(DmsProductVo product);

    /**
     * 根据条件获取可用的产品促销数量
     *
     * @param promotionProduct
     * @return
     */
    Integer selectAvailableCountByCondition(DmsPromotionProductVo promotionProduct);
}