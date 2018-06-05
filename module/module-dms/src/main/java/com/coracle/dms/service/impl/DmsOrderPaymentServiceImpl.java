package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsOrderPaymentMapper;
import com.coracle.dms.po.DmsAttachmentRelation;
import com.coracle.dms.po.DmsOrder;
import com.coracle.dms.po.DmsOrderOperationLog;
import com.coracle.dms.po.DmsOrderPayment;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsOrderPaymentVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.xiruo.medbid.util.DecimalUtil;
import com.xiruo.medbid.util.OrderRandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DmsOrderPaymentServiceImpl extends BaseServiceImpl<DmsOrderPayment> implements DmsOrderPaymentService {
    private static final Logger logger = Logger.getLogger(DmsOrderPaymentServiceImpl.class);

    @Autowired
    private DmsOrderPaymentMapper dmsOrderPaymentMapper;

    @Autowired
    private DmsOrderOperationLogService dmsOrderOperationLogService;

    @Autowired
    private DmsOrderService dmsOrderService;

    @Autowired
    private DmsAttachmentRelationService dmsAttachmentRelationService;

    @Autowired
    private DmsMessageService dmsMessageService;

    @Override
    public IMybatisDao<DmsOrderPayment> getBaseDao() {
        return dmsOrderPaymentMapper;
    }

    /**
     * 新增订单付款记录
     * @param orderPaymentVo
     */
    @Override
    public void create(DmsOrderPaymentVo orderPaymentVo) {

        /* 先统计订单的收款金额，如果收款金额大于订单的金额，则提示用户 */
        DmsOrder order = dmsOrderService.selectByPrimaryKey(orderPaymentVo.getOrderId());
        BigDecimal received = dmsOrderPaymentMapper.selectAmountByOrderId(orderPaymentVo.getOrderId());

        //收款金额总额
        BigDecimal receivedAmount = DecimalUtil.round(received.add(orderPaymentVo.getAmount()));
        if (receivedAmount.compareTo(order.getAmount()) > 0) {
            throw new ServiceException("付款总金额大于订单总金额，请核实后再提交");
        }

        orderPaymentVo.setCode(OrderRandomUtils.generateOrderCode());  //生成支付单号
        orderPaymentVo.setConfirmAmount(new BigDecimal(0));
        orderPaymentVo.setType(DmsModuleEnums.PAYMENT_TYPE.PUBLIC_TRANSFER.getType());  //对公转账
        orderPaymentVo.setStatus(DmsModuleEnums.PAYMENT_CONFIRM_TYPE.UNCONFIRMED.getType());  //未确认

        int count = dmsOrderPaymentMapper.insert(orderPaymentVo);
        if (count < 0) {
            throw new ServiceException("新增付款信息失败");
        }

        /* 保存转账凭证 */
        List<DmsAttachmentRelation> attachmentRelationList = Lists.newArrayList();
        for (Long picId : orderPaymentVo.getPicIdList()) {
            DmsAttachmentRelation attachmentRelation = new DmsAttachmentRelation();
            attachmentRelation.setAttachId(picId);
            attachmentRelation.setRelatedTableType(DmsModuleEnums.ATTACHMENT_RELATED_TABLE_TYPE.TRANSFER_VOUCHER.getType());
            attachmentRelation.setRelatedTableId(orderPaymentVo.getId());
            attachmentRelation.setCreatedDate(new Date());
            attachmentRelation.setCreatedBy(orderPaymentVo.getCreatedBy());
            attachmentRelation.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

            attachmentRelationList.add(attachmentRelation);
        }
        if (!attachmentRelationList.isEmpty()) {
            dmsAttachmentRelationService.batchInsert(attachmentRelationList);
        }

        order.setPayStatus(DmsModuleEnums.PAYMENT_STATUS_TYPE.CONFIRM_AWAIT.getType());
        order.setLastUpdatedBy(orderPaymentVo.getCreatedBy());
        order.setLastUpdatedDate(new Date());
        dmsOrderService.updateByPrimaryKeySelective(order);
        sendMessage(1,order.getId(),order.getUserId(),order.getCustomerName(),orderPaymentVo.getAmount().doubleValue(),orderPaymentVo.getType(),orderPaymentVo.getLastUpdatedBy());
    }

    /**
     * 订单收款确认
     * @param orderPayment
     */
    @Override
    public void confirm(DmsOrderPayment orderPayment) {
        DmsOrderPayment entity = dmsOrderPaymentMapper.selectByPrimaryKey(orderPayment.getId());
        if (entity == null) {
            throw new ServiceException("数据库中不存在id为: " + orderPayment.getId() + "的订单付款信息");
        }

        /* 确认到账金额不能大于支付金额 */
        BigDecimal payAmount = entity.getAmount();  //支付金额
        BigDecimal confirmAmount = orderPayment.getConfirmAmount();  //确认到账金额
        logger.error("comfirmAmount: " + confirmAmount);
        if (confirmAmount.subtract(payAmount).compareTo(new BigDecimal(0)) > 0) {
            throw new ServiceException("确认到账金额不能大于支付金额");
        }

        /* 修改"确认收款状态"和"确认到账日期" */
        orderPayment.setStatus(DmsModuleEnums.PAYMENT_CONFIRM_TYPE.CONFIRMED.getType());
        orderPayment.setConfirmDate(new Date());
        dmsOrderPaymentMapper.updateByPrimaryKeySelective(orderPayment);

        /* 如果订单已全部收款完成，则订单收款状态修改为"已收款"；*/
        /* 否则，则订单收款状态修改为"部分到账" */
        DmsOrder order = dmsOrderService.selectByPrimaryKey(entity.getOrderId());
        BigDecimal received = dmsOrderPaymentMapper.selectAmountByOrderId(entity.getOrderId());
        logger.error("received: " + received);
        logger.error("received + confirmAmount: " + received);
        if (received.compareTo(order.getAmount()) >= 0) {
            order.setPayStatus(DmsModuleEnums.PAYMENT_STATUS_TYPE.FINISHED.getType());
        } else {
            order.setPayStatus(DmsModuleEnums.PAYMENT_STATUS_TYPE.PART_PAID.getType());
        }
        order.setLastUpdatedBy(orderPayment.getLastUpdatedBy());
        order.setLastUpdatedDate(new Date());

        /* 修改订单收款状态*/
        dmsOrderService.updateByPrimaryKeySelective(order);

        sendMessage(2,order.getId(),order.getUserId(),order.getCustomerName(),confirmAmount.doubleValue(),entity.getType(),orderPayment.getLastUpdatedBy());
    }

    /**
     * 获取订单付款信息详情
     * @param id
     * @return
     */
    @Override
    public DmsOrderPaymentVo detail(Long id) {
        return dmsOrderPaymentMapper.selectVoByPrimaryKey(id);
    }

    private void sendMessage(Integer type,Long orderId,Long staffId,String channelName,Double money,Integer payWay,Long userId){
        String title = "";
        String subject = "";
        String content = "";
        int messageType = DmsModuleEnums.MESSAGE_TYPE.MESSAGE_ORDER.getType();
        int entityType = DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_ORDER.getType();
        List<Long> staffs = new ArrayList<>();
        if (type==1){//创建付款单
            staffs.add(0L);//发给品牌商
            List<Long> staffs2 = new ArrayList<>();
            staffs2.add(staffId);
            title = "有一笔付款需要您确认";
            subject = channelName + "向您支付了￥"+money+"，需要您确认";
            content = "已进行付款，付款方式："+(payWay==1?"对公转账":"线上支付")+"，付款金额：￥"+money+"。";
            dmsMessageService.sendMessage(messageType, entityType, orderId,title,subject, content, staffs2,0,userId);
        }else if (type==2){//确认收款
            staffs.add(staffId);
            title = "商家确认收款";
            subject = "商家已确认收款，金额￥"+money+"。";
            content = "商家已确认收款，金额￥"+money+"。";
        }
        dmsMessageService.sendMessage(messageType, entityType, orderId,title,subject, content, staffs,1,userId);
    }
}