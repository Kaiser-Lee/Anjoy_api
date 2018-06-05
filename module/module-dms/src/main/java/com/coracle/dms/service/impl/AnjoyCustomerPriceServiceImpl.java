package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.AnjoyCustomerPriceMapper;
import com.coracle.dms.po.AnjoyCustomerPrice;
import com.coracle.dms.service.AnjoyCustomerPriceService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnjoyCustomerPriceServiceImpl extends BaseServiceImpl<AnjoyCustomerPrice> implements AnjoyCustomerPriceService {
    private static final Logger logger = Logger.getLogger(AnjoyCustomerPriceServiceImpl.class);

    @Autowired
    private AnjoyCustomerPriceMapper anjoyCustomerPriceMapper;

    @Override
    public IMybatisDao<AnjoyCustomerPrice> getBaseDao() {
        return anjoyCustomerPriceMapper;
    }
}