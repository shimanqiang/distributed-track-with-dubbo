package com.huifenqi.jedi.track.consumer;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by t3tiger on 2017/9/11.
 */

public class TrackConsumerManager implements InitializingBean, DisposableBean {

    private static volatile boolean isRun;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (isRun) {
            return;
        }
        isRun = true;
        TrackConsumer consumer = TrackConsumerFactory.create();
        new Thread(() -> {
            while (isRun) {
                consumer.doWork();
            }
        }).start();
    }

    @Override
    public void destroy() throws Exception {
        isRun = false;
    }
}
