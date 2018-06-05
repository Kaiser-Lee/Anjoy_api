/**
 * create by hcs
 * @date 2017-09
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsOrderProductEvaluation;
import com.coracle.dms.vo.DmsOrderProductEvaluationVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsOrderProductEvaluationMapper extends IMybatisDao<DmsOrderProductEvaluation> {

    List<DmsOrderProductEvaluationVo> selectVoByCondition(DmsOrderProductEvaluation orderProductEvaluation);

    List<DmsOrderProductEvaluationVo> selectVoByParam(DmsOrderProductEvaluationVo param);

    List<DmsOrderProductEvaluationVo> selectVoByOrderProductProductId(Long productId);

    /**
     * 根据主键id逻辑删除组织
     * @param id
     */
    void removeByPrimaryKey(Long id);

    void batchRemove(List<Long> ids);
}