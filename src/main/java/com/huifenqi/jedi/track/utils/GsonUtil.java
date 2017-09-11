package com.huifenqi.jedi.track.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

    private static Gson gson = new GsonBuilder()
            //.setPrettyPrinting()  // 输出的时候易读
            .serializeNulls()  // 对于值为null的字段也支持序列化
            .setDateFormat("yyyy-MM-dd HH:mm:ss")  // 对于Date类型值解析为指定类型
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)  // 将实体类的驼峰式变量序列化为小写+下划线的字段
            .create();

    private static Gson gsonNormal = new GsonBuilder()
            //.setPrettyPrinting()  // 输出的时候易读
            .setDateFormat("yyyy-MM-dd HH:mm:ss")  // 对于Date类型值解析为指定类型
            .create();


    public static Gson buildGson() {
        return gson;
    }

    public static Gson buildGsonNormal() {
        return gsonNormal;
    }
}