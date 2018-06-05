package com.coracle.dms.service;

import com.coracle.dms.po.DmsSysAreaRegion;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsSysAreaRegionService extends IBaseService<DmsSysAreaRegion> {

    /**
     *
     * 通过区域id，删除指定区域下的管理地区
     */
    void deleteByAreaId(Long areaId);
}