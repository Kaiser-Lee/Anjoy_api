/**
 * create by huangbaidong
 * @date 2017-03
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class BsdJobEntity extends AdapterEntity implements Serializable {
    private Long jobId;

    private String jobName;

    private String jobObject;

    private String jobClass;

    private String jobMethod;

    private Integer jobStatus;

    private Integer jobType;

    private String cronExpr;

    private Long runTimes;

    private Long runDuration;

    private Date runtimeLast;

    private Date runtimeNext;

    private Date syncBeginTime;

    private Date syncEndTime;

    @ApiModelProperty("创建日期")
    private Date createdDate;
    @ApiModelProperty("创建人ID")
    private Long createdBy;
    @ApiModelProperty("最后更新时间")
    private Date lastUpdatedDate;
    @ApiModelProperty("最后更新人ID")
    private Long lastUpdatedBy;
    @ApiModelProperty("软删除标识")
    private Integer removeFlag;

    private static final long serialVersionUID = 1L;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobObject() {
        return jobObject;
    }

    public void setJobObject(String jobObject) {
        this.jobObject = jobObject == null ? null : jobObject.trim();
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass == null ? null : jobClass.trim();
    }

    public String getJobMethod() {
        return jobMethod;
    }

    public void setJobMethod(String jobMethod) {
        this.jobMethod = jobMethod == null ? null : jobMethod.trim();
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr == null ? null : cronExpr.trim();
    }

    public Long getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(Long runTimes) {
        this.runTimes = runTimes;
    }

    public Long getRunDuration() {
        return runDuration;
    }

    public void setRunDuration(Long runDuration) {
        this.runDuration = runDuration;
    }

    public Date getRuntimeLast() {
        return runtimeLast;
    }

    public void setRuntimeLast(Date runtimeLast) {
        this.runtimeLast = runtimeLast;
    }

    public Date getRuntimeNext() {
        return runtimeNext;
    }

    public void setRuntimeNext(Date runtimeNext) {
        this.runtimeNext = runtimeNext;
    }

    public Date getSyncBeginTime() {
        return syncBeginTime;
    }

    public void setSyncBeginTime(Date syncBeginTime) {
        this.syncBeginTime = syncBeginTime;
    }

    public Date getSyncEndTime() {
        return syncEndTime;
    }

    public void setSyncEndTime(Date syncEndTime) {
        this.syncEndTime = syncEndTime;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }
}