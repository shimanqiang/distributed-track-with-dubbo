package com.huifenqi.jedi.track.presistent;

/**
 * Created by t3tiger on 2017/9/12.
 */
public class PrintConsolePresistent implements TrackPresistent {
    @Override
    public void presistent(String content) {
        System.out.println(content);
    }
}
