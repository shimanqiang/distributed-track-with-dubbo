package com.huifenqi.jedi.track.common;

import java.time.LocalDate;

/**
 * Created by t3tiger on 2017/9/12.
 */
public class CommonConst {
    public static class RedisKeys {
        public static String trackKey;
        static {
            trackKey = "jedi:track:key:" + LocalDate.now().toString();
        }
    }
}
