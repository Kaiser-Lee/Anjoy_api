package com.coracle.yk.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DTO对象字段值转义注解  （列如将数据库id转成name）
 * <br />
 * jackson在将对象转成json时，在需要将id转成name的get属性方法上，打上该注解即可。
 * <br />
 * 注意：如果该转换对象没有存到缓存中，将导致N+1查询，请谨慎使用（此种情况建议使用数据库连接查询）
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtJsonSerialize {

    /**
     * 实际JsonSerializer类的全路径
     */
    String value();
}
