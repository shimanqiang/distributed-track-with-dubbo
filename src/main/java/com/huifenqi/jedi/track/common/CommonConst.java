package com.huifenqi.jedi.track.common;

import java.time.LocalDate;

/**
 * Created by t3tiger on 2017/9/12.
 */
public interface CommonConst {
    interface RedisKeys {
        String trackKey = "jedi:track:key:" + LocalDate.now().toString();
    }
}
