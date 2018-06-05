package com.coracle.dms.service.impl;

import java.util.Date;
import java.util.List;

import com.coracle.dms.service.DmsStoreService;
import com.coracle.dms.vo.DmsStorageTransportationVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coracle.dms.dao.mybatis.DmsStorageTransportationMapper;
import com.coracle.dms.po.DmsProduct;
import com.coracle.dms.po.DmsStorageTransportation;
import com.coracle.dms.service.DmsProductService;
import com.coracle.dms.service.DmsStorageTransportationService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;


@Service
public class DmsStorageTransportationServiceImpl extends BaseServiceImpl<DmsStorageTransportation> implements DmsStorageTransportationService {
    private static final Logger logger = Logger.getLogger(DmsStorageTransportationServiceImpl.class);

    @Autowired
    private DmsStorageTransportationMapper dmsStorageTransportationMapper;
    @Autowired
    private DmsProductService dmsProductService;

    @Autowired
    private DmsStoreService dmsStoreService;

    @Override
    public IMybatisDao<DmsStorageTransportation> getBaseDao() {
        return dmsStorageTransportationMapper;
    }

    @Override
    public PageHelper.Page<DmsStorageTransportationVo> selectForListPage(DmsStorageTransportationVo search) {
        try {
            PageHelper.startPage(search.getP(), search.getS());

            Long orgType = search.getOrgType();
            if (orgType == 1) {  //品牌商
                search.setOrgType(1L);
            } else if (orgType == 2) {  //渠道商
                Long channelId = search.getOrgId();
                search.setChannelId(channelId);
                List<Long> storeIdList = dmsStoreService.selectStoreIdListByChannelId(channelId);
                search.setStoreIdList(storeIdList);
                search.setOrgType(2L);
            }
            dmsStorageTransportationMapper.getPageList(search);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            logger.error("分页查询在途库存列表失败！", e);
            throw new ServiceException("分页查询在途库存列表异常--->>>");
        }
    }

    @Override
    public void create(DmsStorageTransportation param,UserSession session) {
        this.checkParam(param);
        DmsProduct product = this.dmsProductService.selectByPrimaryKey(param.getProductId());
		if(product == null){
			 throw new ServiceException("产品不存在！");
		}
		param.setSendTime(new Date());
		param.setCreatedBy(session.getId());
		param.setCreatedDate(new Date());
		param.setRemoveFlag(0);
        dmsStorageTransportationMapper.insert(param);
    }
    
    @Override
    public void updateTrans(DmsStorageTransportation param) {
    	if (param == null || param.getRelationId() == null || param.getRelationType() == null) {
            throw new ServiceException("参数异常");
        }
    	DmsStorageTransportation search = new DmsStorageTransportation();
    	search.setRelationId(param.getRelationId());
    	search.setRelationType(param.getRelationType());
    	List<DmsStorageTransportation> transList = this.dmsStorageTransportationMapper.selectByCondition(search);
    	if(BlankUtil.isEmpty(transList)){
    		 throw new ServiceException("获取在途数据不存在");
    	}
    	DmsStorageTransportation entity = transList.get(0);
    	this.dmsStorageTransportationMapper.updateById(entity.getId());
    }
    @Override
    public void updateById(Long id) {

        dmsStorageTransportationMapper.updateById(id);
    }

    private void checkParam(DmsStorageTransportation param) {
        if (param == null || param.getRelationId() == null || param.getRelationType() == null) {
            throw new ServiceException("参数异常");
        }
        if (param.getTransportationNum() == null) {
            throw new ServiceException("数量不能为空");
        }
        if (param.getSendStorage() == null) {
            throw new ServiceException("发出仓库不能为空");
        }
        if (param.getProductId() == null || param.getProductId() == 0) {
            throw new ServiceException("产品不能为空");
        }
    }
}