/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsProductCategory;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsProductCategoryMapper extends IMybatisDao<DmsProductCategory> {
	
	void updateInvalidCategory(DmsProductCategory category);
	
	void deleteByIdSoft(Object id);
	
	List<DmsProductCategory> selectByPid(Long pid);
	
	List<TreeNode> selectByParentId(Long id);
	
	List<DmsProductCategory> selectByPathList(Long id);
	
	Integer getLevelMax();

	/**
	 * 批量插入
	 *
	 * @param productCategoryList
	 */
	void batchInsert(List<DmsProductCategory> productCategoryList);

	/**
	 * 根据安井的父id获取组织信息
	 *
	 * @param list
	 * @return
	 */
	List<DmsProductCategory> listByAnjoyParentId(List<String> list);

	List<DmsProductCategory> listByParentId(List<Long> list);

	List<DmsProductCategory> selectSon();

	DmsProductCategory selectByCode(String code);

	Integer deleteProductCategorySyncAnjoy();

	DmsProductCategory selectOneByCondition(DmsProductCategory dmsProductCategory);




}