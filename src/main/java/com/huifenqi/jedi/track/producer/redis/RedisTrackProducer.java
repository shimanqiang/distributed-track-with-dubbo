package com.huifenqi.jedi.track.producer.redis;

import com.huifenqi.jedi.track.TrackStorage;
import com.huifenqi.jedi.track.common.CommonConst;
import com.huifenqi.jedi.track.config.TrackRedisProperties;
import com.huifenqi.jedi.track.producer.TrackProducer;
import com.huifenqi.jedi.track.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

/**
 * Created by t3tiger on 2017/9/11.
 */
public class RedisTrackProducer implements TrackProducer {

    @Autowired
    TrackStorage trackStorage;

    @Autowired
    TrackRedisProperties trackRedisProperties;

    Jedis jedis;

    public RedisTrackProducer() {
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

        jedis.lpush(CommonConst.RedisKeys.trackKey, content);

        //设置过期2天
        jedis.expire(CommonConst.RedisKeys.trackKey, 2 * 24 * 60 * 60);
    }


}
