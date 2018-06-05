
package com.coracle.yk.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * @author tanyb
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OperationLog {
	
	/** 操作内容 */		
	public String operation() default "";
	
	/** 操作业务类型 */		
	public int relatedType() default 1;		
	
	/** 描述：可使用动态参数，同Annotation基本用法,即{参数名} */		
	public String description() default "";
}
