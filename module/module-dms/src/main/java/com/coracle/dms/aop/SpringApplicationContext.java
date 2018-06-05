package com.coracle.dms.aop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringApplicationContext.applicationContext;
    }

    /**
     * 根据Bean名称获取实例
     *
     * @param name Bean注册名称
     * @return bean实例
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    /**
     * 根据Class获取实例
     *
     * @param cls Bean注册名称
     * @return bean实例
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> cls) throws BeansException {
        return applicationContext.getBean(cls);
    }

    /**
     * 根据Class获取实例
     *
     * @param name Bean注册名称
     * @return bean实例
     * @throws BeansException
     */
    public static boolean containsBean(String name) throws BeansException {
        return applicationContext.containsBean(name);
    }

    /**
     * 根据Bean的名称获取预期类型的Bean
     *
     * @param beanName     Bean的名称
     * @param expectedType 预期类型
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> expectedType) {
        return applicationContext.getBean(beanName, expectedType);
    }

    public static <T> List<T> getBeans(Class<T> expectedType) {
        List<T> list = new ArrayList<T>();
        for (T t : applicationContext.getBeansOfType(expectedType).values()) {
            list.add(t);
        }
        return list;
    }
}
