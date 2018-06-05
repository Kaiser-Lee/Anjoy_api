package com.coracle.dms.vo.tz;

import com.coracle.dms.po.tz.TgOrder;
import com.coracle.dms.po.tz.TgOrderProduct;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Class description goes here.
 *
 * @author huangad@coracle.com
 * @create 2018/1/15
 */
public class TgOrderVo extends TgOrder {

    @ApiModelProperty("查询参数：下单起始时间")
    private String startCreatedDate;

    @ApiModelProperty("查询参数：下单终止时间")
    private String endCreatedDate;

    @ApiModelProperty("查询参数：物料编号")
    private String productCode;

    @ApiModelProperty("订单产品列表")
    private List<TgOrderProduct> productList;

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<TgOrderProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<TgOrderProduct> productList) {
        this.productList = productList;
    }
}
