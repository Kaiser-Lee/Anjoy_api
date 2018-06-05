package com.coracle.dms.vo;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

public class DmsShoppingCartInfoVo extends AdapterEntity {
    @ApiModelProperty("购物车商品信息")
    private List<DmsShoppingCartVo> productList;

    @ApiModelProperty("总额")
    private BigDecimal total;

    @ApiModelProperty("合计")
    private BigDecimal sum;

    @ApiModelProperty("立减")
    private BigDecimal discount;

    @ApiModelProperty("可使用额度")
    private BigDecimal availableLimit;

    @ApiModelProperty("(渠道)最小起订量")
    private Long minOrderQuantity;

    public List<DmsShoppingCartVo> getProductList() {
        return productList;
    }

    public void setProductList(List<DmsShoppingCartVo> productList) {
        this.productList = productList;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(BigDecimal availableLimit) {
        this.availableLimit = availableLimit;
    }

    public Long getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(Long minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }


}