package com.coracle.yk.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Vincent
 * @version 1.0 2017-07-04 16:50
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ExtJsonSerialize("systemTypeJsonSerializer")
public @interface SystemTypeJsonSerialize {

    String value();
}
