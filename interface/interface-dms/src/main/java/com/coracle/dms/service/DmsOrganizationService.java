package com.coracle.dms.service;

import com.coracle.dms.po.DmsOrganization;
import com.coracle.dms.vo.DmsOrganizationVo;
import com.coracle.yk.base.vo.TreeNodeVo;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsOrganizationService extends IBaseService<DmsOrganization> {

    /**
     * 新增/修改组织
     * @param organizationVo
     */
    void insertOrUpdate(DmsOrganizationVo organizationVo);

    /**
     * 组织详情
     * @param id
     * @return
     */
    DmsOrganizationVo detail(Long id);

    /**
     * 组织架构树形结构数据
     * @param id
     * @return
     */
    List<TreeNodeVo> tree(Long id);

    /**
     * 逻辑删除组织
     * @param organization
     */
    void remove(DmsOrganization organization);

    /**
     * 获取根品牌商，但新增组织时未做限制
     * @return
     */
    DmsOrganization getRootOrganization();

    Map<String, DmsOrganization> getOrganizationMap();

    void anjoySyn();
}