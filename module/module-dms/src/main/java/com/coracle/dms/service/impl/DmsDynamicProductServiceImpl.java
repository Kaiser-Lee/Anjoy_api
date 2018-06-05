package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsDynamicProductMapper;
import com.coracle.dms.po.DmsDynamicProduct;
import com.coracle.dms.service.DmsDynamicProductService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsDynamicProductServiceImpl extends BaseServiceImpl<DmsDynamicProduct> implements DmsDynamicProductService {
    private static final Logger logger = Logger.getLogger(DmsDynamicProductServiceImpl.class);

    @Autowired
    private DmsDynamicProductMapper dmsDynamicProductMapper;

    @Override
    public IMybatisDao<DmsDynamicProduct> getBaseDao() {
        return dmsDynamicProductMapper;
    }


}