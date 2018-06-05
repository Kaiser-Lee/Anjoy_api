package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsOrderProductEvaluationMapper;
import com.coracle.dms.po.DmsOrderProductEvaluation;
import com.coracle.dms.service.DmsOrderProductEvaluationService;
import com.coracle.dms.vo.DmsOrderProductEvaluationVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsOrderProductEvaluationServiceImpl extends BaseServiceImpl<DmsOrderProductEvaluation> implements DmsOrderProductEvaluationService {
    private static final Logger logger = Logger.getLogger(DmsOrderProductEvaluationServiceImpl.class);

    @Autowired
    private DmsOrderProductEvaluationMapper dmsOrderProductEvaluationMapper;

    @Override
    public IMybatisDao<DmsOrderProductEvaluation> getBaseDao() {
        return dmsOrderProductEvaluationMapper;
    }

    /**
     * 根据订单产品id获取评价
     *
     * @param orderProductEvaluation
     * @return
     */
    @Override
    public List<DmsOrderProductEvaluationVo> selectVoByCondition(DmsOrderProductEvaluation orderProductEvaluation) {
        return dmsOrderProductEvaluationMapper.selectVoByCondition(orderProductEvaluation);
    }

    /**
     * 获取产品的评价列表
     *
     * @param  productId
     * @return
     */
    public List<DmsOrderProductEvaluationVo> getProductEvaluation(Long productId) {
        List<DmsOrderProductEvaluationVo> dmsOrderProductEvaluationVoList = dmsOrderProductEvaluationMapper.selectVoByOrderProductProductId(productId);
        return dmsOrderProductEvaluationVoList;
    }

    /**
     * 根据产品id获取评价
     *
     * @param orderProductEvaluation
     * @return
     */
    @Override
    public PageHelper.Page<DmsOrderProductEvaluationVo> selectVoByParam(DmsOrderProductEvaluationVo orderProductEvaluation) {
        try {
            PageHelper.startPage(orderProductEvaluation.getP(), orderProductEvaluation.getS());
            dmsOrderProductEvaluationMapper.selectVoByParam(orderProductEvaluation);
        } catch (Exception e) {
            logger.error("订单分页查询异常!", e);
            throw new ServiceException("订单分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 根据主键id逻辑删除组织
     * @param id
     */
    @Override
    public void removeByPrimaryKey(Long id) {
        dmsOrderProductEvaluationMapper.removeByPrimaryKey(id);
    }

    /**
     * 根据id列表批量逻辑删除
     * @param ids
     */
    @Override
    public void batchRemove(List<Long> ids) {
        dmsOrderProductEvaluationMapper.batchRemove(ids);
    }

    /**
     * 单个或批量删除订单产品评价
     *
     * @param param
     */
    @Override
    public void remove(DmsOrderProductEvaluation param) {
        Long id = param.getId();
        List<Long> idList = param.getIds();

        //如果ids参数不为空，则优先作为批量删除操作处理；否则，作为单个删除处理
        if (idList != null && idList.size() > 0) {
            dmsOrderProductEvaluationMapper.batchRemove(idList);
        }
        else if (id != null) {  //如果id不为空，
            dmsOrderProductEvaluationMapper.removeByPrimaryKey(id);
        } else {
            throw new ServiceException("参数错误");
        }
    }
}