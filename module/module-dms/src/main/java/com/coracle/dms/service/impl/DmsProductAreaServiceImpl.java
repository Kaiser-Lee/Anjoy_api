package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsProductAreaMapper;
import com.coracle.dms.po.DmsProductArea;
import com.coracle.dms.po.DmsTreeRelation;
import com.coracle.dms.service.DmsProductAreaService;
import com.coracle.dms.service.DmsTreeRelationService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DmsProductAreaServiceImpl extends BaseServiceImpl<DmsProductArea> implements DmsProductAreaService {
    private static final Logger logger = Logger.getLogger(DmsProductAreaServiceImpl.class);

    @Autowired
    private DmsProductAreaMapper dmsProductAreaMapper;

    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;

    @Override
    public IMybatisDao<DmsProductArea> getBaseDao() {
        return dmsProductAreaMapper;
    }

    /**
     * 根据渠道id和产品id判断该产品是否可为该渠道下账号可见：0-不可见；1-可见
     * @param channelId
     * @param productId
     * @return
     */
    @Override
    public Integer selectByChannelIdAndProductId(Long channelId, Long productId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("channelId", channelId);
        param.put("productId", productId);
        return dmsProductAreaMapper.selectByChannelIdAndProductId(param);
    }
}