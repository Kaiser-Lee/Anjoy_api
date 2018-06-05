package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsLadderPriceMapper;
import com.coracle.dms.po.DmsLadderPrice;
import com.coracle.dms.service.DmsLadderPriceService;
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
public class DmsLadderPriceServiceImpl extends BaseServiceImpl<DmsLadderPrice> implements DmsLadderPriceService {
    private static final Logger logger = Logger.getLogger(DmsLadderPriceServiceImpl.class);

    @Autowired
    private DmsLadderPriceMapper dmsLadderPriceMapper;

    @Override
    public IMybatisDao<DmsLadderPrice> getBaseDao() {
        return dmsLadderPriceMapper;
    }

    @Override
    public void insertOrUpdate(Long ladderPriceProductId, List<DmsLadderPrice> dmsLadderPriceList, UserSession userSession){
        Long userId = userSession.getId();
        Date now = new Date();
        //根据阶梯价格产品项的id查询数据库中对应的的阶梯价格id列表
        List<Long> existIdList = dmsLadderPriceMapper.selectIdByLadderPriceProductId(ladderPriceProductId);

        //要删除的数据的id列表，初始化为数据库中存在的id
        //id：数据库中存在，用户提交的参数中不存在的，则为要删除的数据
        List<Long> removeIdList = Lists.newArrayList(existIdList);

        for (DmsLadderPrice lp:dmsLadderPriceList) {
            Integer id = lp.getId();
            if(id == null){
                lp.setLadderpriceporductId(ladderPriceProductId);
                lp.setCreateDate(now);
                lp.setCreateBy(userId);
                lp.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                dmsLadderPriceMapper.insert(lp);

            }else{
                if(existIdList.contains(lp.getId())){
                    lp.setLastUpdateDate(now);
                    lp.setLastUpdateBy(userId);
                    dmsLadderPriceMapper.updateByPrimaryKeySelective(lp);
                    removeIdList.remove(id);
                }
            }
        }

        if(!removeIdList.isEmpty()){
            dmsLadderPriceMapper.batchRemove(removeIdList);
        }

    }


}