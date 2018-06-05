package com.coracle.yk.xdatabase.common.db;

public class DatabaseContextHolder {
	public static final String DS_TYPE_BASE = "dataSourceBase";
	public static final String DS_TYPE_MANAGE = "dataSourceManage";
	public static final String DS_TYPE_COMMON = "dataSourceCommon";
	public static final String DS_TYPE_PRODUCTION = "dataSourceProduction";
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public synchronized static void setCustomerType(String customerType) {
		contextHolder.set(customerType);
	}

	public synchronized static String getCustomerType() {
		return contextHolder.get();
	}

	public synchronized static void clearCustomerType() {
		contextHolder.remove();
	}
}
