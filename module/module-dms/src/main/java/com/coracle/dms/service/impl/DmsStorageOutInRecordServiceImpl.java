package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsStorageOutInRecordMapper;
import com.coracle.dms.po.*;
import com.coracle.dms.service.DmsStorageOutInRecordService;
import com.coracle.dms.service.DmsStoreService;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.vo.DmsStorageOutInRecordVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmsStorageOutInRecordServiceImpl extends BaseServiceImpl<DmsStorageOutInRecord> implements DmsStorageOutInRecordService {
    private static final Logger logger = Logger.getLogger(DmsStorageOutInRecordServiceImpl.class);

    @Autowired
    private DmsStorageOutInRecordMapper dmsStorageOutInRecordMapper;

    @Autowired
    private DmsStoreService dmsStoreService;

    @Override
    public IMybatisDao<DmsStorageOutInRecord> getBaseDao() {
        return dmsStorageOutInRecordMapper;
    }

    @Override
    public PageHelper.Page<DmsStorageOutInRecordVo> findStorageOutInRecordPageList(DmsStorageOutInRecordVo outInStorageRecord) {
        try {
            PageHelper.startPage(outInStorageRecord.getP(),outInStorageRecord.getS());

            Long orgType = outInStorageRecord.getOrgType();
            if (orgType == 1) {  //品牌商
                outInStorageRecord.setOrgType(1L);
            } else if (orgType == 2) {  //渠道商
                Long channelId = outInStorageRecord.getOrgId();
                outInStorageRecord.setChannelId(channelId);
                List<Long> storeIdList = dmsStoreService.selectStoreIdListByChannelId(channelId);
                outInStorageRecord.setStoreIdList(storeIdList);
                outInStorageRecord.setOrgType(2L);
            }
            dmsStorageOutInRecordMapper.findStorageOutInRecordPageList(outInStorageRecord);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public List<DmsStorageOutInRecordVo> findStorageOutInRecordByIds(List<Integer> exPortIds) {
        try {
            return dmsStorageOutInRecordMapper.findStorageOutInRecordByIds(exPortIds);
        } catch (Exception e) {
            throw new ServiceException("查询异常");
        }
    }

    @Override
    public PageHelper.Page<DmsStorageOutInRecordVo> findStorageOutInRecordList(DmsStorageOutInRecordVo outInStorageRecord) {
        try {
            //查询出入库数量统计，不分页
            int totalNum = dmsStorageOutInRecordMapper.findStorageOutInRecordListNum(outInStorageRecord);
            PageHelper.startPage(outInStorageRecord.getP(),outInStorageRecord.getS());
            List<DmsStorageOutInRecordVo> list= dmsStorageOutInRecordMapper.findStorageOutInRecordList(outInStorageRecord);
            if(totalNum!=0){
                for(int i=0;i<list.size();i++){
                    list.get(i).setTotalNum(totalNum);
                }
            }
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }


    public DmsStorageOutInRecord getDetails(Long id) {
        DmsStorageOutInRecord outInStorageRecord=dmsStorageOutInRecordMapper.getDetails(id);
        return outInStorageRecord;
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(DmsStorageOutInRecord outInStorageRecord) {
        outInStorageRecord.setRemoveFlag(0);
        dmsStorageOutInRecordMapper.insert(outInStorageRecord);
    }
}