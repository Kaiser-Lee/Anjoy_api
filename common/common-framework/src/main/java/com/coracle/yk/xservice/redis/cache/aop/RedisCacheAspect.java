package com.coracle.yk.xservice.redis.cache.aop;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.coracle.yk.xframework.cache.intf.IBaseCache;
import com.coracle.yk.xframework.cache.intf.IBaseCache.RedisCache;
import com.coracle.yk.xframework.cache.intf.IBaseCache.RedisEvict;
import com.coracle.yk.xframework.cache.intf.IBaseCache.RedisEvicts;
import com.coracle.yk.xframework.common.YkConstants;
import com.coracle.yk.xframework.redis.JedisClusterFactory;
import com.coracle.yk.xframework.redis.YkJedisCluster;

//@Aspect
@Component
public class RedisCacheAspect {
    public static final Logger LOG = Logger.getLogger(RedisCacheAspect.class);

    @Autowired
    private JedisClusterFactory jedisClusterFactory;


    /**
     * 方法调用前，先查询缓存。如果存在缓存，则返回缓存数据，阻止方法调用;
     * 如果没有缓存，则调用业务方法，然后将结果放到缓存中
     * @param jp
     * @return
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
	@Around("execution(* com.coracle.yk.xservice.*db.intf.*Service.select*(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.get*(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.find*(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.load*(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.count*(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.search*(..))")
    public Object cache(ProceedingJoinPoint jp) throws Throwable {
    	System.out.println("aop cache:" + jp.getTarget().getClass().getName());
    	// result是方法的最终返回结果
        Object result = null;
    	Class<?> intfs[] = jp.getTarget().getClass().getInterfaces();
    	Class<?> targetIntf = null;
    	for(Class<?> cls : intfs) {
    		//有继承关系但不等于
    		if(IBaseCache.class.isAssignableFrom(cls) && !cls.equals(IBaseCache.class)) {
    			targetIntf = cls;
    			break;
    		}
    	}
    	if(targetIntf == null) {
    		LOG.info("未找到缓存接口,无需进行缓存处理...");
    		result = jp.proceed(jp.getArgs());
    	} else {
	        // 得到类名、方法名和参数
	        String clazzName = jp.getTarget().getClass().getName();
	        String methodName = jp.getSignature().getName();
	        Object[] args = jp.getArgs();
	
	        // 根据类名，方法名和参数生成key
	        String key = genKey(clazzName, methodName, args);
	        if (LOG.isDebugEnabled()) {
	        	LOG.debug("生成key:{" + key + "}");
	        }
	
	        // 得到被代理的方法,实际是实现类
	        Method method = ((MethodSignature) jp.getSignature()).getMethod();
	        // 得到接口方法
	        Method me = targetIntf.getDeclaredMethod(jp.getSignature().getName(),
	        		method.getParameterTypes());
	        // 得到被代理的方法上的注解
	        RedisCache redisCache = me.getAnnotation(RedisCache.class);
	        if(redisCache == null) {
	        	LOG.info("接口上未找到@RedisCache注解，无需进行缓存处理...");
	        } else {
		        YkJedisCluster cluster = jedisClusterFactory.create();
		    	try {
			        Class<? extends Object> modelType = redisCache.type();
			        int rt = redisCache.result();
			        String flag = modelType.getName() + "_" + rt;
			
			        // 检查redis中是否有缓存
			        String value = (String)cluster.hget(flag, key);
		//	        if(true) {
			        if (null == value) {
			            // 缓存未命中
			            if (LOG.isDebugEnabled()) {
			                LOG.debug("缓存未命中");
			            }
			
			            // 调用代理方法进行处理
			            result = jp.proceed(args);
			
			            // 序列化查询结果
			            String json = serialize(result);
			
			            // 序列化结果放入缓存
		//	            rt.opsForHash().put(modelType.getName(), key, json);
			            //保存结构map<name of AdapterEntity, map<key, json>>
			            //120分钟失效
			            cluster.expire(flag, 7200);
			            cluster.hset(flag, key, json);
			        } else {
			            // 缓存命中
			            if (LOG.isDebugEnabled()) {
			                LOG.debug("缓存命中, value = {" + value + "}");
			            }
			
			            // 得到被代理方法的返回值类型
			            Class<? extends Object> returnType = ((MethodSignature) jp.getSignature()).getReturnType();
			
			            // 反序列化从缓存中拿到的json
			            result = deserialize(value, returnType, modelType);
			
			            if (LOG.isDebugEnabled()) {
			                LOG.debug("反序列化结果 = {" + result + "}");
			            }
			        }
		    	} finally {
		    		if(cluster != null) {
		    			jedisClusterFactory.returnJedisCluster(cluster);
		    			cluster = null;
		    		}
		    	}
	        }
    	}
        return result;
    }

    /**
     * 在方法调用前清除缓存，然后调用业务方法
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.coracle.yk.xservice.*db.intf.*Service.insert*(..))" +
    		"|| execution(* com.coracle.yk.xservice.*db.intf.*Service.create*(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.update*(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.delete*(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.add*(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.edit*(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.modify(..))" +
            "|| execution(* com.coracle.yk.xservice.*db.intf.*Service.remove*(..))")
    public Object evictCache(ProceedingJoinPoint jp) throws Throwable {
    	System.out.println("aop evict:" + jp.getTarget().getClass().getName());
    	Class<?> intfs[] = jp.getTarget().getClass().getInterfaces();
    	Class<?> targetIntf = null;
    	for(Class<?> cls : intfs) {
    		//有继承关系但不等于
    		if(IBaseCache.class.isAssignableFrom(cls) && !cls.equals(IBaseCache.class)) {
    			targetIntf = cls;
    			break;
    		}
    	}
    	if(targetIntf == null) {
    		LOG.info("未找到缓存接口,无需进行缓存处理...");
    	} else {
    		// 得到被代理的方法,实际是实现类
	        Method method = ((MethodSignature) jp.getSignature()).getMethod();
	        // 得到接口方法
	        Method me = targetIntf.getDeclaredMethod(jp.getSignature().getName(),
	        		method.getParameterTypes());
	        String mName = me.getName().toLowerCase();
	        RedisEvict re = me.getAnnotation(RedisEvict.class);
	        RedisEvicts res = me.getAnnotation(RedisEvicts.class);
	        if(re != null) {
	        	YkJedisCluster cluster = jedisClusterFactory.create();
		    	try {
			        // 得到被代理的方法上的注解
			        Class<? extends Object> modelType = re.type();
			        
			        if (LOG.isDebugEnabled()) {
			            LOG.debug("清空缓存: {" + modelType.getName() + "}");
			        }
			
			        // 清除对应缓存
			        cluster.hdel(modelType.getName() + "_" + IBaseCache.RESULT_TYPE_MULTI);
			        if(!mName.startsWith("insert") && !mName.startsWith("add")) {
			        	cluster.hdel(modelType.getName() + "_" + IBaseCache.RESULT_TYPE_SINGLE);
			        }
			        
		    	} finally {
		    		if(null != cluster) {
		    			jedisClusterFactory.returnJedisCluster(cluster);
		    			cluster = null;
		    		}
		    	}
	        }
	        if(res != null) {
	        	YkJedisCluster cluster = jedisClusterFactory.create();
		    	try {
			        // 得到被代理的方法上的注解
			        Class<? extends Object>[] modelTypes = res.type();
			        
			        if (LOG.isDebugEnabled()) {
			            LOG.debug("清空缓存: {" + Arrays.asList(modelTypes) + "}");
			        }
			
			        // 清除对应缓存
			        for(Class<? extends Object> modelType : modelTypes) {
				        cluster.hdel(modelType.getName() + "_" + IBaseCache.RESULT_TYPE_MULTI);
				        if(!mName.startsWith("insert") && !mName.startsWith("add")) {
				        	cluster.hdel(modelType.getName() + "_" + IBaseCache.RESULT_TYPE_SINGLE);
				        }
			        }
			        
		    	} finally {
		    		if(null != cluster) {
		    			jedisClusterFactory.returnJedisCluster(cluster);
		    			cluster = null;
		    		}
		    	}
	    	}
	        if(re == null && res == null) {
	    		LOG.info("接口上未找到@RedisEvict和@RedisEvicts注解，无需进行缓存处理...");
	    	}
    	}
    	
        return jp.proceed(jp.getArgs());
    }



    /**
     * 根据类名、方法名和参数生成key
     * @param clazzName
     * @param methodName
     * @param args 方法参数
     * @return
     */
    protected String genKey(String clazzName, String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder(clazzName);
        sb.append(YkConstants.DELIMITER);
        sb.append(methodName);
        sb.append(YkConstants.DELIMITER);

        for (Object obj : args) {
            sb.append(serialize(obj, true));
            sb.append(YkConstants.DELIMITER);
        }
//        System.out.println(sb);
        return sb.toString();
    }

    protected String serialize(Object target) throws IOException {
        return serialize(target, false);
    }

    protected Object deserialize(String jsonString, Class<? extends Object> clazz, Class<? extends Object> modelType) throws ParseException {
        // 序列化结果应该是List对象
        if (clazz.isAssignableFrom(List.class)) {
        	return JSON.parseArray(jsonString, modelType);
        }

        // 序列化结果是普通对象
        return JSON.parseObject(jsonString, clazz);
    }
    
    private String serialize(Object target, boolean killNull) {
    	SerializerFeature[] featureArr = null;
    	if(!killNull) {
    		featureArr = new SerializerFeature[] {
    				SerializerFeature.WriteClassName,
    				SerializerFeature.PrettyFormat,
    				SerializerFeature.WriteNullBooleanAsFalse,
    				SerializerFeature.WriteNullListAsEmpty,
    				SerializerFeature.WriteNullNumberAsZero,
    				SerializerFeature.WriteNullStringAsEmpty
    		};
    	} else {
    		featureArr = new SerializerFeature[] {
    				SerializerFeature.IgnoreNonFieldGetter,
    				SerializerFeature.NotWriteDefaultValue,
    				SerializerFeature.SkipTransientField,
    				SerializerFeature.NotWriteRootClassName,
    				SerializerFeature.WriteNonStringKeyAsString,
    				SerializerFeature.WriteNullListAsEmpty,
    				SerializerFeature.SortField
    		};
    	}
    	return JSON.toJSONString(target, featureArr);
    }
}
