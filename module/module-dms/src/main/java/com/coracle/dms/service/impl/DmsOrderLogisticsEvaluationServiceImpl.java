package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsOrderLogisticsEvaluationMapper;
import com.coracle.dms.po.DmsOrderLogisticsEvaluation;
import com.coracle.dms.service.DmsOrderLogisticsEvaluationService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsOrderLogisticsEvaluationServiceImpl extends BaseServiceImpl<DmsOrderLogisticsEvaluation> implements DmsOrderLogisticsEvaluationService {
    private static final Logger logger = Logger.getLogger(DmsOrderLogisticsEvaluationServiceImpl.class);

    @Autowired
    private DmsOrderLogisticsEvaluationMapper dmsOrderLogisticsEvaluationMapper;

    @Override
    public IMybatisDao<DmsOrderLogisticsEvaluation> getBaseDao() {
        return dmsOrderLogisticsEvaluationMapper;
    }
}