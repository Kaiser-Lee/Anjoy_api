package com.coracle.dms.service;

import com.coracle.dms.po.DmsPromotionProductRecord;
import com.coracle.dms.vo.DmsPromotionProductRecordVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsPromotionProductRecordService extends IBaseService<DmsPromotionProductRecord> {

    /**
     * 促销产品销售记录列表
     *
     * @param promotionProductRecord
     * @return
     */
    PageHelper.Page<DmsPromotionProductRecordVo> pageList(DmsPromotionProductRecordVo promotionProductRecord);

    /**
     *  根据促销产品id逻辑删除
     *
     * @param promotionProductId
     */
    void removeByPromotionProductId(Long promotionProductId);
}