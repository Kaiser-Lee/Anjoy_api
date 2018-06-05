package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.KnQrcodeNumMapper;
import com.coracle.dms.po.KnQrcodeNum;
import com.coracle.dms.service.KnQrcodeNumService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnQrcodeNumServiceImpl extends BaseServiceImpl<KnQrcodeNum> implements KnQrcodeNumService {
    private static final Logger logger = Logger.getLogger(KnQrcodeNumServiceImpl.class);

    @Autowired
    private KnQrcodeNumMapper knQrcodeNumMapper;

    @Override
    public IMybatisDao<KnQrcodeNum> getBaseDao() {
        return knQrcodeNumMapper;
    }
    @Override
    public KnQrcodeNum findByQrcode(String qrCode){
        return knQrcodeNumMapper.selectByPrimaryKey(qrCode);
    }

}