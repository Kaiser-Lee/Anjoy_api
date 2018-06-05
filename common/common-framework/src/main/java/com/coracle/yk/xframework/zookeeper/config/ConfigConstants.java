package com.coracle.yk.xframework.zookeeper.config;

public final class ConfigConstants {
	public final static String DEFAULT_CONFIG_VALUE = "";
	public final static String DEFAULT_PASSWORD="123456";
	public final static String DEFAULT_CHARSET = "UTF-8";
	public final static String[] ZK_ADDRESS_ARRAY = {
		"127.0.0.1",
		"127.0.0.1",
		"127.0.0.1",
		"127.0.0.1",
		"127.0.0.1"
	};
	public final static int[] ZK_PORT_ARRAY = {
		2181,212,2183,2184,2185
	};
	public final static String CONFIG_ROOT = "/properties";
	public final static String NAME_SPACE = "config";
	
	public final static String ZOOKEEPER_CONFIG_DB = "ykee_comm";
	public final static String ZOOKEEPER_CONFIG_TABLE = "zookeeper_config";
}
