/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsDataDictionay;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsDataDictionayMapper extends IMybatisDao<DmsDataDictionay> {

    List<DmsDataDictionay> findDictionayPageList(DmsDataDictionay dmsDataDictionay);

    List<DmsDataDictionay> findDictionayList(DmsDataDictionay dmsDataDictionay);

    DmsDataDictionay selectByName(String name);

    DmsDataDictionay selectBySkey(String sKey);

    void updateById(Long id);
}