package com.coracle.dms.service;

import com.coracle.dms.po.DmsChannelEmployee;
import com.coracle.yk.xframework.po.DmsChannelMutilVo;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsChannelEmployeeService extends IBaseService<DmsChannelEmployee> {

    void deleteByEmployeeID(Long EmployeeId);

    List<DmsChannelMutilVo> selectChannelList(Long userId);

    void anjoySyn();
}