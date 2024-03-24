package com.quickpay.commons.utils;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext appContext;

    public static ApplicationContext getAppContext() {
        return appContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) appContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return appContext.getBean(requiredType);
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return appContext.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return appContext.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return appContext.isSingleton(name);
    }

    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return appContext.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return appContext.getAliases(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.appContext = applicationContext;
    }

}
