package com.coracle.dms.service;

import com.coracle.dms.po.KnQrcodeNum;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface KnQrcodeNumService extends IBaseService<KnQrcodeNum> {
    KnQrcodeNum findByQrcode(String qrCode);
}