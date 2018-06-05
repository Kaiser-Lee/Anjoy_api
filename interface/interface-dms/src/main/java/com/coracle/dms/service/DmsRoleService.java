package com.coracle.dms.service;

import com.coracle.dms.po.DmsRole;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsRoleService extends IBaseService<DmsRole> {
    PageHelper.Page<DmsRole> pageList(DmsRole role);

    List<DmsRole> selectByCondition(DmsRole role);
}