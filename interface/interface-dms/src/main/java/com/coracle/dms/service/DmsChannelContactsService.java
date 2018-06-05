package com.coracle.dms.service;

import java.util.List;
import java.util.Map;

import com.coracle.dms.po.DmsChannelContacts;
import com.coracle.dms.vo.DmsChannelContactsVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsChannelContactsService extends IBaseService<DmsChannelContacts> {
	
	/**
	 * 分页查询
	 * @param searchVo
	 * @return
	 */
	Page<DmsChannelContactsVo> selectForListPage(DmsChannelContactsVo searchVo);
	/**
	 * 保存渠道联系人数据
	 * @param contactsVo
	 */
	void create(DmsChannelContactsVo contactsVo,UserSession session);
	/**
	 * 修改联系人信息
	 * @param contactsVo
	 * @param session
	 */
	void update(DmsChannelContactsVo contactsVo, UserSession session);

	/**
	 * 查看联系人详情
	 * @param id
	 * @param session
	 * @return
	 */
	Map<String, Object> detail(Long id, UserSession session);

	/**
	 * 根据渠道id列表获取用户ID列表
	 * @param ids
	 * @return
	 */
	List<Long> getUserIdsByChannelIds(List<Long> ids);
	/**
	 * 根据用户id查询渠道联系人
	 * @param userId
	 * @return
	 */
	DmsChannelContactsVo queryContactByUserId(Long userId);
	/**
	 * 渠道联系人开通账号
	 * @param contactId
	 * @param session
	 */
	void createAccount(Long contactId,UserSession session);
}