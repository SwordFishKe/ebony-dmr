package com.tpv.xmic.dlna.dmr.interfaces;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/27 16:52
 * desc   :
 * version: 1.0
 */
public interface IRenderFunc {

    void setRenderType();

    void prepare(String name, String url);

    void play();

    void pause();

    void stop();

    void seekTo(int time);

    void setVolume(int vol);

    int getCurrentPosition();

    int getDuration();

    void exit();

    void next(String name, String url);
}
