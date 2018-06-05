/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsStorageInventory;
import com.coracle.dms.vo.DmsStorageInventoryVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DmsStorageInventoryMapper extends IMybatisDao<DmsStorageInventory> {

    /***
     * 库存列表查询
     * @return
     */
    List<DmsStorageInventoryVo> findStorageInventoryPageList(DmsStorageInventory dmsStorageInventory);

    /***
     * app-库存列表（按产品）
     * @return
     */
    List<Map<String,Object>> findStorageInventoryListByProduct(DmsStorageInventory dmsStorageInventory);

    /***
     * app-库存列表（按门店）
     * @return
     */
    List<Map<String,Object>> findStorageInventoryListByStore(Map<String, Object> param);
    /***
     * app-查询门店list
     * @return
     */
    List<Map<String,Object>> findStorageInventoryListByStoreList(DmsStorageInventoryVo dmsStorageInventory);

    /**
     * 获取库存详情
     * @param id
     * @return
     */
    DmsStorageInventory getDetails(Long id);

    /**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsStorageInventory> list);

    /**
     * 库存增减
     * @param
     */
    void addOrSubtract(DmsStorageInventoryVo paramVo);

    /**
     * 根据仓库id，货位id,产品id查询是否存库存数量
     * @param
     */
    List<DmsStorageInventory> findBySidLidPid(DmsStorageInventoryVo paramVo);

    /**
     * 根据产品id，产品规格id查询库存（app查看产品库存）
     * @param
     */
    Long findByProductId(DmsStorageInventoryVo paramVo);

    /***
     * app-库存列表（按产品查看详情）
     * @return
     */
    List<Map<String,Object>> findStorageInventoryListByProductId(DmsStorageInventoryVo paramVo);
    /***
     * app-库存列表（按产品查看详情）  数量统计
     * @return
     */
    Integer findStorageInventoryListByProductIdNum(DmsStorageInventoryVo paramVo);

    /**
     * 根据产品id和规格属性获取有库存的仓库[货位]信息
     * @param param
     * @return
     */
    List<TreeNode> selectStorageByOrderProduct(Map<String, Object> param);

    /**
     * 从视图中获取库存数量和
     * @param param
     * @return
     */
    DmsStorageInventoryVo selectByPrimaryKeyForView(Map<String, Object> param);
    /**
     * 查询仓库或者货位库存数量
     * @param storageId
     * @param storageLocalId
     * @return
     */
    Integer getStorageLocalCount(@Param("storageId") Long storageId,@Param("storageLocalId") Long storageLocalId);

    /**
     * 根据条件获取可用量
     * @param storageInventory
     * @return
     */
    DmsStorageInventoryVo selectVoByCondition(DmsStorageInventory storageInventory);
}