package com.huifenqi.jedi.track.producer.redis;

import com.huifenqi.jedi.track.TrackStorage;
import com.huifenqi.jedi.track.producer.TrackProducer;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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

    private void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(1);
        config.setMaxIdle(8);

        JedisPool pool = new JedisPool(config,
                trackRedisProperties.getHost(),
                trackRedisProperties.getPort(),
                6000,
                trackRedisProperties.getPassword());

        jedis = pool.getResource();
        jedis.connect();
        jedis.select(trackRedisProperties.getDatabase());

        System.out.println(String.format("服务正在运行:%s，使用的DB:%d ", jedis.ping(), jedis.getDB()));
    }

    @Override
    public void doWork() {
        if (jedis == null || !jedis.isConnected()) {
            init();
        }

        String content = trackStorage.pop();
        if (content == null) {
            return;
        }
        System.out.println(content);

    }


}
