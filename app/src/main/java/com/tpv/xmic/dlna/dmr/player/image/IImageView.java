package com.tpv.xmic.dlna.dmr.player.image;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/28 11:20
 * desc   :
 * version: 1.0
 */
public interface IImageView {
    public void prepare(String name, String url);

    public void next(String name, String url) ;

    public void exit();
}
