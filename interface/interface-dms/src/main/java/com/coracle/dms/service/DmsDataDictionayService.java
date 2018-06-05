package com.coracle.dms.service;

import com.coracle.dms.po.DmsDataDictionay;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsDataDictionayService extends IBaseService<DmsDataDictionay> {

    /***
     *数据项列表查询
     * @return
     */
    public PageHelper.Page<DmsDataDictionay> findDictionayPageList(DmsDataDictionay dmsDataDictionay);

    /***
     *从属数据项列表
     * @return
     */
    public List<DmsDataDictionay> findDictionayList(DmsDataDictionay dmsDataDictionay);

    /**
     *
     * 通过名称，获取指定的数据项Id
     */
    DmsDataDictionay selectByName(String name);

    /**
     *
     * 通过数据项key，获取指定的数据项Id
     */
    DmsDataDictionay selectBySkey(String sKey);

    /**
     *
     * 新增数据项
     */
    void insertTo(DmsDataDictionay dmsDataDictionay);

    /**
     * 通过id 软删除
     */
    void updateById(Long id);
}