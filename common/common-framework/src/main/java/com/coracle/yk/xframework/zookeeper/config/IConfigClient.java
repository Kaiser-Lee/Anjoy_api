package com.coracle.yk.xframework.zookeeper.config;

import java.util.List;

public interface IConfigClient {
	byte[] getConfig(String path) throws Exception;
	List<String> getConfigurations() throws Exception;
}
