package com.coracle.yk.xframework.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * String工具类，继承自apache的StringUtils
 * @author sunyu_000
 *
 */
public class StringUtil extends StringUtils {
	
	/**
	 * 转换为小写
	 * @param str
	 * @return
	 */
	public static String toLowerCase(String str) {
		if (isBlank(str)) {
			return str;
		}
		
		return str.toLowerCase();
	}

	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String upperCaseFirstLetter(String str) {
		if(str!=null && str.length()>0) {
			return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
		} else {
			return str;
		}
	}

	/**
	 * 判断list是否为空
	 * <li>空：true</li>
	 * <li>非空：false</li>
	 * @param list
	 * @return
	 */
	public static boolean isListEmpty(List<?> list) {
		if (list == null) {
			return true;
		}
		if (list.size() == 0) {
			return true;
		}
		
		return false;
	}
	/**
	 * 判断list是否为空
	 * <li>非空：true</li>
	 * <li>空：false</li>
	 * @param list
	 * @return
	 */
	public static boolean isListNotEmpty(List<?> list) {
		return !isListEmpty(list);
	}
	
	/**
	 * 将map中取出的string/int转化为int
	 * @param obj
	 * @return
	 */
	public static Integer object2Integer(Object obj) {
		if (obj instanceof String && StringUtil.isNotBlank((String)obj)) {
			return Integer.parseInt((String)obj);
		} else if (obj instanceof Integer) {
			return (Integer)obj;
		} else {
			return null;
		}
		
	}
	
	/**
	 * 将map中取出的string/double转化为double
	 * @param obj
	 * @return
	 */
	public static Double object2Double(Object obj) {
		if (obj instanceof String && StringUtil.isNotBlank((String)obj)) {
			return Double.parseDouble((String)obj);
		} else if (obj instanceof Double) {
			return (Double)obj;
		} else {
			return null;
		}
		
	}
	
	/**
	 * 将map中取出的string/long转化为long
	 * @param obj
	 * @return
	 */
	public static Long object2Long(Object obj) {
		if (obj instanceof String && StringUtil.isNotBlank((String)obj)) {
			return Long.parseLong((String) obj);
		} else if (obj instanceof Long) {
			return (Long)obj;
		} else {
			return null;
		}
		
	}
	
	/**
	 * 将map中取出的值转化为string
	 * @param obj
	 * @return
	 */
	public static String object2String(Object obj) {
		if (obj instanceof String) {
			return (String)obj;
		} else if (obj instanceof Double) {
			return Double.toString((Double)obj);
		} else if (obj instanceof Integer) {
			return Integer.toString((Integer)obj);
		} else if (obj instanceof Long) {
			return Long.toString((Long)obj);
		} else {
			return null;
		}
		
	}
	/***
	 * json字符串数组转化成list
	 * @return
	 */
	public static List<Map> jsonStrToList(String jsonArrayStr){
		String jsonStr="";
		if("".equals(jsonArrayStr)||jsonArrayStr==null){
			return null;
		}
		if(jsonArrayStr.startsWith("")&&jsonArrayStr.endsWith("")){
			jsonStr=jsonArrayStr.substring(1,jsonArrayStr.length()-1);
		}
		if(jsonStr.startsWith("[")&&jsonStr.endsWith("]")){
			return JSON.parseArray(jsonStr, Map.class);
		}else {
			return null;
		}

	}


	/**
	 * 下划线转驼峰
	 * @param str
	 * created by hbd 20160722
	 * @return
	 */
	public static String underlineToHump(String str, String sign) {
		if(sign==null || sign.length()<=0) {
			return null;
		}
		if(str != null && str.length()>0) {
			String[] strings = str.split(sign);
			StringBuffer hump = new StringBuffer();
			for(int i=0; i<strings.length; i++) {
				hump.append(upperCaseFirstLetter(strings[i]));
			}
			return hump.toString();
		}
		return null;
	}
}
