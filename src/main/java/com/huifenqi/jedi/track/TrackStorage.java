package com.huifenqi.jedi.track;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by t3tiger on 2017/9/8.
 */
public class TrackStorage {
    private LinkedBlockingQueue<String> storage;

    public TrackStorage() {
        if (storage == null) {
            storage = new LinkedBlockingQueue<>();
        }
    }

//    private static volatile TrackStorage instance = null;
//
//    public static TrackStorage getInstance() {
//        if (instance == null) {
//            synchronized (instance) {
//                if (instance == null) {
//                    instance = new TrackStorage();
//                }
//            }
//        }
//        return instance;
//    }

    public boolean add(String content) {
        if (storage == null) {
            storage = new LinkedBlockingQueue<>();
        }
        return storage.offer(content);
    }

    public String pop() {
        if (storage == null) {
            storage = new LinkedBlockingQueue<>();
        }

        try {
            //return storage.take();
            return storage.poll(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return storage.poll();
    }

    public void dispose() {
        if (storage != null) {
            //storage.offer("1");//防止挂起，死锁
            storage.clear();
            storage = null;
        }
    }
}
