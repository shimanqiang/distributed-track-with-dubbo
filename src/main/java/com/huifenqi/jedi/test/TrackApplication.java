package com.huifenqi.jedi.test;

import com.huifenqi.jedi.track.TrackApplicationContextHolder;
import com.huifenqi.jedi.track.TrackStorage;
import com.huifenqi.jedi.track.anno.EnableTrack;
import com.huifenqi.jedi.track.utils.SpringBeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;

/**
 * Created by t3tiger on 2017/9/11.
 */
@SpringBootApplication
@EnableTrack
public class TrackApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(TrackApplication.class, args);

        TrackStorage bean = SpringBeanUtils.getBean(TrackStorage.class);

        for (int i = 0; i < 10; i++) {
            bean.add("hello track" + i);
            Thread.sleep(500);
        }

        //System.out.println(bean.add("hello track2"));


        Thread.sleep( 5 * 1000);
        AnnotationConfigEmbeddedWebApplicationContext ctx = (AnnotationConfigEmbeddedWebApplicationContext) TrackApplicationContextHolder.ctx;
        System.out.println(TrackApplicationContextHolder.ctx.getClass().getName());
        ctx.close();
    }
}
