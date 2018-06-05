package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by
 * User：arno
 * Date：2018/2/1-11:20
 */
public class DmsReturnRequestVo  implements Serializable {

    @ApiModelProperty("EAS经销商编号")
    private String easCustomerNo;
    @ApiModelProperty("EAS退货单号")
    private String easReturnNo;
    @ApiModelProperty("申请日期")
    private Date applyDate;
    @ApiModelProperty("订单总额，不包括优惠")
    private BigDecimal amount;

    private List<DmsReturnItemVo> productItems=new ArrayList<>();

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getEasCustomerNo() {
        return easCustomerNo;
    }

    public void setEasCustomerNo(String easCustomerNo) {
        this.easCustomerNo = easCustomerNo;
    }

    public String getEasReturnNo() {
        return easReturnNo;
    }

    public void setEasReturnNo(String easReturnNo) {
        this.easReturnNo = easReturnNo;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public List<DmsReturnItemVo> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<DmsReturnItemVo> productItems) {
        this.productItems = productItems;
    }
}
