package com.coracle.yk.xframework.zookeeper.config;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("zookeeperIConfigImpl")
public class ZookeeperIConfigImpl implements IConfig {
	private CuratorFramework client = null;
	@Autowired
	private ZookeeperFactory zookeeperFactory;
	
	@PostConstruct
	public void init() {
		client = zookeeperFactory.getCuratorFramework();
		if(!exists(ConfigConstants.CONFIG_ROOT)) {
			try {
				//不能建立临时节点，临时节点不支持子节点
//				client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(ConfigConstants.CONFIG_ROOT);
				client.create().withMode(CreateMode.PERSISTENT).forPath(ConfigConstants.CONFIG_ROOT);
			} catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
	}
	
	@PreDestroy
	public void destroy() {
		client = null;
	}

    @Override
    public byte[] getConfig(String path) throws Exception {
    	path = ConfigConstants.CONFIG_ROOT + "/" + path;
        return getConfig(client, path);
    }
    
    public static byte[] getConfig(CuratorFramework client, String path) throws Exception {
        if (!exists(client, path)) {
            throw new RuntimeException("Path " + path + " does not exists.");
        }
        return client.getData().forPath(path);
    }
    
    @Override
    public void putConfig(String path, byte[] data) throws Exception {
    	path = ConfigConstants.CONFIG_ROOT + "/" + path;
    	putConfig(client, path, data);
    }
    
    public static void putConfig(CuratorFramework client, String path, byte[] data) throws Exception {
    	if (!exists(client, path)) {
            client.create().forPath(path);
        }
    	client.setData().forPath(path, data);
    }
    
    @Override
    public List<String> getConfigurations() throws Exception {
        return getConfigurations(client);
    }
    
    public static List<String> getConfigurations(CuratorFramework client) throws Exception {
        if (!exists(client, ConfigConstants.CONFIG_ROOT)) {
            throw new RuntimeException("Path " + ConfigConstants.CONFIG_ROOT + " does not exists.");
        }
        return client.getChildren().forPath(ConfigConstants.CONFIG_ROOT);
    }
     
    private boolean exists(String path) {
    	return exists(client, path);
    }
    
    public static boolean exists(CuratorFramework client, String path) {
    	Stat stat = null;
    	try {
    		stat = client.checkExists().forPath(path);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
        return !(stat == null);
    }

	@Override
	public void removeConfig(String path) throws Exception {
		if (!exists(client, path)) {
            throw new RuntimeException("Path " + path + " does not exists.");
        }
        client.delete().forPath(path);
	}

	@Override
	public void clearConfig() throws Exception {
		if (!exists(ConfigConstants.CONFIG_ROOT)) {
            throw new RuntimeException("Path " + ConfigConstants.CONFIG_ROOT + " does not exists.");
        }
        client.delete().forPath(ConfigConstants.CONFIG_ROOT);
	}
}
