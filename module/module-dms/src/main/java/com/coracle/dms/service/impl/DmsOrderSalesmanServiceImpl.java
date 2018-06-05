package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsOrderSalesmanMapper;
import com.coracle.dms.po.DmsOrderSalesman;
import com.coracle.dms.service.DmsOrderSalesmanService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsOrderSalesmanServiceImpl extends BaseServiceImpl<DmsOrderSalesman> implements DmsOrderSalesmanService {
    private static final Logger logger = Logger.getLogger(DmsOrderSalesmanServiceImpl.class);

    @Autowired
    private DmsOrderSalesmanMapper dmsOrderSalesmanMapper;

    @Override
    public IMybatisDao<DmsOrderSalesman> getBaseDao() {
        return dmsOrderSalesmanMapper;
    }
}