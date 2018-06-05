package com.coracle.yk.xframework.zookeeper.config.spring;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.coracle.yk.xframework.zookeeper.config.ConfigConstants;
import com.coracle.yk.xframework.zookeeper.config.IConfig;

public class ZookeeperPropertyPlaceholderConfigurerPusher extends PropertyPlaceholderConfigurer {
	private Properties props;
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		this.props = props;
	}
	
	public void init() {}
	public void destroy() {
		if(props != null) {
			props.clear();
			props = null;
		}
	}
	
	public void pushConfiguration2Zookeeper(IConfig zookeeperIConfigImpl) {
		try {
			if(props.isEmpty()) {
				System.out.println("Warning Properties: Not any config-item in properties file is pushed to zookeeper server...");
			} else {
				for(Object key : props.keySet()) {
	//				System.out.println(key + "=" + props.getProperty((String)key));
					zookeeperIConfigImpl.putConfig((String)key, props.getProperty((String)key, ConfigConstants.DEFAULT_CONFIG_VALUE).getBytes(ConfigConstants.DEFAULT_CHARSET));
				}
				System.out.println("Put global.properties to zookeeper completed...");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
