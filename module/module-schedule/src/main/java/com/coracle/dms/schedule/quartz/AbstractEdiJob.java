package com.coracle.dms.schedule.quartz;

import com.coracle.dms.po.JobEntity;
import com.coracle.dms.schedule.service.JobEntityService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huangbaidong
 * 2017/3/29.
 */
public abstract class AbstractEdiJob implements Job
{
    protected JobEntity jobEntity;
    protected static final Logger logger= LoggerFactory.getLogger(AbstractEdiJob.class);
    private Long beginTime;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobEntityService jobService=SchedulerHelper.getJobService(context);
        preExcute(jobService,context);
        exeucuteInternal(context);
        postExcute(jobService,context);
    }

    abstract public void exeucuteInternal(JobExecutionContext context);

    public void preExcute(JobEntityService jobService,JobExecutionContext context)
    {
        beginTime=System.currentTimeMillis();
    }

    public void postExcute(JobEntityService jobService,JobExecutionContext context)
    {
        //获得最新的jobEntiry
        jobEntity=jobService.getJobById(jobEntity.getJobId());
        if(jobEntity==null)
        {
            logger.warn(jobEntity.getJobId()+"job不存在");
            return;
        }

        if(context.getFireTime()!=null)
            jobEntity.setRuntimeLast(context.getFireTime());
        if(context.getNextFireTime()!=null)
            jobEntity.setRuntimeNext(context.getNextFireTime());

        Long times=jobEntity.getRunTimes();
        jobEntity.setRunTimes((times==null?0l:times)+1);
        Long duration=jobEntity.getRunDuration();
        jobEntity.setRunDuration((duration==null?0l:duration)+(System.currentTimeMillis() - beginTime));
        jobService.updateJob(jobEntity);
    }


    public void setJobEntity(JobEntity jobEntity) {
        this.jobEntity = jobEntity;
    }


}
