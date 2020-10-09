package com.tpv.xmic.dlna.dmr.service;

import android.os.Binder;

import com.tpv.xmic.dlna.dmr.interfaces.IRemoteInterface;
import com.tpv.xmic.dlna.dmr.interfaces.IRenderFunc;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/28 8:42
 * desc   :
 * version: 1.0
 */
public class ProtocolBinder extends Binder {

    private IRenderFunc mRender;
    private IRemoteInterface mRemote;

    public ProtocolBinder(IRemoteInterface remote) {
        this.mRemote = remote;
    }

    public IRemoteInterface getRemote() {
        return mRemote;
    }

    public void setRender(IRenderFunc render) {
        this.mRender = render;
    }

    public IRenderFunc getRender() {
        return this.mRender;
    }
}
