/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsProduct;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DmsProductMapper extends IMybatisDao<DmsProduct> {

    List<DmsProductVo> findProductPCPageList(DmsProduct dmsProduct);

    List<DmsProductVo> findProductPCSpecPageList(DmsProduct dmsProduct);

    List<DmsProductVo> findProductPageList(DmsProductVo dmsProduct);

    List<DmsProductVo> findProductPageListStore(DmsProductVo dmsProductVo);

    DmsProductVo getProductDetails(Map<String, Object> map);

    DmsProductVo getProductDetailsAPP(Map<String, Object> map);

    List<DmsProductVo> getProductDetailSpecMatrix(Map<String, Object> map);

    void addReadCount(Long id);

    void up(List<Long> ids);

    void down(List<Long> ids);

    void updateById(Long id);

    Map<String, Object> findProductNameSpecName(Map<String, Object> map);

    /**
     * 获取用户收藏页面的产品价格
     *
     * @param product
     * @return
     */
    BigDecimal selectMinPriceForUserCollect(DmsProductVo product);

    /**
     * 根据商品编码查询商品分类
     */
    String selectByProductCode(String ProductCode);

    /**
     * 通过产品编码查询产品信息
     * @param code
     * @return
     */
    DmsProduct selectByCode(String code);

    /**
     * 根据条件查询渠道下的商品
     */

    List<DmsProductVo> findProductForMinimum(DmsProductVo product);

    List<DmsProductVo> findProductForMinimum2(DmsChannelVo dmsChannelVo);

    List<DmsProductVo> findProductById(Long id);

    /**
     * 根据产品规格获取产品列表
     *
     * @return
     */
    List<DmsProductVo> listBySpecifications(DmsProductVo param);

    /**
     * 批量插入
     *
     * @param list
     */
    void batchInsert(List<DmsProduct> list);

    void batchUpdate(List<DmsProduct> list);

    /**
     * 通过渠道CODE，产品CODE获取价格
     * @param paramMap
     * easChannelCode
     * easProductCode
     * @return
     */
    BigDecimal selectProductPrice(Map<String, Object> paramMap);

    Integer deleteProductSyncAnjoy();

    DmsProduct selectOneByCondition(DmsProduct dmsProduct);

    /**
     * 判断经销商是否有产品白名单数据
     * @param channelId
     * @return
     */
    Integer findCountChannelProductWhite(@Param("channelId") Long channelId);

}