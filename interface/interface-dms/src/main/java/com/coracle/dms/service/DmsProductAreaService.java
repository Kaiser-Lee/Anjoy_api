package com.coracle.dms.service;

import com.coracle.dms.po.DmsProductArea;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsProductAreaService extends IBaseService<DmsProductArea> {
    Integer selectByChannelIdAndProductId(Long channelId, Long productId);
}