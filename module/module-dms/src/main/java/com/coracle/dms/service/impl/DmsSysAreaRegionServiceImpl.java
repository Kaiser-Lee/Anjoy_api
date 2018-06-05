package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsSysAreaRegionMapper;
import com.coracle.dms.po.DmsSysAreaRegion;
import com.coracle.dms.service.DmsSysAreaRegionService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsSysAreaRegionServiceImpl extends BaseServiceImpl<DmsSysAreaRegion> implements DmsSysAreaRegionService {
    private static final Logger logger = Logger.getLogger(DmsSysAreaRegionServiceImpl.class);

    @Autowired
    private DmsSysAreaRegionMapper dmsSysAreaRegionMapper;

    @Override
    public IMybatisDao<DmsSysAreaRegion> getBaseDao() {
        return dmsSysAreaRegionMapper;
    }

    @Override
    public void deleteByAreaId(Long areaId) {
         dmsSysAreaRegionMapper.deleteByAreaId(areaId);
    }

}