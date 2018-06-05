package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.dao.mybatis.DmsOrderProductMapper;
import com.coracle.dms.dao.mybatis.DmsProductMapper;
import com.coracle.dms.po.DmsChannel;
import com.coracle.dms.po.DmsOrder;
import com.coracle.dms.po.DmsOrderProduct;
import com.coracle.dms.po.DmsProduct;
import com.coracle.dms.service.DmsOrderProductService;
import com.coracle.dms.service.DmsOrderService;
import com.coracle.dms.vo.DmsOrderProductVo;
import com.coracle.dms.vo.DmsOrderVo;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.dms.webservice.model.AnjoyOrderProductModel;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.xiruo.medbid.components.date.DateUtils;
import com.xiruo.medbid.util.ArithUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DmsOrderProductServiceImpl extends BaseServiceImpl<DmsOrderProduct> implements DmsOrderProductService {
    private static final Logger logger = LoggerFactory.getLogger(DmsOrderProductServiceImpl.class);
    @Autowired
    private DmsOrderProductMapper dmsOrderProductMapper;
    @Autowired
    private DmsProductMapper dmsProductMapper;
    @Autowired
    private DmsOrderService dmsOrderService;
    @Override
    public IMybatisDao<DmsOrderProduct> getBaseDao() {
        return dmsOrderProductMapper;
    }

    /**
     * 批量新增订单产品信息
     * @param orderProductList
     */
    @Override
    public void batchInsert(List<DmsOrderProduct> orderProductList) {
        dmsOrderProductMapper.batchInsert(orderProductList);
    }

    /**
     * 根据订单id获取待发货产品信息列表，并根据产品id和规格属性获取有库存的仓库和货位列表
     * @param orderId
     * @return
     */
    @Override
    public List<DmsOrderProductVo> selectStorageInfoByOrderId(Long orderId) {
        return dmsOrderProductMapper.selectStorageInfoByOrderId(orderId);
    }

    /**
     * 根据订单产品id条件获取星级统计
     * @param productId
     * @return
     */
    @Override
    public Map<String,Object> selectCountByOrderProductProductId(Long productId) {
        return dmsOrderProductMapper.selectCountByOrderProductProductId(productId);
    }

    /**
     * 获取未收货的订单产品数量
     * @param orderId
     * @return
     */
    @Override
    public Integer selectUnreceivedCountByOrderId(Long orderId) {
        return dmsOrderProductMapper.selectUnreceivedCountByOrderId(orderId);
    }

    /**
     * 已完成订单的订单产品列表(分页)
     * @param order
     * @return
     */
    @Override
    public PageHelper.Page<DmsOrderProductVo> finishedPageList(DmsOrderVo order) {
        try {
            PageHelper.startPage(order.getP(), order.getS());
            dmsOrderProductMapper.selectFinishedByOrder(order);
        } catch (Exception e) {
            logger.error("分页查询异常!", e);
            throw new ServiceException("分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 根据订单id获取订单总金额
     * @param orderId
     * @return
     */
    @Override
    public BigDecimal selectAmountByOrderId(Long orderId) {
        return dmsOrderProductMapper.selectAmountByOrderId(orderId);
    }

    /**
     * 修改订单产品信息
     * @param param
     */
    @Override
    public void update(DmsOrderVo param) {
        Long orderId = param.getId();
        Date date = new Date();
        Long userId = param.getUserId();

        DmsOrder dmsOrder = dmsOrderService.selectByPrimaryKey(orderId);
        if(dmsOrder == null){
            throw new ServiceException("订单ID不存在");
        }
        dmsOrder.setFreight(param.getFreight());
        dmsOrderService.updateByPrimaryKeySelective(dmsOrder);

        List<Long> idList = Lists.newArrayList();
        for (DmsOrderProductVo op : param.getProductList()) {
            if (op.getId() == null) {  //新增的数据
                op.setOrderId(orderId);
                op.setShoppingcartId(0L);  //手动添加的商品，没有对应的购物车数据id
                op.setReturnCount(0);
                op.setCreatedDate(date);
                op.setCreatedBy(userId);
                op.setLastUpdatedDate(date);
                op.setLastUpdatedBy(userId);
                op.setRemoveFlag(0);
                this.insert(op);
            } else {
                op.setLastUpdatedDate(date);
                op.setLastUpdatedBy(userId);
                this.updateByPrimaryKeySelective(op);
            }
            idList.add(op.getId());
        }

        //删除订单下产品
        DmsOrderProductVo op = new DmsOrderProductVo();
        op.setOrderId(orderId);
        op.setIds(idList);
        dmsOrderProductMapper.removeByCondition(op);
    }

    /**
     * DMS订单产品 - 安井订单产品参数转换
     * @param orderProductList
     * @return
     */
    @Override
    public List<AnjoyOrderProductModel> convertParam(List<DmsOrderProduct> orderProductList, Map<String, Object> paramMap) {
        /**
         * 实际含税单价&含税单价：通过系统获取
         * 实际单价&单价：含税单价 / 1.17
         * 金额：单价 * 数量
         * 价税合计：含税单价 * 数量
         * 税额：价税合计减金额
         */
        DmsChannel dmsChannel = (DmsChannel) paramMap.get("dmsChannel");
        String channelFid = dmsChannel.getAnjoyId();

        List<AnjoyOrderProductModel> anjoyOrderProductList = Lists.newArrayList();
        Map<Long, DmsProduct> productCacheMap = new HashMap<>();
        String currentDate = DateUtils.formatDate(new Date(), DateUtils.DATETIME);
        for (DmsOrderProduct op : orderProductList) {
            DmsProduct dmsProduct = null;
            if(productCacheMap.get(op.getProductId()) == null){
                dmsProduct = dmsProductMapper.selectByPrimaryKey(op.getProductId());
                productCacheMap.put(op.getProductId(), dmsProduct);
            }else {
                dmsProduct = productCacheMap.get(op.getProductId());
            }
            String anjoyProductFid = dmsProduct.getAnjoyId();
            String anjoyProductCode = dmsProduct.getCode();
            if(op.getPrice() == null && op.getPrice().compareTo(BigDecimal.ZERO) <= 0){
                throw new ServiceException("产品金额不能小于零：{}", JSON.toJSONString(op));
            }
            /** 订单明细 */
            AnjoyOrderProductModel anjoyOrderProductModel = new AnjoyOrderProductModel();
            /** 物料：对应DMS产品 anjoy_id , 安井这边的物料 FID */
            anjoyOrderProductModel.setMaterial(anjoyProductFid);
            /** 计量单位：安井（箱） FID：AdUAAAAHuOtbglxX */
            anjoyOrderProductModel.setUnit("AdUAAAAHuOtbglxX");
            //anjoyOrderProductModel.setRemark("备注");//备注
            //anjoyOrderProductModel.setReasonCode("原因代码");//原因代码
            //anjoyOrderProductModel.setIsPresent(false);//是否赠品
            //数量：商品小计
            anjoyOrderProductModel.setQty(new BigDecimal(op.getCount()));
            //anjoyOrderProductModel.setAssistQty(new BigDecimal("1"));//辅助单位数量
            //单价：DMS渠道对应商品单价（未税）除以1.17
            double price = ArithUtil.div(op.getPrice().doubleValue(), 1.17);
            anjoyOrderProductModel.setPrice(new BigDecimal(price));
            //含税单价：DMS渠道对应商品单价（含税）
            anjoyOrderProductModel.setTaxPrice(op.getPrice());
            //anjoyOrderProductModel.setCheapRate(new BigDecimal("1.02"));//减价比例
            //anjoyOrderProductModel.setDiscountCondition(-1);//折扣条件
            //anjoyOrderProductModel.setDiscountType(-1);//折扣方式
            //anjoyOrderProductModel.setDiscountCondition(0);//单位折扣（率）
            //anjoyOrderProductModel.setDiscountAmount(new BigDecimal("64.75"));//折扣额
            //金额
            double amount = ArithUtil.mul(price, op.getCount().doubleValue());
            anjoyOrderProductModel.setAmount(new BigDecimal(amount));
            //实际单价
            anjoyOrderProductModel.setActualPrice(new BigDecimal(price));
            //实际含税单价（含税）
            anjoyOrderProductModel.setActualTaxPrice(op.getPrice());
            anjoyOrderProductModel.setTaxRate(new BigDecimal("17"));//税率
            //价税合计（含税总价）
            double taxAmount = op.getPrice().doubleValue() * op.getCount();
            anjoyOrderProductModel.setTaxAmount(new BigDecimal(taxAmount));
            //税额（实际含税单价 * 0.17）
            double tax = ArithUtil.sub(taxAmount, amount);
            anjoyOrderProductModel.setTax(new BigDecimal(tax));

            anjoyOrderProductModel.setTotalTax(anjoyOrderProductModel.getTax());//本位币税额
            anjoyOrderProductModel.setTotalAmount(anjoyOrderProductModel.getAmount());//本位币金额
            anjoyOrderProductModel.setTotalTaxAmount(anjoyOrderProductModel.getTaxAmount());//价税合计本位币

            anjoyOrderProductModel.setSendDate(currentDate);//发货日期
            anjoyOrderProductModel.setDeliveryDate(currentDate);//交货日期
            //anjoyOrderProductModel.setStorageOrgUnit("AdUAAAAAB+LM567U");//发货组织
            //anjoyOrderProductModel.setUnOrderedQty(new BigDecimal("22"));//未订货数量
            //anjoyOrderProductModel.setQuantityUnCtrl(new BigDecimal("10"));//不控制数量
            //anjoyOrderProductModel.setReason("原因");//原因
            //anjoyOrderProductModel.setIsBetweenCompanySend(null);//是否跨公司发货

            anjoyOrderProductModel.setDeliveryCustomer(channelFid);//送货客户
            anjoyOrderProductModel.setPaymentCustomer(channelFid);//收款客户
            anjoyOrderProductModel.setReceiveCustomer(channelFid);//应收客户
            //anjoyOrderProductModel.setSendAddress("送货地址");//送货地址

            //基本计量单位（安井-公斤）FID：gw5fUwEOEADgAAsRwKgSOFuCXFc=
            anjoyOrderProductModel.setBaseUnit("gw5fUwEOEADgAAsRwKgSOFuCXFc=");
            //基本单位数量	BigDecimal		是
            String anjoyUnitFID = anjoyOrderProductModel.getUnit();
            StringBuilder suffixSqler = new StringBuilder();
            suffixSqler.append(" AND B.FNUMBER = '")
                .append(anjoyProductCode).append("' AND A.计量单位ID = '")
                .append(anjoyUnitFID).append("' ");
            JSONArray productUnitArray = AnjoySynClient.getAnjoyProductUnit(suffixSqler.toString());
            if(productUnitArray != null && productUnitArray.size() > 0){
                JSONObject productUnitObject = (JSONObject) productUnitArray.get(0);
                String status = productUnitObject.getString("产品状态");
                String fnumber = productUnitObject.getString("产品代码");
                String fname = productUnitObject.getString("产品名称");
                String funit = productUnitObject.getString("计量单位");
                String fmaterialid = productUnitObject.getString("物料ID");
                String FMeasureUnitID = productUnitObject.getString("计量单位ID");
                BigDecimal frate = productUnitObject.getBigDecimal("与基本计量单位换算率");
                String fsftjdw = productUnitObject.getString("是否统计单位");
                String fsfjbjldw = productUnitObject.getString("是否基本计量单位");

                logger.info("打印安井-产品对应计量数据：{}", JSON.toJSONString(productUnitObject));
                BigDecimal dmsQty = anjoyOrderProductModel.getQty();
                double anjoyBaseQty = ArithUtil.mul(frate.doubleValue(), dmsQty.doubleValue());
                anjoyOrderProductModel.setBaseQty(new BigDecimal(anjoyBaseQty));
            }else {
                logger.error("未获取到安井 产品对应计量单位数据 ！SQL语句：{} ", suffixSqler.toString());
                throw new ServiceException("未获取到安井 产品对应计量单位数据 ！");
            }

            anjoyOrderProductList.add(anjoyOrderProductModel);
        }
        return anjoyOrderProductList;
    }

    /*public List<AnjoyOrderProduct> convertParam(List<DmsOrderProductVo> orderProductList) {
        List<AnjoyOrderProduct> anjoyOrderProductList = Lists.newArrayList();
        for (DmsOrderProduct op : orderProductList) {
            AnjoyOrderProduct anjoyOrderProduct = new AnjoyOrderProduct();
            anjoyOrderProduct.setFMaterialID("");  //物料（主数据） - 产品的anjoyId
            anjoyOrderProduct.setFUnitID("");  //计量单位（主数据）待定
            anjoyOrderProduct.setFRemark("");
            anjoyOrderProduct.setFReasonCodeID("");   //原因代码（主数据）待定
            anjoyOrderProduct.setFIsPresent(false);  //是否赠品
            anjoyOrderProduct.setFQty(new BigDecimal(op.getCount()));
//        anjoyOrderProduct.setFAssistQty();
            anjoyOrderProduct.setFPrice(op.getPrice());
//        anjoyOrderProduct.setFTaxPrice();
//        anjoyOrderProduct.setFCheapRate();
            anjoyOrderProduct.setFDiscountCondition(0);  //折扣条件（枚举）待定
            anjoyOrderProduct.setFDiscountType(0);  //折扣方式（枚举）待定
//        anjoyOrderProduct.setFDiscount();
//        anjoyOrderProduct.setFDiscountAmount();
            anjoyOrderProduct.setFAmount(op.getPrice());
            anjoyOrderProduct.setFActualPrice(op.getPrice());
            anjoyOrderProduct.setFActualTaxPrice(op.getPrice());
//        anjoyOrderProduct.setFTaxRate();
//        anjoyOrderProduct.setFTax();
//        anjoyOrderProduct.setFTaxAmount();
            anjoyOrderProduct.setFSendDate(new Date());
            anjoyOrderProduct.setFDeliveryDate(new Date());
            anjoyOrderProduct.setFStorageOrgUnitID("");  //发货组织（主数据）待定
//        anjoyOrderProduct.setFUnOrderedQty();
//        anjoyOrderProduct.setFQuantityUnCtrl();
            anjoyOrderProduct.setFReason("");
            anjoyOrderProduct.setFIsBetweenCompanySend(false);
            anjoyOrderProduct.setFDeliveryCustomerID("");  //送货客户（主数据）待定
            anjoyOrderProduct.setFPaymentCustomerID("");  //收款客户（主数据）待定
            anjoyOrderProduct.setFReceiveCustomerID("");  //应收客户（主数据）待定
            anjoyOrderProduct.setFSendAddress("");

            anjoyOrderProductList.add(anjoyOrderProduct);
        }

        return anjoyOrderProductList;
    }*/

}