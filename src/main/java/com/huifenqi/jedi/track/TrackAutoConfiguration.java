package com.huifenqi.jedi.track;

import com.huifenqi.jedi.track.producer.redis.RedisTrackProducer;
import com.huifenqi.jedi.track.producer.redis.TrackRedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

@Configuration
//@ConditionalOnClass(RpcContext.class)
@Import(TrackAspect.class)
@EnableConfigurationProperties({TrackRedisProperties.class})
public class TrackAutoConfiguration implements Ordered{

    @Bean
    public RedisTrackProducer redisTrackProducer() {
        return new RedisTrackProducer();
    }

    @Bean
    public TrackStorage trackStorage() {
        return new TrackStorage();
    }


    @Override
    public int getOrder() {
        return -1234560;
    }
}
