package com.coracle.dms.service;

import java.util.List;

import com.coracle.dms.po.DmsProductSpec;
import com.coracle.dms.vo.DmsProductSpecVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsProductSpecService extends IBaseService<DmsProductSpec> {
	/**
	 * 分页查询规格数据
	 * @param spec
	 * @return
	 */
	PageHelper.Page<DmsProductSpecVo> selectForListPage(DmsProductSpecVo spec);
	/**
	 * 查询规格详情
	 * @param id
	 * @return
	 */
	DmsProductSpecVo detail(Long id);
	/**
	 * 创建产品规格
	 * @param vo
	 */
	void create(DmsProductSpecVo vo);
	/**
	 * 修改产品规格
	 * @param vo
	 */
	void update(DmsProductSpecVo vo);
	
	/**
     * 删除产品规格
     * @param category
     */
    void delete(DmsProductSpec spec);
    /**
     * 修改产品规格为失效
     * @param category
     */
    void updateActive(DmsProductSpec spec);
    
    List<DmsProductSpecVo> selectSpecList(DmsProductSpecVo spec);
}