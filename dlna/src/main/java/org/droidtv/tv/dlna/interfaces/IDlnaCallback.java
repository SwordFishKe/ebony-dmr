package org.droidtv.tv.dlna.interfaces;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/27 13:13
 * desc   :
 * version: 1.0
 */
public interface IDlnaCallback {

    /**
     * start play
     * @param mediaName name
     * @param mediaType type
     * @param url paly url
     */
    void commandSetTransportUrl(String mediaName, MediaType mediaType, String url);

    void commandPlay();

    void commandPlayWithPlaySpeed();

    /**
     * set seek to @target
     * @param target
     */
    void commandSeekTarget(int target);

    void commandPause();

    void commandStop();
}
