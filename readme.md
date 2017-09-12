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
    #持久化配置
    presistent:
      className: com.huifenqi.jedi.test.TestPrintPresistent

```

* 说明：持久化配置在monitor模块配置即可，需要具体的实现接口TrackPresistent。
* 对应monitor模块在做持久化的时候，需要通过@EnableTrack注解开启消费功能