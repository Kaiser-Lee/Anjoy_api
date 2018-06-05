package com.coracle.dms.service;

import com.coracle.dms.po.DmsBackGoodsMaterial;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsBackGoodsMaterialService extends IBaseService<DmsBackGoodsMaterial> {
    PageHelper.Page<DmsBackGoodsMaterial> selectPageList(DmsBackGoodsMaterial dmsBackGoodsMaterial);
}