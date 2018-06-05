package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsRoleMapper;
import com.coracle.dms.po.DmsRole;
import com.coracle.dms.service.DmsRoleService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsRoleServiceImpl extends BaseServiceImpl<DmsRole> implements DmsRoleService {
    private static final Logger logger = Logger.getLogger(DmsRoleServiceImpl.class);

    @Autowired
    private DmsRoleMapper dmsRoleMapper;

    @Override
    public IMybatisDao<DmsRole> getBaseDao() {
        return dmsRoleMapper;
    }

    @Override
    public List<DmsRole> selectByCondition(DmsRole role) {
        return dmsRoleMapper.selectByCondition(role);
    }

    @Override
    public PageHelper.Page<DmsRole> pageList(DmsRole role) {
        try {
            PageHelper.startPage(role.getP(), role.getS());
            dmsRoleMapper.selectByCondition(role);
        } catch (Exception e) {
            logger.error("角色分页查询异常!", e);
            throw new ServiceException("角色分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }
}