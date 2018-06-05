/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.po.DmsProductSpec;
import com.coracle.dms.vo.DmsProductSpecVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsProductSpecMapper extends IMybatisDao<DmsProductSpec> {
	
	void updateInvalidSpec(DmsProductSpec spec);
	
	void deleteByIdSoft(Object id);
	
	List<DmsProductSpecVo> getPageList(DmsProductSpecVo paramVo);
	
	DmsProductSpecVo selectById(Long id);
	
	List<DmsProductSpecVo> selectSpecList(DmsProductSpecVo spec);
}