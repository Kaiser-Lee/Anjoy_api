package com.coracle.dms.service.impl.tz;

import com.coracle.dms.dao.mybatis.tz.TgOrderProductPartMapper;
import com.coracle.dms.po.tz.TgOrderProductPart;
import com.coracle.dms.service.tz.TgOrderProductPartService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TgOrderProductPartServiceImpl extends BaseServiceImpl<TgOrderProductPart> implements TgOrderProductPartService {
    private static final Logger logger = Logger.getLogger(TgOrderProductPartServiceImpl.class);

    @Autowired
    private TgOrderProductPartMapper tgOrderProductPartMapper;

    @Override
    public IMybatisDao<TgOrderProductPart> getBaseDao() {
        return tgOrderProductPartMapper;
    }
}