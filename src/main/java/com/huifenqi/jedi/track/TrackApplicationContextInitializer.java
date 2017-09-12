package com.huifenqi.jedi.track;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class TrackApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TrackApplicationContextHolder.ctx = applicationContext;

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        //System.out.println("启动的环境：" + environment.getProperty("spring.profiles.active"));
    }
}
