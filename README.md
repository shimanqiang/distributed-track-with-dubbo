```yaml
spring:
  application:
    #必须配置-对应的模块
    name: jedi-track
jedi:
  track:
    #开启追踪
    enabled: true
    #Redis配置
    redis:
      enabled: true
      host: 47.93.114.117
      port: 6379
      password: test001
      database: 3
    #持久化配置(仅monitor配置即可)
    presistent:
      #写入文件:com.huifenqi.jedi.track.presistent.FilePresistent
      #控制台打印 : com.huifenqi.jedi.track.presistent.PrintConsolePresistent
      #其他持久化方式实现接口：com.huifenqi.jedi.track.presistent.TrackPresistent
      className: com.huifenqi.jedi.track.presistent.FilePresistent
      #持久化方式为文件时，需要配置，不配置默认/data/track
      path: /data/track

```

* 说明：持久化配置在monitor模块配置即可，需要具体的实现接口TrackPresistent，可扩展具体的持久化方式
* 对应monitor模块在做持久化的时候，需要通过@EnableTrack注解开启消费功能


```
@Pointcut("(@annotation(com.huifenqi.jedi.track.anno.Track))  || (execution(public * com.huifenqi..facade..*Impl.*(..))) || (execution(public * com.huifenqi..controller..*.*(..)))")

以上是配置默认的切面，如果你的模块不包含在内。需要在对应的方法上
添加@Track注解
```
