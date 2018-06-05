package com.coracle.dms.xweb.common.util;

import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.xframework.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成po某些公共字段的默认值
 * 2016-7-14 hbd
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PoDefaultValueGenerator {

    public static final String CB="createdBy";
    public static final String CD="createdDate";
    public static final String UB="lastUpdatedBy";
    public static final String UD="lastUpdatedDate";
    public static final String RF="removeFlag";
    public static final String VS="version";


    private static Map<String,EntityDefaultValueConfig> defaultFieldMap = new HashMap<>();

    static  {
            defaultFieldMap.put(CB, new EntityDefaultValueConfig(new Class[]{Long.class}, 1, 2));
            defaultFieldMap.put(CD,new EntityDefaultValueConfig(new Class[]{Date.class}, "currentDate"));
            defaultFieldMap.put(UB,new EntityDefaultValueConfig(new Class[]{Long.class}, 1, 2));
            defaultFieldMap.put(UD,new EntityDefaultValueConfig(new Class[]{Date.class}, "currentDate"));
            defaultFieldMap.put(RF,new EntityDefaultValueConfig(new Class[]{Integer.class, Long.class}, 0));
            defaultFieldMap.put(VS,new EntityDefaultValueConfig(new Class[]{Integer.class, Long.class}, 0));
    }

	public static <T> void setDefaultValue (T t) {
        if(t != null) {
            Class tClass = t.getClass();
            Method setMethod;
            for (String key : defaultFieldMap.keySet()) {
                EntityDefaultValueConfig config = (EntityDefaultValueConfig) defaultFieldMap.get(key);
                try {
                    setMethod = tClass.getMethod("set" + StringUtil.upperCaseFirstLetter(key), config.getParamClass());
                    if (setMethod != null) {
                        if ("java.lang.Integer".equals(config.getParamClass().getName())) {
                            setMethod.invoke(t, config.getParamDefaultValue());
                        } else if ("java.util.Date".equals(config.getParamClass().getName())) {
                            setMethod.invoke(t, new Date());
                        } else {
                            setMethod.invoke(t, config.getParamDefaultValue());
                        }
                    }
                } catch (NoSuchMethodException e) {
                    //System.out.println("PoDefaultValueGenerator NoSuchMethodException:" + e.getMessage());
                    continue;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> void setDefaultValueWithSource(T targetObj, Object fromObj) {
        if(targetObj != null && fromObj != null) {
            Class tClass = targetObj.getClass();
            Class sClass = fromObj.getClass();
            Method setMethod;
            Method getMethod;
            for (String key : defaultFieldMap.keySet()) {
                EntityDefaultValueConfig config = (EntityDefaultValueConfig) defaultFieldMap.get(key);
                try {
                    setMethod = tClass.getMethod("set" + StringUtil.upperCaseFirstLetter(key), config.getParamClass());
                    if (1 == config.getParamSource()) {
                        if ("java.lang.Integer".equals(config.getParamClass().getName())) {
                            setMethod.invoke(targetObj, config.getParamDefaultValue());
                        } else if ("java.util.Date".equals(config.getParamClass().getName())) {
                            setMethod.invoke(targetObj, new Date());
                        } else {
                            setMethod.invoke(targetObj, config.getParamDefaultValue());
                        }
                    } else {
                        getMethod = sClass.getMethod("get" + StringUtil.upperCaseFirstLetter(key));
                        setMethod.invoke(targetObj, getMethod.invoke(fromObj));
                    }
                } catch (NoSuchMethodException e) {
                    System.out.println("PoDefaultValueGenerator NoSuchMethodException:" + e.getMessage());
                    continue;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 默认字段信息配置类
     */
    static class EntityDefaultValueConfig {

        //set方法的参数类型
        private Class paramClass = null;
        private Class[] paramClasses = null;
        //set参数值来源(1:默认,  2:外部类同名get方法获取)
        private Integer paramSource = 1;
        //set方法的默认值
        private Object paramDefaultValue = null;

        public EntityDefaultValueConfig() {
        }

        public EntityDefaultValueConfig(Class[] paramClasses, Object paramDefaultValue) {
            this.paramSource = 1;
//            this.paramClass = paramClass;
            this.paramClasses = paramClasses;
            this.paramDefaultValue = paramDefaultValue;
        }

        public EntityDefaultValueConfig(Class[] paramClasses, Object paramDefaultValue, Integer paramSource) {
            this.paramSource = paramSource;
            this.paramClasses = paramClasses;
            this.paramDefaultValue = paramDefaultValue;
        }

        public Class getParamClass() {
            return paramClass;
        }

        public void setParamClass(Class paramClass) {
            this.paramClass = paramClass;
        }

        public Object getParamDefaultValue() {
            return paramDefaultValue;
        }

        public void setParamDefaultValue(Object paramDefaultValue) {
            this.paramDefaultValue = paramDefaultValue;
        }

        public Integer getParamSource() {
            return paramSource;
        }

        public void setParamSource(Integer paramSource) {
            this.paramSource = paramSource;
        }
    }
    /**
     * 为po类设置默认值
     * @param t po对象
     * @param <T> 泛型
     */
    public static <T> void putDefaultValue(T t){
        if(t==null){
            return;
        }
        if(isUpdate(t)){
            putUpdateDefault(t);
        }else{
            putCreateDefault(t);
        }
    }

    public static <T> void putCreateDefault(T t){
        Long userId = LoginManager.getCurrentUserId();
        Date date=new Date();
        putDefaultValue(t,CB,userId);
        putDefaultValue(t,CD,date);
        putDefaultValue(t,UB,userId);
        putDefaultValue(t,UD,date);
        putDefaultValue(t,RF,0);
//        putDefaultValue(t,VS,1);
    }

    public static <T> void putUpdateDefault(T t){
        putDefaultValue(t, UB, LoginManager.getCurrentUserId());
        putDefaultValue(t, UD, new Date());
    }

    public static <T> void putDefaultValue(T t,String field,Object value){
        try{
            Method getMethod=BeanUtils.findMethod(t.getClass(),"get"+StringUtil.upperCaseFirstLetter(field));
            if(getMethod==null){
                return;
            }
            Class returnType=getMethod.getReturnType();
            value=convertValue(value,returnType);


            Method setMethod=BeanUtils.findMethod(t.getClass(),"set"+StringUtil.upperCaseFirstLetter(field),returnType);
            if(setMethod==null){
                return;
            }
            setMethod.invoke(t,value);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private static Object convertValue(Object value,Class returnType){
        if(value==null){
            return null;
        }
        if(returnType==Boolean.class){
            return "1".equals(value.toString());
        }else if(returnType==Integer.class){
            return Integer.valueOf(value.toString());
        }
        return value;
    }
    public static <T> boolean isUpdate(T t){
        try{
            Method idMethod=BeanUtils.findMethod(t.getClass(),"getId");
            Object id=idMethod.invoke(t);
            if(id!=null&&StringUtils.isNotBlank(id.toString())&&Long.valueOf(id.toString())>0){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }



    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//        CstCustomerVo cstCustomerVo = new CstCustomerVo();
//        CstCustomer cstCustomer = new CstCustomer();
//        AdapterEntity entity = new AdapterEntity();
//        UserSession session = new UserSession();
//        session.setDeptId(300);
//        session.setOrgId(101);
//        com.shj.framework.xframework.po.PoDefaultValueGenerator.setDefaultValueWithSource(cstCustomerVo,session);
//        System.out.println(cstCustomerVo.getCreatedBy());
//        System.out.println(cstCustomerVo.getCreatedDate());
//        System.out.println(cstCustomerVo.getDeptId());
//        System.out.println(cstCustomerVo.getOrgId());
    }
}
