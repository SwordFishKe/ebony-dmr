package org.droidtv.tv.dlna.interfaces;

import android.os.RemoteException;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/27 13:26
 * desc   :
 * version: 1.0
 */
public interface IDlnaInterface {

    /**
     * @param callback
     */
    void registerCallback(IDlnaCallback callback);

    /**
     * set local dmr state
     * @param state
     * @throws RemoteException
     */
    void updatePlayState(PlayState state) throws RemoteException;

    /**
     * set local play progress
     * @param progress
     * @param total
     * @throws RemoteException
     */
    void updateProgress(int progress, int total) throws RemoteException;

    /**
     * set system volume
     * @param volume
     * @throws RemoteException
     */
    void setVolume(int volume) throws RemoteException;

}
