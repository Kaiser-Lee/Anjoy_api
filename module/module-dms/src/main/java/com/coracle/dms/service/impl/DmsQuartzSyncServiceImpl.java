package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsQuartzSyncMapper;
import com.coracle.dms.po.DmsQuartzSync;
import com.coracle.dms.service.DmsQuartzSyncService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsQuartzSyncServiceImpl extends BaseServiceImpl<DmsQuartzSync> implements DmsQuartzSyncService {
    private static final Logger logger = Logger.getLogger(DmsQuartzSyncServiceImpl.class);

    @Autowired
    private DmsQuartzSyncMapper dmsQuartzSyncMapper;

    @Override
    public IMybatisDao<DmsQuartzSync> getBaseDao() {
        return dmsQuartzSyncMapper;
    }
}