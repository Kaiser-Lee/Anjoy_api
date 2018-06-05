package com.coracle.dms.service;

import com.coracle.dms.dto.DmsRetailOutStorageDto;
import com.coracle.dms.po.DmsDynamic;
import com.coracle.dms.vo.DmsDynamicVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsDynamicService extends IBaseService<DmsDynamic> {

    /***
     * 新增客户动态
     * @param dmsDynamicVo
     */
    void addCustomerDynamic(DmsDynamicVo dmsDynamicVo, UserSession userSession);

    /**
     * 分页获取客户购买记录列表
     * @param dmsDynamicVo
     * @return
     */
    PageHelper.Page<DmsDynamicVo> selectForPage(DmsDynamicVo dmsDynamicVo);

    /**
     * 获取全部购买记录列表
     * @param dmsDynamicVo
     * @return
     */
    List<DmsDynamicVo> getAllList(DmsDynamicVo dmsDynamicVo);

    /***
     * 新增零售出库记录
     * @param dmsRetailOutStorageDto
     */
    void addRetailOutStorage(DmsRetailOutStorageDto dmsRetailOutStorageDto, UserSession userSession);
}