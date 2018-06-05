package com.coracle.dms.vo;

import com.coracle.dms.po.DmsOrder;
import com.coracle.dms.po.DmsOrderPayment;
import com.coracle.dms.po.DmsUserAddress;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

public class DmsOrderVo extends DmsOrder{
    @ApiModelProperty("订单状态(订货端)")
    private String buyerOrderStatusText;

    @ApiModelProperty("订单状态(厂商端)")
    private String sellerOrderStatusText;

    @ApiModelProperty("收款状态")
    private String payStatusText;

    @ApiModelProperty("查询参数：下单起始时间")
    private String startCreatedDate;

    @ApiModelProperty("查询参数：下单终止时间")
    private String endCreatedDate;

    @ApiModelProperty("收货人地址信息id")
    private Long userAddressId;

    @ApiModelProperty("收货人地址信息")
    private DmsUserAddress userAddress;

    @ApiModelProperty("增值税发票信息id")
    private Long invoiceId;

    @ApiModelProperty("订单产品列表")
    private List<DmsOrderProductVo> productList;

    @ApiModelProperty("订单收款记录列表")
    private List<DmsOrderPayment> paymentList;

    @ApiModelProperty("待发货产品列表")
    private List<DmsOrderProductVo> undeliveredList;

    @ApiModelProperty("已发货产品列表")
    private List<DmsOrderDeliveryItemVo> deliveredList;

    @ApiModelProperty("待支付金额")
    private BigDecimal unpaidAmount;

    @ApiModelProperty("待确认金额")
    private BigDecimal unconfirmedAmount;

    @ApiModelProperty("配送方式")
    private String deliverTypeText;

    @ApiModelProperty("是否已评价")
    private Integer evaluated;

    @ApiModelProperty("待评价产品的数量")
    private Integer unevaluatedCount;

    @ApiModelProperty("订单产品名称列表，逗号分隔形式")
    private String orderProductNames;

    @ApiModelProperty("订单已收获产品的数量")
    private Integer receivedCount;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("订单相关提示消息")
    private String message;

    @ApiModelProperty("当前登录人的用户id")
    private Long loginId;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("是否有消息提示")
    private Integer hasMessage;

    @ApiModelProperty("是否是再来一单")
    private Integer isAgain;

    @ApiModelProperty("订单类型：1-常规订单；2-预订单；3-特价订单")
    private String typeText;

    @ApiModelProperty("收货地址（安井）")
    private DmsChannelAddressVo channelAddress;

    @ApiModelProperty("收货地址id")
    private Long channelAddressId;

    @ApiModelProperty("数量合计")
    private Long qtyTotal;

    @ApiModelProperty("基本数量合计(箱)")
    private Long baseQtyTotal;

    @ApiModelProperty("总重量")
    private BigDecimal weightTotal;

    @ApiModelProperty("总体积")
    private BigDecimal volumeTotal;

    @ApiModelProperty("重量/体积比")
    private BigDecimal weightVolumeRatio;

    @ApiModelProperty("订单实际金额")
    private BigDecimal realAmount;
    @ApiModelProperty("渠道id")
    private Long channelId;

    public String getBuyerOrderStatusText() {
        return buyerOrderStatusText;
    }

    public void setBuyerOrderStatusText(String buyerOrderStatusText) {
        this.buyerOrderStatusText = buyerOrderStatusText;
    }

    public String getSellerOrderStatusText() {
        return sellerOrderStatusText;
    }

    public void setSellerOrderStatusText(String sellerOrderStatusText) {
        this.sellerOrderStatusText = sellerOrderStatusText;
    }

    public String getPayStatusText() {
        return payStatusText;
    }

    public void setPayStatusText(String payStatusText) {
        this.payStatusText = payStatusText;
    }

    public String getStartCreatedDate() {
        return startCreatedDate;
    }

    public void setStartCreatedDate(String startCreatedDate) {
        this.startCreatedDate = startCreatedDate;
    }

    public String getEndCreatedDate() {
        return endCreatedDate;
    }

    public void setEndCreatedDate(String endCreatedDate) {
        this.endCreatedDate = endCreatedDate;
    }

    public Long getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Long userAddressId) {
        this.userAddressId = userAddressId;
    }

    public DmsUserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(DmsUserAddress userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public Long getInvoiceId() {
        return invoiceId;
    }

    @Override
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public List<DmsOrderProductVo> getProductList() {
        return productList;
    }

    public void setProductList(List<DmsOrderProductVo> productList) {
        this.productList = productList;
    }

    public List<DmsOrderPayment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<DmsOrderPayment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<DmsOrderProductVo> getUndeliveredList() {
        return undeliveredList;
    }

    public void setUndeliveredList(List<DmsOrderProductVo> undeliveredList) {
        this.undeliveredList = undeliveredList;
    }

    public List<DmsOrderDeliveryItemVo> getDeliveredList() {
        return deliveredList;
    }

    public void setDeliveredList(List<DmsOrderDeliveryItemVo> deliveredList) {
        this.deliveredList = deliveredList;
    }

    public BigDecimal getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(BigDecimal unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public BigDecimal getUnconfirmedAmount() {
        return unconfirmedAmount;
    }

    public void setUnconfirmedAmount(BigDecimal unconfirmedAmount) {
        this.unconfirmedAmount = unconfirmedAmount;
    }

    public String getDeliverTypeText() {
        return deliverTypeText;
    }

    public void setDeliverTypeText(String deliverTypeText) {
        this.deliverTypeText = deliverTypeText;
    }

    public Integer getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(Integer evaluated) {
        this.evaluated = evaluated;
    }

    public Integer getUnevaluatedCount() {
        return unevaluatedCount;
    }

    public void setUnevaluatedCount(Integer unevaluatedCount) {
        this.unevaluatedCount = unevaluatedCount;
    }

    public String getOrderProductNames() {
        return orderProductNames;
    }

    public void setOrderProductNames(String orderProductNames) {
        this.orderProductNames = orderProductNames;
    }

    public Integer getReceivedCount() {
        return receivedCount;
    }

    public void setReceivedCount(Integer receivedCount) {
        this.receivedCount = receivedCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public Integer getHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(Integer hasMessage) {
        this.hasMessage = hasMessage;
    }

    public Integer getIsAgain() {
        return isAgain;
    }

    public void setIsAgain(Integer isAgain) {
        this.isAgain = isAgain;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public BigDecimal getWeightTotal() {
        return weightTotal;
    }

    public void setWeightTotal(BigDecimal weightTotal) {
        this.weightTotal = weightTotal;
    }

    public BigDecimal getVolumeTotal() {
        return volumeTotal;
    }

    public void setVolumeTotal(BigDecimal volumeTotal) {
        this.volumeTotal = volumeTotal;
    }

    public BigDecimal getWeightVolumeRatio() {
        return weightVolumeRatio;
    }

    public void setWeightVolumeRatio(BigDecimal weightVolumeRatio) {
        this.weightVolumeRatio = weightVolumeRatio;
    }

    public DmsChannelAddressVo getChannelAddress() {
        return channelAddress;
    }

    public void setChannelAddress(DmsChannelAddressVo channelAddress) {
        this.channelAddress = channelAddress;
    }

    public Long getChannelAddressId() {
        return channelAddressId;
    }

    public void setChannelAddressId(Long channelAddressId) {
        this.channelAddressId = channelAddressId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getQtyTotal() {
        return qtyTotal;
    }

    public void setQtyTotal(Long qtyTotal) {
        this.qtyTotal = qtyTotal;
    }

    public Long getBaseQtyTotal() {
        return baseQtyTotal;
    }

    public void setBaseQtyTotal(Long baseQtyTotal) {
        this.baseQtyTotal = baseQtyTotal;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}
