package com.tpv.xmic.dlna.dmr.player.video;

import android.view.SurfaceHolder;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/30 15:20
 * desc   :
 * version: 1.0
 */
public interface IVideoView {
    SurfaceHolder getSurfaceHolder();
    void displaySurfaceBg(boolean display);

    void displayLoading(boolean display);

    void exit();
}
