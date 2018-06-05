/**
 * 
 */
package com.coracle.yk.xframework.redis.session;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.coracle.yk.xframework.redis.JedisClusterFactory;
import com.coracle.yk.xframework.redis.YkJedisCluster;

/**
 * @author Xiruo.Jiang
 * @Createdate 2015-11-12
 * @todo session机制，存在redis内存中，解决web集群中session共享问题
 * sessionKey: 客户端的IP加上浏览器的信息 
 */
@Service("session")
@Scope("singleton")
public final class YkSession {
	private final static String YK_SESSION_KEY = "YK_SESSION_KEY";
	@Autowired
	private JedisClusterFactory jedisClusterFactory;

	@PostConstruct
	public void init() {
		
	}
	@PreDestroy
	public void destory() {
		
	}
	
	//默认过期时间，20分钟
	@Value("${session.expiredTime.seconds}")
	private int expiredTime;
	
	/** 
     * @todo 构造函数 
     * @desc 建立redis连接，取得已有sessionID，并取得所有session的值 
     */  
    private YkSession() { 
        
    }
    
    public int getExpiredTime() {
		return expiredTime;
	}
    
	public void setExpiredTime(int expiredTime) {
		this.expiredTime = expiredTime;
		System.out.println(expiredTime);
	}

	/** 
     * @todo 设置session 值 
     * @desc 每次设置的值不会马上写入缓存，不过会记录在内存中，所以写入的值在当次也会有效 
     * @param 
     * @param any $value Session的值 
     */  
    public void setAttribute(String sessionKey, String name, String value) {  
    	YkJedisCluster cluster = jedisClusterFactory.create();
    	try {
    		checkSessionExpiredTime(cluster, sessionKey);
	        cluster.hset(sessionKey, name, value);
    	} finally {
    		if(null != cluster) {
    			jedisClusterFactory.returnJedisCluster(cluster);
    			cluster = null;
    		}
    	}
    }

	public String getAttribute(String sessionKey, String name) {  
		YkJedisCluster cluster = jedisClusterFactory.create();
    	try {
    		if(checkSessionExpiredTime(cluster, sessionKey)) {
    			return cluster.hget(sessionKey, name);
    		}
    		return null;
    	} finally {
    		if(null != cluster) {
    			jedisClusterFactory.returnJedisCluster(cluster);
    			cluster = null;
    		}
    	}
    }
	
	public List<String> getAttributes(String sessionKey, String...names) {  
		YkJedisCluster cluster = jedisClusterFactory.create();
    	try {
    		if(checkSessionExpiredTime(cluster, sessionKey)) {
    			return cluster.hmget(sessionKey, names);
    		}
    		return null;
    	} finally {
    		if(null != cluster) {
    			jedisClusterFactory.returnJedisCluster(cluster);
    			cluster = null;
    		}
    	}
    }

	/**
	 * @todo 取得session中所有的字段
	 * @desc 
	 * @return map
	 */
	public Map<String, String> getAll(String sessionKey) {
		YkJedisCluster cluster = jedisClusterFactory.create();
    	try {
    		if(checkSessionExpiredTime(cluster, sessionKey)) {
    			return cluster.hgetAll(sessionKey);
    		}
    		return null;
    	} finally {
    		if(null != cluster) {
    			jedisClusterFactory.returnJedisCluster(cluster);
    			cluster = null;
    		}
    	}
	}

	/** 
     * @todo 删除session中的值 
     * @param string $name session中的key 
     * @return 无返回值 
     */  
    public void removeAttribute(String sessionKey, String name) {  
    	YkJedisCluster cluster = jedisClusterFactory.create();
    	try {
    		if(checkSessionExpiredTime(cluster, sessionKey)) {
    			cluster.hdel(sessionKey, name);
    		}
    	} finally {
    		if(null != cluster) {
    			jedisClusterFactory.returnJedisCluster(cluster);
    			cluster = null;
    		}
    	}
    }
    
    public void removeAttributes(String sessionKey, String...names) {  
    	YkJedisCluster cluster = jedisClusterFactory.create();
    	try {
    		if(checkSessionExpiredTime(cluster, sessionKey)) {
    			cluster.hdel(sessionKey, names);
    		}
    	} finally {
    		if(null != cluster) {
    			jedisClusterFactory.returnJedisCluster(cluster);
    			cluster = null;
    		}
    	}
    }
    
    public void clearSession(String sessionKey) {
    	YkJedisCluster cluster = jedisClusterFactory.create();
    	try {
    		if(checkSessionExpiredTime(cluster, sessionKey)) {
    			cluster.del(sessionKey);
    		}
    	} finally {
    		if(null != cluster) {
    			jedisClusterFactory.returnJedisCluster(cluster);
    			cluster = null;
    		}
    	}
    }
    
    public boolean checkSessionExpiredTime(String sessionKey) {
    	YkJedisCluster cluster = jedisClusterFactory.create();
    	try {
    		return checkSessionExpiredTime(cluster, sessionKey);
    	} finally {
    		if(null != cluster) {
    			jedisClusterFactory.returnJedisCluster(cluster);
    			cluster = null;
    		}
    	}
    }
    
    public boolean checkSessionExpiredTime(YkJedisCluster cluster, String sessionKey) {
    	try {
			if(expiredTime <= 0 && cluster.get(YK_SESSION_KEY) == null) {
				cluster.del(sessionKey);
				return false;
			}
			return true;
    	} finally {
    		if(expiredTime > 0) {
    			cluster.setex(YK_SESSION_KEY, expiredTime, YK_SESSION_KEY);
    		}
    	}
    }
}
