package com.coracle.dms.service;

import com.coracle.dms.dto.DmsRemarkDto;
import com.coracle.dms.po.DmsRemark;
import com.coracle.dms.vo.DmsRemarkVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsRemarkService extends IBaseService<DmsRemark> {
	/**
	 * 查询备注列表
	 * @param searchVo
	 * @return
	 */
	Page<DmsRemarkDto> selectForListPage(DmsRemarkDto paramDto);
	/**
	 * 创建备注信息
	 * @param paramVo
	 * @param userSession
	 */
	public void createRemark(DmsRemarkVo paramVo,UserSession userSession);
	
	void updateRemark(DmsRemarkVo paramVo);
	
	void deleteRemark(DmsRemarkVo paramVo);
}