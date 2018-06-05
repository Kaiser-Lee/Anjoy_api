package com.coracle.dms.vo;

import com.coracle.dms.po.DmsOrderLogisticsEvaluation;
import com.coracle.dms.po.DmsOrderProductEvaluation;
import com.coracle.yk.base.po.AdapterEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DmsOrderEvaluationVo extends AdapterEntity implements Serializable {

    @ApiModelProperty("当前登录账号id")
    private Long userId;

    @ApiModelProperty("评价人")
    private String evaluator;

    @ApiModelProperty("评价人的头像")
    private String picUrl;

    @ApiModelProperty("评价时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdDate;

    @ApiModelProperty("订单物流服务评价信息")
    private DmsOrderLogisticsEvaluation logisticsEvaluation;

    @ApiModelProperty("订单商品评价信息")
    private List<DmsOrderProductEvaluationVo> productEvaluationList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public DmsOrderLogisticsEvaluation getLogisticsEvaluation() {
        return logisticsEvaluation;
    }

    public void setLogisticsEvaluation(DmsOrderLogisticsEvaluation logisticsEvaluation) {
        this.logisticsEvaluation = logisticsEvaluation;
    }

    public List<DmsOrderProductEvaluationVo> getProductEvaluationList() {
        return productEvaluationList;
    }

    public void setProductEvaluationList(List<DmsOrderProductEvaluationVo> productEvaluationList) {
        this.productEvaluationList = productEvaluationList;
    }
}
