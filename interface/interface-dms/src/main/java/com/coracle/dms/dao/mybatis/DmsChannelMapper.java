/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsChannel;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DmsChannelMapper extends IMybatisDao<DmsChannel> {
	/**
	 * 分页查询渠道信息
	 * @param channelVo
	 * @return
	 */
	List<DmsChannelVo> getPageList(DmsChannelVo channelVo);
	/**
	 * 查询渠道数据
	 * @param searchVo
	 * @return
	 */
	List<Map<String,Object>> getList(DmsChannelVo searchVo);
	/**
	 * 根据id查询渠道详情
	 * @param id
	 * @return
	 */
	DmsChannelVo selectDetailByPrimaryKey(Long id);
    /**
     * 按照渠道名称查询列表
     * @param name
     * @return
     */
    List<Long> selectChannelId(String name);

	DmsChannel getChannelInfoByUserId(Long id);

	/**
	 * 根据userId 查询渠道详情
	 */
	DmsChannelVo selectByUserId(Long userId);

	/**
	 * 渠道树形结构
	 */

	List<TreeNode> selectByParentId(Long pid);


	void updateMinOrderQuantity(@Param("minOrderQuantity") Long minOrderQuantity, @Param("id")Long id);

	/**
	 * 根据渠道编码获取渠道数量
     *
	 * @param code
	 * @return
	 */
	Integer countByCode(String code);

	void batchInsert(List<DmsChannel> list);

	/**
	 * 同步安井渠道数据前先将DMS所有渠道数据置为：已删除状态
	 */
	Integer deleteChannelSyncAnjoy();

}