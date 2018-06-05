package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsSynSurplusGoodMapper;
import com.coracle.dms.po.DmsSynSurplusGood;
import com.coracle.dms.service.DmsSynSurplusGoodService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsSynSurplusGoodServiceImpl extends BaseServiceImpl<DmsSynSurplusGood> implements DmsSynSurplusGoodService {
    private static final Logger logger = Logger.getLogger(DmsSynSurplusGoodServiceImpl.class);

    @Autowired
    private DmsSynSurplusGoodMapper dmsSynSurplusGoodMapper;

    @Override
    public IMybatisDao<DmsSynSurplusGood> getBaseDao() {
        return dmsSynSurplusGoodMapper;
    }
}