package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsPromotionScopeMapper;
import com.coracle.dms.po.DmsPromotionScope;
import com.coracle.dms.service.DmsPromotionScopeService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DmsPromotionScopeServiceImpl extends BaseServiceImpl<DmsPromotionScope> implements DmsPromotionScopeService {
    private static final Logger logger = Logger.getLogger(DmsPromotionScopeServiceImpl.class);

    @Autowired
    private DmsPromotionScopeMapper dmsPromotionScopeMapper;

    @Override
    public IMybatisDao<DmsPromotionScope> getBaseDao() {
        return dmsPromotionScopeMapper;
    }

    /**
     * 根据促销活动id删除促销适用范围信息
     *
     * @param promotionId
     */
    @Override
    public void deleteByPromotionId(Long promotionId) {
        dmsPromotionScopeMapper.deleteByPromotionId(promotionId);
    }

    /**
     * 新增/修改促销适用范围信息
     *
     *  @param promotionId
     * @param scopeIdList
     * @param session
     */
    @Override
    public void insertOrUpdate(Long promotionId, List<Long> scopeIdList, UserSession session) {
        Long userId = session.getId();
        Date now = new Date();

        //先删除，后新增
        deleteByPromotionId(promotionId);

        List<DmsPromotionScope> insertScopeList = Lists.newArrayList();
        for (Long scopeId : scopeIdList) {
            DmsPromotionScope ps = new DmsPromotionScope();
            ps.setPromotionId(promotionId);
            ps.setScopeId(scopeId);
            ps.setCreatedBy(userId);
            ps.setCreatedDate(now);
            ps.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

            insertScopeList.add(ps);
        }

        //批量插入
        if (!insertScopeList.isEmpty()) {
            dmsPromotionScopeMapper.batchInsert(insertScopeList);
        }
    }
}