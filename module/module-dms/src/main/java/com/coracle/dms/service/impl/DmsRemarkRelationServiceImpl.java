package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsRemarkRelationMapper;
import com.coracle.dms.po.DmsRemarkRelation;
import com.coracle.dms.service.DmsRemarkRelationService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsRemarkRelationServiceImpl extends BaseServiceImpl<DmsRemarkRelation> implements DmsRemarkRelationService {
    private static final Logger logger = Logger.getLogger(DmsRemarkRelationServiceImpl.class);

    @Autowired
    private DmsRemarkRelationMapper dmsRemarkRelationMapper;

    @Override
    public IMybatisDao<DmsRemarkRelation> getBaseDao() {
        return dmsRemarkRelationMapper;
    }
}