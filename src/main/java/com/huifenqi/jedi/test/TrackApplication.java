package com.huifenqi.jedi.test;

import com.huifenqi.jedi.track.TrackApplicationContextHolder;
import com.huifenqi.jedi.track.TrackStorage;
import com.huifenqi.jedi.track.producer.redis.TrackRedisProperties;
import com.huifenqi.jedi.track.utils.SpringBeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;

/**
 * Created by t3tiger on 2017/9/11.
 */
@SpringBootApplication
public class TrackApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(TrackApplication.class, args);

        TrackStorage bean = SpringBeanUtils.getBean(TrackStorage.class);
        System.out.println(bean.add("hello track"));
        Thread.sleep(10 * 1000);
        System.out.println(bean.add("hello track2"));
        AnnotationConfigEmbeddedWebApplicationContext ctx = (AnnotationConfigEmbeddedWebApplicationContext) TrackApplicationContextHolder.ctx;
        System.out.println(TrackApplicationContextHolder.ctx.getClass().getName());
        ctx.close();
    }
}
