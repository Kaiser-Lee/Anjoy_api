package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsExceptionClaimsMapper;
import com.coracle.dms.po.DmsExceptionClaims;
import com.coracle.dms.service.DmsExceptionClaimsService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsExceptionClaimsServiceImpl extends BaseServiceImpl<DmsExceptionClaims> implements DmsExceptionClaimsService {
    private static final Logger logger = Logger.getLogger(DmsExceptionClaimsServiceImpl.class);

    @Autowired
    private DmsExceptionClaimsMapper dmsExceptionClaimsMapper;

    @Override
    public IMybatisDao<DmsExceptionClaims> getBaseDao() {
        return dmsExceptionClaimsMapper;
    }
}