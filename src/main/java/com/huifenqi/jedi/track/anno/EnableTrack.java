package com.huifenqi.jedi.track.anno;

import com.huifenqi.jedi.track.EnableTrackRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import(value = {EnableTrackRegistrar.class})
public @interface EnableTrack {

}
