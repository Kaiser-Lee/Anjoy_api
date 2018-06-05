/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.dto.DmsRemarkDto;
import com.coracle.dms.po.DmsRemark;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsRemarkMapper extends IMybatisDao<DmsRemark> {
	
	List<DmsRemarkDto> getPageList(DmsRemarkDto searchDto);
}