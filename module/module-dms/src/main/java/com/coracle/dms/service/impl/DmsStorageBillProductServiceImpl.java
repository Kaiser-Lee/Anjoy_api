package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsStorageBillProductMapper;
import com.coracle.dms.po.DmsStorageBillProduct;
import com.coracle.dms.service.DmsStorageBillProductService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsStorageBillProductServiceImpl extends BaseServiceImpl<DmsStorageBillProduct> implements DmsStorageBillProductService {
    private static final Logger logger = Logger.getLogger(DmsStorageBillProductServiceImpl.class);

    @Autowired
    private DmsStorageBillProductMapper dmsStorageBillProductMapper;

    @Override
    public IMybatisDao<DmsStorageBillProduct> getBaseDao() {
        return dmsStorageBillProductMapper;
    }
}