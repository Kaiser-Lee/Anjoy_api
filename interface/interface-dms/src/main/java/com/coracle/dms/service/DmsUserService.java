package com.coracle.dms.service;

import com.coracle.dms.dto.DmsUserDetailDto;
import com.coracle.dms.dto.DmsUserInfoDto;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.vo.DmsUserVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsUserService extends IBaseService<DmsUser> {

    /**
     * 新增账号
     * @param userVo
     * @param session
     */
    DmsUser create(DmsUserVo userVo, UserSession session);

    DmsUser create(DmsUserVo userVo);

    String encryptPassword(String salt, String plainPassword);

    PageHelper.Page<DmsUserVo> pageList(DmsUserVo userVo);

    /**
     * 重置密码
     * @param userId
     * @param plainPassword
     * @param session
     */
    void updatePassword(Long userId, String plainPassword, UserSession session);

    /**
     * 账号审核
     * @param user
     */
    void audit(DmsUser user);

    /**
     * 更改账号状态
     * @param user
     */
    void updateStatus(DmsUser user);

    /**
     * 根据用户的来源表类型和关联的id查询用户的其他信息，
     * 一般只有一条记录，但为了防止数据数目异常，用了list，一般取第一条
     * @param dmsUserInfoDto
     * @return
     */
    List<Map<String,Object>> selectUserDetail(DmsUserInfoDto dmsUserInfoDto);

    /**
     * 获取用户的个人信息
     * @param userSession
     * @return
     */
    DmsUserDetailDto getUserContactsDetail(UserSession userSession);

    /**
     * 更新用户基本信息以及联系信息
     */
    void updateUserInfo(DmsUserDetailDto dmsUserDetailDto,long userId);

    /**
     * 获取账号审核信息
     * @param userId
     * @return
     */
    DmsUserDetailDto getUserAccountAuditDetail(long userId);

    /**
     * 根据主键id获取Vo对象
     * @param id
     * @return
     */
    DmsUserVo selectVoByPrimaryKey(Long id);

    /**
     * 根据userId获取Vo对象
     * @param id
     * @return
     */
    DmsUserVo selectRoleByUserId(Long id);

    void updatePassword(Long userId, String plainPassword);

    void updatePassword(Long mxmId, String oldPassword, String newPassword, UserSession session);

    List<Long> selectMxmIdByUserIdList(List<Long> userIdList);

    void push();

    Long selectUser(DmsUserInfoDto dmsUserInfoDto);

    List<DmsUser> selectUserListByChannelId(Long channelId);

}