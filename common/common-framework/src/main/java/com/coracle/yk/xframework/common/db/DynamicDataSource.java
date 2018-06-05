package com.coracle.yk.xframework.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {  
  
    @Override  
    protected synchronized Object determineCurrentLookupKey() {  
        return DatabaseContextHolder.getCustomerType();
    }
}
