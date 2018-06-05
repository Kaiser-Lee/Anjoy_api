package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsPublishRrangeMapper;
import com.coracle.dms.po.DmsChannelInformation;
import com.coracle.dms.po.DmsPublishRrange;
import com.coracle.dms.service.DmsPublishRrangeService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DmsPublishRrangeServiceImpl extends BaseServiceImpl<DmsPublishRrange> implements DmsPublishRrangeService {
    private static final Logger logger = Logger.getLogger(DmsPublishRrangeServiceImpl.class);

    @Autowired
    private DmsPublishRrangeMapper dmsPublishRrangeMapper;

    @Override
    public IMybatisDao<DmsPublishRrange> getBaseDao() {
        return dmsPublishRrangeMapper;
    }

    /**
     * 批量添加发布范围
     * @param dmsPublishRrangeList
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchInsertRange(List<DmsPublishRrange> dmsPublishRrangeList){
        dmsPublishRrangeMapper.batchInsert(dmsPublishRrangeList);
    }
    /**
     * 删除原有的范围
     * @param dmsPublishRrange
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteByRelatedInfo(DmsPublishRrange dmsPublishRrange){
        dmsPublishRrangeMapper.deleteByRelatedInfo(dmsPublishRrange);
    }

    /**
     * 依据发布范围ID，获取名字
     * @param ids
     * @return
     */
    public List<Map<String,Object>> getPublishRangeNameIds(List<Long> ids){
        return dmsPublishRrangeMapper.getPublishRangeNameIds(ids);
    }
}