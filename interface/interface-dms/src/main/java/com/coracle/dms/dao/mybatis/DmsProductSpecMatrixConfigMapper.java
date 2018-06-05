/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsProductSpecMatrixConfig;
import com.coracle.dms.vo.DmsProductSpecMatrixConfigVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsProductSpecMatrixConfigMapper extends IMybatisDao<DmsProductSpecMatrixConfig> {

    /**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsProductSpecMatrixConfig> list);

    /**
     * 批量删除
     * @param list
     */
    void batchDelete(List<DmsProductSpecMatrixConfig> list);

   /**
     * 根据产品id获取产品的规格属性矩阵vo
     * @param product
     * @return
     */
    List<DmsProductSpecMatrixConfigVo> selectVoByProductId(DmsProductVo product);

    /**
     * 根据产品id和规格属性获取产品的库存
     * @param dmsProductSpecMatrixConfigVo
     * @return
     */
    List<DmsProductSpecMatrixConfigVo> selectVoByProductIdSpec(DmsProductSpecMatrixConfigVo dmsProductSpecMatrixConfigVo);

    /**
     * 根据产品id获取产品规格属性的数量
     *
     * @param productId
     * @return
     */
    Integer selectCountByProductId(Long productId);
}