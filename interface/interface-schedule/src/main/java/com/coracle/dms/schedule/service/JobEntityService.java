package com.coracle.dms.schedule.service;

import com.coracle.dms.po.JobEntity;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface JobEntityService extends IBaseService<JobEntity> {

    void updateJob(JobEntity jobEntity);

    JobEntity getJobById(Long jobId);

    List<JobEntity> getActiveJob();

    /**
     * 重新加载定时器
     */
    void reloadSchedule();
}