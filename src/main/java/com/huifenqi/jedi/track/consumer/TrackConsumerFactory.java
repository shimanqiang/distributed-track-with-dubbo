package com.huifenqi.jedi.track.consumer;

import com.huifenqi.jedi.track.consumer.redis.RedisTrackConsumer;
import com.huifenqi.jedi.track.utils.SpringBeanUtils;

/**
 * Created by t3tiger on 2017/9/11.
 */
public class TrackConsumerFactory {
    public static final TrackConsumer create() {
        return SpringBeanUtils.getBean("redisTrackConsumer", RedisTrackConsumer.class);
    }
}
