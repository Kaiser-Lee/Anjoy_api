package com.coracle.dms.aop;

import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.redis.RedisUtil;
import com.coracle.yk.xframework.redis.lock.RedisBasedDistributedLock;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;


/**
 * Redis缓存分布式锁切面类
 * created by huangbaidong
 * created date 2017-03-30
 */



//@Aspect
//@Component
public class RedisCacheDistributedLockAspect {
    public static final Logger LOG = Logger.getLogger(RedisCacheDistributedLockAspect.class);

	@Value("${time.server.port}")
	private Integer port;
	@Value("${time.server.host}")
	private String host;

	@Resource
	private RedisUtil redisUtil;

	//待付款订单锁
	protected RedisBasedDistributedLock payingOrderLock = null;
	private static final String PAYING_ORDER_LOCK_KEY = "paying.order.lock";
	private static final long LOCK_EXPIRE = 5 * 1000;

	/**
	 * 初始化锁信息
	 */
	@PostConstruct
	public void init() {
		try {
			//时间服务器地址信息
			SocketAddress addr = new InetSocketAddress(host, port);
			//初始化锁
			payingOrderLock = new RedisBasedDistributedLock(redisUtil, PAYING_ORDER_LOCK_KEY, LOCK_EXPIRE, addr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * 在需要同步的方法前后加锁
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* com.coracle.dms.cache.impl.BsdDFKOrderCacheManagerImpl.put*(..))" +
			"|| execution(* com.coracle.dms.cache.impl.BsdDFKOrderCacheManagerImpl.remove*(..))" +
			"|| execution(* com.coracle.dms.cache.impl.BsdDFKOrderCacheManagerImpl.clear*(..))" +
			"|| execution(* com.coracle.dms.cache.impl.BsdDFKOrderCacheManagerImpl.update*(..))")
	public Object payingOrderSyncLock(ProceedingJoinPoint jp) throws Throwable {
		Object o = null;
		//5秒获取锁超时
		if(payingOrderLock.tryLock(5, TimeUnit.SECONDS)) {
			System.out.println("---------->获取分布式锁");
			try {
				o = jp.proceed(jp.getArgs());
			} finally {
				payingOrderLock.unlock();
			}
		} else {
			throw new ServiceException("获取锁超时,max wait time 5000ms");
		}
		return o;
	}
}
