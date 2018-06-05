/**
 * create by hcs
 * @date 2017-10
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsPromotionScope;
import com.coracle.dms.vo.DmsPromotionScopeVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsPromotionScopeMapper extends IMybatisDao<DmsPromotionScope> {

    /**
     * 根据促销活动id删除促销适用范围信息
     *
     * @param promotionId
     */
    void deleteByPromotionId(Long promotionId);

    /**
     * 批量插入
     *
     * @param promotionScopeList
     */
    void batchInsert(List<DmsPromotionScope> promotionScopeList);

    /**
     * 根据促销活动id获取vo对象
     *
     * @param promotionId
     * @return
     */
    List<DmsPromotionScopeVo> selectVoByPromotionId(Long promotionId);

    /**
     * 根据促销活动id获取促销适用范围
     *
     * @param promotionId
     * @return
     */
    String selectScopeByPromotionId(Long promotionId);
}