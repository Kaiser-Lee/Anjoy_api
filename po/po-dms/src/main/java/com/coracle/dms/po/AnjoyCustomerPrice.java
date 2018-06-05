/**
 * create by lengf
 * @date 2018-03
 */
package com.coracle.dms.po;

import java.io.Serializable;

public class AnjoyCustomerPrice extends AnjoyCustomerPriceKey implements Serializable {
    private Double fprice;

    private static final long serialVersionUID = 1L;

    public Double getFprice() {
        return fprice;
    }

    public void setFprice(Double fprice) {
        this.fprice = fprice;
    }
}