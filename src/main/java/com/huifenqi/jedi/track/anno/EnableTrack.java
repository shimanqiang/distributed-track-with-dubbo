package com.huifenqi.jedi.track.anno;

import com.huifenqi.jedi.track.config.TrackPresistentConfig;
import com.huifenqi.jedi.track.consumer.TrackConsumerManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import(value = {TrackConsumerManager.class})
//@Import(value = {EnableTrackRegistrar.class})
@EnableConfigurationProperties({TrackPresistentConfig.class})
public @interface EnableTrack {

}
