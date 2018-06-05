package com.coracle.dms.service;

import com.coracle.dms.po.DmsHomeImages;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsHomeImagesService extends IBaseService<DmsHomeImages> {

    /**
     * 保存首页轮播图片集合
     */
    void save(List<DmsHomeImages> indexImages, long userId);

    /**
     * 获取首页轮播图片集合
     */
    List<Map<String,Object>> getList(DmsHomeImages image);
}