/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsStore;
import com.coracle.dms.vo.DmsStoreVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsStoreMapper extends IMybatisDao<DmsStore> {
    /**
     * 分页获取门店列表
     * @param dmsStoreVo
     * @return
     */
    List<DmsStoreVo> getPageList(DmsStoreVo dmsStoreVo);

    /**
     * 分页获取门店列表
     * @param dmsStoreVo
     * @return
     */
    List<DmsStoreVo> getPageListByApp(DmsStoreVo dmsStoreVo);

    DmsStoreVo getStoreDetailByPrimaryKey(Long id);

    DmsStore getStoreInfoByUserId(Long id);

    String getStoreIdsByChannelId(Long id);

    /**
     * 根据渠道id获取渠道下的门店id列表
     *
     * @param channelId
     * @return
     */
    List<Long> selectStoreIdListByChannelId(Long channelId);
}