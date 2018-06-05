package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsUserRoleMapper;
import com.coracle.dms.po.DmsUserRole;
import com.coracle.dms.service.DmsUserRoleService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DmsUserRoleServiceImpl extends BaseServiceImpl<DmsUserRole> implements DmsUserRoleService {
    private static final Logger logger = Logger.getLogger(DmsUserRoleServiceImpl.class);

    @Autowired
    private DmsUserRoleMapper dmsUserRoleMapper;

    @Override
    public IMybatisDao<DmsUserRole> getBaseDao() {
        return dmsUserRoleMapper;
    }

    /**
     * 新增账号-角色关联信息
     * @param userId
     * @param roleId
     * @param createdBy
     */
    @Override
    public void createUserRole(Long userId, Long roleId, Long createdBy) {
        DmsUserRole userRole = new DmsUserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setCreatedBy(createdBy);
        userRole.setCreatedDate(new Date());
        userRole.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsUserRoleMapper.insert(userRole);
    }


    @Override
    public void insertOrUpdateUserRole(Long userId, Long roleId, Long createdBy){
        DmsUserRole userRole = new DmsUserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        List<DmsUserRole> roleList=dmsUserRoleMapper.selectByCondition(userRole);
        if(!BlankUtil.isEmpty(roleList)){
            for(DmsUserRole role:roleList){
                dmsUserRoleMapper.deleteByPrimaryKey(role.getId());
            }
        }
        userRole.setCreatedBy(createdBy);
        userRole.setCreatedDate(new Date());
        userRole.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsUserRoleMapper.insert(userRole);
    }
}