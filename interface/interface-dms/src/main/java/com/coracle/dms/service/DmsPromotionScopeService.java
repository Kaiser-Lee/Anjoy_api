package com.coracle.dms.service;

import com.coracle.dms.po.DmsPromotionScope;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsPromotionScopeService extends IBaseService<DmsPromotionScope> {

    /**
     * 根据促销活动id删除促销适用范围信息
     *
     * @param promotionId
     */
    void deleteByPromotionId(Long promotionId);

    /**
     * 批量新增促销适用范围信息
     *  @param promotionId
     * @param scopeIdList
     * @param session
     */
    void insertOrUpdate(Long promotionId, List<Long> scopeIdList, UserSession session);
}