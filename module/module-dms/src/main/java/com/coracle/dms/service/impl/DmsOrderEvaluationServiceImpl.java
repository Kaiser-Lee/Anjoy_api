package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsAttachmentRelation;
import com.coracle.dms.po.DmsOrderLogisticsEvaluation;
import com.coracle.dms.po.DmsOrderProductEvaluation;
import com.coracle.dms.service.DmsAttachmentRelationService;
import com.coracle.dms.service.DmsOrderEvaluationService;
import com.coracle.dms.service.DmsOrderLogisticsEvaluationService;
import com.coracle.dms.service.DmsOrderProductEvaluationService;
import com.coracle.dms.vo.DmsOrderEvaluationVo;
import com.coracle.dms.vo.DmsOrderProductEvaluationVo;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DmsOrderEvaluationServiceImpl implements DmsOrderEvaluationService {
    private static final Logger logger = Logger.getLogger(DmsOrderEvaluationServiceImpl.class);

    @Autowired
    private DmsAttachmentRelationService dmsAttachmentRelationService;

    @Autowired
    private DmsOrderProductEvaluationService dmsOrderProductEvaluationService;

    @Autowired
    private DmsOrderLogisticsEvaluationService dmsOrderLogisticsEvaluationService;

    /**
     * 新建订单评价
     * @param evaluationVo
     */
    @Override
    public void create(DmsOrderEvaluationVo evaluationVo) {
        Long userId = evaluationVo.getUserId();
        DmsOrderLogisticsEvaluation logisticsEvaluation = evaluationVo.getLogisticsEvaluation();
        if (logisticsEvaluation != null) {
            logisticsEvaluation.setCreatedBy(userId);
            logisticsEvaluation.setCreatedDate(new Date());
            logisticsEvaluation.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

            /* 1.新增订单物流服务评价信息 */
            int count = dmsOrderLogisticsEvaluationService.insert(evaluationVo.getLogisticsEvaluation());
            if (count < 0) {
                throw new ServiceException("新增订单物流服务评价信息出错");
            }
        }

        /* 2.新增订单产品评价信息 */
        List<DmsOrderProductEvaluationVo> productEvaluationVoList = evaluationVo.getProductEvaluationList();
        for (DmsOrderProductEvaluationVo productEvaluationVo : productEvaluationVoList) {
            productEvaluationVo.setCreatedBy(userId);
            productEvaluationVo.setCreatedDate(new Date());
            productEvaluationVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            Integer count = dmsOrderProductEvaluationService.insert(productEvaluationVo);
            if (count < 0) {
                throw new ServiceException("新增订单产品评价信息出错");
            }
            List<DmsAttachmentRelation> attachmentRelationList = Lists.newArrayList();
            for (Long picId : productEvaluationVo.getPicFileIdList()) {
                DmsAttachmentRelation attachmentRelation = new DmsAttachmentRelation();
                attachmentRelation.setAttachId(picId);
                attachmentRelation.setRelatedTableType(DmsModuleEnums.ATTACHMENT_RELATED_TABLE_TYPE.ORDER_PRODUCT_EVALUATION.getType());
                attachmentRelation.setRelatedTableId(productEvaluationVo.getId());
                attachmentRelation.setCreatedBy(userId);
                attachmentRelation.setCreatedDate(new Date());
                attachmentRelation.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                attachmentRelationList.add(attachmentRelation);
            }
            //批量插入附件（图片）关联信息
            if (!attachmentRelationList.isEmpty()) {
                dmsAttachmentRelationService.batchInsert(attachmentRelationList);
            }
        }
    }

    /**
     * 获取订单评价详情
     * @param param
     */
    @Override
    public DmsOrderEvaluationVo detail(DmsOrderProductEvaluation param) {
        DmsOrderEvaluationVo result = new DmsOrderEvaluationVo();

        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        /* 1.如果传了orderId和orderProductId参数，则获取的是某一条订单产品的评价 */
        /* 2.如果只传orderId参数，则获取的是该订单所有订单产品的评价 */
        List<DmsOrderProductEvaluationVo> orderProductEvaluationVoList = dmsOrderProductEvaluationService.selectVoByCondition(param);
        result.setProductEvaluationList(orderProductEvaluationVoList);

        /* 设置评价人和评价人的头像，放到最外层 */
        if (!orderProductEvaluationVoList.isEmpty()) {
            DmsOrderProductEvaluationVo first = orderProductEvaluationVoList.get(0);
            result.setCreatedDate(first.getCreatedDate());
            result.setEvaluator(first.getEvaluator());
            result.setPicUrl(first.getPicUrl());
        }

        /* 3.获取物流服务评价信息 */
        DmsOrderLogisticsEvaluation condition = new DmsOrderLogisticsEvaluation();
        condition.setOrderId(param.getOrderId());
        condition.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        List<DmsOrderLogisticsEvaluation> orderLogisticsEvaluation = dmsOrderLogisticsEvaluationService.selectByCondition(condition);
        if (orderLogisticsEvaluation != null && !orderLogisticsEvaluation.isEmpty()) {
            result.setLogisticsEvaluation(orderLogisticsEvaluation.get(0));
        } else {
            result.setLogisticsEvaluation(new DmsOrderLogisticsEvaluation());
        }

        return result;
    }

}