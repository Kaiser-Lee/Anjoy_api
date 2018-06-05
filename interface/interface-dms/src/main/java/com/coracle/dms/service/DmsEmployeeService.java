package com.coracle.dms.service;

import com.coracle.dms.po.DmsEmployee;
import com.coracle.dms.vo.DmsEmployeeVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface DmsEmployeeService extends IBaseService<DmsEmployee> {

    /**
     * 新增/修改员工
     * @param employeeVo
     */
    void insertOrUpdate(DmsEmployeeVo employeeVo, UserSession session);

    /**
     * 员工列表（分页）
     * @param employeeVo
     * @return
     */
    PageHelper.Page<DmsEmployeeVo> pageList(DmsEmployeeVo employeeVo);

    /**
     * 员工详情
     * @param id
     * @return
     */
    DmsEmployeeVo detail(Long id);

    void batchRemove(List<Long> ids);

    Map<String, DmsEmployeeVo> getEmployeeMap();

    void anjoySyn(UserSession session);

    @Transactional(readOnly = false)
    void anjoySyn();

    void insertOrUpdate(DmsEmployeeVo employeeVo);
}