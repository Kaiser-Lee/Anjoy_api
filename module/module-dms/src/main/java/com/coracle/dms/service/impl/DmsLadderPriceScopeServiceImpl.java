package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsLadderPriceScopeMapper;
import com.coracle.dms.po.DmsLadderPriceScope;
import com.coracle.dms.po.DmsPromotionScope;
import com.coracle.dms.service.DmsLadderPriceScopeService;
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
public class DmsLadderPriceScopeServiceImpl extends BaseServiceImpl<DmsLadderPriceScope> implements DmsLadderPriceScopeService {
    private static final Logger logger = Logger.getLogger(DmsLadderPriceScopeServiceImpl.class);

    @Autowired
    private DmsLadderPriceScopeMapper dmsLadderPriceScopeMapper;

    @Override
    public IMybatisDao<DmsLadderPriceScope> getBaseDao() {
        return dmsLadderPriceScopeMapper;
    }
    /**
     * 根据阶梯价格id删除使用范围
     */
    @Override
    public void deleteByLadderPriceProductId(Long ladderPriceProductId){

        dmsLadderPriceScopeMapper.deleteByLadderPriceProductId(ladderPriceProductId);

    }

    /**
     * 新增/修改阶梯价格适用范围信息
     *
     *  @param ladderPriceProductId
     * @param scopeIdList
     * @param userSession
     */
    @Override
    public void insertOrUpdate(Long ladderPriceProductId, List<Long> scopeIdList, UserSession userSession) {
        Long userId = userSession.getId();
        Date now = new Date();

        //先删除，后新增
        deleteByLadderPriceProductId(ladderPriceProductId);

        List<DmsLadderPriceScope> insertScopeList = Lists.newArrayList();
        for (Long scopeId : scopeIdList) {
            DmsLadderPriceScope lps = new DmsLadderPriceScope();
            lps.setLadderpriceproductCode(ladderPriceProductId);
            lps.setScopeId(scopeId);
            lps.setCreatedBy(userId);
            lps.setCreatedDate(now);
            lps.setRemoveFlag(Integer.valueOf(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType()));

            insertScopeList.add(lps);
        }

        //批量插入
        if (!insertScopeList.isEmpty()) {
            dmsLadderPriceScopeMapper.batchInsert(insertScopeList);
        }
    }


}