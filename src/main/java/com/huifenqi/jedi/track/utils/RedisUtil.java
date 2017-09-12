package com.huifenqi.jedi.track.utils;

import com.huifenqi.jedi.track.config.TrackRedisProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by t3tiger on 2017/9/12.
 */
public class RedisUtil {
    public static Jedis getJedisClient(TrackRedisProperties trackRedisProperties) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(1);
        config.setMaxIdle(8);

        JedisPool pool = new JedisPool(config,
                trackRedisProperties.getHost(),
                trackRedisProperties.getPort(),
                6000,
                trackRedisProperties.getPassword());

        Jedis jedis = pool.getResource();
        jedis.connect();
        jedis.select(trackRedisProperties.getDatabase());

        System.out.println(String.format("服务正在运行:%s，使用的DB:%d ", jedis.ping(), jedis.getDB()));

        return jedis;
    }
}
