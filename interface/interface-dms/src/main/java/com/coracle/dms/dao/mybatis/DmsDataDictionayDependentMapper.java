/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsDataDictionayDependent;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DmsDataDictionayDependentMapper extends IMybatisDao<DmsDataDictionayDependent> {

    List<DmsDataDictionayDependent> findByDictionayId(Map<String,Object> map);
    List<DmsDataDictionayDependent> findByDependentDataItemId(Long dependentDataItemId);
    void updateById(Long id);

    String getDataValueName(@Param("sKey") String sKey,@Param("dataValue") String dataValue);
}