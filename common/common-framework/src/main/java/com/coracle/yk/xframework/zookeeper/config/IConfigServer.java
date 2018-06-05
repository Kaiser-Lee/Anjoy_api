package com.coracle.yk.xframework.zookeeper.config;

public interface IConfigServer {
	void putConfig(String path, byte[] value) throws Exception;
	void removeConfig(String path) throws Exception;
	void clearConfig() throws Exception;
}
