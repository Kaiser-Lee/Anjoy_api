/**
 * create by hcs
 *
 * @date 2017-11
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsPromotionProductRecord;
import com.coracle.dms.vo.DmsPromotionProductRecordVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsPromotionProductRecordMapper extends IMybatisDao<DmsPromotionProductRecord> {

    /**
     * 根据促销产品id获取促销产品销售记录
     *
     * @param promotionProductRecord
     * @return
     */
    List<DmsPromotionProductRecordVo> selectVoByPromotionProductId(DmsPromotionProductRecordVo promotionProductRecord);

    /**
     *  根据促销产品id逻辑删除
     *
     * @param promotionProductId
     */
    void removeByPromotionProductId(Long promotionProductId);
}