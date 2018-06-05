package com.coracle.dms.service;

import com.coracle.dms.po.DmsStorageInventory;
import com.coracle.dms.vo.DmsStorageInventoryVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsStorageInventoryService extends IBaseService<DmsStorageInventory> {
    /***
     * 库存列表查询
     * @return
     */
    PageHelper.Page<DmsStorageInventoryVo> findStorageInventoryPageList(DmsStorageInventoryVo dmsStorageInventory);

    /***
     * app-库存列表（按产品）
     * @return
     */
    PageHelper.Page<Map<String,Object>> findStorageInventoryListByProduct(DmsStorageInventoryVo dmsStorageInventory,UserSession userSession);

    /***
     * app-库存列表（按门店）
     * @return
     */
    PageHelper.Page<Map<String,Object>> findStorageInventoryListByStoreList(DmsStorageInventoryVo dmsStorageInventoryVo,UserSession userSession);

    /***
     * app-库存列表（按门店获取库存列表，不按规格区分，合并产品库存）
     * @return
     */
    PageHelper.Page<Map<String,Object>> findStorageInventoryListByStore(DmsStorageInventoryVo dmsStorageInventoryVo,UserSession userSession);

    /**
     * 获取库存详情
     * @param id
     * @return
     */
    DmsStorageInventory getDetails(Long id);

    /**
     * 新增库存记录
     * @param
     */
    void create(DmsStorageInventory dmsStorageInventory);

    /**
     * 库存增减
     * @param
     */
    void addOrSubtract(DmsStorageInventoryVo paramVo, UserSession userSession);

    /**
     * 库存调整
     * @param
     */
    void adjustment(DmsStorageInventoryVo paramVo,UserSession userSession);

    /**
     * 根据产品id，产品规格id查询库存（app查看产品库存）
     * @param
     */
    Long findByProductId(DmsStorageInventoryVo paramVo);

    /***
     * app-库存列表（按门店）
     * @return
     */
    PageHelper.Page<Map<String,Object>> getDetail(DmsStorageInventoryVo paramVo,UserSession userSession);

    DmsStorageInventoryVo selectVoCondition(DmsStorageInventory storageInventory);
}