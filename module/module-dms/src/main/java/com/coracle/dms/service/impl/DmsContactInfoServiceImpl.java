package com.coracle.dms.service.impl;

import java.util.List;

import com.coracle.dms.dao.mybatis.DmsContactInfoMapper;
import com.coracle.dms.po.DmsContactInfo;
import com.coracle.dms.service.DmsContactInfoService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsContactInfoServiceImpl extends BaseServiceImpl<DmsContactInfo> implements DmsContactInfoService {
    private static final Logger logger = Logger.getLogger(DmsContactInfoServiceImpl.class);

    @Autowired
    private DmsContactInfoMapper dmsContactInfoMapper;

    @Override
    public IMybatisDao<DmsContactInfo> getBaseDao() {
        return dmsContactInfoMapper;
    }

	@Override
	public void batchInsert(List<DmsContactInfo> list) {
		dmsContactInfoMapper.batchInsert(list);
	}

	@Override
	public List<DmsContactInfo> findByCondition(DmsContactInfo search) {
		// TODO Auto-generated method stub
		return dmsContactInfoMapper.selectByCondition(search);
	}

	/**
	 * 新增/修改联系信息
	 * @param contactInfoList
	 */
	@Override
	public void insertOrUpdate(List<DmsContactInfo> contactInfoList) {
        List<DmsContactInfo> insertList = Lists.newArrayList();
		for (DmsContactInfo contactInfo : contactInfoList) {
			Long id = contactInfo.getId();
    		if (id == null || id == 0) {  //新增
                insertList.add(contactInfo);
			} else {  //修改
                dmsContactInfoMapper.updateByPrimaryKeySelective(contactInfo);
			}
		}
		if (!insertList.isEmpty()) {
			dmsContactInfoMapper.batchInsert(insertList);
		}
	}
	@Override
	public void deleteByRelated(DmsContactInfo entity) {
		 this.dmsContactInfoMapper.deleteByRelated(entity);
	}
}