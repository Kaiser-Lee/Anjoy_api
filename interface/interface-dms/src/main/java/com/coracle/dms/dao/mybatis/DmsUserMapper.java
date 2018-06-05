/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.dto.DmsUserInfoDto;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.vo.DmsUserVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DmsUserMapper extends IMybatisDao<DmsUser> {

    /**
     * 根据条件获取账号Vo对象
     * @param userVo
     * @return
     */
    List<DmsUserVo> selectVoByCondition(DmsUserVo userVo);

    /**
     * 根据主键id获取Vo对象
     * @param id
     * @return
     */
    DmsUserVo selectVoByPrimaryKey(Long id);

    /**
     * 根据账号获取数量
     * @param account
     * @return
     */
    Integer selectCountByAccount(String account);

    /**
     * 根据用户的来源表类型和关联的id查询用户的其他信息，
     * 一般只有一条记录，但为了防止数据数目异常，用了list，一般取第一条
     * @param dmsUserInfoDto
     * @return
     */
    List<Map<String,Object>> selectUserDetail(DmsUserInfoDto dmsUserInfoDto);

    /**
     * 获取门店店员账号信息
     * @param id
     * @return
     */
    DmsUser getDmsAccountContactList(Long id);
    /**
     * 获取渠道联系人账号
     * @param id
     * @return
     */
    DmsUser getChannelAccount(Long id);

    /**
     * 根据userId获取role
     * @param id
     * @return
     */
    DmsUserVo selectRoleByUserId(Long id);

    /**
     * 根据mxmId获取用户信息
     * @param mxmId
     * @return
     */
    DmsUser selectByMxmId(Long mxmId);

    /**
     * 根据账号id列表获取账号对应的mxm用户的id列表
     * @param userIdList
     * @return
     */
    List<Long> selectMxmIdByUserIdList(List<Long> userIdList);

    /**
     * 根据用户id获取用户姓名
     *
     * @param id
     * @return
     */
    String selectNameById(Long id);

    /**
     * 查询用户Id
     * @return
     */
    Long selectUserId(DmsUserInfoDto dmsUserInfoDto);

    List<DmsUser> selectUserListByChannelId(@Param(value = "channelId") Long channelId);

}
