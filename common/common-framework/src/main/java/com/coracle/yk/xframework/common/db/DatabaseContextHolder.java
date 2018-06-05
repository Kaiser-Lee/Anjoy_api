package com.coracle.yk.xframework.common.db;

import org.apache.log4j.Logger;

public class DatabaseContextHolder {
	private static final Logger LOG = Logger.getLogger(DatabaseContextHolder.class);
	
	public static final String DS_TYPE_CUSTOMER = "dataSourceCustomer";
	public static final String DS_TYPE_MANAGE = "dataSourceManage";
	public static final String DS_TYPE_COMMON = "dataSourceComm";
	public static final String DS_TYPE_PRODUCTION = "dataSourceProduct";
	public static final String DS_TYPE_SECURITY="dataSourceSecurity";
	public static final String DS_TYPE_ORG="dataSourceOrg";
	public static final String DS_TYPE_TRACK="dataSourceTrack";
	public static final String DS_TYPE_PROGRAM="dataSourceProgram";
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public synchronized static void setCustomerType(String customerType) {
		LOG.info("Youkee database exchange to " + customerType + "...");
		contextHolder.set(customerType);
	}

	public synchronized static String getCustomerType() {
		return contextHolder.get();
	}

	public synchronized static void clearCustomerType() {
		contextHolder.remove();
	}
}
