package com.huifenqi.jedi.track.producer.redis;

import com.huifenqi.jedi.track.TrackStorage;
import com.huifenqi.jedi.track.common.CommonConst;
import com.huifenqi.jedi.track.config.TrackRedisProperties;
import com.huifenqi.jedi.track.producer.TrackProducer;
import com.huifenqi.jedi.track.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.net.SocketTimeoutException;
import java.time.LocalDate;

/**
 * Created by t3tiger on 2017/9/11.
 */
public class RedisTrackProducer implements TrackProducer {

    @Autowired
    TrackStorage trackStorage;

    @Autowired
    TrackRedisProperties trackRedisProperties;

    Jedis jedis;

    private RedisTrackProducer.MyEvent event;

    public RedisTrackProducer() {
        //初始化redis key
        CommonConst.RedisKeys.trackKey = "jedi:track:key:" + LocalDate.now().toString();


        //启动redis辅助进程
        event = new RedisTrackProducer.MyEvent();
        event.start();

    }

    @Override
    public void stop() {
        event.stopNow();
    }

    @Override
    public void doWork() {
        if (jedis == null || !jedis.isConnected()) {
            jedis = RedisUtil.getJedisClient(trackRedisProperties);
        }
        if (jedis == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        String content = trackStorage.pop();
        if (content == null) {
            return;
        }

        //Long listSize = jedis.llen(CommonConst.RedisKeys.trackKey);
        Long listSize = event.getDataSize();
        if (listSize > 1000) {
            System.err.println("redis中的数据未被消费完毕,丢弃:" + content);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            jedis.lpush(CommonConst.RedisKeys.trackKey, content);
        } catch (Exception e) {
            System.err.println("Redis连接超时...");
            e.printStackTrace();
        }

        //设置过期2天
        //jedis.expire(CommonConst.RedisKeys.trackKey, 2 * 24 * 60 * 60);
    }


    class MyEvent extends Thread {
        private boolean isRun = true;
        private long dataSize;

        @Override
        public void run() {
            while (isRun) {
                if (jedis == null) {
                    //休息1s
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        continue;
                    }
                }
                //设置key
                CommonConst.RedisKeys.trackKey = "jedi:track:key:" + LocalDate.now().toString();

                //检查redis数据长度
                dataSize = jedis.llen(CommonConst.RedisKeys.trackKey);

                //设置过期2天
                jedis.expire(CommonConst.RedisKeys.trackKey, 2 * 24 * 60 * 60);

                //休息15s
                try {
                    Thread.sleep(15 * 1000);
                } catch (InterruptedException e) {
                    continue;
                }
            }
        }

        public void stopNow() {
            isRun = false;
        }

        public long getDataSize() {
            return dataSize;
        }
    }

}
