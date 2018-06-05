/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsProductAttachFile;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsProductAttachFileMapper extends IMybatisDao<DmsProductAttachFile> {

    /**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsProductAttachFile> list);

    /**
     * 批量删除
     * @param list
     */
    void batchDelete(List<DmsProductAttachFile> list);

}