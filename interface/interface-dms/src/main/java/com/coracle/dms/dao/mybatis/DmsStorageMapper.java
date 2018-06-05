/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coracle.dms.po.DmsStorage;
import com.coracle.dms.vo.DmsStorageVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsStorageMapper extends IMybatisDao<DmsStorage> {
	
	List<DmsStorageVo> getPageList(DmsStorageVo search);

	List<DmsStorageVo> getPageListForBill(DmsStorageVo search);

	/**
	 * 根据组织id查询仓库
	 * @param orgId
	 * @return
	 */
	DmsStorage selectByOrgId(Long orgId);
	
	DmsStorageVo selectDetailByPrimaryKey(Long id);
	/**
	 * 判断渠道编号是否存在
	 * @param oldCode
	 * @param code
	 * @return
	 */
	Integer getCountStorageCode(@Param("oldCode") String oldCode,@Param("code") String code);

	/**
	 * 根据渠道联系人的账号id获取渠道商的仓库
	 * @param userId
	 * @return
	 */
	DmsStorage selectByChannelContactUserId(Long userId);
	/**
	 * 获取仓库树
	 * @param paramVo
	 * @return
	 */
	List<TreeNode> selectStorageTree(DmsStorageVo paramVo);
	
	DmsStorage selectByRelation(@Param("relationId") Long relationId,@Param("relationType") Integer relationType);
}