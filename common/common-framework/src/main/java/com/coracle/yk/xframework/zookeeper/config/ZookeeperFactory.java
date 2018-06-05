package com.coracle.yk.xframework.zookeeper.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("zookeeperFactory")
@Scope("singleton")
public final class ZookeeperFactory {
    private static final ZookeeperFactory instance = new ZookeeperFactory();
    public static ZookeeperFactory getInstance() {
    	return instance;
    }
	@Value("${zoo.connectString}")
    private String connectString;
    @Value("${zoo.maxRetries}")
    private int maxRetries;
    @Value("${zoo.baseSleepTimems}")
    private int baseSleepTimems;
    
    private CuratorFramework client;
    
    public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public int getBaseSleepTimems() {
		return baseSleepTimems;
	}

	public void setBaseSleepTimems(int baseSleepTimems) {
		this.baseSleepTimems = baseSleepTimems;
	}

	private ZookeeperFactory() {}
	
	@PostConstruct
	public void init() {
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
 
    public CuratorFramework getCuratorFramework() {
        return client;
    }
}
