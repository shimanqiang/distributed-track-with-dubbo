package com.huifenqi.jedi.track;

import com.huifenqi.jedi.track.anno.EnableTrack;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class EnableTrackRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        System.out.println(String.format("@%s这个注解现在什么也没有做的呢!!", EnableTrack.class.getSimpleName()));

    }
}
