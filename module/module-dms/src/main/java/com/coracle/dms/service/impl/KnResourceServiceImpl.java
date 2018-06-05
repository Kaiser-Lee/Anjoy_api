package com.coracle.dms.service.impl;

import com.coracle.dms.po.KnResource;
import com.coracle.dms.dao.mybatis.KnResourceMapper;
import com.coracle.dms.service.KnResourceService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnResourceServiceImpl extends BaseServiceImpl<KnResource> implements KnResourceService {
    private static final Logger logger = Logger.getLogger(KnResourceServiceImpl.class);

    @Autowired
    private KnResourceMapper knResourceMapper;

    @Override
    public IMybatisDao<KnResource> getBaseDao() {
        return knResourceMapper;
    }

    @Override
    public List<KnResource> selectResourcesByUserId(Long id) {
        return knResourceMapper.selectResourcesByUserId(id);
    }

    @Override
    public List<String> selectButtonsByMxmIdAndResId(Long userId,Long resId){
        KnResource resource=new KnResource();
        resource.setSupId(resId);
        resource.setUpdateId(userId);
        return knResourceMapper.selectButtonsByResId(resource);
    }

    @Override
    public List<KnResource> selectResourcesByIds(List<Long> ids) {
        return knResourceMapper.selectResourcesByIds(ids);
    }
}