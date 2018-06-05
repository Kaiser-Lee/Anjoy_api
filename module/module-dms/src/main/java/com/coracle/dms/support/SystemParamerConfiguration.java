package com.coracle.dms.support;

import com.coracle.dms.aop.SpringApplicationContext;
import com.coracle.dms.service.DmsGlobalVariableService;
import com.coracle.dms.service.DmsSerialNumService;
import com.coracle.yk.xframework.configuration.AbstractConfiguration;

public class SystemParamerConfiguration extends AbstractConfiguration {

    private static final long serialVersionUID = 1L;

    private static SystemParamerConfiguration systemParamerConfiguration;

    private DmsGlobalVariableService dmsGlobalVariableService;
    private DmsSerialNumService dmsSerialNumService;

    public static SystemParamerConfiguration getInstance() {
        if (systemParamerConfiguration == null) {
            systemParamerConfiguration = new SystemParamerConfiguration();
        }
        return systemParamerConfiguration;
    }

    private SystemParamerConfiguration(){
    	dmsGlobalVariableService = SpringApplicationContext.getBean(DmsGlobalVariableService.class);
        dmsSerialNumService = SpringApplicationContext.getBean(DmsSerialNumService.class);
    }

    @Override
    public String get(String key) {
        if (this.dmsGlobalVariableService == null) {
            return null;
        }
        return dmsGlobalVariableService.queryValueByKey(key);
    }

    @Override
    public boolean hasConfig(String key) {
        return this.get(key) != null;
    }

    public String createSerialNumStr(String type){
        if (this.dmsSerialNumService == null) {
            return null;
        }
        return dmsSerialNumService.createSerialNumStr(type);
    }
}
