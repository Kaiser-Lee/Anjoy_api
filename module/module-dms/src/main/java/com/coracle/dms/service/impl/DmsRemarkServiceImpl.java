package com.coracle.dms.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coracle.dms.dao.mybatis.DmsRemarkMapper;
import com.coracle.dms.dao.mybatis.DmsRemarkRelationMapper;
import com.coracle.dms.dto.DmsRemarkDto;
import com.coracle.dms.po.DmsRemark;
import com.coracle.dms.po.DmsRemarkRelation;
import com.coracle.dms.service.DmsRemarkService;
import com.coracle.dms.vo.DmsRemarkVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;

/**
 * 备注相关业务层
 * @author tanyb
 *
 */
@Service
public class DmsRemarkServiceImpl extends BaseServiceImpl<DmsRemark> implements DmsRemarkService {
    @Autowired
    private DmsRemarkMapper dmsRemarkMapper;
    @Autowired
    private DmsRemarkRelationMapper dmsRemarkRelationMapper;

    @Override
    public IMybatisDao<DmsRemark> getBaseDao() {
        return dmsRemarkMapper;
    }
    
    /**
     * 渠道分页查询
     * @param searchVo 分页查询条件
     */
    @SuppressWarnings("unchecked")
	@Override
	public Page<DmsRemarkDto> selectForListPage(DmsRemarkDto searchDto) {
		try {
            PageHelper.startPage(searchDto.getP(), searchDto.getS());
            this.dmsRemarkMapper.getPageList(searchDto);
            return PageHelper.endPage();
        } catch (Exception e) {
        	e.printStackTrace();
            PageHelper.endPage();
            throw new ServiceException("备注分页查询异常--->>>");
        }
	}
    
    @Override
    public void createRemark(DmsRemarkVo paramVo,UserSession userSession) {
    	if (paramVo == null || paramVo.getRelatedTableType() == null || paramVo.getRelatedTableId() == null) {
			throw new ServiceException("参数异常");
		}
    	this.dmsRemarkMapper.insert(paramVo);
    	Long remarkId = paramVo.getId();
    	DmsRemarkRelation remarkRel = new DmsRemarkRelation();
    	remarkRel.setRemarkId(remarkId);
    	remarkRel.setRelatedTableType(paramVo.getRelatedTableType());
    	remarkRel.setRelatedTableId(paramVo.getRelatedTableId());
    	remarkRel.setCreatedBy(paramVo.getCreatedBy() != null ? paramVo.getCreatedBy() : userSession.getId());
    	remarkRel.setCreatedDate(new Date());
    	remarkRel.setRemoveFlag(0);
    	this.dmsRemarkRelationMapper.insert(remarkRel);
    	
    }
	@Override
	public void updateRemark(DmsRemarkVo paramVo) {
		if (paramVo == null || paramVo.getId() == null) {
			throw new ServiceException("参数异常");
		}
		DmsRemark entity = this.dmsRemarkMapper.selectByPrimaryKey(paramVo.getId());
        if(entity == null){  
        	throw new ServiceException("数据库获取数据不存在！");
        }
		this.dmsRemarkMapper.updateByPrimaryKeySelective(paramVo);
	}
	@Override
	public void deleteRemark(DmsRemarkVo paramVo) {
		if (paramVo == null || paramVo.getId() == null) {
			throw new ServiceException("参数异常");
		}
		DmsRemarkRelation relation = this.dmsRemarkRelationMapper.selectByPrimaryKey(paramVo.getId());
		if(relation == null){
			throw new ServiceException("数据库获取数据不存在！");
		}
		relation.setId(paramVo.getId());
		relation.setRemoveFlag(1);
		this.dmsRemarkRelationMapper.updateByPrimaryKeySelective(relation);
		
		DmsRemark remark = new DmsRemark();
		remark.setId(relation.getRemarkId());
		remark.setRemoveFlag(1);
		this.dmsRemarkMapper.updateByPrimaryKeySelective(remark);
	}
}