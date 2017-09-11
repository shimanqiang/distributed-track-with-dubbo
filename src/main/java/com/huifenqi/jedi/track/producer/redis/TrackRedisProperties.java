package com.huifenqi.jedi.track.producer.redis;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by t3tiger on 2017/9/11.
 */
//@ConditionalOnProperty(
//        value = {"jedi.track.redis.enabled"},
//        matchIfMissing = false
//)
@ConfigurationProperties(
        prefix = "jedi.track.redis"
)

@AutoConfigureBefore({RedisTrackProducer.class})
public class TrackRedisProperties {
    private boolean enabled;
    private String host;
    private int port;
    private String password;
    private int database;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
}
