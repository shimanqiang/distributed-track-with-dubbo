package com.huifenqi.jedi.track;

import com.alibaba.dubbo.rpc.RpcContext;
import com.huifenqi.jedi.track.config.TrackRedisProperties;
import com.huifenqi.jedi.track.consumer.TrackConsumerManager;
import com.huifenqi.jedi.track.consumer.redis.RedisTrackConsumer;
import com.huifenqi.jedi.track.producer.TrackProducerManager;
import com.huifenqi.jedi.track.producer.redis.RedisTrackProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(RpcContext.class)
@Import(TrackAspect.class)
@EnableConfigurationProperties({TrackRedisProperties.class})
//@AutoConfigureOrder(Integer.MIN_VALUE)
public class TrackAutoConfiguration {

    @Bean
    public RedisTrackProducer redisTrackProducer() {
        return new RedisTrackProducer();
    }

    @Bean
    @ConditionalOnBean(TrackConsumerManager.class)
    public RedisTrackConsumer redisTrackConsumer() {
        return new RedisTrackConsumer();
    }

    @Bean
    public TrackStorage trackStorage() {
        return new TrackStorage();
    }

    @Bean
    @ConditionalOnProperty(
            value = {"jedi.track.enabled"},
            matchIfMissing = false
    )
    public TrackProducerManager trackConsumerManager() {
        return new TrackProducerManager();
    }
}
