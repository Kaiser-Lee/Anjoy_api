package com.coracle.dms.service;

import com.coracle.dms.po.DmsChannelAddress;
import com.coracle.dms.vo.DmsChannelAddressVo;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsChannelAddressService extends IBaseService<DmsChannelAddress> {

    void deleteByAddressID(Long addressId);

    List<DmsChannelAddressVo> list(UserSession session);

    void setDefault(DmsChannelAddress param, UserSession session);

    DmsChannelAddressVo getDefault(Long channelId);

    DmsChannelAddressVo getVoByPrimaryKey(Long id);

    /**
     * 同步安井渠道-收货人地址信息
     * 调用安井的渠道-收货人地址同步接口，返回JSON数组格式数据
     */
    void anjoySyn();


}