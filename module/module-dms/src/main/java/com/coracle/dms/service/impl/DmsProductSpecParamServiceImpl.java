package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsProductSpecParamMapper;
import com.coracle.dms.po.DmsProductSpecParam;
import com.coracle.dms.service.DmsProductSpecParamService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsProductSpecParamServiceImpl extends BaseServiceImpl<DmsProductSpecParam> implements DmsProductSpecParamService {
    private static final Logger logger = Logger.getLogger(DmsProductSpecParamServiceImpl.class);

    @Autowired
    private DmsProductSpecParamMapper dmsProductSpecParamMapper;

    @Override
    public IMybatisDao<DmsProductSpecParam> getBaseDao() {
        return dmsProductSpecParamMapper;
    }
}