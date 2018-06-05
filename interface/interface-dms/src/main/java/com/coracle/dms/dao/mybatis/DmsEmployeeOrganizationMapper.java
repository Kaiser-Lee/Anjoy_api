/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsEmployeeOrganization;
import com.coracle.dms.vo.DmsEmployeeOrganizationVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsEmployeeOrganizationMapper extends IMybatisDao<DmsEmployeeOrganization> {

    /**
     * 批量插入组织-区域管理信息
     * @param employeeOrganizationList
     */
    void batchInsert(List<DmsEmployeeOrganization> employeeOrganizationList);

    /**
     * 根据id列表批量逻辑删除
     * @param ids
     */
    void batchRemove(List<Long> ids);

    /**
     * 根据员工id获取员工-组织管理信息id列表
     * @param employeeId
     * @return
     */
    List<Long> selectIdListByEmployeeId(Long employeeId);

    /**
     * 根据员工id获取员工组织关系信息列表
     * @param employeeId
     * @return
     */
    List<DmsEmployeeOrganizationVo> selectVoByEmployeeId(Long employeeId);

    /**
     * 将员工的所有主组织(除了id为#{id}的这一条)改为非主组织
     * @param employeeOrganization
     */
    void updateNotMajorByCondition(DmsEmployeeOrganization employeeOrganization);

    /**
     * 将组织的所有主负责人(除了id为#{id}的这一条)改为非主负责人
     * @param employeeOrganization
     */
    void updateNotChargeByCondition(DmsEmployeeOrganization employeeOrganization);
}
