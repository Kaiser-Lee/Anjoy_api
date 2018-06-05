package com.coracle.dms.service;

import com.coracle.dms.po.DmsOrganizationArea;
import com.coracle.dms.vo.DmsOrganizationVo;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsOrganizationAreaService extends IBaseService<DmsOrganizationArea> {

    void insertOrUpdate(DmsOrganizationVo organizationVo);

    void removeByOrganizationId(Long organizationId);
}