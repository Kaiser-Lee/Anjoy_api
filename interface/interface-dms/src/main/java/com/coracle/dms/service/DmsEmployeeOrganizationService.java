package com.coracle.dms.service;

import com.coracle.dms.po.DmsEmployeeOrganization;
import com.coracle.dms.vo.DmsEmployeeVo;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsEmployeeOrganizationService extends IBaseService<DmsEmployeeOrganization> {

    void insertOrUpdate(DmsEmployeeVo employeeVo);
}