/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsGlobalVariable;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsGlobalVariableMapper extends IMybatisDao<DmsGlobalVariable> {
    /**
     * 分页获取全局变量列表
     * @param dmsGlobalVariable
     * @return
     */
    List<DmsGlobalVariable> getPageList(DmsGlobalVariable dmsGlobalVariable);
    
    DmsGlobalVariable queryByKey(String paramKey);
}