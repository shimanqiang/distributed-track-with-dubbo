package com.huifenqi.jedi.track.utils;

import com.huifenqi.jedi.track.TrackApplicationContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by smq on 2017/4/25.
 */
public class SpringBeanUtils{
    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return TrackApplicationContextHolder.ctx;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
