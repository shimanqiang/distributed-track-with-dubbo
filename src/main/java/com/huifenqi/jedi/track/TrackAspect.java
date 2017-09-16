package com.huifenqi.jedi.track;

import com.alibaba.dubbo.rpc.RpcContext;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author smq
 */
@Aspect
@Configuration
//@Profile({"dev", "test", "pre"})
@ConditionalOnProperty(
        value = {"jedi.track.enabled"},
        matchIfMissing = false
)
public class TrackAspect {
    private Logger logger = Logger.getLogger(getClass());

    private static final ThreadLocal<String> TRACK_ID_LOCAL = new InheritableThreadLocal<>();
    private static final ThreadLocal<TrackBean> LOCAL = new ThreadLocal<>();


    @Autowired
    TrackStorage trackStorage;


    @Pointcut("(@annotation(com.huifenqi.jedi.track.anno.Track))  || (execution(public * com.huifenqi..facade..*Impl.*(..))) || (execution(public * com.huifenqi..controller..*.*(..)))  || (execution(public * com.huifenqi.jedi.router.service..*.*(..)))")
    public void track() {

    }


    @Around("track()")
    public Object doTrack(ProceedingJoinPoint pjp) throws Throwable {
        Object retObject = null;

        //TODO 策略处理
        try {
            String trackId = null;
            //Object[] args = pjp.getArgs();
            try {

                //从dubbo上下文中获取
                RpcContext rpcContext = RpcContext.getContext();
                if (rpcContext != null) {
                    trackId = rpcContext.getAttachment("trackId");
                } else {
                    logger.debug("Dubbo RPC Context is not work");
                }

                logger.debug("rpc:" + trackId);

                //从线程变量副本中获取
                if (null == trackId || trackId.length() == 0) {
                    trackId = TRACK_ID_LOCAL.get();
                }

                logger.debug("thread:" + trackId);

                //生成trackId
                if (null == trackId || trackId.length() == 0) {
                    trackId = UUID.randomUUID().toString();
                }

                logger.debug("uuid:" + trackId);

                //dubbo上下文保存trackId
                if (rpcContext != null) {
                    rpcContext.setAttachment("trackId", trackId);
                }

                //线程间保存trackId
                TRACK_ID_LOCAL.set(trackId);

                //构造持久化数据
                TrackBean bean = new TrackBean();
                bean.setStartTime(System.currentTimeMillis());
                bean.setTrackId(trackId);
                bean.setEnvironment(TrackApplicationContextHolder.getEnv());
                bean.setModule(TrackApplicationContextHolder.getModule());


                Signature signature = pjp.getSignature();
                bean.setClazz(signature.getDeclaringTypeName());
                bean.setMethod(signature.getName());

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
                if (bean != null) {
                    bean.setEndTime(System.currentTimeMillis());
                    trackStorage.add(bean.buildJson());
                } else {
                    logger.debug("----------------------LOCAL对象为空------------------------");
                }
            } catch (Exception e) {
                logger.warn("===============忽略3============", e);
            } finally {
                LOCAL.remove();
                //TRACK_ID_LOCAL.remove();
            }
        }
        return retObject;
    }


//
//    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
//
//    @Before("track()")
//    public void doBefore(JoinPoint joinPoint) {
//        logger.debug("成功进入切面");
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
