package com.coracle.dms.service;

import com.coracle.dms.po.DmsUserRole;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsUserRoleService extends IBaseService<DmsUserRole> {
    void createUserRole(Long userId, Long roleId, Long createdBy);

    void insertOrUpdateUserRole(Long userId, Long roleId, Long createdBy);
}