/**
 * create by lengf
 * @date 2018-03
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import java.io.Serializable;

public class AnjoyCustomerPriceKey extends AdapterEntity implements Serializable {
    private String easChannelCode;

    private String easProductCode;

    private static final long serialVersionUID = 1L;

    public String getEasChannelCode() {
        return easChannelCode;
    }

    public void setEasChannelCode(String easChannelCode) {
        this.easChannelCode = easChannelCode == null ? null : easChannelCode.trim();
    }

    public String getEasProductCode() {
        return easProductCode;
    }

    public void setEasProductCode(String easProductCode) {
        this.easProductCode = easProductCode == null ? null : easProductCode.trim();
    }
}