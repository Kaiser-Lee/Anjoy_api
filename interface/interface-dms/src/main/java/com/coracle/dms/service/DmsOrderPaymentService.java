package com.coracle.dms.service;

import com.coracle.dms.po.DmsOrderPayment;
import com.coracle.dms.vo.DmsOrderPaymentVo;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsOrderPaymentService extends IBaseService<DmsOrderPayment> {
    void create(DmsOrderPaymentVo orderPaymentVo);

    void confirm(DmsOrderPayment orderPayment);

    DmsOrderPaymentVo detail(Long id);
}