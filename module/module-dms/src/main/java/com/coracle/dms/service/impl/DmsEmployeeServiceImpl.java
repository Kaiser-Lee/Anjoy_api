package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsEmployeeMapper;
import com.coracle.dms.po.DmsContactInfo;
import com.coracle.dms.po.DmsEmployee;
import com.coracle.dms.po.DmsOrganization;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsEmployeeOrganizationVo;
import com.coracle.dms.vo.DmsEmployeeVo;
import com.coracle.dms.vo.DmsUserVo;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiruo.medbid.components.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsEmployeeServiceImpl extends BaseServiceImpl<DmsEmployee> implements DmsEmployeeService {
    private static final Logger logger = Logger.getLogger(DmsEmployeeServiceImpl.class);
    @Autowired
    private DmsEmployeeMapper dmsEmployeeMapper;

    @Autowired
    private DmsUserService dmsUserService;
    @Autowired
    private DmsContactInfoService dmsContactInfoService;

    @Autowired
    private DmsEmployeeOrganizationService dmsEmployeeOrganizationService;

    @Autowired
    private DmsOrganizationService dmsOrganizationService;

    @Override
    public IMybatisDao<DmsEmployee> getBaseDao() {
        return dmsEmployeeMapper;
    }

    @Autowired
    private DmsUserRoleService dmsUserRoleService;

    @Override
    public void insertOrUpdate(DmsEmployeeVo employeeVo, UserSession session) {
        checkParam(employeeVo);

        boolean isInsert = employeeVo.getId() == null ? true : false;
        Long userId = employeeVo.getId() == null ? employeeVo.getCreatedBy() : employeeVo.getLastUpdatedBy();
        Date now = new Date();

        if (isInsert) {  //新增操作
            /* 1.保存员工信息 */
            dmsEmployeeMapper.insert(employeeVo);
            /* 为员工新建账号 */
            DmsUserVo user = new DmsUserVo();
            user.setAccount(employeeVo.getAccount());
            user.setName(employeeVo.getName());
            user.setPlainPassword(employeeVo.getPlainPassword());
            user.setSource(DmsModuleEnums.ACCOUNT_SOURCE_TYPE.EMPLOYEE.getType());
            user.setStaffId(employeeVo.getId());
            user.setRoleId(employeeVo.getRoleId());
            user.setCreatedBy(userId);  //账号的创建人设置为员工的创建人

            DmsUser entity = dmsUserService.create(user, session);  //调用新增账号接口
            userId=entity.getId();
            employeeVo.setUserId(entity.getId());
            dmsEmployeeMapper.updateByPrimaryKeySelective(employeeVo);
        } else {  //修改操作
            Long id = employeeVo.getId();
            DmsEmployee employee = dmsEmployeeMapper.selectVoByPrimaryKey(id);
            if (employee == null) {
                throw new ServiceException("数据库中不存在id为: " + id + "的员工数据");
            }
            userId=employee.getUserId();
            if (!StringUtils.isBlank(employeeVo.getPlainPassword())) {  //如果用户传入明文密码，则修改密码
                dmsUserService.updatePassword(employeeVo.getUserId(), employeeVo.getPlainPassword(), session);
            }
            dmsEmployeeMapper.updateByPrimaryKeySelective(employeeVo);

            /* 4.员工与角色关系 */
            if (employeeVo.getRoleId() != null) {
                dmsUserRoleService.insertOrUpdateUserRole(userId, employeeVo.getRoleId(), session.getId());
            }
        }

        /* 2.新增/修改联系信息 */
        List<DmsContactInfo> contactInfoList = employeeVo.getContactInfoList();
        for (DmsContactInfo contactInfo : contactInfoList) {
            Long id = contactInfo.getId();
            if (id == null || id == 0) {  //id为0或空的时候表示新增的联系信息
                contactInfo.setCreatedBy(userId);
                contactInfo.setCreatedDate(now);
            } else {  //修改联系信息
                contactInfo.setLastUpdatedBy(userId);
                contactInfo.setLastUpdatedDate(now);
            }
            contactInfo.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.EMPLOYEE.getType());
            contactInfo.setRelatedTableId(employeeVo.getId());
            contactInfo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        }
        dmsContactInfoService.insertOrUpdate(contactInfoList);

        /* 3.新增/修改员工-组织关联信息 */
        List<DmsEmployeeOrganizationVo> employeeOrganizationList = employeeVo.getEmployeeOrganizationList();
        if (employeeOrganizationList != null && !employeeOrganizationList.isEmpty()) {
            dmsEmployeeOrganizationService.insertOrUpdate(employeeVo);
        }



    }

    /**
     * 员工列表（分页）
     *
     * @param employeeVo
     * @return
     */
    @Override
    public PageHelper.Page<DmsEmployeeVo> pageList(DmsEmployeeVo employeeVo) {
        try {
            PageHelper.startPage(employeeVo.getP(), employeeVo.getS());
            dmsEmployeeMapper.selectVoByCondition(employeeVo);
        } catch (Exception e) {
            logger.error("员工分页查询异常!", e);
            throw new ServiceException("员工分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 员工详情
     *
     * @param id
     * @return
     */
    @Override
    public DmsEmployeeVo detail(Long id) {
        return dmsEmployeeMapper.selectVoByPrimaryKey(id);
    }

    private void checkParam(DmsEmployeeVo employee) {
        if (employee == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(employee.getName())) {
            throw new ServiceException("姓名不能为空!");
        }
        if (StringUtils.isBlank(employee.getAccount())) {
            throw new ServiceException("账号不能为空!");
        }
    }

    /**
     * 批量逻辑删除
     *
     * @param ids
     */
    @Override
    public void batchRemove(List<Long> ids) {
        dmsEmployeeMapper.batchRemove(ids);
    }

    /**
     * 返回 人员的anjoyId-人员实体 映射集
     *
     * @return
     */
    @Override
    public Map<String, DmsEmployeeVo> getEmployeeMap() {
        List<DmsEmployeeVo> employeeList = dmsEmployeeMapper.selectVoByCondition(new DmsEmployeeVo());
        Map<String, DmsEmployeeVo> employeeMap = Maps.newHashMap();
        for (DmsEmployeeVo employee : employeeList) {
            employeeMap.put(employee.getAnjoyId(), employee);
        }
        return employeeMap;
    }

    /**
     * 同步安井人员信息
     * 调用安井人员同步接口，返回JSON数组格式数据
     */
    @Override
    @Transactional(readOnly = false)
    public void anjoySyn(UserSession session) {
        logger.info("*************************** start 开始同步安井人员信息 ***************************");

        //调用安井的人员同步接口，返回JSON数组格式数据
        JSONArray jsonArray = AnjoySynClient.getAnjoySynDataBySQLType(AnjoySynClient.SYN_TYPE.EMPLOYEE);
        List<DmsEmployee> employeeList = Lists.newArrayList();

        Map<String, DmsOrganization> organizationMap = dmsOrganizationService.getOrganizationMap();
        Date now = new Date();
        Map<String, DmsEmployeeVo> employeeMap = this.getEmployeeMap();

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            String anjoyId = jsonObject.getString("FID");

            boolean isInsert = !employeeMap.containsKey(anjoyId);
            DmsEmployeeVo employee;
            if (isInsert) {
                employee = new DmsEmployeeVo();
                employee.setCreatedBy(0L);
                employee.setCreatedDate(now);
            } else {
                employee = employeeMap.get(anjoyId);
                employee.setLastUpdatedBy(0L);
                employee.setLastUpdatedDate(now);
            }

            employee.setAnjoyId(jsonObject.getString("FID"));
            employee.setName(jsonObject.getString("FNAME_L2"));
            employee.setAccount(jsonObject.getString("FNUMBER"));
            employee.setPlainPassword(DmsModuleEnums.DEFAULT_PASSWORD);
            employee.setStatus(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
            employee.setSex(0);
            employee.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

            List<DmsContactInfo> contactInfoList = Lists.newArrayList();
            if (jsonObject.get("FEMAIL") != null) {
                DmsContactInfo contactInfo = new DmsContactInfo();
                contactInfo.setType(DmsModuleEnums.CONTACT_TYPE.EMAIL.getType());
                contactInfo.setContent(jsonObject.getString("FEMAIL"));
                contactInfoList.add(contactInfo);
            }
            if (jsonObject.get("FOFFICEPHONE") != null) {
                DmsContactInfo contactInfo = new DmsContactInfo();
                contactInfo.setType(DmsModuleEnums.CONTACT_TYPE.PHONE.getType());
                contactInfo.setContent(jsonObject.getString("FOFFICEPHONE"));
                contactInfoList.add(contactInfo);
            }
            if (jsonObject.get("FCELL") != null) {
                DmsContactInfo contactInfo = new DmsContactInfo();
                contactInfo.setType(DmsModuleEnums.CONTACT_TYPE.MOBILE.getType());
                contactInfo.setContent(jsonObject.getString("FCELL"));
                contactInfoList.add(contactInfo);
            }

            List<DmsContactInfo> existedContactInfoList = employee.getContactInfoList();
            if (existedContactInfoList != null && existedContactInfoList.size() > 0) {
                for (DmsContactInfo contactInfo : existedContactInfoList) {

                }
            } else {

            }

            employee.setContactInfoList(contactInfoList);

            DmsOrganization organization = organizationMap.get(jsonObject.getString("FHRORGUNITID"));

            List<DmsEmployeeOrganizationVo> employeeOrganizationList = Lists.newArrayList();
            DmsEmployeeOrganizationVo employeeOrganization = new DmsEmployeeOrganizationVo();
            employeeOrganization.setOrganizationId(organization.getId());
            employeeOrganization.setIsMajor(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
            employeeOrganization.setIsCharge(DmsModuleEnums.ACTIVE_STATUS.DIABLE.getType());
            employeeOrganizationList.add(employeeOrganization);
            employee.setEmployeeOrganizationList(employeeOrganizationList);

            insertOrUpdate(employee, session);
        }

        logger.info("*************************** end 结束同步安井人员信息 ***************************");
    }


    @Override
    @Transactional(readOnly = false)
    public void anjoySyn() {
        logger.info("*************************** start 开始同步安井人员信息 ***************************");

        //调用安井的人员同步接口，返回JSON数组格式数据
        JSONArray jsonArray = AnjoySynClient.getAnjoySynDataBySQLType(AnjoySynClient.SYN_TYPE.EMPLOYEE);
        List<DmsEmployee> employeeList = Lists.newArrayList();

        Map<String, DmsOrganization> organizationMap = dmsOrganizationService.getOrganizationMap();
        Date now = new Date();
        Map<String, DmsEmployeeVo> employeeMap = this.getEmployeeMap();

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            String anjoyId = jsonObject.getString("FID");

            boolean isInsert = !employeeMap.containsKey(anjoyId);
            DmsEmployeeVo employee;
            if (isInsert) {
                employee =new DmsEmployeeVo();
                employee.setCreatedBy(0L);
                employee.setCreatedDate(now);
            } else {
                employee = employeeMap.get(anjoyId);
                employee.setLastUpdatedBy(0L);
                employee.setLastUpdatedDate(now);
            }

            employee.setAnjoyId(jsonObject.getString("FID"));
            employee.setName(jsonObject.getString("FNAME_L2"));
            employee.setAccount(jsonObject.getString("FNUMBER"));
            employee.setPlainPassword(DmsModuleEnums.DEFAULT_PASSWORD);
            employee.setStatus(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
            employee.setSex(0);
            employee.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

            List<DmsContactInfo> contactInfoList = Lists.newArrayList();
            if (jsonObject.get("FEMAIL") != null) {
                DmsContactInfo contactInfo = new DmsContactInfo();
                contactInfo.setType(DmsModuleEnums.CONTACT_TYPE.EMAIL.getType());
                contactInfo.setContent(jsonObject.getString("FEMAIL"));
                contactInfoList.add(contactInfo);
            }
            if (jsonObject.get("FOFFICEPHONE") != null) {
                DmsContactInfo contactInfo = new DmsContactInfo();
                contactInfo.setType(DmsModuleEnums.CONTACT_TYPE.PHONE.getType());
                contactInfo.setContent(jsonObject.getString("FOFFICEPHONE"));
                contactInfoList.add(contactInfo);
            }
            if (jsonObject.get("FCELL") != null) {
                DmsContactInfo contactInfo = new DmsContactInfo();
                contactInfo.setType(DmsModuleEnums.CONTACT_TYPE.MOBILE.getType());
                contactInfo.setContent(jsonObject.getString("FCELL"));
                contactInfoList.add(contactInfo);
            }

            List<DmsContactInfo> existedContactInfoList = employee.getContactInfoList();
            if (existedContactInfoList != null && existedContactInfoList.size() > 0) {
                for (DmsContactInfo contactInfo : existedContactInfoList) {

                }
            } else {

            }

            employee.setContactInfoList(contactInfoList);

            DmsOrganization organization = organizationMap.get(jsonObject.getString("FHRORGUNITID"));

            List<DmsEmployeeOrganizationVo> employeeOrganizationList = Lists.newArrayList();
            DmsEmployeeOrganizationVo employeeOrganization = new DmsEmployeeOrganizationVo();
            employeeOrganization.setOrganizationId(organization.getId());
            employeeOrganization.setIsMajor(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
            employeeOrganization.setIsCharge(DmsModuleEnums.ACTIVE_STATUS.DIABLE.getType());
            employeeOrganizationList.add(employeeOrganization);
            employee.setEmployeeOrganizationList(employeeOrganizationList);

            insertOrUpdate(employee);
        }

        logger.info("*************************** end 结束同步安井人员信息 ***************************");
    }

    @Override
    public void insertOrUpdate(DmsEmployeeVo employeeVo) {
        checkParam(employeeVo);

        boolean isInsert = employeeVo.getId() == null ? true : false;
        Long userId = employeeVo.getId() == null ? employeeVo.getCreatedBy() : employeeVo.getLastUpdatedBy();
        Date now = new Date();

        if (isInsert) {  //新增操作
            /* 1.保存员工信息 */
            dmsEmployeeMapper.insert(employeeVo);
            /* 为员工新建账号 */
            DmsUserVo user = new DmsUserVo();
            user.setAccount(employeeVo.getAccount());
            user.setName(employeeVo.getName());
            user.setPlainPassword(employeeVo.getPlainPassword());
            user.setSource(DmsModuleEnums.ACCOUNT_SOURCE_TYPE.EMPLOYEE.getType());
            user.setStaffId(employeeVo.getId());
            user.setRoleId(employeeVo.getRoleId());
            user.setCreatedBy(userId);  //账号的创建人设置为员工的创建人

            DmsUser entity = dmsUserService.create(user);  //调用新增账号接口
            userId=entity.getId();
            employeeVo.setUserId(entity.getId());
            dmsEmployeeMapper.updateByPrimaryKeySelective(employeeVo);
        } else {  //修改操作
            Long id = employeeVo.getId();
            DmsEmployee employee = dmsEmployeeMapper.selectVoByPrimaryKey(id);
            if (employee == null) {
                throw new ServiceException("数据库中不存在id为: " + id + "的员工数据");
            }
            userId=employee.getUserId();
            if (!StringUtils.isBlank(employeeVo.getPlainPassword())) {  //如果用户传入明文密码，则修改密码
                dmsUserService.updatePassword(employeeVo.getUserId(), employeeVo.getPlainPassword());
            }
            dmsEmployeeMapper.updateByPrimaryKeySelective(employeeVo);

            /* 4.员工与角色关系 */
            if (employeeVo.getRoleId() != null) {
                dmsUserRoleService.insertOrUpdateUserRole(userId, employeeVo.getRoleId(), null);
            }
        }

        /* 2.新增/修改联系信息 */
        List<DmsContactInfo> contactInfoList = employeeVo.getContactInfoList();
        for (DmsContactInfo contactInfo : contactInfoList) {
            Long id = contactInfo.getId();
            if (id == null || id == 0) {  //id为0或空的时候表示新增的联系信息
                contactInfo.setCreatedBy(userId);
                contactInfo.setCreatedDate(now);
            } else {  //修改联系信息
                contactInfo.setLastUpdatedBy(userId);
                contactInfo.setLastUpdatedDate(now);
            }
            contactInfo.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.EMPLOYEE.getType());
            contactInfo.setRelatedTableId(employeeVo.getId());
            contactInfo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        }
        dmsContactInfoService.insertOrUpdate(contactInfoList);

        /* 3.新增/修改员工-组织关联信息 */
        List<DmsEmployeeOrganizationVo> employeeOrganizationList = employeeVo.getEmployeeOrganizationList();
        if (employeeOrganizationList != null && !employeeOrganizationList.isEmpty()) {
            dmsEmployeeOrganizationService.insertOrUpdate(employeeVo);
        }



    }

}