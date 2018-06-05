package com.coracle.dms.service;

import com.coracle.dms.po.DmsUserAddress;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsUserAddressService extends IBaseService<DmsUserAddress> {
    /**
     * 添加收货地址，返回主键ID
     * @param dmsUserAddress
     * @return
     */
    void saveOrUpdate(DmsUserAddress dmsUserAddress);

    /**
     * 把所有的默认地址变为非默认地址
     * @param dmsUserAddress
     */
    void dealDefaultAddress(DmsUserAddress dmsUserAddress);

    DmsUserAddress getDefaultAddress(Long userId);
}