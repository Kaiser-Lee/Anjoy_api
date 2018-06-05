package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.constant.DmsGlobalVariableKeyConstants;
import com.coracle.dms.constant.DmsRoleCodeConstants;
import com.coracle.dms.dao.mybatis.DmsChannelEmployeeMapper;
import com.coracle.dms.dao.mybatis.DmsUserMapper;
import com.coracle.dms.dto.DmsUserDetailDto;
import com.coracle.dms.dto.DmsUserInfoDto;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.support.SystemParamerConfiguration;
import com.coracle.dms.vo.DmsUserVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiruo.medbid.components.HttpRequestUtils;
import com.xiruo.medbid.components.StringUtils;
import com.xiruo.medbid.util.BeanConvertHelper;
import com.xiruo.medbid.util.Digests;
import com.xiruo.medbid.util.Encodes;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsUserServiceImpl extends BaseServiceImpl<DmsUser> implements DmsUserService {
    private static final Logger logger = Logger.getLogger(DmsUserServiceImpl.class);

    @Value("${mxm.url}")
    private String mxmUrl;
    @Autowired
    private DmsUserMapper dmsUserMapper;
    @Autowired
    private DmsRoleService dmsRoleService;
    @Autowired
    private DmsUserRoleService dmsUserRoleService;
    @Autowired
    private DmsGlobalVariableService dmsGlobalVariableService;
    @Autowired
    private DmsContactInfoService dmsContactInfoService;
    @Autowired
    private DmsEmployeeService dmsEmployeeService;
    @Autowired
    private DmsChannelContactsService dmsChannelContactsService;
    @Autowired
    private DmsSellerService dmsSellerService;
    @Autowired
    private DmsStoreService dmsStoreService;
    @Autowired
    private DmsChannelService dmsChannelService;
    @Autowired
    private DmsOrganizationService dmsOrganizationService;
    @Autowired
    private DmsDataDictionayDependentService dmsDataDictionayDependentService;
    @Autowired
    private DmsMessageService dmsMessageService;
    @Autowired
    private DmsChannelEmployeeMapper dmsChannelEmployeeMapper;

    @Override
    public IMybatisDao<DmsUser> getBaseDao() {
        return dmsUserMapper;
    }

    /**
     * 新增账号
     * @param userVo
     * @param session
     */
    @Override
    public DmsUser create(DmsUserVo userVo, UserSession session) {
        checkParam(userVo);

        userVo.setCreatedDate(new Date());
        userVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

        /* 判断账号是否已存在 */
        Integer count = dmsUserMapper.selectCountByAccount(userVo.getAccount());
        if (count > 0) {
            throw new ServiceException("账号：" + userVo.getAccount() + "已存在!");
        }

        /* 如果没有设置密码，则从设置密码为全局变量中的默认密码 */
        if (StringUtils.isBlank(userVo.getPlainPassword())) {
            userVo.setPlainPassword(getDefaultPassword());
        }
        /* 设置salt和密码信息 */
        userVo.setSalt(Encodes.encodeHex(Digests.generateSalt(Digests.SALT_SIZE)));
        userVo.setPassword(encryptPassword(userVo.getSalt(), userVo.getPlainPassword()));

        /* 如果是admin或品牌商角色的账号在pc端创建(开通)的账号，则不需要进行审核操作，直接设置账号状态为：启用 */
        /* 其他情况将账号状态设置为：待审核 */
        if (DmsRoleCodeConstants.BM.equals(session.getRoleCode())
                || DmsRoleCodeConstants.ADMIN.equals(session.getRoleCode())) {
            userVo.setStatus(DmsModuleEnums.ACCOUNT_STATUS_TYPE.VALID.getType());
        } else {
            userVo.setStatus(DmsModuleEnums.ACCOUNT_STATUS_TYPE.UNCHECKED.getType());
        }

        /* 调用mxm的新增用户接口，同步在xsimple中创建账号(用户) */
        Long mxmId = mxm("/user/insertOrUpdate", userVo);
        if (mxmId == null) {
            throw new ServiceException("mxm同步新增用户失败");
        } else {
            userVo.setMxmId(mxmId);
        }

        /* 新增账号 */
        dmsUserMapper.insert(userVo);

        /* 新增账号-角色关联信息 */
        //若账户的角色信息为空，则根据账号来源获取角色id
        dmsUserRoleService.createUserRole(userVo.getId(), userVo.getRoleId(), userVo.getCreatedBy());

       /* 发送账号开通申请消息,当是品牌商时，不发送消息 */
        if (!"BM".equals(session.getRoleCode())){
            sendMessage(userVo);
        }
        return userVo;
    }

    @Override
    public DmsUser create(DmsUserVo userVo) {
        checkParam(userVo);

        userVo.setCreatedDate(new Date());
        userVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

        /* 判断账号是否已存在 */
        Integer count = dmsUserMapper.selectCountByAccount(userVo.getAccount());
        if (count > 0) {
            throw new ServiceException("账号：" + userVo.getAccount() + "已存在!");
        }

        /* 如果没有设置密码，则从设置密码为全局变量中的默认密码 */
        if (StringUtils.isBlank(userVo.getPlainPassword())) {
            userVo.setPlainPassword(getDefaultPassword());
        }
        /* 设置salt和密码信息 */
        userVo.setSalt(Encodes.encodeHex(Digests.generateSalt(Digests.SALT_SIZE)));
        userVo.setPassword(encryptPassword(userVo.getSalt(), userVo.getPlainPassword()));

        /* 如果是admin或品牌商角色的账号在pc端创建(开通)的账号，则不需要进行审核操作，直接设置账号状态为：启用 */
        /* 其他情况将账号状态设置为：待审核 */
        userVo.setStatus(DmsModuleEnums.ACCOUNT_STATUS_TYPE.VALID.getType());
        /* 调用mxm的新增用户接口，同步在xsimple中创建账号(用户) */
        Long mxmId = mxm("/user/insertOrUpdate", userVo);
        if (mxmId == null) {
            throw new ServiceException("mxm同步新增用户失败");
        } else {
            userVo.setMxmId(mxmId);
        }

        /* 新增账号 */
        dmsUserMapper.insert(userVo);

        /* 新增账号-角色关联信息 */
        //若账户的角色信息为空，则根据账号来源获取角色id
        dmsUserRoleService.createUserRole(userVo.getId(), userVo.getRoleId(), userVo.getCreatedBy());

        return userVo;
    }

    /**
     * 根据salt和明文密码得到密码
     * 密码生成方法参照xsimple，但salt和密码不与xsimple同步
     * @param salt
     * @param plainPassword
     * @return
     */
    @Override
    public String encryptPassword(String salt, String plainPassword) {
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), Digests.HASH_INTERATIONS);
        String password = Encodes.encodeHex(hashPassword);
        return password;
    }

    /**
     * mxm同步接口，返回mxm的kn_user表的id
     * @param url
     * @param userVo
     * @return
     */
    private Long mxm(String url, DmsUserVo userVo) {
        url = mxmUrl + url;

        Map<String, Object> param = convertParam(userVo);
        JSONObject json = JSONObject.fromObject(param);
        Long id = null;

        try {
            JSONObject response = HttpRequestUtils.doPostByJson(url, json);
            Boolean success = response.getBoolean("status");
            if (success) {
                id = response.getLong("errorMessage");
            }
            return id;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("调用mxm同步新增/修改用户接口异常: /user/insertOrUpdate");
        }
    }

    /**
     * 设置mxm同步接口的参数
     * 因为mxm的KnUser实体里面包含几个枚举类型，直接传实体时遇到问题，所以用这种方式传
     * 后续如果有更好的做法请提出并优化
     * @param userVo
     * @return
     */
    private Map<String, Object> convertParam(DmsUserVo userVo) {
        checkParam(userVo);
        Map<String, Object> param = Maps.newHashMap();
        param.put("id", userVo.getMxmId());
        param.put("loginName", userVo.getAccount());
        param.put("plainPassword", userVo.getPlainPassword());
        param.put("name", userVo.getName());

        DmsRole role = dmsRoleService.selectByPrimaryKey(userVo.getRoleId());
        param.put("roleId", role.getMxmId());  //角色id设置为mxm的角色表的id

        return param;
    }

    /**
     * 账号开通申请消息
     * @param userVo
     */
    private void sendMessage(DmsUserVo userVo){
        try {
            //发送账号开通申请
            DmsUserInfoDto dmsUserInfoDto = new DmsUserInfoDto();
            dmsUserInfoDto.setSource(userVo.getSource());
            dmsUserInfoDto.setStaffId(userVo.getStaffId());
            List<Map<String,Object>> list = selectUserDetail(dmsUserInfoDto);
            //先给用户自己增加一条记录但设置为列表不显示
            String title = " 提交了账号开通申请";
            String content = "提交了新的账号申请，账号：";
            DmsMessage dmsMessage = new DmsMessage();
            //消息类型，
            dmsMessage.setMessageType(DmsModuleEnums.MESSAGE_TYPE.MESSAGE_ACCOUNT.getType());
            dmsMessage.setEntityId(userVo.getId());
            dmsMessage.setIsShow(0);//是否列表显示
            dmsMessage.setEntityType(DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_USER.getType());
            //设置消息的title
            dmsMessage.setTitle(list.get(0).get("orgName")==null ? title : list.get(0).get("orgName").toString() + title);
            //设置消息的内容
            dmsMessage.setContent(content + userVo.getAccount()+"。");
            dmsMessage.setCreatedBy(userVo.getCreatedBy());
            dmsMessage.setLastUpdatedBy(userVo.getCreatedBy());
            dmsMessage.setStaffId(userVo.getCreatedBy());
            dmsMessageService.sendMessage(dmsMessage);
            //发给品牌商的消息，重新设置收信人、和是否列表显示
            dmsMessage.setStaffId(0L);
            dmsMessage.setIsShow(1);//是否列表显示
            dmsMessageService.sendMessage(dmsMessage);
        } catch (Exception e) {
            logger.error("账号开通发送消息异常：",e);
        }
    }

    /**
     * 账号列表(分页)
     * @param userVo
     * @return
     */
    @Override
    public PageHelper.Page<DmsUserVo> pageList(DmsUserVo userVo) {
        try {
            PageHelper.startPage(userVo.getP(), userVo.getS());
            dmsUserMapper.selectVoByCondition(userVo);
        } catch (Exception e) {
            logger.error("账号分页查询异常!", e);
            throw new ServiceException("账号分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 审核账号
     * @param user
     */
    @Override
    public void audit(DmsUser user) {
        DmsUser entity = dmsUserMapper.selectVoByPrimaryKey(user.getId());
        if (entity == null) {
            throw new ServiceException("数据库中不存在id为: " + user.getId() + "的账号!");
        }
        Integer auditOpinion = user.getAuditOpinion();
        if (auditOpinion == null) {
            throw new ServiceException("审核意见为空!");
        }
        String title = "";
        String subject = "";
        String content = "";
        if (auditOpinion == DmsModuleEnums.AUDIT_OPINION_TYPE.DISAGREE.getType()) {  //拒绝
            user.setStatus(DmsModuleEnums.ACCOUNT_STATUS_TYPE.INVALID.getType());  //审核拒绝后账号为禁用状态
            title = "商家拒绝了本次账号申请";
            subject = "商家拒绝了本次账号申请。";
            content = "商家拒绝了本次账号申请。";
        } else {  //同意
            user.setStatus(DmsModuleEnums.ACCOUNT_STATUS_TYPE.VALID.getType());  //审核同意后账号为启用状态
            title = "商家已同意了账号申请";
            subject = "商家同意了本次账号申请。账号：" + entity.getAccount() + "，初始密码：" + getDefaultPassword() + "，欢迎登陆使用本系统。";
            content = "商家同意了本次账号申请。账号：" + entity.getAccount() + "，初始密码：" + getDefaultPassword() + "，欢迎登陆使用本系统。";
        }
        user.setAuditor(user.getLastUpdatedBy());
        user.setAuditDate(new Date());
        dmsUserMapper.updateByPrimaryKeySelective(user);

        try {
            List<Long> staffList = new ArrayList<>();
            staffList.add(entity.getCreatedBy());
            dmsMessageService.sendMessage(DmsModuleEnums.MESSAGE_TYPE.MESSAGE_ACCOUNT.getType(), DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_USER.getType(), entity.getId(),
                    title,subject, content, staffList, 1, user.getLastUpdatedBy());
        } catch (Exception e) {
            logger.error("账号审核发送消息异常：", e);
        }
    }

    /**
     * 更新账号状态
     * @param user
     */
    @Override
    public void updateStatus(DmsUser user) {
        DmsUser entity = dmsUserMapper.selectByPrimaryKey(user.getId());
        if (entity == null) {
            throw new ServiceException("数据库中不存在id为: " + user.getId() + "的账号!");
        }
        if (entity.getStatus() == DmsModuleEnums.ACCOUNT_STATUS_TYPE.UNCHECKED.getType()) {
            throw new ServiceException("操作失败：该账号尚未通过审核!");
        }
        dmsUserMapper.updateByPrimaryKeySelective(user);  //修改数据库中账号的状态
    }

    /**
     * 修改密码
     * @param userId
     * @param plainPassword
     * @param session
     */
    @Override
    public void updatePassword(Long userId, String plainPassword, UserSession session) {
        DmsUser user = dmsUserMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new ServiceException("数据库中查询不到id为: " + userId + "的账号!");
        }

        /* 1.修改密码 */
        if (StringUtils.isBlank(plainPassword)){  //如果没有设置明文密码，则重置为全局变量中的默认密码
            plainPassword = getDefaultPassword();
        }
        user.setPassword(encryptPassword(user.getSalt(), plainPassword));
        user.setLastUpdatedBy(session.getId());
        user.setLastUpdatedDate(new Date());
        dmsUserMapper.updateByPrimaryKeySelective(user);

        DmsUserVo userVo = new DmsUserVo();
        BeanConvertHelper.copyProperties(user, userVo);
        userVo.setPlainPassword(plainPassword);
        /* 2.同步调用mxm的修改密码接口 */
        Long id = mxm("/user/updatePassword", userVo);
        if (id == null) {
            throw new ServiceException("mxm同步修改密码失败");
        }
    }


    @Override
    public void updatePassword(Long userId, String plainPassword) {
        DmsUser user = dmsUserMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new ServiceException("数据库中查询不到id为: " + userId + "的账号!");
        }

        /* 1.修改密码 */
        if (StringUtils.isBlank(plainPassword)){  //如果没有设置明文密码，则重置为全局变量中的默认密码
            plainPassword = getDefaultPassword();
        }
        user.setPassword(encryptPassword(user.getSalt(), plainPassword));
        user.setLastUpdatedDate(new Date());
        dmsUserMapper.updateByPrimaryKeySelective(user);

        DmsUserVo userVo = new DmsUserVo();
        BeanConvertHelper.copyProperties(user, userVo);
        userVo.setPlainPassword(plainPassword);
        /* 2.同步调用mxm的修改密码接口 */
        Long id = mxm("/user/updatePassword", userVo);
        if (id == null) {
            throw new ServiceException("mxm同步修改密码失败");
        }
    }

    /**
     * 根据mxmId修改用户密码
     * @param mxmId
     * @param oldPassword
     * @param newPassword
     * @param session
     */
    @Override
    public void updatePassword(Long mxmId, String oldPassword, String newPassword, UserSession session) {
        DmsUser user = dmsUserMapper.selectByMxmId(mxmId);
        if (user == null) {
            throw new ServiceException("参数错误，无法获取mxmId为: " + mxmId + "的用户信息");
        }
        if (!encryptPassword(user.getSalt(), oldPassword).equals(user.getPassword())) {  //校验用户输入的旧密码是否正确
            throw new ServiceException("旧密码错误");
        } else {
            updatePassword(user.getId(), newPassword, session);
        }
    }

    /**
     * 根据账号来源获取角色id
     * @param source
     * @return
     */
    private Long getRoleIdBySource(Integer source) {
        Long roleId;
        DmsRole roleParam = new DmsRole();  //角色查询参数
        roleParam.setRemoveFlag(0);
        //根据账号来源的不同设置账号角色
        //备注：v1版本只有四个角色，角色和来源一一对应，角色设置统一在此处理，在代码中写死
        //若之后版本角色和账号来源增多的时候，在代码调用处设置角色参数
        if (source == DmsModuleEnums.ACCOUNT_SOURCE_TYPE.EMPLOYEE.getType()) {
            roleParam.setCode(DmsRoleCodeConstants.BM);  //订货端
        } else if (source == DmsModuleEnums.ACCOUNT_SOURCE_TYPE.CHANNEL_CONTACTS.getType()) {  //渠道联系人
            roleParam.setCode(DmsRoleCodeConstants.CO);  //订货端
        } else if (source == DmsModuleEnums.ACCOUNT_SOURCE_TYPE.SELLER.getType()) {  //门店店员
            roleParam.setCode(DmsRoleCodeConstants.SR);  //零售端
        } else {
            throw new ServiceException("未知账号来源!");
        }

        List<DmsRole> roleList = dmsRoleService.selectByCondition(roleParam);
        if (roleList.isEmpty()) {
            throw new ServiceException("角色为空，code: " + roleParam.getCode());
        } else if (roleList.size() > 1) {
            throw new ServiceException("角色设置异常(数量>1), code: " + roleParam.getCode());
        } else {
            roleId = roleList.get(0).getId();
        }
        return roleId;
    }

    /**
     * 获取全局变量中设置的默认密码
     * @return
     */
    private String getDefaultPassword() {
        return SystemParamerConfiguration.getInstance().get(DmsGlobalVariableKeyConstants.DEFAULT_PASSWORD);
    }

    /**
     * 参数检查
     * @param userVo
     */
    private void checkParam(DmsUserVo userVo) {
        if (userVo == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(userVo.getAccount())) {
            throw new ServiceException("账号不能为空!");
        } else if (userVo.getAccount().equals("admin")) {
            throw new ServiceException("请不要以\"admin\"作为账号");
        }
        if (userVo.getSource() == null) {
            throw new ServiceException("账号来源不能为空!");
        }
        if (userVo.getStaffId() == null) {
            throw new ServiceException("与账号关联的人员id不能为空!");
        }
        if (userVo.getRoleId() == null) {
            userVo.setRoleId(getRoleIdBySource(userVo.getSource()));
        }
    }

    /**
     * 根据用户的来源表类型和关联的id查询用户的其他信息，
     * 一般只有一条记录，但为了防止数据数目异常，用了list，一般取第一条
     * @param dmsUserInfoDto
     * @return
     */
    public List<Map<String,Object>> selectUserDetail(DmsUserInfoDto dmsUserInfoDto){
        return dmsUserMapper.selectUserDetail(dmsUserInfoDto);
    }

    /**
     * 获取用户的个人信息
     * @param userSession
     * @return
     */
    public DmsUserDetailDto getUserContactsDetail(UserSession userSession){
        long userId = userSession.getId();
        DmsUserDetailDto dmsUserDetailDto = new DmsUserDetailDto();
        DmsUser dmsUser = selectByPrimaryKey(userId);
        if (BlankUtil.isEmpty(dmsUser)) {
            throw new ServiceException("用户不存在！");
        }
        dmsUserDetailDto.setPhotoUrl(dmsUser.getPhotoUrl());
        dmsUserDetailDto.setSource(dmsUser.getSource());
        dmsUserDetailDto.setStaffId(dmsUser.getStaffId());
        dmsUserDetailDto.setId(dmsUser.getId());
        dmsUserDetailDto.setAccount(dmsUser.getAccount());
        DmsContactInfo dci = new DmsContactInfo();
        dci.setRelatedTableId(dmsUser.getStaffId());
        if (dmsUser.getSource() == DmsModuleEnums.ACCOUNT_SOURCE_TYPE.EMPLOYEE.getType()){//系统员工
            DmsEmployee employee = dmsEmployeeService.selectByPrimaryKey(dmsUser.getStaffId());
            dci.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.EMPLOYEE.getType());
            dmsUserDetailDto.setName(employee.getName());
            dmsUserDetailDto.setSex(String.valueOf(employee.getSex()));
            //业务员
            if(userSession.getSalesman() == true && userSession.getDmsChannelEmployeeVo() != null){
                DmsChannelEmployee dmsChannelEmployeeCondition = new DmsChannelEmployee();
                dmsChannelEmployeeCondition.setUserId(userId);
                dmsChannelEmployeeCondition.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

                List<DmsChannelEmployee> dmsChannelEmployeeList = dmsChannelEmployeeMapper.selectByCondition(dmsChannelEmployeeCondition);
                if(dmsChannelEmployeeList != null && dmsChannelEmployeeList.size() > 0){
                    DmsChannelEmployee dmsChannelEmployee = dmsChannelEmployeeList.get(0);
                    if(dmsChannelEmployee != null){
                        dmsUserDetailDto.setName(dmsChannelEmployee.getName());
                    }
                }
            }
        }else if (dmsUser.getSource() == DmsModuleEnums.ACCOUNT_SOURCE_TYPE.CHANNEL_CONTACTS.getType()) {//渠道联系人
            DmsChannelContacts dmsChannelContacts = dmsChannelContactsService.selectByPrimaryKey(dmsUser.getStaffId());
            dci.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.CHANNEL_CONTACT.getType());
            dmsUserDetailDto.setBirthday(dmsChannelContacts.getBirthdayDate());
            dmsUserDetailDto.setName(dmsChannelContacts.getName());
            dmsUserDetailDto.setSex(dmsChannelContacts.getSex());
            dmsUserDetailDto.setPost(dmsChannelContacts.getPost());
            dmsUserDetailDto.setPostText(dmsChannelContacts.getPost());
        }else if (dmsUser.getSource() == DmsModuleEnums.ACCOUNT_SOURCE_TYPE.SELLER.getType()) {//门店店员联系人
            DmsSeller dmsSeller = dmsSellerService.selectByPrimaryKey(dmsUser.getStaffId());
            dci.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.STORE_CONTACT.getType());
            dmsUserDetailDto.setBirthday(dmsSeller.getBirthday());
            dmsUserDetailDto.setName(dmsSeller.getName());
            dmsUserDetailDto.setSex(dmsSeller.getSex());
            dmsUserDetailDto.setPost(dmsSeller.getPost());
            dmsUserDetailDto.setPostText(dmsDataDictionayDependentService.getDataValueName("store_post_type",dmsSeller.getPost()));
        }else {
            //其他情况暂留
        }
        if (BlankUtil.isNotEmpty(dmsUserDetailDto.getSex())) {
            dmsUserDetailDto.setSexText(dmsDataDictionayDependentService.getDataValueName("sex", dmsUserDetailDto.getSex()));
        }
        List<DmsContactInfo> contactInfos = dmsContactInfoService.selectByCondition(dci);
        dmsUserDetailDto.setDmsContactInfoList(contactInfos);
        if (BlankUtil.isNotEmpty(contactInfos)){
            List<String> mobileList = new ArrayList<>();
            for(DmsContactInfo contactInfo : contactInfos){
                switch (contactInfo.getType()){
                    case 1:mobileList.add(contactInfo.getContent()); break;//手机号码是列表
                    case 2:dmsUserDetailDto.setPhoneText(contactInfo.getContent());break;
                    case 3:dmsUserDetailDto.setEmailText(contactInfo.getContent());break;
                    case 4:dmsUserDetailDto.setQqText(contactInfo.getContent());break;
                    case 5:dmsUserDetailDto.setWeiText(contactInfo.getContent());break;
                }
            }
            dmsUserDetailDto.setMobileTextList(mobileList);
        }
        return dmsUserDetailDto;
    }

    /**
     * 获取账号审核信息
     * @param userId
     * @return
     */
    public DmsUserDetailDto getUserAccountAuditDetail(long userId){
        DmsUserDetailDto dmsUserDetailDto = new DmsUserDetailDto();
        DmsUser dmsUser = dmsUserMapper.selectByPrimaryKey(userId);
        if (BlankUtil.isEmpty(dmsUser)){
            throw new ServiceException("未获取到ID为【"+userId+"】的用户信息");
        }
        dmsUserDetailDto.setSource(dmsUser.getSource());
        dmsUserDetailDto.setStaffId(dmsUser.getStaffId());
        dmsUserDetailDto.setId(dmsUser.getId());
        dmsUserDetailDto.setAccount(dmsUser.getAccount());
        dmsUserDetailDto.setStatus(dmsUser.getStatus());
        dmsUserDetailDto.setApplyDate(dmsUser.getCreatedDate());

        DmsUserVo dmsRoleVo = selectRoleByUserId(dmsUser.getId());
        if (BlankUtil.isNotEmpty(dmsRoleVo)) {
            dmsUserDetailDto.setRoleName(dmsRoleVo.getRoleName() == null ? "" : dmsRoleVo.getRoleName());
        }

        DmsUserInfoDto dmsUserInfoDto = new DmsUserInfoDto();
        dmsUserInfoDto.setSource(dmsUser.getSource());
        dmsUserInfoDto.setStaffId(dmsUser.getStaffId());
        List<Map<String,Object>> list = dmsUserMapper.selectUserDetail(dmsUserInfoDto);
        if (BlankUtil.isEmpty(list)){
            throw new ServiceException("无法获取用户的联系信息");
        }
        Long orgId = Long.parseLong(list.get(0).get("orgId")==null?"0":list.get(0).get("orgId").toString());//所属机构ID
        dmsUserDetailDto.setName(list.get(0).get("userName")==null?"":list.get(0).get("userName").toString());
        dmsUserDetailDto.setPost(dmsDataDictionayDependentService.getDataValueName("store_post_type",list.get(0).get("post")==null?"":list.get(0).get("post").toString()));
        if (dmsUser.getSource()==DmsModuleEnums.ACCOUNT_SOURCE_TYPE.EMPLOYEE.getType()){//系统员工
            dmsUserDetailDto.setOrgName(list.get(0).get("orgName")==null?"":list.get(0).get("orgName").toString());
        }else if (dmsUser.getSource()==DmsModuleEnums.ACCOUNT_SOURCE_TYPE.CHANNEL_CONTACTS.getType()){//渠道
            dmsUserDetailDto.setChannelName(list.get(0).get("orgName")==null?"":list.get(0).get("orgName").toString());
            dmsUserDetailDto.setPostText(dmsDataDictionayDependentService.getDataValueName("channel_post_type",list.get(0).get("post")==null?"":list.get(0).get("post").toString()));
        }else if (dmsUser.getSource()==DmsModuleEnums.ACCOUNT_SOURCE_TYPE.SELLER.getType()){//门店
            dmsUserDetailDto.setStoreName(list.get(0).get("orgName")==null?"":list.get(0).get("orgName").toString());
            dmsUserDetailDto.setPostText(dmsDataDictionayDependentService.getDataValueName("store_post_type",list.get(0).get("post")==null?"":list.get(0).get("post").toString()));
            DmsStore dmsStore = dmsStoreService.selectByPrimaryKey(orgId);
            if (BlankUtil.isNotEmpty(dmsStore)){
                dmsUserDetailDto.setStoreName(dmsStore.getName());
                if (dmsStore.getOperateWay() == DmsModuleEnums.STORE_OPERATE_WAY.DISTRIBUTE.getType()){
                    DmsChannel dmsChannel = dmsChannelService.selectByPrimaryKey(dmsStore.getBelongDealer());
                    if (BlankUtil.isNotEmpty(dmsChannel)){
                        dmsUserDetailDto.setChannelName(dmsChannel.getName());
                    }
                }
            }
        }else {
        }
        DmsUserVo dmsUserVo = dmsUserMapper.selectVoByPrimaryKey(dmsUser.getCreatedBy());//申请人信息
        if (BlankUtil.isNotEmpty(dmsUserVo)){
            dmsUserDetailDto.setApplyName(dmsUserVo.getName());
        }
        if (BlankUtil.isNotEmpty(dmsUser.getAuditor())){
            DmsUserVo dmsUserVo2 = dmsUserMapper.selectVoByPrimaryKey(dmsUser.getAuditor());//审批人信息
            if (BlankUtil.isNotEmpty(dmsUserVo2)){
                dmsUserDetailDto.setAuditOpinion(dmsUser.getAuditOpinion() == DmsModuleEnums.AUDIT_OPINION_TYPE.AGREE.getType() ? "同意" : "拒绝");
                dmsUserDetailDto.setAuditRemark(dmsUser.getAuditRemark());
                dmsUserDetailDto.setAuditDate(dmsUser.getAuditDate());
                dmsUserDetailDto.setAuditName(dmsUserVo2.getName());
            }
        }
        return dmsUserDetailDto;
    }

    /**
     * 根据主键id获取账号信息
     * @param id
     * @return
     */
    @Override
    public DmsUserVo selectVoByPrimaryKey(Long id) {
        return dmsUserMapper.selectVoByPrimaryKey(id);
    }

    /**
     * 更新用户基本信息以及联系信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(DmsUserDetailDto dmsUserDetailDto,long userId){
        DmsUser dmsUser = selectByPrimaryKey(userId);
        if (BlankUtil.isEmpty(dmsUser)) {
            throw new ServiceException("用户不存在！");
        }
        if(BlankUtil.isNotEmpty(dmsUserDetailDto.getPhotoUrl())) {
            DmsUser dmsUser1 = new DmsUser();
            dmsUser1.setId(dmsUser.getId());
            dmsUser1.setPhotoUrl(dmsUserDetailDto.getPhotoUrl());
            updateByPrimaryKeySelective(dmsUser1);//更新用户基本信息
        }
        DmsContactInfo dci = new DmsContactInfo();
        dci.setRelatedTableId(dmsUser.getStaffId());
        int source = 0;
        long staffId = 0;
        if (dmsUser.getSource() == DmsModuleEnums.ACCOUNT_SOURCE_TYPE.EMPLOYEE.getType()){//系统员工
            DmsEmployee employee = dmsEmployeeService.selectByPrimaryKey(dmsUser.getStaffId());
            dci.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.EMPLOYEE.getType());
            if (BlankUtil.isEmpty(employee)){
                throw new ServiceException("未查询到信息！");
            }
            staffId = employee.getId();
            source = DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.EMPLOYEE.getType();
            DmsEmployee employee1 = new DmsEmployee();
            employee1.setId(employee.getId());
            employee1.setName(dmsUserDetailDto.getName());
            //employee1.setSex(Integer.parseInt(dmsUserDetailDto.getSex()));
            dmsEmployeeService.updateByPrimaryKeySelective(employee1);//修改员工信息
        }else if (dmsUser.getSource() == DmsModuleEnums.ACCOUNT_SOURCE_TYPE.CHANNEL_CONTACTS.getType()) {//渠道联系人
            DmsChannelContacts dmsChannelContacts = dmsChannelContactsService.selectByPrimaryKey(dmsUser.getStaffId());
            dci.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.CHANNEL_CONTACT.getType());
            if (BlankUtil.isEmpty(dmsChannelContacts)){
                throw new ServiceException("未查询到信息！");
            }
            staffId = dmsChannelContacts.getId();
            source = DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.CHANNEL_CONTACT.getType();
            DmsChannelContacts dmsChannelContacts1 = new DmsChannelContacts();
            dmsChannelContacts1.setId(dmsChannelContacts.getId());
            dmsChannelContacts1.setName(dmsUserDetailDto.getName());
            //dmsChannelContacts1.setSex(dmsUserDetailDto.getSex());
            //dmsChannelContacts1.setBirthdayDate(dmsUserDetailDto.getBirthday());
            dmsChannelContactsService.updateByPrimaryKeySelective(dmsChannelContacts1);//修改渠道联系人信息
        }else if (dmsUser.getSource() == DmsModuleEnums.ACCOUNT_SOURCE_TYPE.SELLER.getType()) {//门店店员联系人
            DmsSeller dmsSeller = dmsSellerService.selectByPrimaryKey(dmsUser.getStaffId());
            dci.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.STORE_CONTACT.getType());
            if (BlankUtil.isEmpty(dmsSeller)){
                throw new ServiceException("未查询到信息！");
            }
            staffId = dmsSeller.getId();
            source = DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.STORE_CONTACT.getType();
            DmsSeller dmsSeller1 = new DmsSeller();
            dmsSeller1.setId(dmsSeller.getId());
            dmsSeller1.setName(dmsUserDetailDto.getName());
            //dmsSeller1.setSex(dmsUserDetailDto.getSex());
            //dmsSeller1.setBirthday(dmsUserDetailDto.getBirthday());
            dmsSellerService.updateByPrimaryKeySelective(dmsSeller1);//修改门店店员信息
        }else {
            //其他情况暂留
        }
        List<DmsContactInfo> contactInfos = dmsContactInfoService.selectByCondition(dci);//数据库中保存的联系信息

        List<DmsContactInfo> contactInfoList = dmsUserDetailDto.getDmsContactInfoList();//提交过来的联系信息
        if(BlankUtil.isEmpty(contactInfos)) {//当联系信息为空时，为插入
            if (BlankUtil.isNotEmpty(contactInfoList)){
                batchAdd(contactInfoList,source,staffId,dmsUser.getId());
            }
        }else {//否则走修改
            List<Long> ids = new ArrayList<>();
            for (DmsContactInfo d:contactInfos){
                ids.add(d.getId());
            }
            DmsContactInfo dmsContactInfo = new DmsContactInfo();
            dmsContactInfo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.REMOVE.getType());
            dmsContactInfoService.updateByIdsSelective(dmsContactInfo,ids);
            if (BlankUtil.isNotEmpty(contactInfoList)){
                batchAdd(contactInfoList,source,staffId,dmsUser.getId());
            }
        }
    }

    /**
     * 批量添加
     * @param contactInfoList
     * @param staffId
     * @param userId
     */
    private void batchAdd(List<DmsContactInfo> contactInfoList,int source,long staffId,long userId){
        for (DmsContactInfo contactInfo : contactInfoList) {
            contactInfo.setRelatedTableType(source);
            contactInfo.setRelatedTableId(staffId);
            contactInfo.setCreatedBy(userId);
            contactInfo.setCreatedDate(new Date());
            contactInfo.setRemoveFlag(0);
        }
        dmsContactInfoService.batchInsert(contactInfoList);
    }

    /**
     * 根据userId获取role
     * @param id
     * @return
     */
    @Override
    public DmsUserVo selectRoleByUserId(Long id){
        return dmsUserMapper.selectRoleByUserId(id);
    }

    /**
     * 根据账号id列表获取账号对应的mxm用户的id列表
     * @param userIdList
     * @return
     */
    @Override
    public List<Long> selectMxmIdByUserIdList(List<Long> userIdList) {
        return dmsUserMapper.selectMxmIdByUserIdList(userIdList);
    }

    @Override
    public void push() {
        List<Long> userIdList = Lists.newArrayList();
        userIdList.add(2323L);
        dmsMessageService.push("测试消息标题", "测试消息内容", userIdList);
    }

    @Override
    public Long selectUser(DmsUserInfoDto dmsUserInfoDto){
        return dmsUserMapper.selectUserId(dmsUserInfoDto);
    }

    @Override
    public List<DmsUser> selectUserListByChannelId(Long channelId) {
        return dmsUserMapper.selectUserListByChannelId(channelId);
    }
}