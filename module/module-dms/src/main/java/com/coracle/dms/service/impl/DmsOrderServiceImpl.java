package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.constant.DmsRoleCodeConstants;
import com.coracle.dms.dao.mybatis.DmsChannelMapper;
import com.coracle.dms.dao.mybatis.DmsOrderMapper;
import com.coracle.dms.dao.mybatis.DmsOrderSalesmanMapper;
import com.coracle.dms.dao.mybatis.DmsProductMapper;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.support.SystemParamerConfiguration;
import com.coracle.dms.vo.*;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.dms.webservice.model.AnjoyOrderModel;
import com.coracle.dms.webservice.model.AnjoyOrderProductModel;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.DmsChannelEmployeeVo;
import com.coracle.yk.xframework.po.DmsChannelInfoVo;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiruo.medbid.components.DateUtils;
import com.xiruo.medbid.components.StringUtils;
import com.xiruo.medbid.util.ArithUtil;
import com.xiruo.medbid.util.BeanConvertHelper;
import com.xiruo.medbid.util.DecimalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DmsOrderServiceImpl extends BaseServiceImpl<DmsOrder> implements DmsOrderService {
    private static final Logger logger = LoggerFactory.getLogger(DmsOrderServiceImpl.class);

    @Autowired
    private DmsOrderMapper dmsOrderMapper;

    @Autowired
    private DmsProductService dmsProductService;

    @Autowired
    private DmsProductMapper dmsProductMapper;

    @Autowired
    private DmsOrderProductService dmsOrderProductService;

    @Autowired
    private DmsChannelAddressService dmsChannelAddressService;

    @Autowired
    private DmsUserValueAddedTaxInvoiceService dmsUserValueAddedTaxInvoiceService;

    @Autowired
    private DmsUserAddressService dmsUserAddressService;

    @Autowired
    private DmsUserService dmsUserService;

    @Autowired
    private DmsChannelContactsService dmsChannelContactsService;

    @Autowired
    private DmsChannelService dmsChannelService;

    @Autowired
    private DmsShoppingCartService dmsShoppingCartService;

    @Autowired
    private DmsOrderOperationLogService dmsOrderOperationLogService;

    @Autowired
    private DmsOrderReturnService dmsOrderReturnService;

    @Autowired
    private DmsOrderDeliveryItemService dmsOrderDeliveryItemService;

    @Autowired
    private DmsSysAreaService dmsSysAreaService;

    @Autowired
    private DmsSysRegionService dmsSysRegionService;

    @Autowired
    private DmsMessageService dmsMessageService;

    @Autowired
    private DmsProductAreaService dmsProductAreaService;

    @Autowired
    private DmsPromotionProductService dmsPromotionProductService;

    @Autowired
    private DmsProductSpecMatrixConfigService dmsProductSpecMatrixConfigService;

    @Autowired
    private DmsPromotionProductRecordService dmsPromotionProductRecordService;

    @Autowired
    private DmsChannelMapper dmsChannelMapper;

    @Autowired
    private DmsOrderSalesmanMapper dmsOrderSalesmanMapper;

    @Override
    public IMybatisDao<DmsOrder> getBaseDao() {
        return dmsOrderMapper;
    }

    /**
     * 创建订单
     *
     * @param orderVo
     * @param session
     */
    @Override
    public DmsOrder create(DmsOrderVo orderVo, UserSession session) {

        /* 检查并设置参数的值 */
        this.checkParam(orderVo);
        this.initParam(orderVo);

        /* 新增订单信息 */
        int count = dmsOrderMapper.insert(orderVo);
        if (count <= 0) {
            throw new ServiceException("新增订单异常");
        }

        DmsChannel dmsChannel = dmsChannelMapper.selectByPrimaryKey(session.getOrgId());

        /* 新增订单产品信息 */
        List<DmsOrderProduct> insertOrderProductList = Lists.newArrayList();
        for (DmsOrderProductVo op : orderVo.getProductList()) {

            if (op.getCount() <= 0) {
                throw new ServiceException("订单内产品数量不能为0");
            }

            Long productId = op.getProductId();
            DmsProduct product = dmsProductService.selectByPrimaryKey(productId);
            if (product == null) {
                throw new ServiceException("订单内的产品异常，不存在以下产品id: ");
            }

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("easChannelCode", dmsChannel.getCode());
            paramMap.put("easProductCode", product.getCode());
            BigDecimal productPrice = dmsProductMapper.selectProductPrice(paramMap);
            if(productPrice == null || productPrice.compareTo(BigDecimal.ZERO) <= 0){
                throw new ServiceException("商品价格不能小于零");
            }

            DmsChannelInfoVo dmsChannelInfoVo = dmsChannelService.getChannelInfo(dmsChannel.getId());;
            boolean isLocked = dmsChannelInfoVo.isloked();//dmsChannelService.getAnjoyChannelIsLock(dmsChannel.getAnjoyId());
            if(isLocked){
                throw new ServiceException("已被锁单，请联系业务员");
            }

            boolean isOvered = dmsChannelInfoVo.isovered();//dmsChannelService.getAnjoyChannelOverAccountPeriod(dmsChannel.getCode());
            if(isOvered){
                throw new ServiceException("已超账期，请联系业务员");
            }

            /* 根据渠道id和产品id判断该产品是否可为当前登录账号可见 */
            /* todo 如果不可见，给前端提供标识提示产品已下架 */
            Integer visible = dmsProductAreaService.selectByChannelIdAndProductId(session.getOrgId(), productId);
            if (visible == 0) {
                throw new ServiceException("无法购买产品: " + product.getName() + " | 规格: [" + op.getSpecUnionKey() + "]; 原因: 产品销售区域不在渠道管辖区域范围内");
            }

            BigDecimal originalPrice;
            //如果产品规格id为空，则获取产品的价格；否则，根据产品规格获取价格
            Long productSpecId = op.getProductSpecId();
            if (productSpecId == null) {
                originalPrice = product.getShowPrice();
            } else {
                DmsProductSpecMatrixConfig psmc = dmsProductSpecMatrixConfigService.selectByPrimaryKey(productSpecId);
                if (psmc == null) {
                    throw new ServiceException("数据库中不存在id为: " + productSpecId + " 的产品规格信息");
                }
                originalPrice = psmc.getPrice();
            }

            //判断用户提交的产品原价参数和数据库中获取的产品原价是否相等
            if(originalPrice != null && op.getOriginalPrice() != null){
                boolean isOriginalPriceEqual = DecimalUtil.round(originalPrice).compareTo(DecimalUtil.round(op.getOriginalPrice())) == 0;
                if (!isOriginalPriceEqual) {
                    logger.info("后台获取产品原价: " + originalPrice);
                    logger.info("前端参数产品原价: " + op.getOriginalPrice());
                    logger.error("产品价格异常");
                }
            }

            //根据产品[规格]获取最佳促销的值
            DmsProductVo param = new DmsProductVo();
            param.setId(op.getProductId());
            param.setSpecId(op.getProductSpecId());
            //判断当前登录人是否是订货端(渠道联系人)账号
            boolean isChannelContact = session.getRoleCode().equals(DmsRoleCodeConstants.CO);
            if (isChannelContact) {
                param.setChannelId(session.getOrgId());
            }
            DmsPromotionProductVo promotionProduct = dmsPromotionProductService.bestChoice(param);

            //判断产品价格、折扣信息等是否有变化
            Long promotionProductId = op.getPromotionProductId();
            if (promotionProductId != null) {
                //如果参数里的用户提交的订单产品促销信息和数据库中获取的该产品的最佳促销信息不一致，则提示促销活动已结束
                boolean isSamePromotion;
                if (promotionProduct != null) {
                    isSamePromotion = promotionProduct.getId().equals(promotionProductId);
                } else {
                    isSamePromotion = false;
                }
                if (!isSamePromotion) {
                    logger.info("后台获取的最佳产品促销id为: " + promotionProduct.getId());
                    logger.info("前端参数的最佳产品促销id为: " + op.getPromotionProductId());
                    throw new ServiceException("促销活动已结束，订单提交失败");
                }

                DmsPromotionProductVo param1 = new DmsPromotionProductVo();
                param1.setId(promotionProductId);
                param1.setChannelId(session.getOrgId());

                //在产品有促销的情况下，如果用户提交的促销产品数量大于计算所得的促销产品可用数量，则提示促销活动已结束
                Integer availableCount = dmsPromotionProductService.selectAvailableCountByCondition(param1);
                if (availableCount != -1 && availableCount < op.getCount()) {
                    throw new ServiceException("促销活动已结束，订单提交失败");
                }

                BigDecimal discountedPrice = originalPrice.multiply(promotionProduct.getDiscount()).divide(new BigDecimal(100));
                boolean isDiscountedPriceEqual = DecimalUtil.round(discountedPrice).compareTo(DecimalUtil.round(op.getPrice())) == 0;
                if (!isDiscountedPriceEqual) {
                    logger.info("后台计算促销价格: " + discountedPrice);
                    logger.info("前端参数促销价格: " + op.getPrice());
                    logger.error("促销价格异常");
                    throw new ServiceException("促销活动已结束，订单提交失败");
                } else {
                    //新增商品促销打折记录
                    DmsPromotionProductRecord record = new DmsPromotionProductRecord();
                    record.setPromotionProductId(promotionProduct.getId());
                    record.setOrderId(orderVo.getId());
                    record.setChannelId(session.getOrgId());
                    record.setCount(op.getCount());
                    record.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                    record.setCreatedDate(new Date());
                    record.setCreatedBy(session.getId());
                    dmsPromotionProductRecordService.insert(record);

                    //更新促销商品已售数量信息
                    DmsPromotionProduct dmsPromotionProduct = dmsPromotionProductService.selectByPrimaryKey(promotionProductId);
                    if (dmsPromotionProduct != null) {
                        dmsPromotionProduct.setSoldQuantity(dmsPromotionProduct.getSoldQuantity() + op.getCount());
                        dmsPromotionProduct.setLastUpdatedBy(session.getId());
                        dmsPromotionProduct.setLastUpdatedDate(new Date());
                        dmsPromotionProductService.updateByPrimaryKeySelective(dmsPromotionProduct);
                    }
                }
            }

            op.setProductName(product.getName());  //设置产品名称，之后不能改变
            op.setProductCode(product.getCode()); //产品编码
            op.setReturnCount(0);  //退换货数量默认为0
            op.setOrderId(orderVo.getId());
            op.setWeight(product.getWeight());
            op.setVolume(product.getVolume());
            op.setCreatedBy(orderVo.getCreatedBy());
            op.setCreatedDate(new Date());
            op.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            insertOrderProductList.add(op);

            /* 更新订单产品对应的购物车数据的状态 */
            Long shoppingcartId = op.getShoppingcartId();
            DmsShoppingCart shoppingCart = dmsShoppingCartService.selectByPrimaryKey(shoppingcartId);
            if (shoppingCart != null) {
                Integer scType = shoppingCart.getType();
                Date now = new Date();
                if (scType == DmsModuleEnums.SHOPPINT_CART_TYPE.BALANCED.getType()) {
                    //如果订单产品对应的购物车数据类型是 已结算数据 类型，则还需要将该订单产品信息重新插入一条到购物车中，与原来的已结算数据区分开
                    dmsShoppingCartService.insert(session.getId(), op.getProductId(), op.getProductSpecId(), op.getSpecUnionKey(),
                            op.getCount().longValue(), DmsModuleEnums.SHOPPINT_CART_TYPE.BALANCED.getType(), now);
                } else if (scType == DmsModuleEnums.SHOPPINT_CART_TYPE.SHOPPING_CART.getType()
                        || scType == DmsModuleEnums.SHOPPINT_CART_TYPE.MANUAL.getType()) {
                    //如果订单产品对应的购物车数据类型是 购物车 或 手动添加的常购商品 类型，则更新其数量和状态
                    shoppingCart.setType(DmsModuleEnums.SHOPPINT_CART_TYPE.BALANCED.getType());
                    shoppingCart.setCount(op.getCount().longValue());
                    shoppingCart.setBalanceDate(now);
                    dmsShoppingCartService.updateByPrimaryKeySelective(shoppingCart);
                }
            }
        }

        /* 批量新增订单商品信息 */
        if (!insertOrderProductList.isEmpty()) {
            dmsOrderProductService.batchInsert(insertOrderProductList);
        }

        /* 检验订单金额 */
        checkAmount(orderVo);
        Long time1 = System.currentTimeMillis();
        /* 效验订单金额是否超出可用额度 */
        checkAvailableLimit(orderVo);
        Long time2 = System.currentTimeMillis();
        logger.info("***********信用额度检验耗时：{}",(time2-time1)/1000);
        /* 4.新增订单操作日志 */
        dmsOrderOperationLogService.createOrderOperLog(orderVo.getId(), "订单提交", orderVo.getCreatedBy());

        /* 返回新增的订单信息 */
        DmsOrder result = dmsOrderMapper.selectByPrimaryKey(orderVo.getId());

        //判断是否为渠道-业务员代下单
        DmsChannelEmployeeVo dmsChannelEmployeeVo = session.getDmsChannelEmployeeVo();
        if(dmsChannelEmployeeVo != null && dmsChannelEmployeeVo.getSalesmanId() != null){
            DmsOrderSalesman dmsOrderSalesman = new DmsOrderSalesman();
            dmsOrderSalesman.setSalesmanId(dmsChannelEmployeeVo.getSalesmanId());
            dmsOrderSalesman.setSalesmanName(dmsChannelEmployeeVo.getSalesmanName());
            dmsOrderSalesman.setOrderId(result.getId());
            dmsOrderSalesman.setOrderCode(result.getCode());

            dmsOrderSalesmanMapper.insert(dmsOrderSalesman);
            logger.info("渠道业务员代下单：{}", JSON.toJSONString(dmsOrderSalesman));
        }

        //发送消息
        sendMessage(1, orderVo.getId(), orderVo.getUserId(), orderVo.getCode(), orderVo.getCustomerName(), orderVo.getAmount().doubleValue(), orderVo.getUserId(), "");

        return result;
    }

    /**
     * 订单审核
     *
     * @param order
     */
    @Override
    public DmsOrder audit(DmsOrderVo order, UserSession userSession) {
        Long orderId = order.getId();
        DmsOrder entity = dmsOrderMapper.selectByPrimaryKey(orderId);
        if (entity == null) {
            throw new ServiceException("数据库中不存在id为: " + orderId + "的订单数据");
        }
//        boolean needBMPAudit = false;  //是否需要发起BMP审批流的判断条件
//        //处理新增的订单产品信息
//        List<DmsOrderProduct> insertOrderProductList = Lists.newArrayList();
//        for (DmsOrderProduct orderProduct : order.getProductList()) {
//            if (orderProduct.getId() == null) {
//                insertOrderProductList.add(orderProduct);
//            }
//
//            //判断订单商品的单价和阳光单价是否一样，如果不一样的话，则需要发起BMP审批流
//            boolean isPriceEqual = DecimalUtil.round(orderProduct.getYankonPrice()).compareTo(DecimalUtil.round(orderProduct.getPrice())) == 0;
//            if (!isPriceEqual) {
//                needBMPAudit = true;
//            }
//
//        }

        if (order.getAuditOpinion() == DmsModuleEnums.AUDIT_OPINION_TYPE.DISAGREE.getType()) {  //审核拒绝，则订单进入"已取消"状态
            order.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.CANCEL.getType());
            dmsOrderOperationLogService.createOrderOperLog(orderId, "审核拒绝", order.getLastUpdatedBy());
            dmsOrderOperationLogService.createOrderOperLog(orderId, "取消", order.getLastUpdatedBy());
        } else {
            //审核同意，则订单进入"待发货"状态
            order.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.DELIVER_AWAIT.getType());
            dmsOrderOperationLogService.createOrderOperLog(orderId, "审核同意", order.getLastUpdatedBy());
            order.setFinishedDate(new Date());  //设置订单审核通过时间

            DmsChannel dmsChannel = dmsChannelMapper.selectByUserId(entity.getUserId());
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("dmsChannel", dmsChannel);
            //将订单推送给EAS
            pushOrderToAnjoy(entity, paramMap);
        }
        order.setAuditor(order.getLastUpdatedBy());
        order.setAuditDate(new Date());
        dmsOrderMapper.updateByPrimaryKeySelective(order);

        int type = 0;
        if (order.getAuditOpinion() == DmsModuleEnums.AUDIT_OPINION_TYPE.AGREE.getType()) type = 2;
        else if (order.getAuditOpinion() == DmsModuleEnums.AUDIT_OPINION_TYPE.DISAGREE.getType()) type = 3;

        DmsOrderProduct dmsOrderProduct = new DmsOrderProduct();
        dmsOrderProduct.setOrderId(entity.getId());
        dmsOrderProduct.setRemoveFlag(0);
        List<DmsOrderProduct> list = dmsOrderProductService.selectByCondition(dmsOrderProduct);
        String productName = "";
        if (BlankUtil.isNotEmpty(list)) {
            for (DmsOrderProduct dop : list) {
                productName = productName + dop.getProductName() + ";";
                if (productName.length() > 30) break;
            }
        }

        Long time1 = System.currentTimeMillis();

        //暂时屏蔽，耗时43秒，需要查明原因，taok 20180304
        //sendMessage(type, entity.getId(), entity.getUserId(), entity.getCode(), entity.getCustomerName(), entity.getAmount().doubleValue(), order.getLastUpdatedBy(), productName);
        Long time2 = System.currentTimeMillis();

        logger.info("*************推送耗时:{}***********",(time2-time1)/1000);
        return entity;
    }


    @Override
    public void audit(String orderNo, Integer auit,String remark) {
        DmsOrder order=new DmsOrder();
        order.setThirdOrderNo(orderNo);
        List<DmsOrder> orderList = dmsOrderMapper.selectByCondition(order);
        if (BlankUtil.isEmpty(orderList)) {
            throw new ServiceException("数据库中不存在订单号为 " + orderNo + "的订单数据");
        }
        order=orderList.get(0);
        if (auit== DmsModuleEnums.AUDIT_OPINION_TYPE.DISAGREE.getType()) {  //审核拒绝，则订单进入"已取消"状态
            order.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.CANCEL.getType());
            dmsOrderOperationLogService.createOrderOperLog(order.getId(), "审核拒绝", 1L);
            dmsOrderOperationLogService.createOrderOperLog(order.getId(), "取消", 1L);
        } else {  //审核同意，则订单进入"待发货"状态
            order.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.DELIVER_AWAIT.getType());
            dmsOrderOperationLogService.createOrderOperLog(order.getId(), "审核同意", 1L);
            order.setFinishedDate(new Date());  //设置订单审核通过时间
        }
        order.setAuditor(order.getLastUpdatedBy());
        order.setAuditDate(new Date());
        order.setAuditRemark(remark);
        order.setAuditOpinion(auit);
        dmsOrderMapper.updateByPrimaryKeySelective(order);

        int type = 0;
        if (order.getAuditOpinion() == DmsModuleEnums.AUDIT_OPINION_TYPE.AGREE.getType()) type = 2;
        else if (order.getAuditOpinion() == DmsModuleEnums.AUDIT_OPINION_TYPE.DISAGREE.getType()) type = 3;

        DmsOrderProduct dmsOrderProduct = new DmsOrderProduct();
        dmsOrderProduct.setOrderId(order.getId());
        dmsOrderProduct.setRemoveFlag(0);
        List<DmsOrderProduct> list = dmsOrderProductService.selectByCondition(dmsOrderProduct);
        String productName = "";
        if (BlankUtil.isNotEmpty(list)) {
            for (DmsOrderProduct dop : list) {
                productName = productName + dop.getProductName() + ";";
                if (productName.length() > 30) break;
            }
        }
        sendMessage(type, order.getId(), order.getUserId(), order.getCode(), order.getCustomerName(), order.getAmount().doubleValue(), order.getLastUpdatedBy(), productName);

    }

    public void auditBPM(DmsOrderVo orderVo) {
        //todo 客户对接接口

    }

    /**
     * 取消订单
     *
     * @param order
     */
    @Override
    public void cancel(DmsOrder order) {
        Long orderId = order.getId();
        Long userId = order.getLastUpdatedBy();

        DmsOrder entity = dmsOrderMapper.selectByPrimaryKey(orderId);
        if (entity == null) {
            throw new ServiceException("数据库中不存在id为: " + orderId + "的订单数据");
        }

        /* 除了订单状态为"未审核"的订单，其余订单都不允许取消 */
        if (entity.getOrderStatus() != DmsModuleEnums.ORDER_STATUS_TYPE.AUDIT_AWAIT.getType()) {
            throw new ServiceException("审核后的订单不允许取消");
        }
        /* 除了收款状态为"未支付"的订单，其余订单都不允许取消 */
        if (entity.getPayStatus() != DmsModuleEnums.PAYMENT_STATUS_TYPE.UNPAY.getType()) {
            throw new ServiceException("该订单已有付款记录，不允许取消");
        }

        //修改订单状态为"已取消"
        order.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.CANCEL.getType());
        dmsOrderMapper.updateByPrimaryKeySelective(order);

        //如果订单产品里面有参与促销的，则需要增加促销产品的可用数量
        //将所有订单产品设置为已删除
        DmsOrderProduct param = new DmsOrderProduct();
        param.setOrderId(orderId);
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        List<DmsOrderProduct> orderProductList = dmsOrderProductService.selectByCondition(param);
        for (DmsOrderProduct op : orderProductList) {
            Long promotionProductId = op.getPromotionProductId();
            if (promotionProductId != null) {

                //删除促销产品出售记录
                dmsPromotionProductRecordService.removeByPromotionProductId(promotionProductId);

                //减少促销产品已售数量
                DmsPromotionProduct promotionProduct = dmsPromotionProductService.selectByPrimaryKey(promotionProductId);
                if (promotionProduct == null) {
                    throw new ServiceException("数据库中不存在id为: " + promotionProductId + " 的促销产品信息");
                }
                promotionProduct.setSoldQuantity(promotionProduct.getSoldQuantity() - op.getCount());
                promotionProduct.setLastUpdatedBy(userId);
                promotionProduct.setLastUpdatedDate(new Date());
                dmsPromotionProductService.updateByPrimaryKeySelective(promotionProduct);
            }
        }

        /* 新增订单操作日志 */
        dmsOrderOperationLogService.createOrderOperLog(orderId, "取消", order.getLastUpdatedBy());

        sendMessage(4, entity.getId(), entity.getUserId(), entity.getCode(), entity.getCustomerName(), entity.getAmount().doubleValue(), order.getLastUpdatedBy(), "");
    }

    /**
     * 根据发货、收货情况修改订单状态
     *
     * @param order
     */
    @Override
    public void updateOrderStatusByItem(DmsOrder order) {
        Long userId = order.getLastUpdatedBy();

        /* 判断订单是否还有未收货的产品：若无，则修改订单状态为"已完成" */
        /* 否则，判断订单是否还有"已发货但还未确认收货"的产品：若无，则订单状态为"待厂商发货"；若有，订单状态为"待收货" */
        Integer unreceivedCount = dmsOrderProductService.selectUnreceivedCountByOrderId(order.getId());
        if (unreceivedCount == 0) {
            order.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.FINISHED.getType());
        } else {
            Integer unconfirmedCount = dmsOrderDeliveryItemService.selectUnconfirmedCount(order.getId());
            if (unconfirmedCount > 0) {
                order.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.RECEIVE_AWAIT.getType());
            } else {
                order.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.DELIVER_AWAIT.getType());
            }
        }
        order.setLastUpdatedDate(new Date());
        dmsOrderMapper.updateByPrimaryKeySelective(order);

        /* 新增订单操作日志 */
        dmsOrderOperationLogService.createOrderOperLog(order.getId(), "客户收货", userId);
        if (order.getOrderStatus() == DmsModuleEnums.ORDER_STATUS_TYPE.FINISHED.getType()) {
            dmsOrderOperationLogService.createOrderOperLog(order.getId(), "完成", userId);
        }
    }

    /**
     * 确认收货-订单
     *
     * @param order
     * @param session
     */
    @Override
    public void confirm(DmsOrder order, UserSession session) {
        Long userId = order.getLastUpdatedBy();

        /* 根据订单id获取所有订单发货产品记录 */
        DmsOrderDeliveryItem param = new DmsOrderDeliveryItem();
        param.setOrderId(order.getId());
        param.setRelatedType(DmsModuleEnums.ORDER_DELIVERY_TYPE.ORDER.getType());
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        List<DmsOrderDeliveryItem> itemList = dmsOrderDeliveryItemService.selectByCondition(param);

        /* 修改订单下的所有发货单的收货日期 */
        for (DmsOrderDeliveryItem item : itemList) {
            item.setReceiveDate(new Date());
            item.setLastUpdatedBy(userId);
            item.setLastUpdatedDate(new Date());
            dmsOrderDeliveryItemService.updateByPrimaryKeySelective(item);

            /* 收货后增加仓库的库存 */
            dmsOrderDeliveryItemService.updateInventory(item, session);
        }

        /* 根据发货、收货情况修改订单状态 */
        order.setLastUpdatedBy(userId);
        updateOrderStatusByItem(order);
        DmsOrder entity = dmsOrderMapper.selectByPrimaryKey(order.getId());

        sendMessage(5, entity.getId(), entity.getUserId(), entity.getCode(), entity.getCustomerName(), entity.getAmount().doubleValue(), order.getLastUpdatedBy(), "");
    }

    /**
     * 订单列表(分页)
     *
     * @param orderVo
     * @param session
     * @return
     */
    @Override
    public PageHelper.Page<DmsOrderVo> pageList(DmsOrderVo orderVo, UserSession session) {
        String roleCode = session.getRoleCode();

        if (roleCode.equals(DmsRoleCodeConstants.CO)) {  //订货端用户可以看所属渠道下的所有订单

            DmsChannelContactsVo channelContacts = dmsChannelContactsService.queryContactByUserId(session.getId());
            orderVo.setChannelId(channelContacts.getChannelId());
            try {
                PageHelper.startPage(orderVo.getP(), orderVo.getS());
                dmsOrderMapper.selectVoByCondition(orderVo);
            } catch (Exception e) {
                logger.error("订单分页查询异常!", e);
                throw new ServiceException("订单分页查询异常!");
            } finally {
                return PageHelper.endPage();
            }

        }else if(roleCode.equals(DmsRoleCodeConstants.BM)){//管理员查看所有订单，所以不传入id

            try {
                PageHelper.startPage(orderVo.getP(), orderVo.getS());
                dmsOrderMapper.selectVoByCondition(orderVo);
            } catch (Exception e) {
                logger.error("订单分页查询异常!", e);
                throw new ServiceException("订单分页查询异常!");
            } finally {
                return PageHelper.endPage();
            }


        }else if(roleCode.equals(DmsRoleCodeConstants.ZSYWY)||roleCode.equals(DmsRoleCodeConstants.NQWLRY)) {//直属业务员和物流人员查看渠道下订单
            orderVo.setUserId(session.getId());
            try {
                PageHelper.startPage(orderVo.getP(), orderVo.getS());
                dmsOrderMapper.selectVoByConditionPC(orderVo);
            } catch (Exception e) {
                logger.error("订单分页查询异常!", e);
                throw new ServiceException("订单分页查询异常!");
            } finally {
                return PageHelper.endPage();
            }


        }else {
            return null;
        }



    }


    @Override
    public PageHelper.Page<DmsOrderVo> pageListPC(DmsOrderVo orderVo, UserSession session){
        String roleCode = session.getRoleCode();
        if(roleCode.equals(DmsRoleCodeConstants.BM)){//品牌商业务员只能看自己所管辖渠道下的订单
            orderVo.setUserId(session.getId());
        }
        try {
            PageHelper.startPage(orderVo.getP(), orderVo.getS());
            dmsOrderMapper.selectVoByConditionPC(orderVo);
        } catch (Exception e) {
            logger.error("订单分页查询异常!", e);
            throw new ServiceException("订单分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }



    }

    /**
     * 用户点击"再来一单"后，返回复制的订单内容
     *
     * @param param
     * @return
     */
    @Override
    public DmsOrderVo again(DmsOrderVo param) {
        DmsOrderVo result = new DmsOrderVo();

        //订单产品信息
        result = dmsOrderMapper.selectVoByOrder(param);

        //如果用户收货地址id不为空，则将收货地址信息返回
        Long addressId = result.getAddressId();
        if (addressId != null) {
            result.setUserAddress(dmsUserAddressService.selectByPrimaryKey(addressId));
        }

        return result;
    }

    /**
     * 订单详情
     *
     * @param param
     * @param session
     * @return
     */
    @Override
    public DmsOrderVo detail(DmsOrderVo param, UserSession session) {
        DmsOrderVo result = new DmsOrderVo();

        Long userId = param.getLastUpdatedBy();
        logger.info("********订单详情参数：{}*********",JSON.toJSONString(param));

        /* 如果id为空或0，则是进入商城的确认订单页面操作 */
        /* 如果id为正数，则是查询订单详情操作 */
        if (param.getId() == 0) {  //返回下订单时需要的一些参数
            /* 默认收货地址 */
            DmsUserAddress userAddress = dmsUserAddressService.getDefaultAddress(userId);
            if (userAddress == null) {
                userAddress = new DmsUserAddress();
            }
            result.setUserAddress(userAddress);  //用户默认收货地址

            //获取当前登录人的渠道信息
            DmsChannelContactsVo channelContacts = dmsChannelContactsService.queryContactByUserId(userId);
            Long channelId = channelContacts.getChannelId();
            DmsChannel channel = dmsChannelService.selectByPrimaryKey(channelId);



            //默认收货地址（安井）
            DmsChannelAddressVo channelAddress = dmsChannelAddressService.getDefault(channelId);
            if (channelAddress == null) {
                channelAddress = new DmsChannelAddressVo();
            }
            result.setChannelAddress(channelAddress);

            //判断商品的数量是否是最小包装量的倍数，不是的话不允许提交（暂时不需要）
//            if (param.getProductList() != null && param.getProductList().size() > 0) {
//                DmsOrderProductVo op = param.getProductList().get(0);
//                DmsProduct product = dmsProductService.selectByPrimaryKey(op.getProductId());
//                Long minPackageQuantity = product.getMinPackageQuantity();
//                if (minPackageQuantity != null && op.getCount() % minPackageQuantity != 0) {
//                    throw new ServiceException("订购数量必须是最小包装量的倍数");
//                }
//            }

            /* 发票抬头 todo 优化：获取发票抬头信息 */
            if (channelContacts == null) {
                throw new ServiceException("无法根据下订单的账号id获取渠道联系人信息!userId为: " + userId);
            } else {
                if (channel == null) {
                    throw new ServiceException("数据库中不存在id为: " + channelId + "的渠道信息");
                } else {
                    result.setCustomerName(channel.getName());  //经销商名字
                    result.setInvoiceTitle(channel.getName());  //发票抬头
                }
            }

            //处理促销逻辑,安井无促销暂时不需要处理促销逻辑
//            DmsOrderVo promotionResult = dealPromotion(param.getProductList(), session);
//            result.setMessage(promotionResult.getMessage());
//            List<DmsOrderProductVo> orderProductList = promotionResult.getProductList();
//            result.setProductList(orderProductList);


            //将同步过来的安井价格set到产品中（其实价格本可以直接从购物车页面传过来的）
            List<DmsOrderProductVo> orderProductVoListNew = new ArrayList<>();
            List<DmsOrderProductVo> orderProductVoList = param.getProductList();
            for(DmsOrderProductVo productVo:orderProductVoList){
                DmsProductVo product2 = dmsProductService.getDetailsApp(productVo.getProductId(),session);

                productVo.setPrice(product2.getShowPrice());
                orderProductVoListNew.add(productVo);

            }
            //购物车总额
            result.setProductList(orderProductVoListNew);
            BigDecimal amount = calculateAmount(orderProductVoList);
            result.setAmount(amount);//订单总金额


            /* 运费 */
            //阳光：判断用户的购买总金额（包括促销）是否大于用户所属渠道的免邮金额。如果是的话，运费设置为0
//            BigDecimal postageFreePrice = channel.getPostageFreePrice();  //渠道的免邮金额
//            BigDecimal freight = BigDecimal.ZERO;

            //总金额大于等于免邮金额，运费为0；否则，运费为用户填写的值
//            if (postageFreePrice != null && DecimalUtil.round(amount).compareTo(DecimalUtil.round(postageFreePrice)) >= 0) {
//                freight = BigDecimal.ZERO;
//            } else {
//                //todo 获取用户填写的运费值
//            }
//          String freightText = SystemParamerConfiguration.getInstance().get(DmsGlobalVariableKeyConstants.FREIGHT);
//            result.setFreight(freight);  //运费
        } else {  //查询订单详情
            param.setLoginId(session.getId());
            DmsOrderVo order = dmsOrderMapper.selectVoByOrder(param);

            if (order == null) {
                throw new ServiceException("数据库中查询不到id为: " + param.getId() + "的订单");

            } else {
                order.setWeightVolumeRatio(BigDecimal.ZERO);
                if (!(order.getVolumeTotal().compareTo(BigDecimal.ZERO) == 0)) {
                    BigDecimal weightVolumeRatio = new BigDecimal(
                            ArithUtil.div(order.getWeightTotal().doubleValue(), order.getVolumeTotal().doubleValue(), 6)
                    );
                    order.setWeightVolumeRatio(weightVolumeRatio);
                }


                Long qtyTotal = calculateQty(order.getProductList());
                order.setQtyTotal(qtyTotal);

            }

            //阳光DMS：收货地址
            //setUserAddressId(order);
            /* 安井：收货地址 */
            setChannelAddressId(order);

            BeanConvertHelper.copyProperties(order, result);
        }

        //目前没有促销，总金额就是实际金额
        result.setRealAmount(result.getAmount());
        return result;
    }

    /**
     * 计算订单产品的总金额
     *
     * @param orderProductList
     * @return
     */
    private BigDecimal calculateAmount(List<DmsOrderProductVo> orderProductList) {

        BigDecimal amount = BigDecimal.ZERO;
        for (DmsOrderProductVo op : orderProductList) {
            amount = amount.add(op.getPrice().multiply(new BigDecimal(op.getCount())));
        }
        return amount;
    }
    /**
     *
     * 计算订单商品总数量
     * @param orderProductList
     * @return
     */
    private Long calculateQty(List<DmsOrderProductVo> orderProductList) {

        Long qtyTotal = 0L;
        for (DmsOrderProductVo op : orderProductList) {
            qtyTotal = qtyTotal+op.getCount();
        }
        return qtyTotal;
    }



    /**
     * 产品促销逻辑处理
     *
     * @param orderProductList
     * @param session
     * @return
     */
    private DmsOrderVo dealPromotion(List<DmsOrderProductVo> orderProductList, UserSession session) {
        DmsOrderVo result = new DmsOrderVo();
        List<DmsOrderProductVo> resultOrderProductList = Lists.newArrayList();

        for (DmsOrderProductVo op : orderProductList) {
            //如果产品规格id为空，则获取产品的价格；否则，根据产品规格获取价格
            Long productId = op.getProductId();
            Long productSpecId = op.getProductSpecId();
            if (productSpecId == null) {
                DmsProduct product = dmsProductService.selectByPrimaryKey(productId);
                if (product == null) {
                    throw new ServiceException("数据库中不存在id为: {} 的产品信息", productId);
                }
                op.setOriginalPrice(product.getShowPrice());
            } else {
                DmsProductSpecMatrixConfig psmc = dmsProductSpecMatrixConfigService.selectByPrimaryKey(productSpecId);
                if (psmc == null) {
                    throw new ServiceException("数据库中不存在id为: {} 的产品规格信息", productSpecId);
                }
                op.setOriginalPrice(psmc.getPrice());
            }

            //根据产品[规格]获取最佳促销的值
            DmsProductVo param = new DmsProductVo();
            param.setId(op.getProductId());
            param.setSpecId(op.getProductSpecId());
            //判断当前登录人是否是订货端(渠道联系人)账号
            boolean isChannelContact = session.getRoleCode().equals(DmsRoleCodeConstants.CO);
            if (isChannelContact) {
                param.setChannelId(session.getOrgId());
            }
            DmsPromotionProductVo promotionProduct = dmsPromotionProductService.bestChoice(param);

            //如果产品有满足条件的促销折扣，则返回折扣价；否则，返回原价
            if (promotionProduct != null) {

                Long channelId = session.getOrgId();
                promotionProduct.setChannelId(channelId);
                //根据条件获取当前订货端的可用促销产品数量
                Integer availableCount = dmsPromotionProductService.selectAvailableCountByCondition(promotionProduct);

                //可用数量不为-1代表促销数量有限制
                //可用数量不为-1，而且可用数量小于用户下单数量，需要进行拆单处理
                //可用数量内的按促销价销售，数量之外的按原价销售
                if (availableCount != -1 && availableCount < op.getCount()) {

                    //按促销价销售部分
                    if (availableCount > 0) {
                        DmsOrderProductVo promotionOrderProduct = new DmsOrderProductVo();
                        BeanConvertHelper.copyProperties(op, promotionOrderProduct);
                        promotionOrderProduct.setCount(availableCount);
                        promotionOrderProduct.setPrice(op.getOriginalPrice().multiply(promotionProduct.getDiscount()).divide(new BigDecimal(100)));
                        promotionOrderProduct.setPromotionProductId(promotionProduct.getId());
                        promotionOrderProduct.setPromotionSubject(promotionProduct.getPromotionSubject());
                        resultOrderProductList.add(promotionOrderProduct);
                    }

                    //按原价销售部分
                    if (op.getCount() != availableCount) {
                        DmsOrderProductVo originalOrderProduct = new DmsOrderProductVo();
                        BeanConvertHelper.copyProperties(op, originalOrderProduct);
                        originalOrderProduct.setCount(op.getCount() - availableCount);
                        originalOrderProduct.setPrice(op.getOriginalPrice());
                        resultOrderProductList.add(originalOrderProduct);

                        result.setHasMessage(1);
                        result.setMessage("您添加的商品数量超过了限购数，超出部分将以原价提交");
                    } else {
                        result.setHasMessage(0);
                    }
                } else {
                    //可用数量为-1，或者可用数量大于用户的下单数量，都直接按照折扣价销售
                    op.setPrice(op.getOriginalPrice().multiply(promotionProduct.getDiscount().divide(new BigDecimal(100))));
                    op.setPromotionProductId(promotionProduct.getId());
                    op.setPromotionSubject(promotionProduct.getPromotionSubject());
                    resultOrderProductList.add(op);
                }
            } else {
                //没有促销的情况，直接按原价下单
                op.setPrice(op.getOriginalPrice());
                resultOrderProductList.add(op);
            }
        }
        result.setProductList(resultOrderProductList);
        return result;
    }

    //阳光DMS：收货地址
    private void setUserAddressId(DmsOrderVo orderVo){
        Long userAddressId = orderVo.getAddressId();
        if (userAddressId != null && userAddressId != 0) {
            DmsUserAddress userAddress = dmsUserAddressService.selectByPrimaryKey(userAddressId);
            if (userAddress == null) {
                throw new ServiceException("收货地址id参数错误，数据库中找不到id为: " + userAddressId + "的收货地址信息");
            } else {
                //将收货人信息填写到订单信息中，下订单后不允许修改
                orderVo.setReceiverName(userAddress.getRecipientName());
                orderVo.setReceiverMobile(userAddress.getMobile());

                String province = userAddress.getProvince();
                String city = userAddress.getCity();
                String county = userAddress.getCounty();
                province = province == null ? "" : province;
                city = city == null ? "" : city;
                county = county == null ? "" : county;

                orderVo.setReceiverAddress(province + city + county + userAddress.getShippingAddress());
                orderVo.setReceiverAddress(orderVo.getReceiverAddress().replaceAll("null", ""));
            }
        }
    }

    /* 安井：收货地址 */
    private void setChannelAddressId(DmsOrderVo orderVo){
        Long channelAddressId = orderVo.getChannelAddressId()==null?orderVo.getAddressId():orderVo.getChannelAddressId();
        if (channelAddressId != null && channelAddressId != 0) {
            DmsChannelAddressVo channelAddress = dmsChannelAddressService.getVoByPrimaryKey(channelAddressId);
            if (channelAddress == null) {
                throw new ServiceException("收货地址不存在：" + channelAddressId);
            } else {
                orderVo.setChannelAddress(channelAddress);
                /* 将收货人信息填写到订单信息中，下订单后不允许修改 */
                orderVo.setReceiverName(channelAddress.getRecipientName());
                orderVo.setReceiverMobile(channelAddress.getMobile());

                String province = channelAddress.getProvinceText();
                String city = channelAddress.getCityText();
                String county = channelAddress.getCountyText();
                province = province == null ? "" : province;
                city = city == null ? "" : city;
                county = county == null ? "" : county;

                orderVo.setReceiverAddress(province + city + county + channelAddress.getReceiveAddress());
                orderVo.setReceiverAddress(orderVo.getReceiverAddress().replaceAll("null", ""));
                orderVo.setAddressId(channelAddressId);
            }
        }
    }

    /**
     * 初始化参数
     *
     * @param orderVo
     */
    private void initParam(DmsOrderVo orderVo) {

        /* 设置订单默认值 */
        if (orderVo.getSource() == null) {  //默认为app下单
            orderVo.setSource(DmsModuleEnums.ORDER_SOURCE.APP.getType());
        }
        orderVo.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.AUDIT_AWAIT.getType());  //订单状态：待审核状态
        orderVo.setPayStatus(DmsModuleEnums.PAYMENT_STATUS_TYPE.UNPAY.getType());  //收款状态：未支付
        orderVo.setUserId(orderVo.getCreatedBy());
        orderVo.setCode(SystemParamerConfiguration.getInstance().createSerialNumStr(DmsModuleEnums.ORDER_SERIAL_KEY));  //生成订单号

        if(orderVo.getChannelAddressId()==null){
            throw new ServiceException("渠道收货地址为空！");
        }
        /* 安井：收货地址 */
        setChannelAddressId(orderVo);

        /* 收货地址不能为空 */
        /*Long userAddressId = orderVo.getAddressId();
        if (userAddressId != null && userAddressId != 0) {
            DmsUserAddress userAddress = dmsUserAddressService.selectByPrimaryKey(userAddressId);
            if (userAddress == null) {
                throw new ServiceException("收货地址id参数错误，数据库中找不到id为: " + userAddressId + "的收货地址信息");
            } else {
                *//* 将收货人信息填写到订单信息中，下订单后不允许修改 *//*
                orderVo.setReceiverName(userAddress.getRecipientName());
                orderVo.setReceiverMobile(userAddress.getMobile());
                orderVo.setReceiverAddress(userAddress.getProvince() + userAddress.getCity() + userAddress.getCounty() + userAddress.getShippingAddress());
            }
        } else {
            throw new ServiceException("收货地址id参数为空");
        }*/

        /* 检验并设置发票信息 */
        Long invoiceId = orderVo.getInvoiceId();
        if (invoiceId != null && invoiceId != 0) {  //增值税发票信息
            DmsUserValueAddedTaxInvoice invoice = dmsUserValueAddedTaxInvoiceService.selectByPrimaryKey(invoiceId);
            if (invoice == null) {
                throw new ServiceException("增值税发票信息id参数错误，数据库中找不到id为: " + invoiceId + "的增值税发票信息");
            } else {
                /* 将发票信息填写到订单信息中，下订单后不允许修改 */
                orderVo.setInvoiceType(DmsModuleEnums.INVOICE_TYPE.VAT.getType());
                orderVo.setInvoiceTitle(invoice.getCompanyName());
                orderVo.setInvoiceTaxpayerCode(invoice.getTaxpayerCode());
                orderVo.setInvoiceAddress(invoice.getAddress());
                orderVo.setInvoicePhone(invoice.getPhone());
                orderVo.setInvoiceBank(invoice.getBankName());
                orderVo.setInvoiceAccount(invoice.getBankAccount());
            }
        } else {  //普通发票
            orderVo.setInvoiceType(DmsModuleEnums.INVOICE_TYPE.NORMAL.getType());
        }

        /* 设置订货客户的信息 */
        Long buyerId = orderVo.getCreatedBy();
        if (buyerId != null && buyerId != 0) {
            DmsUser user = dmsUserService.selectByPrimaryKey(buyerId);
            if (user != null) {
                DmsChannelContactsVo channelContacts = dmsChannelContactsService.queryContactByUserId(buyerId);
                if (channelContacts != null) {
                    /* 订单的联系人为渠道联系人，联系电话为渠道联系人的手机 */
                    orderVo.setContacts(channelContacts.getName());
                    orderVo.setPhone(channelContacts.getMobile());
                    Long channelId = channelContacts.getChannelId();
                    DmsChannel channel = dmsChannelService.selectByPrimaryKey(channelId);
                    if (channel != null) {
                        /* 订单的客户名称为渠道商名称，订单区域为渠道商所属区域 */
                        orderVo.setCustomerName(channel.getName());  //客户名称

                        Long areaId = channel.getAreaId();
                        orderVo.setAreaId(areaId);
                        String area = (areaId == null) ? "" : dmsSysAreaService.selectByPrimaryKey(channel.getAreaId()).getFullPath().replace(',', '/');
                        Integer provinceId = StringUtils.isBlank(channel.getProvince()) ? 0 : Integer.parseInt(channel.getProvince());
                        Integer cityId = StringUtils.isBlank(channel.getCity()) ? 0 : Integer.parseInt(channel.getCity());
                        String province = "", city = "";
                        DmsSysRegion dmsSysRegionProvince = dmsSysRegionService.selectByPrimaryKey(provinceId);
                        if(dmsSysRegionProvince != null){
                            province = dmsSysRegionProvince.getRegionName();
                        }

                        DmsSysRegion dmsSysRegionCity = dmsSysRegionService.selectByPrimaryKey(cityId);
                        if(dmsSysRegionCity != null){
                            city = dmsSysRegionCity.getRegionName();
                        }

                        area = area.substring(area.lastIndexOf("/") + 1);  //截取区域的最后一级


//                        if (!StringUtils.isBlank(province)) {
//                            area += "/" + province;
//                        }
//                        if (!StringUtils.isBlank(city)) {
//                            area += "/" + city;
//                        }

                        orderVo.setArea(area);  //区域信息
                    } else {
                        throw new ServiceException("数据库中不存在id为: " + channelId + "的渠道信息");
                    }
                } else {
                    throw new ServiceException("无法根据下订单的账号id获取渠道联系人信息!userId为: " + buyerId);
                }
            } else {
                throw new ServiceException("数据库中找不到id为: " + buyerId + "的账号信息");
            }
        } else {
            throw new ServiceException("下订单的账号id为空");
        }
    }

    /**
     * 检验并初始化参数
     *
     * @param orderVo
     */
    private void checkParam(DmsOrderVo orderVo) {
        if (orderVo == null) {
            throw new ServiceException("参数异常");
        }
        if (orderVo.getProductList() == null) {
            throw new ServiceException("订单里没有产品...");
        }
        if (orderVo.getExpectDeliveryDate() == null) {
            throw new ServiceException("交货日期为空");
        }

    }

    /**
     * 检验订单金额
     *
     * @param orderVo
     */
    private void checkAmount(DmsOrderVo orderVo) {
        /* 校验前端提交的订单金额参数，防止篡改 */
        /* 运费 */
//        String freightText = SystemParamerConfiguration.getInstance().get(DmsGlobalVariableKeyConstants.FREIGHT);
//        BigDecimal freight = new BigDecimal(freightText);

        BigDecimal amount = new BigDecimal(0);
        for (DmsOrderProduct orderProduct : orderVo.getProductList()) {
            BigDecimal price = orderProduct.getPrice();
            BigDecimal count = new BigDecimal(orderProduct.getCount());
            BigDecimal sum = price.multiply(count);
            amount = amount.add(sum);
        }
//        amount = amount.add(freight);
        logger.info("订单金额: " + amount);
        boolean isAmountEqual = DecimalUtil.round(orderVo.getAmount()).compareTo(DecimalUtil.round(amount)) == 0;
        if (!isAmountEqual) {
            throw new ServiceException("订单金额异常!");
        }
    }

    /* 效验订单金额是否超出可用额度 */
    public void checkAvailableLimit(DmsOrderVo orderVo){
        BigDecimal availableLimit = getAvailableLimit(orderVo.getUserId());
        logger.info("订单金额: {}， 可用额度：{}", orderVo.getAmount(), availableLimit);
        int isLtAmount = DecimalUtil.round(orderVo.getAmount()).compareTo(DecimalUtil.round(availableLimit));
        if (isLtAmount > 0) {
            throw new ServiceException("订单金额超出可用额度! 订单金额："+orderVo.getAmount()+" 可用额度："+availableLimit);
        }
    }

    /**
     * 根据用户id获取各个状态的订单数量
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Integer> count(Long userId) {
        Map<String, Integer> result = Maps.newHashMap();

        DmsChannel dmsChannel = dmsChannelMapper.getChannelInfoByUserId(userId);

        /* 参数 */
        DmsOrder param = new DmsOrder();
        //param.setUserId(userId);
        param.setChannelId(dmsChannel.getId());
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

        /* 1."待审核" */
        param.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.AUDIT_AWAIT.getType());
        Integer auditAwaitCount = dmsOrderMapper.selectCountByCondition(param);
        result.put("auditAwaitCount", auditAwaitCount);

        /* 2."待发货" */
        param.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.DELIVER_AWAIT.getType());
        Integer deliverAwaitCount = dmsOrderMapper.selectCountByCondition(param);
        result.put("deliverAwaitCount", deliverAwaitCount);

        /* 3."待收货" */
        param.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.RECEIVE_AWAIT.getType());
        Integer receiveAwaitCount = dmsOrderMapper.selectCountByCondition(param);
        result.put("receiveAwaitCount", receiveAwaitCount);

        /* 4."待评价" */
        Integer unEvaluatedCount = dmsOrderMapper.selectUnEvaluatedCountByUserId(userId);
        result.put("unEvaluatedCount", unEvaluatedCount);

        /* 5."退换货" */
        Integer returnCount = dmsOrderReturnService.selectCountByUserId(userId);
        result.put("returnCount", returnCount);

        return result;
    }

    private void sendMessage(Integer type, Long orderId, Long staffId, String code, String channelName, Double money, Long userId, String productName) {
        String title = "";
        String subject = "";
        String content = "";
        int messageType = DmsModuleEnums.MESSAGE_TYPE.MESSAGE_ORDER.getType();
        int entityType = DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_ORDER.getType();
        List<Long> staffs = new ArrayList<>();
        if (type == 1) {//创建订单
            staffs.add(0L);//发给品牌商
            List<Long> staffs2 = new ArrayList<>();
            staffs2.add(staffId);
            title = "新的订单需要您审核";
            subject = channelName + "提交了新的订单￥" + money + "，需要您审核";
            content = "发起了新的订单" + code + "，金额￥" + money.doubleValue() + "。";
            dmsMessageService.sendMessage(messageType, entityType, orderId, channelName + " 提交了新的订单", subject, content, staffs2, 0, userId);
        } else if (type == 2) {//审核通过
            staffs.add(staffId);
            title = "您的订单已确认";
            subject = "订单号：" + code + " 商品：" + productName;
            content = "商家同意了本次订货需求。";
        } else if (type == 3) {//审核不通过
            staffs.add(staffId);
            title = "您的订单审核未通过，已关闭";
            subject = "订单号：" + code + " 商品：" + productName;
            content = "商家拒绝了本次订货需求。";
        } else if (type == 4) {//订单取消
            staffs.add(0L);
            List<Long> staffs2 = new ArrayList<>();
            staffs2.add(staffId);
            title = "订单已关闭";
            subject = channelName + "取消了订单" + code + "，金额￥" + money;
            content = "订单已取消，交易关闭。";
            dmsMessageService.sendMessage(messageType, entityType, orderId, title, subject, content, staffs2, 0, userId);
        } else if (type == 5) {//确认收货
            staffs.add(0L);
            List<Long> staffs2 = new ArrayList<>();
            staffs2.add(staffId);
            title = "订单已签收";
            subject = "订单" + code + "已签收成功（点击查看详情）";
            content = "已确认收货。";
            dmsMessageService.sendMessage(messageType, entityType, orderId, title, subject, content, staffs2, 0, userId);
        }

        dmsMessageService.sendMessage(messageType, entityType, orderId, title, subject, content, staffs, 1, userId);
    }

    /**
     * 获取用户可用额度
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getAvailableLimit(Long userId) {
        BigDecimal availableLimit = BigDecimal.ZERO;

        //todo 可使用额度：从安井同步过来的，先写死
        //获取当前用户所在渠道
        DmsChannel dmsChannel = dmsChannelService.getChannelInfoByUserId(userId);
        DmsChannelInfoVo dmsChannelInfoVo = dmsChannelService.getChannelCredit(dmsChannel.getId());
        if(dmsChannelInfoVo != null && dmsChannelInfoVo.getBalance() != null){
            availableLimit = dmsChannelInfoVo.getBalance() ;
        }
        return availableLimit;
    }

    /**
     * 将订单同步到安井
     * @param order
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void pushOrderToAnjoy(DmsOrder order, Map<String, Object> paramMap) {
        AnjoyOrderModel anjoyOrder = convertParam(order);
        DmsOrderProduct dmsOrderProduct = new DmsOrderProduct();
        dmsOrderProduct.setOrderId(order.getId());
        List<DmsOrderProduct> dmsOrderProductList = dmsOrderProductService.selectByCondition(dmsOrderProduct);
        List<AnjoyOrderProductModel> anjoyOrderProductList = dmsOrderProductService.convertParam(dmsOrderProductList, paramMap);
        //抬头金额合计
        BigDecimal totalAmount = BigDecimal.ZERO;
        //抬头税额合计
        BigDecimal totalTax = BigDecimal.ZERO;
        //抬头价税合计
        BigDecimal totalTaxAmount = BigDecimal.ZERO;
        for(AnjoyOrderProductModel entity : anjoyOrderProductList){
            totalAmount = totalAmount.add(entity.getAmount());
            totalTax = totalTax.add(entity.getTax());
            totalTaxAmount = totalTaxAmount.add(entity.getTaxAmount());
        }
        anjoyOrder.setTotalAmount(totalAmount);
        anjoyOrder.setTotalTax(totalTax);
        anjoyOrder.setTotalTaxAmount(totalTaxAmount);

        anjoyOrder.setLocalTotalAmount(totalAmount);
        anjoyOrder.setLocalTotalTaxAmount(totalTaxAmount);

        anjoyOrder.getEntry().addAll(anjoyOrderProductList);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(anjoyOrder);
        logger.info("****************** 推送订单详情：{} ******************", JSON.toJSONString(anjoyOrder));
        List<String> anjoyOrderNumberList = AnjoySynClient.pushOrderToAnjoy(jsonArray);
        logger.info("****************** 推送结果详情：{} ******************",anjoyOrderNumberList);
        if(anjoyOrderNumberList != null){
            String anjoyOrderNumber = anjoyOrderNumberList.get(0);
            logger.info("安井订单号：{}", anjoyOrderNumber);
            DmsOrder updateOrder = new DmsOrder();
            updateOrder.setId(order.getId());
            updateOrder.setThirdOrderNo(anjoyOrderNumber);
            this.updateByPrimaryKeySelective(updateOrder);
        }
    }

    /**
     * DMS订单 - 安井订单 参数转换
     *
     * @param order
     * @return
     */
    @Override
    public AnjoyOrderModel convertParam(DmsOrder order) {
        //获取订单创建人所在渠道
        DmsChannel dmsChannel = dmsChannelService.getChannelInfoByUserId(order.getUserId());

        AnjoyOrderModel anjoyOrderModel = new AnjoyOrderModel();
        /** 订单抬头信息 */
        //创建者：安井默认值：N0DHycUzRz+EO8OJyK+SKhO33n8=
        anjoyOrderModel.setCreator("M5gh3eK5Tki+LBGAWwHn5xO33n8=");

        //业务日期(订单审核通过日期)
        Date finishedDate = order.getFinishedDate();
        if(finishedDate == null){
            finishedDate = new Date();//默认当前时间
        }
        String finishedDateStr = DateUtils.parseToTimeText(finishedDate);
        anjoyOrderModel.setBizDate(finishedDateStr);
        //DMS订单备注
        anjoyOrderModel.setDescription(order.getRemark());
        //业务类型，默认安井普通类型 FID：d8e80652-010e-1000-e000-04c5c0a812202407435C
        anjoyOrderModel.setBizType("d8e80652-010e-1000-e000-04c5c0a812202407435C");
        //是否内部销售：默认值
        anjoyOrderModel.setIsInnerSale(false);
        //订货客户：对应DMS渠道 anjoy_id , 安井FID */
        anjoyOrderModel.setOrderCustomer(dmsChannel.getAnjoyId());
        //anjoyOrderModel.setDeliveryType("51eb893e-0105-1000-e000-0c00c0a8123362E9EE3F");//交货方式
        //币别：默认人民币 安井FID
        anjoyOrderModel.setCurrency("dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC");
        anjoyOrderModel.setExchangeRate(new BigDecimal("1"));//汇率
        //anjoyOrderModel.setPaymentType("2fa35444-5a23-43fb-99ee-6d4fa5f260da6BCA0AB5");//付款方式
        //anjoyOrderModel.setSettlementType("e09a62cd-00fd-1000-e000-0b33c0a8100dE96B2B8E");//结算方式

        //销售组织：对应DMS渠道 anjoy_sale_org_id（从安井渠道接口同步过来的值）, 安井FID
        String saleOrgUnit = "AdUAAAAAB+bM567U";
        if(StringUtils.isNotBlank(dmsChannel.getAnjoySaleOrgId())){
            saleOrgUnit = dmsChannel.getAnjoySaleOrgId();
        }
        anjoyOrderModel.setSaleOrgUnit(saleOrgUnit);

        //anjoyOrderModel.setTotalAmount(new BigDecimal("345.76547"));//抬头金额合计
        //anjoyOrderModel.setTotalTax(new BigDecimal("43.64"));//抬头税额合计
        //anjoyOrderModel.setTotalTaxAmount(new BigDecimal("54.73"));//抬头价税合计
        //anjoyOrderModel.setPreReceived(new BigDecimal("4312.64"));//已收应收款
        //anjoyOrderModel.setUnPrereceivedAmount(new BigDecimal("234.64"));//未预收款金额
        //送货地址：DMS订单收货人地址
        anjoyOrderModel.setSendAddress(order.getReceiverAddress());
        //anjoyOrderModel.setLocalTotalAmount(new BigDecimal("534.65"));//抬头金额本位币合计
        //anjoyOrderModel.setLocalTotalTaxAmount(new BigDecimal("43.64"));//抬头价税本位币合计
        anjoyOrderModel.setIsInTax(true);//是否含税
        //联系电话：DMS订单收货人手机
        anjoyOrderModel.setCustomerPhone(order.getReceiverMobile());
        //联系人：DMS订单收货人
        anjoyOrderModel.setLinkMan(order.getReceiverName());
        //anjoyOrderModel.setIsCentralBalance(null);//是否集中结算
        //anjoyOrderModel.setReceiveCondition("AdUAAAAc9tCu4Nue");//收款条件
        //anjoyOrderModel.setStorageOrgUnit("销售方库存组织");//销售方库存组织
        //anjoyOrderModel.setWarehouse("销售方仓库");//销售方仓库

        //归属区域：对应DMS渠道 anjoy_cfbibscid_id（从安井渠道接口同步过来的值）, 安井FID
        String adminOrgUnit = "1tHH9TVWVECMz9gioAMe+k2COWY=";
        if(StringUtils.isNotBlank(dmsChannel.getAnjoyCfbibscidId())){
            adminOrgUnit = dmsChannel.getAnjoyCfbibscidId();
        }
        anjoyOrderModel.setAdminOrgUnit(adminOrgUnit);
        String orderNumber = order.getCode();
        anjoyOrderModel.setSourceBillId(orderNumber);//圆舟DMS 订单编号

        return anjoyOrderModel;
    }

    /*public AnJoyOrder convertParam(DmsOrder order) {
        AnJoyOrder anJoyOrder = new AnJoyOrder();
        anJoyOrder.setFCreatorId("");  //创建人（主数据）待定
        anJoyOrder.setFBizDate(order.getCreatedDate());
        anJoyOrder.setFdescription("");
        anJoyOrder.setFBizTypeId("");  //业务类型（主数据）待定
        anJoyOrder.setFOrderCustomerID("");  //订货客户（主数据）待定
        anJoyOrder.setFDeliveryTypeID("");  //交货方式（主数据）待定
        anJoyOrder.setFCurrencyId("");  //币别（主数据）待定
//        anJoyOrder.setFExchangeRate();
        anJoyOrder.setFPaymentTypeID("");  //付款方式（主数据）待定
        anJoyOrder.setFSettlementTypeID("");  //结算方式（主数据）待定
        anJoyOrder.setFSaleOrgUnitID("");  //销售组织（主数据）待定
        anJoyOrder.setFTotalAmount(order.getAmount());
//        anJoyOrder.setFTotalTax();
//        anJoyOrder.setFTotalTaxAmount();
//        anJoyOrder.setFPreReceived();
//        anJoyOrder.setFUnPrereceivedAmount();
        anJoyOrder.setFSendAddress(order.getReceiverAddress());
//        anJoyOrder.setFLocalTotalAmount();
//        anJoyOrder.setFLocalTotalTaxAmount();
        anJoyOrder.setFIsInTax(false);
        anJoyOrder.setFCustomerPhone(order.getPhone());
        anJoyOrder.setFLinkMan(order.getContacts());
//        anJoyOrder.setFIsCentralBalance();
//        anJoyOrder.setFReceiveConditionID();
//        anJoyOrder.setFStorageOrgUnitID();
//        anJoyOrder.setFWarehouseID();
        anJoyOrder.setCFGSQYID(order.getArea());
        return anJoyOrder;
    }*/
}