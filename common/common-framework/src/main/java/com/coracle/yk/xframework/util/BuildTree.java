package com.coracle.yk.xframework.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yjr on 2016/7/15.
 */


public class BuildTree {
    /**
     * 构建树形菜单数据
     * @param nodes
     * @param <T>
     * @return
     */
    public static final <T> List<T> buildTree(List<T> nodes) {
        if(null == nodes || nodes.size() == 0){ return null;}
        Map<Long, T> resources = new HashMap<Long, T>();
        List<T> result = new ArrayList<T>();
        try {
            for(int i=0; i<nodes.size(); i++) {
                T node = nodes.get(i);
                Method getId = node.getClass().getMethod("getId");
                Long id = (Long) getId.invoke(node);
                resources.put(id, node);
            }
            for (Map.Entry<Long, T> e : resources.entrySet()) {
                T node = e.getValue();
                Method getparentId = node.getClass().getMethod("getParentId");
                Long parentId = (Long) getparentId.invoke(node);
                if(parentId == 0) {
                    result.add(node);
                } else {
                    T parent = resources.get(parentId);
                    if( null != parent) {
                        @SuppressWarnings("unchecked")
                        List<T> children = (List<T>) parent.getClass().getMethod("getChildren").invoke(parent);
                        children.add(node);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置树形菜单的leaf属性
     * @param nodes
     * @param <T>
     * @return
     */
    public static final <T> List<T> setTreeLeaf(List<T> nodes){
        if(null == nodes || nodes.size() == 0) {return null;}
        for(int i=0; i<nodes.size(); i++){
            try {
                T outNode = nodes.get(i);
                Boolean flage=false;
                Method getId = outNode.getClass().getMethod("getId");
                Long id=(Long) getId.invoke(outNode);
                PropertyDescriptor pd = new PropertyDescriptor("leaf",outNode.getClass());
                Method method = pd.getWriteMethod();
                for (int j=0;j<nodes.size();j++){
                    if(i==j){
                       continue;
                    }
                    T insideNode = nodes.get(j);
                    Method getInsideId = insideNode.getClass().getMethod("getParentId");
                    Integer parentId=(Integer) getInsideId.invoke(insideNode);
                    if(id.equals(parentId)){
                        method.invoke(outNode,false);
                        flage=true;
                    }
                }
                if(!flage){
                    method.invoke(outNode,true);
                }
            } catch (NoSuchMethodException e) {
                System.out.println("找不到相应的方法");
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                System.out.println("找不到相应的字段");
                e.printStackTrace();
            }

        }
        return nodes;
    }

    public static void main(String[] args) {
//        List<TreeSource> list=new ArrayList<TreeSource>();
//        BuildTree.setTreeLeaf(list);
//        List<TreeSource> treeSourceList=BuildTree.buildTree(list);
    }

}
