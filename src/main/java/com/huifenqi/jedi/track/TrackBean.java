package com.huifenqi.jedi.track;

/**
 * Created by t3tiger on 2017/9/7.
 */
public class TrackBean {
    /**
     * 环境
     */
    private String environment;
    /**
     *追踪ID
     */
    private String trackId;
    /**
     * 模块
     */
    private String module;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结果时间
     */
    private Long endTime;

    /**
     * 消耗时间
     */
    private Long executeTime;


    /**
     * 抛出的异常，没有异常为null
     */
    private Throwable throwable;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
        this.executeTime = this.endTime - this.startTime;
    }

    public Long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Long executeTime) {
        this.executeTime = executeTime;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toString() {
        return "{" +
                "environment:'" + environment + '\'' +
                ", trackId:'" + trackId + '\'' +
                ", module:'" + module + '\'' +
                ", startTime:" + startTime +
                ", endTime:" + endTime +
                ", executeTime:" + executeTime +
                ", throwable:" + throwable +
                '}';
    }
}
