/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.po.DmsStorageArea;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsStorageAreaMapper extends IMybatisDao<DmsStorageArea> {
	
	/**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsStorageArea> list);
    
    void deleteByStorageId(Long storageId);
    
    List<DmsStorageArea> getStorageAreaList(Long id);
}