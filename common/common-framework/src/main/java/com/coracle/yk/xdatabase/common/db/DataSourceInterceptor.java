package com.coracle.yk.xdatabase.common.db;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class DataSourceInterceptor {
	
	public synchronized void setDataSourceBase(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DS_TYPE_BASE);
	}
	
	public synchronized void setDataSourceManage(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DS_TYPE_MANAGE);
	}
	
	public synchronized void setDataSourceCommon(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DS_TYPE_COMMON);
	}
	
	public synchronized void setDataSourceProduction(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DS_TYPE_PRODUCTION);
	}
}
