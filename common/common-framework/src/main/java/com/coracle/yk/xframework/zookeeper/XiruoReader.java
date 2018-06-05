package com.coracle.yk.xframework.zookeeper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("xiruoReader-xframwork")
public final class XiruoReader {
	@Value("${jdbc.driverClassName}")
	private String driverClass;
	@Value("${zoo.connectString}")
    private String configRoot;

	public String getConfigRoot() {
		return configRoot;
	}

	public void setConfigRoot(String configRoot) {
		this.configRoot = configRoot;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
}
