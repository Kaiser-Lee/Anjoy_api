package com.coracle.dms.service;

import com.coracle.dms.po.DmsStorageSaleOut;
import com.coracle.dms.vo.DmsStorageVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsStorageSaleOutService extends IBaseService<DmsStorageSaleOut> {

    /**
     * 分页查询列表
     * @param search
     * @return
     */
    PageHelper.Page<DmsStorageSaleOut> selectForListPage(DmsStorageSaleOut search);

    /**
     * 新增销售出库
     * @param search
     * @return
     */
    void insertTo(DmsStorageSaleOut search,UserSession userSession);
}