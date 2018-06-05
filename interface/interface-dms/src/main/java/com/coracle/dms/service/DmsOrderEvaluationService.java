package com.coracle.dms.service;

import com.coracle.dms.po.DmsOrderProductEvaluation;
import com.coracle.dms.vo.DmsOrderEvaluationVo;
import com.coracle.dms.vo.DmsOrderProductEvaluationVo;

import java.util.List;

public interface DmsOrderEvaluationService {

    void create(DmsOrderEvaluationVo evaluationVo);

    DmsOrderEvaluationVo detail(DmsOrderProductEvaluation param);
}