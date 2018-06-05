package com.coracle.dms.service;

import com.coracle.dms.po.DmsStorageBill;
import com.coracle.dms.vo.DmsStorageBillVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsStorageBillService extends IBaseService<DmsStorageBill> {

    /**
     * 分页查询入库单列表
     * @param search
     * @return
     */
    PageHelper.Page<DmsStorageBillVo> selectForListPage(DmsStorageBillVo search);

    /**
     * 新增入库单
     * @param paramVo
     */
    void create(DmsStorageBillVo paramVo,UserSession userSession);

    /**
     * 修改入库单
     * @param paramVo
     */
    void update(DmsStorageBillVo paramVo);

}