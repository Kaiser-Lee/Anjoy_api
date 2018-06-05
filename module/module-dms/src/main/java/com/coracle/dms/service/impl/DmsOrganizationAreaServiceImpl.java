package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsOrganizationAreaMapper;
import com.coracle.dms.po.DmsOrganizationArea;
import com.coracle.dms.service.DmsOrganizationAreaService;
import com.coracle.dms.vo.DmsOrganizationVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsOrganizationAreaServiceImpl extends BaseServiceImpl<DmsOrganizationArea> implements DmsOrganizationAreaService {
    private static final Logger logger = Logger.getLogger(DmsOrganizationAreaServiceImpl.class);

    @Autowired
    private DmsOrganizationAreaMapper dmsOrganizationAreaMapper;

    @Override
    public IMybatisDao<DmsOrganizationArea> getBaseDao() {
        return dmsOrganizationAreaMapper;
    }

    /**
     * 新增/修改组织-区域管理信息
     * @param organizationVo
     */
    @Override
    public void insertOrUpdate(DmsOrganizationVo organizationVo) {
        Long organizationId = organizationVo.getId();
        /* 操作人 */
        Long userId = organizationVo.getLastUpdatedBy() == null ? organizationVo.getCreatedBy() : organizationVo.getLastUpdatedBy();

        /* 获取数据库中的组织-区域管理信息 */
        List<Long> existAreaIdList = dmsOrganizationAreaMapper.selectAreaIdListByOrganizationId(organizationId);
        /* 获取用户提交的管理区域参数 */
        List<Long> paramAreaIdList = organizationVo.getAreaIdList();  //用户提交的管理区域id列表参数

        List<Long> insertAreaIdList = Lists.newArrayList(paramAreaIdList);  //要新增到数据库的组织所管理的区域id列表
        List<Long> removeAreaIdList = Lists.newArrayList(existAreaIdList);  //要从数据库中删除的组织所管理的区域id列表
        insertAreaIdList.removeAll(existAreaIdList);  //参数 - 数据库中已保存的 = 要新增的数据
        removeAreaIdList.removeAll(paramAreaIdList);  //数据库中已保存的 - 参数 = 要删除的数据

        List<DmsOrganizationArea> insertOrgAreaList = Lists.newArrayList();
        for (Long areaId : insertAreaIdList) {
            DmsOrganizationArea entity = new DmsOrganizationArea();
            entity.setOrganizationId(organizationId);
            entity.setAreaId(areaId);
            entity.setCreatedBy(userId);
            entity.setCreatedDate(new Date());
            entity.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            insertOrgAreaList.add(entity);
        }
        if (!insertOrgAreaList.isEmpty()) {
            dmsOrganizationAreaMapper.batchInsert(insertOrgAreaList);  //批量新增组织-区域管理信息
        }

        if (!removeAreaIdList.isEmpty()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("organizationId", organizationId);
            map.put("areaIdList", removeAreaIdList);

            dmsOrganizationAreaMapper.removeByCondition(map);  //批量逻辑删除组织-区域管理信息
        }
    }

    @Override
    public void removeByOrganizationId(Long organizationId) {
        dmsOrganizationAreaMapper.removeByOrganizationId(organizationId);
    }
}