package com.huifenqi.jedi.track;

import com.huifenqi.jedi.track.producer.TrackProducerManager;
import com.huifenqi.jedi.track.utils.SpringBeanUtils;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * Created by t3tiger on 2017/9/11.
 */
public class TrackApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //System.out.println(event.getClass().getName());
        if (event instanceof ApplicationEnvironmentPreparedEvent) { // 初始化环境变量
        } else if (event instanceof ApplicationPreparedEvent) { // 初始化完成
        } else if (event instanceof ContextRefreshedEvent) { // 应用刷新
        } else if (event instanceof ApplicationReadyEvent) {// 应用已启动完成
            TrackProducerManager.start();
        } else if (event instanceof ContextStartedEvent) { //应用启动，需要在代码动态添加监听器才可捕获

        } else if (event instanceof ContextStoppedEvent) { // 应用停止
        } else if (event instanceof ContextClosedEvent) { // 应用关闭
            TrackProducerManager.stop();
            SpringBeanUtils.getBean("trackStorage", TrackStorage.class).dispose();
        } else {
        }
    }
}
