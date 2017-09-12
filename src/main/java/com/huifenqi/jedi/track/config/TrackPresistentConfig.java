package com.huifenqi.jedi.track.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by t3tiger on 2017/9/12.
 */

@ConfigurationProperties(prefix = "jedi.track.presistent")
public class TrackPresistentConfig {
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
