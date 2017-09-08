package com.huifenqi.jedi.track;

import org.springframework.context.ApplicationContext;

/**
 * Created by t3tiger on 2017/9/7.
 */
public class TrackApplicationContextHolder {
    public static ApplicationContext ctx = null;

    public static String getEnv() {
        String env = null;
        if (ctx != null) {
            env = ctx.getEnvironment().getProperty("spring.profiles.active");
        }
        return env;
    }

    public static String getModule() {
        String module = null;
        if (ctx != null) {
            module = ctx.getEnvironment().getProperty("spring.application.name");
        }
        return module;
    }
}
