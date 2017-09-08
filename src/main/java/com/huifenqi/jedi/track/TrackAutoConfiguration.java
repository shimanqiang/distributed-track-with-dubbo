package com.huifenqi.jedi.track;

import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@ConditionalOnClass(RpcContext.class)
@Import(TrackAspect.class)
public class TrackAutoConfiguration {
}
