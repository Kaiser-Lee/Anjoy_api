package com.coracle.dms.service;

import com.coracle.dms.po.DmsDataDictionayDependent;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsDataDictionayDependentService extends IBaseService<DmsDataDictionayDependent> {

    /**
     *
     * 通过数据项dictionayId，获取指定的数据项值集合
     */
    List<DmsDataDictionayDependent> findByDictionayId(Map<String,Object> map);

    /**
     *
     * 通过数据项值dependentDataItemId，获取指定的数据项值集合
     */
    List<DmsDataDictionayDependent> findByDependentDataItemId(Long dependentDataItemId);

    /**
     *
     * 通过id 软删除
     */
    void updateById(Long id);

    /**
     * 通过数据项key和数据项中对应的值获取name
     * @param key
     * @param value
     * @return
     */
    String getDataValueName(String key,String value);

    Map<String, String> getNameKeyMap(String key);

    List<DmsDataDictionayDependent> getListByDictKey(String key);
}