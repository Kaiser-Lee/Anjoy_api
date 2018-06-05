/**
 * create by lenovo
 * @date 2018-03
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsChannelProductAnjoyWhiteList;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;
import java.util.Map;

public interface DmsChannelProductAnjoyWhiteListMapper extends IMybatisDao<DmsChannelProductAnjoyWhiteList> {
    /**
     * 将所有产品白名单置为已删除状态
     * @return
     */
    Integer deleteProductWhiteList();

    /**
     * 判断经销商-产品关系记录是否存在
     * @param paramMap
     * @return
     */
    DmsChannelProductAnjoyWhiteList selectChannelProductExist(Map<String, Object> paramMap);

    /**
     * 批处理，通过渠道CODE，产品CODE 更新 渠道ID，产品ID
     */
    Integer updateChannelProductRelation();

    Integer batchInsert(List dataList);
}