package com.coracle.yk.xframework.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import com.coracle.yk.xframework.po.AdapterEntity;

public final class FwUtil {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static String date2String(Date date) {
		return sdf.format(date);
	}
	
	public static String date2String(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
	
	@SuppressWarnings("rawtypes")
	public static String transferEntity2String(Object entity) {
		StringBuilder builder = new StringBuilder();
    	final Class<? extends Object> entityClass = entity.getClass();
		final Field[] fields = entityClass.getDeclaredFields();
		Object value = null;
		for(Field field : fields) {
			try {
				field.setAccessible(true);
				value = field.get(entity);
				builder.append(",[");
				if(value instanceof Collection) {
//					for(Object obj : ((Collection)value)) {
//						builder.append(transferEntity2String(obj));
//					}
					builder.append(field.getName()).append(":").append(((Collection)value).size());
				} else if(value instanceof AdapterEntity) {
					builder.append(transferEntity2String(value));
				} else {
					builder.append(field.getName()).append(":").append(value);
				}
				builder.append("]");
			} catch(Exception e) {
				continue;
			}
		}
		if(!builder.toString().equals("")) {
			builder.delete(0, 1);
		}
		return builder.toString();
    }
}
