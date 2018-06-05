package com.coracle.yk.xservice.base.intf;

import java.util.List;

/**
 * Created by huangbaidong
 * 2017/3/31.
 */
public interface IBaseCacheManager<T> {

    /**
     * 得到一个缓存对象
     * @param key
     */
    T getOne(String key);

    /**
     * 得到一个缓存对象
     */
    List<T> getAll();

    /**
     * 添加到缓存
     * @param obj
     */
    boolean putInCache(T obj);

    /**
     * 添加到缓存
     * @param objs
     */
    boolean putInCache(List<T> objs);

    /**
     * 删除缓存
     * @param keys
     */
    void removeFromCache(List<String> keys);

    /**
     * 删除缓存
     * @param key
     */
    void removeFromCache(String key);

    /**
     * 清空缓存所有数据
     */
    void clearAll();
}
