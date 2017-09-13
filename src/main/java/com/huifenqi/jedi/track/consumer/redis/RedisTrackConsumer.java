package com.huifenqi.jedi.track.consumer.redis;

import com.huifenqi.jedi.track.common.CommonConst;
import com.huifenqi.jedi.track.config.TrackPresistentConfig;
import com.huifenqi.jedi.track.config.TrackRedisProperties;
import com.huifenqi.jedi.track.consumer.TrackConsumer;
import com.huifenqi.jedi.track.consumer.TrackConsumerManager;
import com.huifenqi.jedi.track.presistent.TrackPresistent;
import com.huifenqi.jedi.track.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by t3tiger on 2017/9/12.
 */
public class RedisTrackConsumer implements TrackConsumer {
    TrackPresistent trackPresistent;

    @Autowired
    TrackRedisProperties trackRedisProperties;

    @Autowired
    TrackPresistentConfig trackPresistentConfig;

    @Autowired
    TrackConsumerManager trackConsumerManager;

    Jedis jedis;

    @Override
    public void doWork() {
        if (jedis == null || !jedis.isConnected()) {
            jedis = RedisUtil.getJedisClient(trackRedisProperties);
        }
        if (jedis == null) {
            return;
        }

        //获取数据
        List<String> list = jedis.brpop(5, CommonConst.RedisKeys.trackKey);
        if (list == null || list.size() <= 1) {
            return;
        }
        String content = list.get(1);

        // 持久化
        if (trackPresistent == null) {
            try {
                Class<?> cls = Class.forName(trackPresistentConfig.getClassName());
                trackPresistent = (TrackPresistent) cls.newInstance();
            } catch (Exception e) {
                System.err.println("需要实现com.huifenqi.jedi.track.consumer.TrackPresistent接口，并配置jedi.track.presistent.className属性");
                return;
            }
        }

        if (trackPresistent != null) {
            trackPresistent.presistent(content);
        }
    }
}
