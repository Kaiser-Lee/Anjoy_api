package com.coracle.dms.service.impl.tz;

import com.coracle.dms.dao.mybatis.tz.TgOrderProductMapper;
import com.coracle.dms.po.tz.TgOrderProduct;
import com.coracle.dms.service.tz.TgOrderProductService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TgOrderProductServiceImpl extends BaseServiceImpl<TgOrderProduct> implements TgOrderProductService {
    private static final Logger logger = Logger.getLogger(TgOrderProductServiceImpl.class);

    @Autowired
    private TgOrderProductMapper tgOrderProductMapper;

    @Override
    public IMybatisDao<TgOrderProduct> getBaseDao() {
        return tgOrderProductMapper;
    }
}