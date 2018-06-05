package com.coracle.dms.service;

import com.coracle.dms.po.DmsOrderProductEvaluation;
import com.coracle.dms.vo.DmsOrderProductEvaluationVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsOrderProductEvaluationService extends IBaseService<DmsOrderProductEvaluation> {
    List<DmsOrderProductEvaluationVo> selectVoByCondition(DmsOrderProductEvaluation orderProductEvaluation);

    List<DmsOrderProductEvaluationVo> getProductEvaluation(Long productId);

    PageHelper.Page<DmsOrderProductEvaluationVo> selectVoByParam(DmsOrderProductEvaluationVo orderProductEvaluation);

    void removeByPrimaryKey(Long id);

    void batchRemove(List<Long> ids);

    void remove(DmsOrderProductEvaluation param);
}