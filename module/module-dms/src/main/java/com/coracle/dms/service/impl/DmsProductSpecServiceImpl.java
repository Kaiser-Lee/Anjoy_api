package com.coracle.dms.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsProductSpecMapper;
import com.coracle.dms.dao.mybatis.DmsProductSpecParamMapper;
import com.coracle.dms.po.DmsProductSpec;
import com.coracle.dms.po.DmsProductSpecParam;
import com.coracle.dms.service.DmsProductSpecService;
import com.coracle.dms.vo.DmsProductSpecVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.StringUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.components.StringUtils;

@Service
public class DmsProductSpecServiceImpl extends BaseServiceImpl<DmsProductSpec> implements DmsProductSpecService {
    @Autowired
    private DmsProductSpecMapper dmsProductSpecMapper;
    
    @Autowired
    private DmsProductSpecParamMapper dmsProductSpecParamMapper;

    @Override
    public IMybatisDao<DmsProductSpec> getBaseDao() {
        return dmsProductSpecMapper;
    }

	@SuppressWarnings("unchecked")
	@Override
	public Page<DmsProductSpecVo> selectForListPage(DmsProductSpecVo category) {
		try {
            PageHelper.startPage(category.getP(), category.getS());
            dmsProductSpecMapper.getPageList(category);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            throw new ServiceException("产品规格分页查询异常--->>>");
        }
	}
	@Override
	public DmsProductSpecVo detail(Long id) {
		return this.dmsProductSpecMapper.selectById(id);
	}
	@Override
	public void create(DmsProductSpecVo vo) {
		this.checkParam(vo);
		this.dmsProductSpecMapper.insert(vo);
		// 保存规格值
		List<DmsProductSpecParam> specValueList = vo.getSpecValueList();
		if (StringUtil.isListNotEmpty(specValueList)) {
			for (DmsProductSpecParam specValue : specValueList) {
				if (specValue != null) {
					specValue.setProductSpecId(vo.getId());
					specValue.setCreatedBy(vo.getCreatedBy());
					specValue.setCreatedDate(new Date());
					specValue.setRemoveFlag(0);
					this.dmsProductSpecParamMapper.insert(specValue);
				}
			}
		}
	}
	
	/**
     * 统一检验参数
     * @param spec
     */
	private void checkParam(DmsProductSpecVo spec) {
		if (spec == null) {
			throw new ServiceException("参数异常");
		}
		if (StringUtils.isBlank(spec.getName())) {
			throw new ServiceException("分类名称不能为空");
		}
		if (spec.getActive() == null) {
			throw new ServiceException("有效性不能为空");
		}
	}

	@Override
	public void update(DmsProductSpecVo vo) {
		this.checkParam(vo);
		if (vo == null || vo.getId() == null) {
			throw new ServiceException("参数异常");
		}
		DmsProductSpec entity = this.dmsProductSpecMapper.selectByPrimaryKey(vo.getId());
		if (entity == null) {
			throw new ServiceException("数据库获取分类为空，不能修改！");
		}
		// 保存规格数据
		this.dmsProductSpecMapper.updateByPrimaryKey(vo);
		// 保存规格值
		List<DmsProductSpecParam> specValueList = vo.getSpecValueList();
		this.dmsProductSpecParamMapper.deleteBySpecId(entity.getId());
		if (StringUtil.isListNotEmpty(specValueList)) {
			for (DmsProductSpecParam specValue : specValueList) {
				if (specValue != null) {
					specValue.setProductSpecId(vo.getId());
					specValue.setCreatedBy(vo.getLastUpdatedBy());
					specValue.setCreatedDate(new Date());
					specValue.setRemoveFlag(0);
					this.dmsProductSpecParamMapper.insert(specValue);
				}
			}
		}
	}

	@Override
	public void delete(DmsProductSpec spec) {
		if (spec == null || spec.getId() == null) {
			throw new ServiceException("参数错误");
		}
		DmsProductSpec entity = this.dmsProductSpecMapper.selectByPrimaryKey(spec.getId());
		if (entity == null) {
			throw new ServiceException("数据库获取数据为空！");
		}
		// 删除规格.将要删除规格下面所有规格的值
		this.dmsProductSpecMapper.deleteByIdSoft(spec.getId());
		this.dmsProductSpecParamMapper.deleteBySpecId(spec.getId());
	}

	@Override
	public void updateActive(DmsProductSpec spec) {
		if (spec == null || spec.getId() == null) {
			throw new ServiceException("参数错误");
		}
		DmsProductSpec entity = this.dmsProductSpecMapper.selectByPrimaryKey(spec.getId());
		if (entity == null) {
			throw new ServiceException("数据库获取数据为空！");
		}
		entity.setActive(DmsModuleEnums.ACTIVE_STATUS.DIABLE.getType());
		entity.setLastUpdatedBy(spec.getLastUpdatedBy());
		entity.setLastUpdatedDate(new Date());
		this.dmsProductSpecMapper.updateInvalidSpec(entity);
	}
	@Override
	public List<DmsProductSpecVo> selectSpecList(DmsProductSpecVo spec) {
		return this.dmsProductSpecMapper.selectSpecList(spec);
	}
}