package com.coracle.dms.service;

import com.coracle.dms.po.DmsLadderPrice;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsLadderPriceService extends IBaseService<DmsLadderPrice> {

    void insertOrUpdate(Long ladderPriceProductId, List<DmsLadderPrice> dmsLadderPriceList, UserSession userSession);

}