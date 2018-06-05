package com.coracle.yk.xframework.zookeeper.config.client;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.coracle.yk.xframework.zookeeper.config.ConfigConstants;
import com.coracle.yk.xframework.zookeeper.config.ZookeeperIConfigImpl;

public class ZookeeperPropertyPlaceholderConfigurerLoader extends
		PropertyPlaceholderConfigurer {
    private String connectString;
    private int maxRetries;
    private int baseSleepTimems;
    
    private CuratorFramework client;
	
	@PostConstruct
	public void init() {
		
	}
	
	private void initClient() {
		if(client == null) {
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimems, maxRetries);
	        client = CuratorFrameworkFactory.builder()
	                .connectString(connectString)
	                .retryPolicy(retryPolicy)
	                .namespace(ConfigConstants.NAME_SPACE)
	                .build();
	        client.start();
		}
	}
	
	@PreDestroy
	public void destroy() {
		CloseableUtils.closeQuietly(client);
	}

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		//读取properties信息
		super.processProperties(beanFactoryToProcess, props);
		loadConfigurationFromZookeeper(props);
		//${...}转换
		super.processProperties(beanFactoryToProcess, props);
//		for(Entry<Object, Object> entry  : props.entrySet()) {
//			System.out.println(entry.getKey() + ":" + entry.getValue());
//		}
	}
	
	/**
	 * 
	 */
	private void loadConfigurationFromZookeeper(Properties props) {
		connectString = props.getProperty("zoo.connectString");
		maxRetries = Integer.parseInt(props.getProperty("zoo.maxRetries"));
		baseSleepTimems = Integer.parseInt(props.getProperty("zoo.baseSleepTimems"));
		try {
			initClient();
			for(String key : ZookeeperIConfigImpl.getConfigurations(client)) {
//				System.out.println(key + "=" + new String(ZookeeperIConfigImpl.getConfig(client, ConfigConstants.CONFIG_ROOT + "/" + key), ConfigConstants.DEFAULT_CHARSET));
				props.put(key, new String(ZookeeperIConfigImpl.getConfig(client, ConfigConstants.CONFIG_ROOT + "/" + key), ConfigConstants.DEFAULT_CHARSET));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
