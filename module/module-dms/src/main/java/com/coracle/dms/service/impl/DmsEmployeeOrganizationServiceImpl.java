package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsEmployeeOrganizationMapper;
import com.coracle.dms.po.DmsEmployeeOrganization;
import com.coracle.dms.service.DmsEmployeeOrganizationService;
import com.coracle.dms.vo.DmsEmployeeOrganizationVo;
import com.coracle.dms.vo.DmsEmployeeVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DmsEmployeeOrganizationServiceImpl extends BaseServiceImpl<DmsEmployeeOrganization> implements DmsEmployeeOrganizationService {
    private static final Logger logger = Logger.getLogger(DmsEmployeeOrganizationServiceImpl.class);

    @Autowired
    private DmsEmployeeOrganizationMapper dmsEmployeeOrganizationMapper;

    @Override
    public IMybatisDao<DmsEmployeeOrganization> getBaseDao() {
        return dmsEmployeeOrganizationMapper;
    }

    @Override
    public void insertOrUpdate(DmsEmployeeVo employeeVo) {
        List<DmsEmployeeOrganizationVo> employeeOrganizationList = employeeVo.getEmployeeOrganizationList();
        checkParam(employeeOrganizationList);  //参数检查

        Long employeeId = employeeVo.getId();
        Long userId = employeeVo.getLastUpdatedBy() == null ? employeeVo.getCreatedBy() : employeeVo.getLastUpdatedBy();
        Date now = new Date();

        List<Long> existIdList = dmsEmployeeOrganizationMapper.selectIdListByEmployeeId(employeeId);  //数据库中的员工-组织管理关系信息的id列表

        //要删除的数据的id列表，初始化为数据库中存在的id
        //id：数据库中存在，用户提交的参数中不存在的，则为要删除的数据
        List<Long> removeIdList = Lists.newArrayList(existIdList);

        for (DmsEmployeeOrganization empOrg : employeeOrganizationList) {  //用户提交的员工-组织管理关系参数列表
            Long id = empOrg.getId();
            if (id == null) {  //id为空，则为要新增的数据
                empOrg.setEmployeeId(employeeId);
                empOrg.setCreatedDate(now);
                empOrg.setCreatedBy(userId);
                empOrg.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                dmsEmployeeOrganizationMapper.insert(empOrg);
            } else {  //修改的数据
                if (existIdList.contains(empOrg.getId())) {  //数据库中存在该id
                    empOrg.setLastUpdatedDate(now);
                    empOrg.setLastUpdatedBy(userId);
                    dmsEmployeeOrganizationMapper.updateByPrimaryKeySelective(empOrg);
                    removeIdList.remove(id);  //如果数据库中存在该id，在用户提交的参数里也有，则从removeIdList中移除(代表这条数据不需要删除)
                } else {  //数据库中不存在该id，但用户提交的参数里有，则抛出异常
                    throw new ServiceException("员工-组织管理关系参数异常，数据库中不存在id为: " + id + "的员工-组织管理关系记录!");
                }
            }

            DmsEmployeeOrganization empOrgParam = new DmsEmployeeOrganization();
            empOrgParam.setId(empOrg.getId());
            //设置为组织主负责人，则需要将该组织的所有其他主负责人标识去掉
            if (empOrg.getIsCharge() == 1) {
                empOrgParam.setOrganizationId(empOrg.getOrganizationId());
                dmsEmployeeOrganizationMapper.updateNotChargeByCondition(empOrgParam);
            }

            //设置为员工主组织，则需要将该员工的所有其他主组织标识去掉
            if (empOrg.getIsMajor() == 1) {
                empOrgParam.setEmployeeId(employeeId);
                dmsEmployeeOrganizationMapper.updateNotMajorByCondition(empOrgParam);
            }
        }
        if (!removeIdList.isEmpty()) {
            dmsEmployeeOrganizationMapper.batchRemove(removeIdList);  //根据id批量逻辑删除
        }
    }

    /**
     * 检验参数
     * @param employeeOrganizationList
     */
    private void checkParam(List<DmsEmployeeOrganizationVo> employeeOrganizationList) {
        Integer majorCount = 0;
        for (DmsEmployeeOrganization employeeOrganization : employeeOrganizationList) {
            if (employeeOrganization.getIsMajor() == 1) {
                ++majorCount;
            }
        }

        /* 员工的主组织，必须有且只能有一个 */
        if (majorCount == 0) {
            throw new ServiceException("必须为员工选择一个主组织");
        } else if (majorCount > 1) {
            throw new ServiceException("一个员工只能有一个主组织");
        }
    }
}