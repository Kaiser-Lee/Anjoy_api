package com.coracle.dms.service;

import com.coracle.dms.po.DmsSerialNum;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsSerialNumService extends IBaseService<DmsSerialNum> {

    DmsSerialNum getInfoByPrimaryKey(String serialKey);

    String createSerialNumStr(String type);
}