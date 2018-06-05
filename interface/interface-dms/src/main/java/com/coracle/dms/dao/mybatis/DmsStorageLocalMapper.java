/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.po.DmsStorageLocal;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsStorageLocalMapper extends IMybatisDao<DmsStorageLocal> {
	/**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsStorageLocal> list);
    
    void deleteByStorageId(Long storageId);
    /**
     * 根据仓库id查询仓库货位
     * @param id
     * @return
     */
    List<DmsStorageLocal> getStorageLocalList(Long id);
}