package com.coracle.dms.service;

import java.util.List;

import com.coracle.dms.po.DmsStorage;
import com.coracle.dms.po.DmsStorageLocal;
import com.coracle.dms.vo.DmsStorageVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsStorageService extends IBaseService<DmsStorage> {
	/**
	 * 分页查询仓库列表
	 * @param search
	 * @return
	 */
	Page<DmsStorageVo> selectForListPage(DmsStorageVo search);

	/**
	 * 入库单仓库列表（入库单只能选择品牌商仓库）
	 * @param search
	 * @return
	 */
	Page<DmsStorageVo> selectForBillListPage(DmsStorageVo search);
	
	/**
	 * 查看仓库详情
	 * @param id
	 * @return
	 */
	DmsStorageVo detail(Long id);
	/**
	 * 新增仓库
	 * @param paramVo
	 */
	void create(DmsStorageVo paramVo);
	
	/**
	 * 修改仓库
	 * @param paramVo
	 */
	void update(DmsStorageVo paramVo);
	/**
	 * 根据组织id查询仓库
	 * @param orgId
	 * @return
	 */
	DmsStorage selectByOrgId(Long orgId);
	/**
	 * 查询货位
	 * @param search
	 * @return
	 */
	Page<DmsStorageLocal> selectLocalForListPage(DmsStorageLocal search);
	/**
	 * 新增仓库货位
	 * @param local
	 */
	void createLocal(DmsStorageLocal local);
	/**
	 * 修改货位
	 * @param local
	 */
	void updateLocal(DmsStorageLocal local);
	/**
	 * 删除货位
	 * @param id
	 */
	void deleteLocal(Long id);

    DmsStorage selectByChannelContactUserId(Long userId);
    
    List<TreeNode> selectStorageTree(DmsStorageVo paramVo);
    /**
     * 根据关联id和类型查询仓库
     * @param relationId
     * @param relationType
     * @return
     */
    DmsStorage selectByRelation(Long relationId,Integer relationType);
}