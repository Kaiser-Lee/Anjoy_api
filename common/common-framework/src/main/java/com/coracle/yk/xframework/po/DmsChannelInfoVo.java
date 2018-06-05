package com.coracle.yk.xframework.po;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @title：
 * @describe：
 * @copyright：Copyright (c) 1997-2017 coracle, Inc.
 * @company：圆舟科技
 * @author：taok
 * @date：2018-03-01 20:40
 * @version：1.0
 */
public class DmsChannelInfoVo implements Serializable {
    private static final long serialVersionUID = 1918968128588254717L;

    private Long channelId;//渠道ID
    private BigDecimal famount;//授信总额
    private String cfcountenddate;//账期为空，就是没有限制
    private BigDecimal qk;//欠款
    private BigDecimal saleamount;//销售金额
    private BigDecimal balance;//授信余额
    private boolean isloked;//true：已锁定，false：未锁定
    private boolean isovered;//true：已超账期，false：未超账期

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public BigDecimal getFamount() {
        return famount;
    }

    public void setFamount(BigDecimal famount) {
        this.famount = famount;
    }

    public String getCfcountenddate() {
        return cfcountenddate;
    }

    public void setCfcountenddate(String cfcountenddate) {
        this.cfcountenddate = cfcountenddate;
    }

    public BigDecimal getQk() {
        return qk;
    }

    public void setQk(BigDecimal qk) {
        this.qk = qk;
    }

    public BigDecimal getSaleamount() {
        return saleamount;
    }

    public void setSaleamount(BigDecimal saleamount) {
        this.saleamount = saleamount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isloked() {
        return isloked;
    }

    public void setIsloked(boolean isloked) {
        this.isloked = isloked;
    }

    public boolean isovered() {
        return isovered;
    }

    public void setIsovered(boolean isovered) {
        this.isovered = isovered;
    }
}
