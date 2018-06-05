/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.po.DmsChannelContacts;
import com.coracle.dms.vo.DmsChannelContactsVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

public interface DmsChannelContactsMapper extends IMybatisDao<DmsChannelContacts> {
	
	List<DmsChannelContactsVo> getPageList(DmsChannelContactsVo searchVo);
	
	DmsChannelContactsVo selectVoByPrimaryKey(Long id);
	/**
	 * 根据渠道id列表获取用户ID列表
	 * @param ids
	 * @return
	 */
	List<Long> getUserIdsByChannelIds(@Param("ids") List<Long> ids);

	/**
	 * 根据用户id查询渠道联系人
	 * @param userId
	 * @return
	 */
	DmsChannelContacts queryContactByUserId(Long userId);
}