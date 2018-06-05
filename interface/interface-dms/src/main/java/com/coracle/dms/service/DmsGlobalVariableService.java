package com.coracle.dms.service;

import com.coracle.dms.po.DmsGlobalVariable;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsGlobalVariableService extends IBaseService<DmsGlobalVariable> {
    /***
     * 新增或者修改全局变量
     * @param dmsGlobalVariable
     * @param userSession
     */
    void saveOrUpdate(DmsGlobalVariable dmsGlobalVariable, UserSession userSession);

    /**
     * 删除全局变量，软删除
     * @param id
     * @param userSession
     */
    void delete(Long id, UserSession userSession);

    /**
     * 全局变量分页查询
     * @param dmsGlobalVariable
     * @return
     */
    PageHelper.Page<DmsGlobalVariable> getPageList(DmsGlobalVariable dmsGlobalVariable, UserSession userSession);
    
    /**
     * 根据参数key获取值.后续考虑加缓存处理
     * @param paramKey
     * @return
     */
    String queryValueByKey(String paramKey); 
}