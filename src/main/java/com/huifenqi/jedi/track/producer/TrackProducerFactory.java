package com.huifenqi.jedi.track.producer;

import com.huifenqi.jedi.track.producer.redis.RedisTrackProducer;
import com.huifenqi.jedi.track.utils.SpringBeanUtils;

/**
 * Created by t3tiger on 2017/9/11.
 */
public class TrackProducerFactory {
    public static final TrackProducer create() {
        return SpringBeanUtils.getBean("redisTrackProducer", RedisTrackProducer.class);
    }
}
