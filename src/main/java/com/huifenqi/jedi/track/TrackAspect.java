package com.huifenqi.jedi.track;

import com.alibaba.dubbo.rpc.RpcContext;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Aspect
@Configuration
//@Profile({"dev", "test", "pre"})
public class TrackAspect {
    private Logger logger = Logger.getLogger(getClass());

    private static final ThreadLocal<TrackBean> LOCAL = new ThreadLocal<>();


    @Pointcut("(@annotation(com.huifenqi.jedi.track.anno.Track))  || (execution(public * com.huifenqi..facade..*Impl.*(..))) || (execution(public * com.huifenqi..controller..*.*(..)))")
    public void track() {

    }

    @Around("track()")
    public Object doTrack(ProceedingJoinPoint pjp) throws Throwable {
        Object retObject = null;
        //TODO 策略处理
        try {
            try {
                String trackId = RpcContext.getContext().getAttachment("trackId");
                if (null == trackId || trackId.length() == 0) {
                    trackId = UUID.randomUUID().toString();
                    RpcContext.getContext().setAttachment("trackId", trackId);
                }
                TrackBean bean = new TrackBean();
                bean.setStartTime(System.currentTimeMillis());
                bean.setTrackId(trackId);
                bean.setEnvironment(TrackApplicationContextHolder.getEnv());
                bean.setModule(TrackApplicationContextHolder.getModule());
                LOCAL.set(bean);
            } catch (Exception e) {
                logger.warn("===============忽略1============", e);
            } finally {
                retObject = pjp.proceed();
            }

        } catch (Throwable t) {
            try {
                TrackBean bean = LOCAL.get();
                bean.setThrowable(t);
            } catch (Exception e) {
                logger.warn("===============忽略2============", e);
            } finally {
                throw t;
            }
        } finally {
            try {
                TrackBean bean = LOCAL.get();
                bean.setEndTime(System.currentTimeMillis());


                if (LOCAL.get() != null) {
                    System.out.println(LOCAL.get().toString());
                } else {
                    System.out.println("----------------------LOCAL对象为空------------------------------");
                }

                //TODO save or  mq or redis
            } catch (Exception e) {
                logger.warn("===============忽略3============", e);
            } finally {
                LOCAL.remove();
            }
        }
        return retObject;
    }


//
//    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
//
//    @Before("track()")
//    public void doBefore(JoinPoint joinPoint) {
//        System.out.println("成功进入切面");
//        logger.debug("doBefore");
//        startTime.set(System.currentTimeMillis());
//
//        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "#" + joinPoint.getSignature().getName());
//        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
//    }
//
//    @After("track()")
//    public void doAfter(JoinPoint joinPoint) {
//
//        logger.debug("doAfter");
//    }
//
//    @AfterReturning(returning = "result", pointcut = "track()")
//    public void doAfterReturning(Object result) throws Exception {
//        logger.debug("doAfterReturning");
//        logger.info("RESP TIME : " + (System.currentTimeMillis() - startTime.get()) + " ms");
//        startTime.remove();
//    }
}
