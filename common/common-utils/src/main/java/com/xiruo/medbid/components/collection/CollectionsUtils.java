package com.xiruo.medbid.components.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xiruo.medbid.components.ArrayUtils;

public class CollectionsUtils<E>{


	public static <E extends Object> Collection<E> contact(Collection <E> c1, Collection<E> c2){
		if(c1 == null){
			return c2;
		}else if(c2 == null){
			return c1;
		}
		c1.addAll(c2);
		return c1;
	}
	
	public static List subList(List sourceList, int fromIndex, int toIndex){
		int endIndex = sourceList.size();
		if(fromIndex > endIndex){
			fromIndex = endIndex;
		}else if(fromIndex < 0){
			fromIndex = 0;
		}
		if(toIndex > endIndex){
			toIndex = endIndex;
		}
		return sourceList.subList(fromIndex, toIndex);
	}
	
	public static List<Object> subList(List<Object> sourceList, int fromIndex){
		return subList(sourceList,fromIndex, sourceList.size());
	}
	
	public static <T> List<T> toList(T... os){		
		return Arrays.asList(os);
	}
	
	public static List<String> toStringList(String... strings){
		return toList(strings);
	}
	
	public static List<Integer> toIntList(int... ints){		 
		int len = ints.length;
		List<Integer> list = new ArrayList<Integer>(len);
		for(int i : ints){
			list.add(i);
		}
		return list;
	}


	public static Map<String, Object> subMap(Map<String, Object> sourceMap, Map<String, Object> targetMap, String keyPrefix){
		Set<Map.Entry<String, Object>> entrys = sourceMap.entrySet();
		for(Map.Entry<String, Object> entry : entrys){
			String key = entry.getKey();
			if(key != null && key.startsWith(keyPrefix)){
				targetMap.put(key.replace(keyPrefix, ""), entry.getValue());
			}
		}
		return targetMap;
	}
	
	public static void print(Collection<?> c){
		ArrayUtils.print(c.toArray());
	}
	
	public static boolean inList(String id, List<String> idList){
		for(String i : idList){
			if(i.equals(id)){
				return true;
			}
		}
		return false;
	}
}
