package com.coracle.dms.init;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by huangbaidong
 * 初始化缓存加载类
 * 2017/3/31.
 */
@Component
public class CacheLoader {

    @PostConstruct
    public void load() {
        //二期会加
//        loadDictionarys();
//        loadSystemConfig();
//        loadDFKOrdersInCache();
//        loadDSHOrdersInCache();
    }

}
