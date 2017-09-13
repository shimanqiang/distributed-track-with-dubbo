package com.huifenqi.jedi.track.producer;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by t3tiger on 2017/9/11.
 */
public class TrackProducerManager implements InitializingBean, DisposableBean {
    private static volatile boolean isRun;

    @Override
    public void destroy() throws Exception {
        isRun = false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (isRun) {
            return;
        }
        isRun = true;
        TrackProducer producer = TrackProducerFactory.create();

        //启动redis生产者
        new Thread(() -> {
            while (isRun) {
                producer.doWork();
            }
            producer.stop();
        }).start();

    }
}
