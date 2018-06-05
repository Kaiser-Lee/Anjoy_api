/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsOrganizationArea;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;
import java.util.Map;

public interface DmsOrganizationAreaMapper extends IMybatisDao<DmsOrganizationArea> {

    /**
     * 批量插入组织-区域管理信息
     * @param dmsOrganizationAreaList
     */
    void batchInsert(List<DmsOrganizationArea> dmsOrganizationAreaList);

    /**
     * 根据组织id逻辑删除组织-区域管理关系
     * @param id
     */
    void removeByOrganizationId(Long id);

    /**
     * 根据条件逻辑删除组织-区域管理关系
     * @param param
     */
    void removeByCondition(Map<String, Object> param);

    /**
     * 根据组织id获取组织管理的区域id列表
     * @param organizationId
     * @return
     */
    List<Long> selectAreaIdListByOrganizationId(Long organizationId);
}