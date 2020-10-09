package com.tpv.xmic.dlna.dmr;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/8/27 11:44
 * desc   :
 * version: 1.0
 */
public interface IResultListener<T> {
    void onCompleted(T t);
}
