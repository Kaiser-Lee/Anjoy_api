package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.JobEntityMapper;
import com.coracle.dms.po.JobEntity;
import com.coracle.dms.service.JobEntityService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobEntityServiceImpl extends BaseServiceImpl<JobEntity> implements JobEntityService {
    private static final Logger logger = Logger.getLogger(JobEntityServiceImpl.class);

    @Autowired
    private JobEntityMapper jobEntityMapper;

    @Override
    public IMybatisDao<JobEntity> getBaseDao() {
        return jobEntityMapper;
    }
}