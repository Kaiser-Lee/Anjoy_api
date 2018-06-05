package com.coracle.dms.xweb.controller;

import com.alibaba.fastjson.JSON;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dto.DmsUserInfoDto;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsUserVo;
import com.coracle.dms.xweb.common.enums.ErrorCodeEnum;
import com.coracle.dms.xweb.common.enums.StatusEnum;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.xframework.po.DmsChannelEmployeeVo;
import com.coracle.yk.xframework.po.DmsChannelInfoVo;
import com.coracle.yk.xframework.po.DmsChannelMutilVo;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xframework.util.StringUtil;
import com.coracle.yk.xframework.util.jsse.HttpRequest;
import com.xiruo.medbid.util.Digests;
import com.xiruo.medbid.util.Encodes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/api/v1/dms/login")
@Api(description = "用户登录接口")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private DmsUserService dmsUserService;
    @Autowired
    private DmsStoreService dmsStoreService;
    @Autowired
    private DmsOrganizationService dmsOrganizationService;
    @Autowired
    private DmsChannelService dmsChannelService;
    @Autowired
    private KnQrcodeNumService knQrcodeNumService;
    @Autowired
    private DmsContactInfoService dmsContactInfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DmsChannelEmployeeService dmsChannelEmployeeService;

    /**
     * 用户登陆
     * @return
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登录接口", response = UserSession.class)
    public Map<String, Object> login(HttpSession session, @ApiParam("账号") @RequestParam String loginName, @ApiParam("密码") @RequestParam String password, @ApiParam("PC端参数") @RequestParam(required = false, name = "platformType") String platformType) {
        Map<String, Object> map = new HashMap<String, Object>();
        Long startTime = System.currentTimeMillis();
        try {
            if (BlankUtil.isEmpty(loginName) || BlankUtil.isEmpty(password)) {
                map.put("status", StatusEnum.ERROR.getStatus());
                map.put("message", "用户名或密码为空！");
                return map;
            }

            List<DmsChannelMutilVo> dmsChannelList = null;//业务员对应的渠道列表
            DmsChannelMutilVo dmsChannelMutilVo = null;
            DmsUser dmsChannelContacts = null;//渠道-联系人
            DmsUser dmsChannelEmployee = null;//渠道-业务员
            boolean showChannelButton = false;//是否显示切换经销商按钮

            DmsUser dmsUser = new DmsUser();
            dmsUser.setAccount(loginName);
            List<DmsUser> dmsUserList = dmsUserService.selectByCondition(dmsUser);
            if (BlankUtil.isEmpty(dmsUserList)) {
                map.put("status", StatusEnum.ERROR.getStatus());
                map.put("message", "该用户不存在！");
                return map;
            } else {
                DmsUser user = dmsUserList.get(0);
                if ("0".equals(user.getStatus() + "")) {
                    map.put("status", StatusEnum.ERROR.getStatus());
                    map.put("message", "该用户已失效！");
                    return map;
                } else if ("2".equals(user.getStatus() + "")) {
                    map.put("status", StatusEnum.ERROR.getStatus());
                    map.put("message", "该用户处在审核中，暂不能登录！");
                    return map;
                } else if (!validate(password, user.getPassword(), user.getSalt())) {
                    map.put("status", StatusEnum.ERROR.getStatus());
                    map.put("message", "密码错误！");
                    return map;
                }

                logger.info("登录用户数据："+JSON.toJSONString(user));
                if(user.getSource() != null && user.getSource() == 2){//渠道联系人
                    DmsChannel dmsChannel = dmsChannelService.getChannelInfoByUserId(user.getId());
                    if(dmsChannel == null){
                        map.put("status", StatusEnum.ERROR.getStatus());
                        map.put("message", "非经销商用户！");
                        return map;
                    }else {
                        if(dmsChannel.getActive() == null
                            || dmsChannel.getActive().equals(DmsModuleEnums.ACTIVE_STATUS.DIABLE.getType())
                        ){
                            map.put("status", StatusEnum.ERROR.getStatus());
                            map.put("message", "经销商已失效，请联系所属业务员！");
                            return map;
                        }else if(dmsChannel.getRemoveFlag() == null
                            || dmsChannel.getRemoveFlag().equals(DmsModuleEnums.REMOVE_FLAG_STATUS.REMOVE.getType())
                        ){
                            map.put("status", StatusEnum.ERROR.getStatus());
                            map.put("message", "经销商已停用，请联系所属业务员！");
                            return map;
                        }
                    }
                }else {
                    if(StringUtil.isBlank(platformType) || !"PC".equalsIgnoreCase(platformType)){
                        //根据业务员ID获取渠道列表
                        dmsChannelList = dmsChannelEmployeeService.selectChannelList(user.getId());
                        if(dmsChannelList != null && dmsChannelList.size() > 0){
                            //获取列表第一个渠道
                            dmsChannelMutilVo =  dmsChannelList.get(0);
                            //根据渠道ID获取联系人列表
                            List<DmsUser> dmsChannelEmployeeList = dmsUserService.selectUserListByChannelId(dmsChannelMutilVo.getId());
                            if(dmsChannelEmployeeList != null && dmsChannelEmployeeList.size() > 0){
                                dmsChannelEmployee = user;//业务员
                                dmsChannelContacts = dmsChannelEmployeeList.get(0);//渠道第一个联系人
                                if(dmsChannelList.size() > 1){
                                    showChannelButton = true;
                                }
                            }
                        }
                    }
                }
            }
            DmsUser b = dmsUserList.get(0);
            DmsChannelEmployeeVo dmsChannelEmployeeVo = null;
            if(dmsChannelEmployee != null){
                dmsChannelEmployeeVo = new DmsChannelEmployeeVo();
                dmsChannelEmployeeVo.setSalesmanId(dmsChannelEmployee.getId());
                dmsChannelEmployeeVo.setDmsChannelList(dmsChannelList);
                b = dmsChannelContacts;
            }
            UserSession userSession = getUserSession(b);
            if(dmsChannelEmployeeVo != null){
                userSession.setDmsChannelEmployeeVo(dmsChannelEmployeeVo);
                userSession.setSalesman(true);
            }
            userSession.setShowChannelButton(showChannelButton);
            userSession.setPlatformType(platformType);
            session.setAttribute(UserSession.USER_SESSION_KEY, userSession);
            map.put("user", userSession);
            map.put("status", StatusEnum.SUCCESS.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error", e);
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", ErrorCodeEnum.DEFAULT.getMessage());
        }
        Long endTime = System.currentTimeMillis();
        double times = ((double) (endTime - startTime) / 1000);
        DecimalFormat df = new DecimalFormat("#.00");
        map.put("responseSeconds",  df.format(times));
        logger.info("登录接口耗时：{} 秒", df.format(times));
        return map;
    }

    @RequestMapping(value = "/switchChannelAccount", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "切换经销商户头接口", response = UserSession.class)
    public Map<String, Object> switchChannelAccount(HttpSession session, @ApiParam("经销商ID") @RequestParam(required = true) Long channelId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DmsChannel dmsChannel = dmsChannelService.selectByPrimaryKey(channelId);
            if(dmsChannel != null){
                UserSession userSession = LoginManager.getUserSession();
                //根据渠道ID获取联系人列表
                List<DmsUser> dmsChannelEmployeeList = dmsUserService.selectUserListByChannelId(dmsChannel.getId());
                if(dmsChannelEmployeeList != null && dmsChannelEmployeeList.size() > 0){
                    //渠道第一个联系人
                    DmsUser dmsChannelContacts = dmsChannelEmployeeList.get(0);
                    if(dmsChannelContacts != null){
                        UserSession newUserSession = getUserSession(dmsChannelContacts);

                        userSession.setId(newUserSession.getId());
                        userSession.setUserName(newUserSession.getUserName());
                        userSession.setOrgId(newUserSession.getOrgId());
                        userSession.setOrgName(newUserSession.getOrgName());
                        userSession.setSuperiorId(newUserSession.getSuperiorId());
                        userSession.setEmployeeType(newUserSession.getEmployeeType());
                        userSession.setLoginName(newUserSession.getLoginName());
                        userSession.setContactUserName(newUserSession.getContactUserName());
                        userSession.setOrgPath(newUserSession.getOrgPath());
                        userSession.setOrgCode(newUserSession.getOrgCode());
                        userSession.setImgUrl(newUserSession.getImgUrl());
                        userSession.setServiceProviderId(newUserSession.getServiceProviderId());
                        userSession.setServiceProviderName(newUserSession.getServiceProviderName());
                        userSession.setOrgIds(newUserSession.getOrgIds());
                        userSession.setLevel(newUserSession.getLevel());
                        userSession.setRoleId(newUserSession.getRoleId());
                        userSession.setRoleName(newUserSession.getRoleName());
                        userSession.setRoleCode(newUserSession.getRoleCode());
                        userSession.setSuperiorOrgId(newUserSession.getSuperiorOrgId());
                        userSession.setSuperiorOrgName(newUserSession.getSuperiorOrgName());
                        //DmsChannelMutilVo dmsChannelVo;//联系人经销商存储对象

                        userSession.setDmsChannelInfoVo(newUserSession.getDmsChannelInfoVo());
                        session.setAttribute("user", userSession);
                        map.put("user", userSession);
                        map.put("status", StatusEnum.SUCCESS.getStatus());
                    }else {
                        map.put("status", StatusEnum.ERROR.getStatus());
                        map.put("message", "该渠道下没有联系人，请联系管理员");
                    }
                }
            }else {
                map.put("status", StatusEnum.ERROR.getStatus());
                map.put("message", "渠道不存在");
            }
        }catch (Exception e){
            logger.error("**************** 切换户头失败：{} ****************", e.getMessage(), e);
        }
        return map;
    }

    /**
     * 短信验证码登录
     *
     * @return
     */
    @RequestMapping(value = "/phoneLogin", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "短信登录接口", response = UserSession.class)
    public Map<String, Object> phoneLogin(HttpSession session, @ApiParam("手机号码") @RequestParam String loginName, @ApiParam("短信验证码") @RequestParam String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (BlankUtil.isEmpty(loginName) || BlankUtil.isEmpty(password)) {
                map.put("status", StatusEnum.ERROR.getStatus());
                map.put("message", "手机号码或验证码为空！");
                return map;
            }
            //在redis中验证验证码是否正确
            String key="loginPhone"+loginName;
            ValueOperations<String, String> operations=redisTemplate.opsForValue();
            Object value = operations.get(key);
            if (value == null) {
                map.put("status", StatusEnum.ERROR.getStatus());
                map.put("message", "验证码不存在！");
                return map;
            }
            if (!password.equals(value.toString())) {
                map.put("status", StatusEnum.ERROR.getStatus());
                map.put("message", "验证码错误！");
                return map;
            }
            Map<String, Object> userMap=getUserByMobile(loginName);
            if((Boolean) userMap.get("status")){
                DmsUser user=(DmsUser) userMap.get("user");
                UserSession userSession = getUserSession(user);
                session.setAttribute("user", userSession);
                map.put("user", userSession);
                map.put("status", StatusEnum.SUCCESS.getStatus());
                if(redisTemplate.hasKey(key)) {
                    redisTemplate.delete(key);
                }
            }else {
                map=userMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error", e);
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", ErrorCodeEnum.DEFAULT.getMessage());
        }

        return map;
    }


    /**
     * 扫描用户登录
     * url:/api/v1/dms/login/login
     * 传入参数:
     * {
     * "qrCode":"343444ewwwwwwwwwwwww"
     * }
     *
     * @param qrCode
     * @param session
     * @return
     * @author kongjw
     * @Title login
     * @Description 登录逻辑处理
     */
    @RequestMapping(value = "/scanlogin", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "扫码登录接口", response = UserSession.class)
    public Map<String, Object> login(@ApiParam("token信息") @RequestParam String qrCode, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //根据二维码qrCode获取扫描状态
            KnQrcodeNum qrcodeNum = knQrcodeNumService.findByQrcode(qrCode);
            if (qrcodeNum!=null&&qrcodeNum.getLoginStatus()) {
                //成功
                String loginName = qrcodeNum.getLoginName();
                DmsUser dmsUser = new DmsUser();
                dmsUser.setAccount(loginName);
                List<DmsUser> dmsUserList = dmsUserService.selectByCondition(dmsUser);
                if(dmsUserList==null||dmsUserList.size()==0){
                    map.put("status", StatusEnum.ERROR.getStatus());
                    map.put("message", "该用户尚未登录！");
                    return map;
                }
                //DmsUser b = dmsUserList.get(0);
                //UserSession userSession = getUserSession(b);
                UserSession user=(UserSession)session.getAttribute("user");
                // 判断用户是否登录，如果没有，
                if (user == null || user.equals("")) {
                    map.put("status", StatusEnum.ERROR.getStatus());
                    map.put("message", "该用户尚未登录！");
                    return map;
                }
                //删除登录信息
                knQrcodeNumService.deleteByPrimaryKey(qrCode);
                map.put("user", user);
                map.put("status", StatusEnum.SUCCESS.getStatus());
            } else {
                map.put("status", StatusEnum.ERROR.getStatus());
                map.put("message", "登录失败！");
                return map;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("error", ex);
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", ErrorCodeEnum.DEFAULT.getMessage());
        }
        return map;
    }


    /**
     * 用户手机号码请求验证码
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "smsValid", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "扫码登录接口")
    public Map<String, Object> sendMsg(@ApiParam("手机号码") @RequestParam String mobile) {
        Map<String, Object> map = new HashMap<>();
        Pattern p = Pattern.compile("^[0-9]{11}$");
        Matcher m = p.matcher(mobile);
    /*	{
              "status" : true,	// 成功为true, 失败为false
			  "errorCode" : "",	// 成功为0，失败为500/400等
			  "errorMessage" : ""
			}*/
        if (!m.find()) {
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", "手机号必须为11位数字");
            return map;
        }
        int message = (int) (Math.random() * 9000 + 1000);

        ValueOperations<String, String> operations=redisTemplate.opsForValue();
        Object value = operations.get("loginPhone" + mobile);
        Integer valid = value == null ? null : Integer.parseInt(value.toString());

        message = (valid == null ? message : valid);
        String relmessage = "您的验证码：" + message + "，请在10分钟内完成输入。如非本人操作，请忽略。详情咨询客服4009980011";
        boolean isSuccess = sendMsg(mobile, relmessage);
        if (isSuccess) {
            operations.set("loginPhone" + mobile, message+"");
            redisTemplate.expire("loginPhone" + mobile, 600000L, TimeUnit.SECONDS);
            map.put("status", StatusEnum.SUCCESS.getStatus());
            map.put("message", "验证码已发送");
        } else {
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", "验证码发送失败");
        }
        return map;

    }

    /**
     * 验证手机验证码是否正确
     * @param mobile
     * @param password
     * @return
     */
    @RequestMapping(value = "validMsg", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "验证手机验证码")
    public Map<String,Object> validMsg(@ApiParam("手机号码") @RequestParam String mobile, @ApiParam("短信验证码") @RequestParam String password){
        Map<String, Object> map = new HashMap<String, Object>();

        if (BlankUtil.isEmpty(mobile) || BlankUtil.isEmpty(password)) {
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", "手机号码或验证码为空！");
            return map;
        }
        //在redis中验证验证码是否正确
        String key="loginPhone"+mobile;
        ValueOperations<String, String> operations=redisTemplate.opsForValue();
        Object value = operations.get(key);
        if (value == null) {
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", "验证码不存在！");
            return map;
        }
        if (!password.equals(value.toString())) {
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", "验证码错误！");
            return map;
        }
        map.put("status", StatusEnum.SUCCESS.getStatus());
        return map;
    }


    /**
     * 重置密码
     * @param mobile 手机号码
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "通过手机号码重置密码")
    public Map<String,Object> resetPassword(@ApiParam("手机号码") @RequestParam String mobile, @ApiParam("短信验证码") @RequestParam String password){
        Map<String, Object> map = new HashMap<String, Object>();
        if (BlankUtil.isEmpty(mobile) || BlankUtil.isEmpty(password)) {
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", "手机号码或密码为空！");
            return map;
        }
        Map<String, Object> userMap=getUserByMobile(mobile);
        if((Boolean) userMap.get("status")){
            DmsUser user=(DmsUser) userMap.get("user");
            user.setPassword(dmsUserService.encryptPassword(user.getSalt(), password));
            dmsUserService.updateStatus(user);
        }else {
            return userMap;
        }

        map.put("status", StatusEnum.SUCCESS.getStatus());
        return map;
    }


    /**
     * 通过手机号获取手机验证码
     * @param mobile
     * @return
     */
    private Map<String, Object> getUserByMobile(String mobile){
        Map<String, Object> map = new HashMap<String, Object>();
        DmsContactInfo dmsContactInfo = new DmsContactInfo();
        dmsContactInfo.setType(1);
        dmsContactInfo.setContent(mobile);
        List<DmsContactInfo> dmsContactInfos = dmsContactInfoService.selectByCondition(dmsContactInfo);
        if (BlankUtil.isEmpty(dmsContactInfos)) {
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", "该用户不存在,请先注册！");
            return map;
        }
        //关联表类型：1-渠道联系人；2-门店联系人；3-员工
        DmsContactInfo contactInfo = dmsContactInfos.get(0);
        DmsUserInfoDto dmsUserInfoDto = new DmsUserInfoDto();
        dmsUserInfoDto.setSource(contactInfo.getRelatedTableType());
        dmsUserInfoDto.setStaffId(contactInfo.getRelatedTableId());
        Long userId = dmsUserService.selectUser(dmsUserInfoDto);
        //查询
        DmsUser user = dmsUserService.selectByPrimaryKey(userId);
        if ("0".equals(user.getStatus() + "")) {
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", "该用户已失效！");
            return map;
        } else if ("2".equals(user.getStatus() + "")) {
            map.put("status", StatusEnum.ERROR.getStatus());
            map.put("message", "该用户处在审核中，暂不能登录！");
            return map;
        }
        map.put("user",user);
        map.put("status",StatusEnum.SUCCESS.getStatus());
        return map;
    }

    private boolean sendMsg(String phone, String message) {
        boolean isSuccess = false;
        JSONObject json = new JSONObject();
        json.put("phone", phone);
        json.put("message", message);
        //{"errorMessage":"","status":true,"errorCode":"0","data":null}
        String ret = HttpRequest.post("http://c.coracle.com:9712/jsse/api/sendSMS").header("X-xSimple-AppKey", "crvdev").header("X-xSimple-Token", "crmdev20160512").send(json.toString()).body();
        String errorCode = ret == null ? "" : (String) JSONObject.fromObject(ret).get("errorCode");
        if ("0".equals(errorCode)) {
            isSuccess = true;
        }
        return isSuccess;
    }


    private void getSuperiorChannel(Long id, UserSession userSession) {
        DmsChannel dmsChannel = dmsChannelService.selectByPrimaryKey(id);
        if (BlankUtil.isNotEmpty(dmsChannel)) {
            userSession.setSuperiorOrgId(dmsChannel.getId());
            userSession.setSuperiorOrgName(dmsChannel.getName());
        } else {
            setSuperiorDefaultValue(userSession);
        }
    }

    private void setSuperiorDefaultValue(UserSession userSession) {
        userSession.setSuperiorOrgId(userSession.getOrgId());
        userSession.setSuperiorOrgName(userSession.getOrgName());
    }

    /**
     * 检验密码的正确性
     *
     * @param plainPassword
     * @param password
     * @param salt
     * @return
     */
    private Boolean validate(String plainPassword, String password, String salt) {
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), Digests.HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(hashPassword));
    }

    private UserSession getUserSession(DmsUser dmsUser) {
        UserSession userSession = new UserSession();
        userSession.setId(dmsUser.getId());
        userSession.setLoginName(dmsUser.getAccount());
        DmsUserInfoDto dmsUserInfoDto = new DmsUserInfoDto();
        dmsUserInfoDto.setSource(dmsUser.getSource());
        dmsUserInfoDto.setStaffId(dmsUser.getStaffId());
        List<Map<String, Object>> list = dmsUserService.selectUserDetail(dmsUserInfoDto);

        // todo 用户登录还要处理
        if (BlankUtil.isNotEmpty(list)) {
            userSession.setOrgId(list.get(0).get("orgId") == null ? null : Long.parseLong(list.get(0).get("orgId").toString()));
            userSession.setUserName(list.get(0).get("userName") == null ? "" : list.get(0).get("userName").toString());
            userSession.setOrgName(list.get(0).get("orgName") == null ? "" : list.get(0).get("orgName").toString());
        }
        //根据用户id查询用户角色信息，当前用户只有一个角色，后期会考虑多角色
        DmsUserVo dmsUserVo = dmsUserService.selectRoleByUserId(dmsUser.getId());
        if (BlankUtil.isNotEmpty(dmsUserVo)) {
            userSession.setRoleId(dmsUserVo.getRoleId() == null ? null : dmsUserVo.getRoleId());
            userSession.setRoleCode(dmsUserVo.getRoleCode() == null ? "" : dmsUserVo.getRoleCode());
            userSession.setRoleName(dmsUserVo.getRoleName() == null ? "" : dmsUserVo.getRoleName());
        }
        dmsUser.setLastLoginDate(new Date());
        dmsUserService.updateByPrimaryKeySelective(dmsUser);
        DmsOrganization dmsOrganization = dmsOrganizationService.getRootOrganization();
        if (BlankUtil.isNotEmpty(dmsOrganization)) {
            userSession.setServiceProviderId(dmsOrganization.getId());
            userSession.setServiceProviderName(dmsOrganization.getName());
        }
        if ("CO".equals(dmsUserVo.getRoleCode())) {//渠道商
            DmsChannel dmsChannel = dmsChannelService.getChannelInfoByUserId(dmsUser.getId());
            if (BlankUtil.isNotEmpty(dmsChannel) && BlankUtil.isNotEmpty(dmsChannel.getParentId()) && dmsChannel.getParentId() > 0) {//有上级
                getSuperiorChannel(dmsChannel.getParentId(), userSession);
            } else {//无上级
                setSuperiorDefaultValue(userSession);
            }

            //获取渠道授信额度、是否超账期、是否已锁单 等
            Long startTime = System.currentTimeMillis();

            DmsChannelInfoVo dmsChannelInfoVo = dmsChannelService.getChannelInfo(dmsChannel.getId());
            userSession.setDmsChannelInfoVo(dmsChannelInfoVo);

            Long endTime = System.currentTimeMillis();
            double times = ((double) (endTime - startTime) / 1000);
            DecimalFormat df = new DecimalFormat("#.00");
            logger.info("调用客户接口耗时：{} 秒", df.format(times));

            //用户多户头（当前及所有下级渠道） add by taok 20180313
            //setSubsetChannelList(userSession, dmsChannel, null);

        } else if ("SR".equals(dmsUserVo.getRoleCode())) {
            DmsStore dmsStore = dmsStoreService.getStoreInfoByUserId(dmsUser.getId());
            if (BlankUtil.isNotEmpty(dmsStore)) {
                if (dmsStore.getOperateWay() == DmsModuleEnums.STORE_OPERATE_WAY.DIRECT.getType()) {//直营
                    if (BlankUtil.isNotEmpty(dmsOrganization)) {
                        userSession.setSuperiorOrgId(dmsOrganization.getId());
                        userSession.setSuperiorOrgName(dmsOrganization.getName());
                    } else {
                        setSuperiorDefaultValue(userSession);
                    }
                } else {
                    getSuperiorChannel(dmsStore.getBelongDealer(), userSession);
                }
            } else {
                setSuperiorDefaultValue(userSession);
            }
        } else {
            setSuperiorDefaultValue(userSession);
        }
        userSession.setImgUrl(dmsUser.getPhotoUrl());
        return userSession;
    }

    /**
     * 用户多户头（当前及所有下级渠道）
     * @param userSession
     * @param dmsChannel
     */
    private void setSubsetChannelList(UserSession userSession, DmsChannel dmsChannel, DmsChannelMutilVo dmsChannelMutilVo) {
        if(dmsChannel != null){
            if(dmsChannelMutilVo == null){
                DmsChannelMutilVo dmsChannelEntity = userSession.getDmsChannelVo();
                if(dmsChannelEntity == null){
                    dmsChannelEntity = new DmsChannelMutilVo();
                    userSession.setDmsChannelVo(dmsChannelEntity);
                }
                dmsChannelEntity.setId(dmsChannel.getId());
                dmsChannelEntity.setName(dmsChannel.getName());
                //dmsChannelEntity.setCode(dmsChannel.getCode());
                //dmsChannelEntity.setShortName(dmsChannel.getShortName());
                //dmsChannelEntity.setRank(dmsChannel.getRank());
                //dmsChannelEntity.setChannelType(dmsChannel.getChannelType());
                dmsChannelEntity.setAddress(dmsChannel.getAddress());
                dmsChannelEntity.setContacts(dmsChannel.getContacts());
                //dmsChannelEntity.setAnjoyId(dmsChannel.getAnjoyId());
                //dmsChannelEntity.setAnjoyParentId(dmsChannel.getAnjoyParentId());
                //dmsChannelEntity.setAnjoyCfbibscidId(dmsChannel.getAnjoyCfbibscidId());
                //dmsChannelEntity.setAnjoySaleOrgId(dmsChannel.getAnjoySaleOrgId());

                setSubsetChannelList(userSession, dmsChannel, dmsChannelEntity);
            }else {
                Long parentId = dmsChannelMutilVo.getId();
                DmsChannel dmsChannelCondition = new DmsChannel();
                dmsChannelCondition.setParentId(parentId);
                dmsChannelCondition.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                List<DmsChannel> subsetDmsChannelList = dmsChannelService.selectByCondition(dmsChannelCondition);
                if(subsetDmsChannelList != null && subsetDmsChannelList.size() > 0){
                    for(DmsChannel subsetDmsChannel : subsetDmsChannelList){
                        DmsChannelMutilVo dmsChannelEntity = new DmsChannelMutilVo();
                        dmsChannelEntity.setId(subsetDmsChannel.getId());
                        dmsChannelEntity.setName(subsetDmsChannel.getName());
                        //dmsChannelEntity.setCode(subsetDmsChannel.getCode());
                        //dmsChannelEntity.setShortName(subsetDmsChannel.getShortName());
                        //dmsChannelEntity.setRank(subsetDmsChannel.getRank());
                        //dmsChannelEntity.setChannelType(subsetDmsChannel.getChannelType());
                        dmsChannelEntity.setAddress(subsetDmsChannel.getAddress());
                        dmsChannelEntity.setContacts(subsetDmsChannel.getContacts());
                        //dmsChannelEntity.setAnjoyId(subsetDmsChannel.getAnjoyId());
                        //dmsChannelEntity.setAnjoyParentId(subsetDmsChannel.getAnjoyParentId());
                        //dmsChannelEntity.setAnjoyCfbibscidId(subsetDmsChannel.getAnjoyCfbibscidId());
                        //dmsChannelEntity.setAnjoySaleOrgId(subsetDmsChannel.getAnjoySaleOrgId());

                        dmsChannelMutilVo.getSubsetChannelList().add(dmsChannelEntity);

                        setSubsetChannelList(userSession, subsetDmsChannel, dmsChannelEntity);
                    }
                }
            }
        }
    }

    /**
     * 安全退出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout(HttpSession session) {
        session.invalidate();
        return session.getId();
    }

}
