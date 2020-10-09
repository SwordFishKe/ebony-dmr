package com.tpv.xmic.dlna.dmr.interfaces;

import com.tpv.xmic.dlna.dmr.service.RenderType;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/27 15:29
 * desc   :
 * version: 1.0
 */
public interface IRemoteInterface {
    void setRenderType(RenderType type);

    void notifyStartPlay();

    void notifyStopPlay();

    void notifyPausePlay();

    void notifySetVolume(int vol);

    void notifyUpdateProgress(int progress, int total);
}
