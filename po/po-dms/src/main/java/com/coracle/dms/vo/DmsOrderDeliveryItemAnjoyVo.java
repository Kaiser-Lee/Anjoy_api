package com.coracle.dms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by
 * author：arno
 * Date：2018/2/26-10:50
 */
public class DmsOrderDeliveryItemAnjoyVo implements Serializable {

    @ApiModelProperty("EAS订单编码")
    private String easOrderNo;
    @ApiModelProperty("订单产品编码")
    private String easProductCode;
    @ApiModelProperty("发货数量")
    private Integer count;
    @ApiModelProperty("发货日期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date deliverDate;

    public String getEasOrderNo() {
        return easOrderNo;
    }

    public void setEasOrderNo(String easOrderNo) {
        this.easOrderNo = easOrderNo;
    }

    public String getEasProductCode() {
        return easProductCode;
    }

    public void setEasProductCode(String easProductCode) {
        this.easProductCode = easProductCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }
}