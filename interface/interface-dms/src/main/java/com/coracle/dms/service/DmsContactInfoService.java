package com.coracle.dms.service;

import java.util.List;

import com.coracle.dms.po.DmsContactInfo;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsContactInfoService extends IBaseService<DmsContactInfo> {
	
	/**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsContactInfo> list);
    
    /**
     * 根据关联表类型和关联表主键id查询联系方式信息集合
     * @param search 查询条件
     */
    @Deprecated
    List<DmsContactInfo> findByCondition(DmsContactInfo search);

    /**
     * 新增/修改联系信息
     * @param contactInfoList
     */
    void insertOrUpdate(List<DmsContactInfo> contactInfoList);
    
    void deleteByRelated(DmsContactInfo entity);
}