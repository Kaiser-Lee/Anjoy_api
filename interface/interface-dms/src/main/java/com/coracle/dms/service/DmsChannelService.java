package com.coracle.dms.service;

import com.coracle.dms.po.DmsChannel;
import com.coracle.yk.xframework.po.DmsChannelInfoVo;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsChannelService extends IBaseService<DmsChannel> {
	/**
	 * 分页查询渠道
	 * @param searchVo
	 * @return
	 */
	Page<DmsChannelVo> selectForListPage(DmsChannelVo searchVo,UserSession session);
	
	/**
	 * 查询渠道列表数据
	 * @param searchVo
	 * @return
	 */
	List<Map<String,Object>> getList(DmsChannelVo searchVo);
	/**
	 * 根据id查询详情
	 * @param id
	 * @param session
	 * @return
	 */
	Map<String,Object> detail(Long id,UserSession session);
	/**
	 * 安井id查询详情
	 */

	Map<String,Object> ajdetail(Long id,UserSession session);
	
	/**
	 * 保存渠道数据
	 * @param paramVo
	 */
	void create(DmsChannelVo paramVo,UserSession session);
	
	/**
	 * 更新渠道数据
     * @param paramVo
     * @param session
     */
	void update(DmsChannelVo paramVo, UserSession session);

	/**
	 * 按照渠道名称查询列表
	 * @param name
	 * @return
	 */
	List<Long> selectChannelId(String name);

	/**
	 * 根据用户ID获取渠道信息
	 * @param id
	 * @return
	 */
	DmsChannel getChannelInfoByUserId(Long id);
	/**
	 * 安井渠道添加
	 */

	void ajcreate(DmsChannelVo paramVo,UserSession session);
	/**
	 * 安井渠道更新修改
	 */
	void ajupdate(DmsChannelVo paramVo);


	DmsChannelVo selectByUserId(Long userId);


	Page<DmsProductVo> findProductForMinimum(DmsProductVo product);


	List<TreeNode> selectRecursiveTree(Long cid);

    Map<String, DmsChannel> getChannelMap();

    void anjoySyn();

	/**
	 * 获取渠道授信额度
	 * @return
     */
	DmsChannelInfoVo getChannelCredit(Long channelId);

	boolean getAnjoyChannelIsLock(String channelAnjoyId);

	/**
	 * 判断渠道是否超账期
	 * true：已超，不允许下单
	 * false：未超，允许下单
	 * @param channelAnjoyCode
	 * @return
	 */
	boolean getAnjoyChannelOverAccountPeriod(String channelAnjoyCode);

	DmsChannelInfoVo getChannelInfo(Long channelId);

}