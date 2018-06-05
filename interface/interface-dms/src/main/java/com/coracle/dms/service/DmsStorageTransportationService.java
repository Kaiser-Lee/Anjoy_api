package com.coracle.dms.service;

import com.coracle.dms.po.DmsStorageTransportation;
import com.coracle.dms.vo.DmsStorageTransportationVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsStorageTransportationService extends IBaseService<DmsStorageTransportation> {

    /**
     * 分页查询在途库存列表
     * @param search
     * @return
     */
    PageHelper.Page<DmsStorageTransportationVo> selectForListPage(DmsStorageTransportationVo search);

    /**
     * 新增在途库存
     * @param param
     */
    void create(DmsStorageTransportation param,UserSession session);

    /**
     * 修改在途库存
     * @param id
     */
    void updateById(Long id);
    
    /**
     * 根据条件修改状态
     * @param param
     */
    void updateTrans(DmsStorageTransportation param);
}