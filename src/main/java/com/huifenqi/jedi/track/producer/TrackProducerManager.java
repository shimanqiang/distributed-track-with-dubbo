package com.huifenqi.jedi.track.producer;

/**
 * Created by t3tiger on 2017/9/11.
 */
public class TrackProducerManager {
    private static volatile boolean isRun;

    public static void start() {
        if (isRun) {
            return;
        }
        isRun = true;
        TrackProducer producer = TrackProducerFactory.create();
        new Thread(() -> {
            while (isRun) {
                producer.doWork();
            }
        }).start();
    }

    public static void stop() {
        isRun = false;
    }
}
