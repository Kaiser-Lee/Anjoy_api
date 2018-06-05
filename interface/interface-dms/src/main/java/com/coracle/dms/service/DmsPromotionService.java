package com.coracle.dms.service;

import com.coracle.dms.po.DmsPromotion;
import com.coracle.dms.vo.DmsPromotionVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsPromotionService extends IBaseService<DmsPromotion> {

    /**
     * 新增/修改促销活动
     *
     * @param promotionVo
     * @param session
     */
    void insertOrUpdate(DmsPromotionVo promotionVo, UserSession session);

    PageHelper.Page<DmsPromotionVo> pageList(DmsPromotionVo promotionVo);

    DmsPromotionVo detail(Long id);

    Boolean modifiable(Long id);
}