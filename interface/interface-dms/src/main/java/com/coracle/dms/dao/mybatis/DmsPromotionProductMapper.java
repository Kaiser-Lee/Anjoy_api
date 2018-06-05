/**
 * create by hcs
 * @date 2017-10
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsProduct;
import com.coracle.dms.po.DmsPromotionProduct;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.vo.DmsPromotionProductVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsPromotionProductMapper extends IMybatisDao<DmsPromotionProduct> {

    /**
     * 根据促销活动id获取促销适用产品信息id列表
     *
     * @param promotionId
     * @return
     */
    List<Long> selectIdListByPromotionId(Long promotionId);

    /**
     * 根据id列表批量逻辑删除
     *
     * @param ids
     */
    void batchRemove(List<Long> ids);

    /**
     * 根据促销活动id获取促销适用产品信息
     *
     * @param promotionId
     * @return
     */
    List<DmsPromotionProductVo> selectVoByPromotionId(Long promotionId);

    /**
     * 根据条件获取指定产品的促销信息
     *
     * @param product
     * @return
     */
    Integer selectCountByProduct(DmsProductVo product);

    /**
     * 根据条件获取折扣最高的一条促销信息
     * 用于订单下单时选择最佳的一条促销
     *
     * @param product
     * @return
     */
    DmsPromotionProductVo selectBestByProduct(DmsProductVo product);

    /**
     * 根据条件获取折扣最高的一条折扣促销类型的促销信息
     *
     * @param product
     * @return
     */
    DmsPromotionProductVo selectBestPromotingPromotionByProduct(DmsProductVo product);

    /**
     * 根据条件获取可用的产品促销数量
     *
     * @param promotionProduct
     * @return
     */
    Integer selectAvailableCountByCondition(DmsPromotionProductVo promotionProduct);
}