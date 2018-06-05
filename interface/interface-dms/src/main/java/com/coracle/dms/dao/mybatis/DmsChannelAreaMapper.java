/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.po.DmsChannelArea;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsChannelAreaMapper extends IMybatisDao<DmsChannelArea> {
	
	/**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsChannelArea> list);

    /**
     * 批量删除
     * @param list
     */
    void batchDelete(List<DmsChannelArea> list);
    
    void deleteByChannelId(Long channelId);
    
    List<DmsChannelArea> getChannelAreaList(Long id);

    /**
     * 根据渠道id获取销售区域id列表
     * @param channelId
     * @return
     */
    List<Long> selectAreaIdByChannelId(Long channelId);
}