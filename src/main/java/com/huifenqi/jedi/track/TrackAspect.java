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

    private static final ThreadLocal<TrackBean> LOCAL = new ThreadLocal<>();


    @Autowired
    TrackStorage trackStorage;


    @Pointcut("(@annotation(com.huifenqi.jedi.track.anno.Track))  || (execution(public * com.huifenqi..facade..*Impl.*(..))) || (execution(public * com.huifenqi..controller..*.*(..)))  || (execution(public * com.huifenqi.jedi.router.service.*.*(..)))")
    public void track() {

    }


    @Around("track()")
    public Object doTrack(ProceedingJoinPoint pjp) throws Throwable {
        Object retObject = null;
        boolean isPortal = false;//
        //TODO 策略处理
        try {
            String trackId = null;
            //Object[] args = pjp.getArgs();
            try {

                //从线程变量中获取
                TrackBean trackBean = LOCAL.get();
                if (trackBean != null) {
                    trackId = trackBean.getTrackId();
                }

                //从dubbo上下文中获取
                RpcContext rpcContext = RpcContext.getContext();
                if (null == trackId || trackId.length() == 0) {
                    if (rpcContext != null) {
                        trackId = rpcContext.getAttachment("trackId");
                    } else {
                        logger.warn("Dubbo RPC is not work");
                    }
                }

                //生成trackId
                if (null == trackId || trackId.length() == 0) {
                    trackId = UUID.randomUUID().toString();
                    isPortal = true;
                }

                //dubbo上下文保存trackId
                if (rpcContext != null) {
                    rpcContext.setAttachment("trackId", trackId);
                }

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
                    System.out.println("----------------------LOCAL对象为空------------------------------");
                }
            } catch (Exception e) {
                logger.warn("===============忽略3============", e);
            } finally {
                if (isPortal) {
                    isPortal = false;
                    LOCAL.remove();
                }
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
