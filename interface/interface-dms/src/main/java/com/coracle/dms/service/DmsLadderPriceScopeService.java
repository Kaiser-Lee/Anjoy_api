package com.coracle.dms.service;

import com.coracle.dms.po.DmsLadderPriceScope;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsLadderPriceScopeService extends IBaseService<DmsLadderPriceScope> {


    /**
     * 根据阶梯价格id删除使用范围
     */

    void deleteByLadderPriceProductId(Long ladderPriceProductId);

    /**
     * 增加或更新使用范围
     */

    void  insertOrUpdate(Long ladderPriceProductId, List<Long> scopeIdList, UserSession userSession);
}