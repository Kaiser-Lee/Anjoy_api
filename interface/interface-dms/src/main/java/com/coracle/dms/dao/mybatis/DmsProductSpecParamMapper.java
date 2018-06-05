/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.po.DmsProductSpecParam;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsProductSpecParamMapper extends IMybatisDao<DmsProductSpecParam> {
	
	void deleteBySpecId(Long specId);
	
	List<DmsProductSpecParam> getSpecList(Long specId);
}