package com.coracle.yk.xframework.cache.intf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IBaseCache {
	final static int RESULT_TYPE_SINGLE = 0;
	final static int RESULT_TYPE_MULTI = 1;

	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@Documented
	@interface RedisCache {
	    Class<? extends Object> type();
	    int result() default RESULT_TYPE_MULTI;
	}
	
	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@Documented
	@interface RedisEvict {
	    Class<? extends Object> type();
	}
	
	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@Documented
	@interface RedisEvicts {
	    Class<? extends Object>[] type();
	}
}
