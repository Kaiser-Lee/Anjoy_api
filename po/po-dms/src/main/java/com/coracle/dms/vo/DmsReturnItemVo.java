package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by
 * User：arno
 * Date：2018/2/1-11:17
 */
public class DmsReturnItemVo  implements Serializable {

    @ApiModelProperty("EAS订货单编号")
    private String easOrderNo;
    @ApiModelProperty("商品编码")
    private String easProductNo;
    @ApiModelProperty("退货数量")
    private Integer count;


    public String getEasOrderNo() {
        return easOrderNo;
    }

    public void setEasOrderNo(String easOrderNo) {
        this.easOrderNo = easOrderNo;
    }

    public String getEasProductNo() {
        return easProductNo;
    }

    public void setEasProductNo(String easProductNo) {
        this.easProductNo = easProductNo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
