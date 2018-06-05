/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.po.DmsChannelProduct;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsChannelProductMapper extends IMybatisDao<DmsChannelProduct> {
	/**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsChannelProduct> list);

    /**
     * 批量删除
     * @param list
     */
    void batchDelete(List<DmsChannelProduct> list);
    
    void deleteByChannelId(Long channelId);
    
    List<DmsChannelProduct> getChannelProductList(Long id);
}