package com.coracle.dms.schedule.service.impl;

import com.coracle.dms.po.JobEntity;
import com.coracle.dms.schedule.dao.mybatis.JobEntityMapper;
import com.coracle.dms.schedule.service.JobEntityService;
import com.coracle.dms.schedule.quartz.SchedulerHelper;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobEntityServiceImpl extends BaseServiceImpl<JobEntity> implements JobEntityService {
    private static final Logger logger = Logger.getLogger(JobEntityServiceImpl.class);

    @Autowired
    private JobEntityMapper jobEntityMapper;
    @Autowired
    private SchedulerHelper schedulerHelper;

    @Override
    public IMybatisDao<JobEntity> getBaseDao() {
        return jobEntityMapper;
    }


    @Override
    public void updateJob(JobEntity jobEntity) {
        jobEntityMapper.updateByPrimaryKeySelective(jobEntity);
    }

    @Override
    public JobEntity getJobById(Long jobId) {
        return jobEntityMapper.selectByPrimaryKey(jobId);
    }


    @Override
    public List<JobEntity> getActiveJob() {
        JobEntity query = new JobEntity();
        query.setRemoveFlag(0);
        List<JobEntity> jobEntities = jobEntityMapper.selectByCondition(query);
        return jobEntities;
    }

    /**
     * 重新加载定时器
     */
    public void reloadSchedule() {
        try {
            schedulerHelper.createActiveJobFromDB();
        } catch (SchedulerException e) {
            throw new ServiceException("重新加载定时任务失败"+e.getMessage());
        }
    }
}