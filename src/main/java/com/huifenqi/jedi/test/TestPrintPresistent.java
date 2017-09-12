package com.huifenqi.jedi.test;

import com.huifenqi.jedi.track.consumer.TrackPresistent;

/**
 * Created by t3tiger on 2017/9/12.
 */
public class TestPrintPresistent implements TrackPresistent {
    @Override
    public void presistent(String content) {
        System.out.println("TestPrintPresistent:" + content);
    }
}
