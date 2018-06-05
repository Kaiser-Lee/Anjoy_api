package com.coracle.dms.service;

import com.coracle.dms.po.DmsStorageOutInRecord;
import com.coracle.dms.vo.DmsStorageOutInRecordVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsStorageOutInRecordService extends IBaseService<DmsStorageOutInRecord> {

    /***
     * 出入库记录列表查询
     * @return
     */
    PageHelper.Page<DmsStorageOutInRecordVo> findStorageOutInRecordPageList(DmsStorageOutInRecordVo outInStorageRecord);

    /***
     * 出入库记录列表查询, 根据ids选择性导出sql查询
     * @return
     */
    List<DmsStorageOutInRecordVo> findStorageOutInRecordByIds(List<Integer> exPortIds);

    /***
     * app--按门店，产品查询出入库记录列表
     * @return
     */
    PageHelper.Page<DmsStorageOutInRecordVo> findStorageOutInRecordList(DmsStorageOutInRecordVo outInStorageRecord);

    /**
     * 获取出入库记录详情
     * @param id
     * @return
     */
    DmsStorageOutInRecord getDetails(Long id);

    /**
     * 新增出入库记录
     * @param param
     */
    void create(DmsStorageOutInRecord param);

}