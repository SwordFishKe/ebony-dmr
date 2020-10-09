package org.droidtv.tv.dlna.interfaces;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/27 13:29
 * desc   :
 * version: 1.0
 */
public enum PlayState {
    PLAYING(1), PAUSED(2), STOPPED(3);
    int code;

    PlayState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
