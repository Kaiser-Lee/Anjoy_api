package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsProductAttachFileMapper;
import com.coracle.dms.po.DmsProductAttachFile;
import com.coracle.dms.service.DmsProductAttachFileService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsProductAttachFileServiceImpl extends BaseServiceImpl<DmsProductAttachFile> implements DmsProductAttachFileService {
    private static final Logger logger = Logger.getLogger(DmsProductAttachFileServiceImpl.class);

    @Autowired
    private DmsProductAttachFileMapper dmsProductAttachFileMapper;

    @Override
    public IMybatisDao<DmsProductAttachFile> getBaseDao() {
        return dmsProductAttachFileMapper;
    }
}