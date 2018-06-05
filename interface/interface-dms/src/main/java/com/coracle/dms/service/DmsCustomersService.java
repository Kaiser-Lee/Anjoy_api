package com.coracle.dms.service;

import com.coracle.dms.po.DmsCustomers;
import com.coracle.dms.vo.DmsCustomersVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsCustomersService extends IBaseService<DmsCustomers> {

    /***
     * 修改客户信息
     * @param dmsCustomersVo
     */
    void updateCustomer(DmsCustomersVo dmsCustomersVo, long userId);

    /**
     * 分页获取客户信息列表
     * @param dmsCustomersVo
     * @return
     */
    PageHelper.Page<DmsCustomersVo> selectForPage(DmsCustomersVo dmsCustomersVo);

    /***
     * 新增客户信息
     * @param dmsCustomersVo
     */
    Long insertCustomer(DmsCustomersVo dmsCustomersVo, long userId);

    /**
     * 获取客户vo
     * @param id
     * @return
     */
    DmsCustomersVo selectVoByPrimaryKey(Long id);
}