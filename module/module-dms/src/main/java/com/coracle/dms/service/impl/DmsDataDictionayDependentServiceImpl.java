package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsDataDictionayDependentMapper;
import com.coracle.dms.po.DmsDataDictionay;
import com.coracle.dms.po.DmsDataDictionayDependent;
import com.coracle.dms.service.DmsDataDictionayDependentService;
import com.coracle.dms.service.DmsDataDictionayService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DmsDataDictionayDependentServiceImpl extends BaseServiceImpl<DmsDataDictionayDependent> implements DmsDataDictionayDependentService {
    private static final Logger logger = Logger.getLogger(DmsDataDictionayDependentServiceImpl.class);

    @Autowired
    private DmsDataDictionayDependentMapper dmsDataDictionayDependentMapper;

    @Autowired
    private DmsDataDictionayService dmsDataDictionayService;

    @Override
    public IMybatisDao<DmsDataDictionayDependent> getBaseDao() {
        return dmsDataDictionayDependentMapper;
    }

    @Override
    public List<DmsDataDictionayDependent> findByDictionayId(Map<String,Object> map) {
        return dmsDataDictionayDependentMapper.findByDictionayId(map);
    }

    @Override
    public List<DmsDataDictionayDependent> findByDependentDataItemId(Long dependentDataItemId) {
        return dmsDataDictionayDependentMapper.findByDependentDataItemId(dependentDataItemId);
    }

    @Override
    public void updateById(Long id) {
        dmsDataDictionayDependentMapper.updateById(id);
    }

    /**
     * 通过数据项key和数据项中对应的值获取name
     * @param key
     * @param value
     * @return
     */
    @Override
    public String getDataValueName(String key, String value) {
        if (BlankUtil.isEmpty(key)||BlankUtil.isEmpty(value)){
            throw new ServiceException("查询的key与value不能为空");
        }
        return dmsDataDictionayDependentMapper.getDataValueName(key,value);
    }

    /**
     * 根据数据字典key获取其下数据字典项列表的name - key映射
     *
     * @param key
     * @return
     */
    @Override
    public Map<String, String> getNameKeyMap(String key) {
        Map<String, String> resultMap = Maps.newHashMap();

        DmsDataDictionay dictionay = dmsDataDictionayService.selectBySkey(key);
        if (dictionay != null) {
            Map<String, Object> param = Maps.newHashMap();
            param.put("dictionaryId", dictionay.getId());
            List<DmsDataDictionayDependent> dictionayDependentList = dmsDataDictionayDependentMapper.findByDictionayId(param);
            for (DmsDataDictionayDependent ddd : dictionayDependentList) {
                resultMap.put(ddd.getName(), ddd.getsKey());
            }
        } else {
            throw new ServiceException("数据字典不存在：{}", key);
        }
        return resultMap;
    }

    public List<DmsDataDictionayDependent> getListByDictKey(String key) {
        List<DmsDataDictionayDependent> dictionayDependentList = new ArrayList<>();

        DmsDataDictionay dictionay = dmsDataDictionayService.selectBySkey(key);
        if (dictionay != null) {
            Map<String, Object> param = Maps.newHashMap();
            param.put("dictionaryId", dictionay.getId());
            dictionayDependentList = dmsDataDictionayDependentMapper.findByDictionayId(param);
        } else {
            throw new ServiceException("数据字典不存在：{}", key);
        }
        return dictionayDependentList;
    }
}