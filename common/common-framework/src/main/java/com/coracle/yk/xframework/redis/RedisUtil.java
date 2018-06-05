package com.coracle.yk.xframework.redis;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis cache 工具类
 * 
 */
public final class RedisUtil {
	private Logger logger = Logger.getLogger(RedisUtil.class);
	private RedisTemplate<Serializable, Object> redisTemplate;
	private String redisIp;

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
//		LogUtil.error("-----------------------------redisIp"+redisIp);
//		System.err.println("-----------------------------redisIp"+redisIp);
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
//		LogUtil.error("-----------------------------redisIp"+redisIp);
//		System.err.println("-----------------------------redisIp" + redisIp);
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
//		LogUtil.error("-----------------------------redisIp"+redisIp);
//		System.err.println("-----------------------------redisIp"+redisIp);
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public String getRedisIp() {
		return redisIp;
	}

	public void setRedisIp(String redisIp) {
		this.redisIp = redisIp;
	}


	/**
	 * 设置新值，同时返回旧值
	 * @param lockKey
	 * @param stringOfLockExpireTime
	 * @return
	 */
	public String getSet(final String lockKey, final String stringOfLockExpireTime) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
				byte[] bytes = redisConnection.getSet(lockKey.getBytes(), stringOfLockExpireTime.getBytes());
				if(bytes != null) {
					return new String(bytes);
				}
				return null;
			}
		});
		return result;
	}

	/**
	 * 如果不存在key则插入
	 * @param lockKey
	 * @param stringOfLockExpireTime
	 * @return true 插入成功， false 插入失败
	 */
	public boolean setnx(final String lockKey, final String stringOfLockExpireTime) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.setNX(lockKey.getBytes(), stringOfLockExpireTime.getBytes());
			}
		});
	}

	/**
	 * setnx 和 getSet方式插入的数据，调用此方法获取
	 * @param key
	 * @return
	 */
	public String getInExecute(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
				byte[] bytes = redisConnection.get(key.getBytes());
				if (bytes == null) {
					return null;
				} else {
					return new String(bytes);
				}
			}
		});
		return result;
	}

	/**
	 * 将缓存保存在map集合中
	 * @param redisKey
	 * @param mapKey
	 * @param mapValue
	 * @return
	 */
	public boolean putInMap(final String redisKey, String mapKey, Object mapValue) {
		boolean result = false;
		try {
			HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
			operations.put(redisKey, mapKey, mapValue);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object getOneFromMap(final String redisKey, String mapKey) {
		HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
		return operations.get(redisKey, mapKey);
	}

	public Object getAllFromMap(final String redisKey) {
		HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
		return operations.values(redisKey);
	}

	public void removeFromMap(final String redisKey, Object obj) {
		HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
		operations.delete(redisKey, obj);
	}

	public boolean setList(final String key, Object value) {
		boolean result = false;
		try {
			ListOperations<Serializable, Object> listOperations = redisTemplate.opsForList();
			listOperations.leftPush(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object getList(final String key) {
		ListOperations<Serializable, Object> listOperations = redisTemplate.opsForList();
		return listOperations.range(key,0,listOperations.size(key));
	}

}
