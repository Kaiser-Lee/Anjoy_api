/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsStorageOutInRecord;
import com.coracle.dms.vo.DmsStorageOutInRecordVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsStorageOutInRecordMapper extends IMybatisDao<DmsStorageOutInRecord> {

    /***
     * 出入库记录列表查询
     * @return
     */
    List<DmsStorageOutInRecord> findStorageOutInRecordPageList(DmsStorageOutInRecord outInStorageRecord);

    /***
     * 出入库记录列表查询, 根据ids选择性导出sql查询
     * @return
     */
    List<DmsStorageOutInRecordVo> findStorageOutInRecordByIds(List<Integer> exPortIds);

    /***
     * app--按门店，产品查询出入库记录列表
     * @return
     */
    List<DmsStorageOutInRecordVo> findStorageOutInRecordList(DmsStorageOutInRecordVo outInStorageRecord);

    /***
     * app--按门店，产品查询出入库记录列表数量
     * @return
     */
    Integer findStorageOutInRecordListNum(DmsStorageOutInRecordVo outInStorageRecord);

    /**
     * 获取出入库记录详情
     * @param id 产品id
     * @return
     */
    DmsStorageOutInRecord getDetails(Long id);

    /**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsStorageOutInRecord> list);
}