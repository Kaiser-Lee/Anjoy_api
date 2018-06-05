/**
 * create by Administrator
 * @date 2017-09
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import java.io.Serializable;

public class DmsSerialNum extends AdapterEntity implements Serializable {
    private String serialKey;

    private Long serialNum;

    private Long serialDate;

    private static final long serialVersionUID = 1L;

    public String getSerialKey() {
        return serialKey;
    }

    public void setSerialKey(String serialKey) {
        this.serialKey = serialKey == null ? null : serialKey.trim();
    }

    public Long getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Long serialNum) {
        this.serialNum = serialNum;
    }

    public Long getSerialDate() {
        return serialDate;
    }

    public void setSerialDate(Long serialDate) {
        this.serialDate = serialDate;
    }
}