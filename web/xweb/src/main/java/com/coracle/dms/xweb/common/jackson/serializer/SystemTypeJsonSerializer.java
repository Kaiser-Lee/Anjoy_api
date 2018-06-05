package com.coracle.dms.xweb.common.jackson.serializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coracle.dms.po.DmsChannelContacts;
import com.coracle.dms.service.DmsChannelContactsService;
import com.coracle.dms.xweb.common.bean.BeanFactory;
import com.coracle.yk.base.annotation.SystemTypeJsonSerialize;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 *
 *
 * @author Vincent
 * @version 1.0 2017-07-04 20:06
 */
public class SystemTypeJsonSerializer extends JsonSerializer<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemTypeJsonSerializer.class);

    private static final String SUFFIX = "Cnt";

    private static DmsChannelContactsService dmsChannelContactsService;

    private void initService(){
        if (dmsChannelContactsService == null) dmsChannelContactsService = (DmsChannelContactsService)BeanFactory.getBeansByClass(DmsChannelContactsService.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, JsonProcessingException {
        initService();
        jsonGenerator.writeObject(value);

        Object obj = jsonGenerator.getCurrentValue();
        String curentName = jsonGenerator.getOutputContext().getCurrentName();
        SystemTypeJsonSerialize anno = this.getSystemTypeJsonSerialize(obj, curentName);

        if (anno == null || StringUtils.isBlank(anno.value())) return;

        DmsChannelContacts en = dmsChannelContactsService.selectByPrimaryKey(anno.value());
//        if (types != null && types.size() > 0) {
//            for (SystemTypeDTO type : types) {
//                if (value.toString().equals(type.getTypeCode())) {
//                    jsonGenerator.writeFieldName(curentName + SUFFIX);
//                    jsonGenerator.writeObject(type.getTypeName());
//                    return;
//                } else {
//                    if (value != null && value instanceof Boolean) {
//                        boolean flag = (Boolean) value;
//                        int aliasValue = flag ? 1 : 0;
//                        if(String.valueOf(aliasValue).equalsIgnoreCase(type.getTypeCode())){
//                            jsonGenerator.writeFieldName(curentName + SUFFIX);
//                            jsonGenerator.writeObject(type.getTypeName());
//                            return;
//                        }
//                    }
//                }
//            }
//        }
    }

    private SystemTypeJsonSerialize getSystemTypeJsonSerialize(Object obj, String curentName){
        String methodName = "get" + StringUtils.capitalize(curentName);
        Class<?> cls = obj.getClass();
        Method method = null;
        try {
            method = cls.getMethod(methodName);
        } catch (Exception e) {
            LOGGER.warn("从{}获取方法{}失败,{}", cls.getName(), methodName, e.toString());
        }

        if (method != null && method.isAnnotationPresent(SystemTypeJsonSerialize.class)) {
            return method.getAnnotation(SystemTypeJsonSerialize.class);
        } else {
            // 从属性获取
            Field field = null;
            try {
                field = cls.getDeclaredField(curentName);
            } catch (NoSuchFieldException e) {
                LOGGER.warn("从{}获取属性{}失败,{}", cls.getName(), curentName, e.toString());
            }

            if (field != null && field.isAnnotationPresent(SystemTypeJsonSerialize.class)) {
                return field.getAnnotation(SystemTypeJsonSerialize.class);
            }
        }
        return null;
    }
}
