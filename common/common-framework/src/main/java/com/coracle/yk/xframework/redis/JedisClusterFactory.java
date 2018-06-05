package com.coracle.yk.xframework.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.util.StringUtils;

import redis.clients.jedis.HostAndPort;

import com.xiruo.medbid.util.Xiruo;

public final class JedisClusterFactory {
	private Set<HostAndPort> set;
	private int connectionTimeout;
	private int soTimeout;
	private int maxRedirections;
	private GenericObjectPoolConfig poolConfig;
	public JedisClusterFactory() {}
	
	public JedisClusterFactory(List<String> hostAndPortList, int connectionTimeout, int soTimeout,
			int maxRedirections, GenericObjectPoolConfig poolConfig) {
		this.connectionTimeout = connectionTimeout;
		this.soTimeout = soTimeout;
		this.maxRedirections = maxRedirections;
		this.poolConfig = poolConfig;
		set = new HashSet<HostAndPort>();
		HostAndPort hap = null;
		String[] array = null;
		for(String s : hostAndPortList) {
			array = StringUtils.split(s, ":");
			hap = new HostAndPort(array[0], Xiruo.nullToInt(array[1]));
			set.add(hap);
		}
	}
	
	public JedisClusterFactory(String servers, int connectionTimeout, int soTimeout,
			int maxRedirections, GenericObjectPoolConfig poolConfig) {
		this.connectionTimeout = connectionTimeout;
		this.soTimeout = soTimeout;
		this.maxRedirections = maxRedirections;
		this.poolConfig = poolConfig;
		set = new HashSet<HostAndPort>();
		HostAndPort hap = null;
		String[] array = null;
		for(String s : StringUtils.split(servers, ",")) {
			array = StringUtils.split(s, ":");
			hap = new HostAndPort(array[0], Xiruo.nullToInt(array[1]));
			set.add(hap);
		}
	}
	
	public YkJedisCluster create() {
		return new YkJedisCluster(set, connectionTimeout, soTimeout, maxRedirections, poolConfig);
	}
	
	public void returnJedisCluster(YkJedisCluster cluster) {
		if(null != cluster) {
			cluster.close();
			cluster = null;
		}
	}
	
	public void destroy() {
		if(null != set) {
			set.clear();
			set = null;
		}
	}
}
