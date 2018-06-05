package com.coracle.dms.xweb.common.interceptor;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coracle.yk.base.annotation.ExtJsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * @author Vincent
 * @version 1.0 2017-07-04 15:03
 */
public class ExtJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExtJacksonAnnotationIntrospector.class);

    private Map<String, Class<? extends JsonSerialize>> serializers;

    @Override
    public Object findSerializer(Annotated a) {
        Object obj = super.findSerializer(a);
        if (obj != null) return obj;

        String classPath = null;
        Iterator<Annotation> iterators = a.annotations().iterator();
        while (iterators.hasNext()) {
            Annotation anno = iterators.next();
            if (ExtJsonSerialize.class.isAssignableFrom(anno.getClass())) {
                classPath = ((ExtJsonSerialize) anno).value();
            } else {
                Annotation _anno = anno.annotationType().getAnnotation(ExtJsonSerialize.class);
                if (_anno != null) {
                    classPath = ((ExtJsonSerialize) _anno).value();
                }
            }
        }

        if (StringUtils.isBlank(classPath)) {
            return null;
        }

        try {
            if (serializers != null && serializers.size() > 0 && serializers.containsKey(classPath)) {
                return serializers.get(classPath).newInstance();
            }
            return Class.forName(classPath).newInstance();
        } catch (Exception e) {
            LOGGER.warn("init serializer class error for class " + classPath + ", " + e.toString());
        }
        return null;
    }

    public void setSerializers(Map<String, Class<? extends JsonSerialize>> serializers) {
        this.serializers = serializers;
    }
}
