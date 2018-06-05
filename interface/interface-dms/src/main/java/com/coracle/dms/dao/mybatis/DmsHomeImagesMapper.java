/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsHomeImages;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DmsHomeImagesMapper extends IMybatisDao<DmsHomeImages> {

    /**
     * 批量更新删除状态
     * @param type
     */
    void batchDelete(@Param(value = "type") Integer type);

    /**
     * 批量增加
     * @param list
     */
    void batchInsert(List<DmsHomeImages> list);

    /**
     * 获取全部图片列表
     * @param image
     * @return
     */
    List<Map<String,Object>> findAll(DmsHomeImages image);
}