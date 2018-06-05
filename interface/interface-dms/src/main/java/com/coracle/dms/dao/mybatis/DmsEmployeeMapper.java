/**
 * create by hcs
 *
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsEmployee;
import com.coracle.dms.vo.DmsEmployeeVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;


public interface DmsEmployeeMapper extends IMybatisDao<DmsEmployee> {

    /**
     * 根据条件获取vo列表
     * @param employeeVo
     * @return
     */
    List<DmsEmployeeVo> selectVoByCondition(DmsEmployeeVo employeeVo);

    /**
     * 根据主键id获取vo对象
     * @param id
     * @return
     */
    DmsEmployeeVo selectVoByPrimaryKey(Long id);

    /**
     * 根据id列表批量逻辑删除
     * @param ids
     */
    void batchRemove(List<Long> ids);
}