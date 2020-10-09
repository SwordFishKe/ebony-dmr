package org.droidtv.tv.dlna;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import org.droidtv.tv.dlna.interfaces.IDlnaCallback;
import org.droidtv.tv.dlna.interfaces.IDlnaInterface;
import org.droidtv.tv.dlna.interfaces.MediaType;
import org.droidtv.tv.dlna.interfaces.PlayState;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/25 15:44
 * desc   :
 * version: 1.0
 */
public class Dlna {

    private static final String TAG = Dlna.class.getSimpleName();
    private static volatile boolean isBind = false;

    private Lock lock = new ReentrantLock();
    private RemoteUPnPMediaRendererService mRemote;
    private IDlnaCallback mCallback;
    private IInitListener mListener;

    private Dlna() {
    }

    private static class SingletonInstance {
        private static final Dlna INSTANCE = new Dlna();
    }

    public static Dlna getInstance() {
        return Dlna.SingletonInstance.INSTANCE;
    }

    public void initDlna(Context context, IInitListener listener) {
        new Thread(() -> {
            try {
                lock.lock();
                Log.d(TAG, "notifyDlnaStart() called");
                mListener = listener;
                Intent startIntent = new Intent();
                startIntent.setAction(DlnaConstants.DLNA_ACTION);
                startIntent.setPackage(DlnaConstants.DLNA_PACKAGE);
//                context.getApplicationContext().sendBroadcast(startIntent, DlnaConstants.DLNA_PERMISSION);
                while (!isBind) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "wait bind sleep error");
                    }
                    Intent intent = new Intent();
                    intent.setPackage(DlnaConstants.DLNA_PACKAGE);
                    intent.setAction(DlnaConstants.DLNA_RENDERER_ACTION);
                    context.getApplicationContext().bindService(intent, mConn, Context.BIND_AUTO_CREATE);
                }
            } finally {
                lock.unlock();
            }
        }).start();
    }

    public void deInitDlna(Context context) {
        Log.d(TAG, "deInitDlna() called");
        try {
            lock.lock();
            if (isBind) {
                context.getApplicationContext().unbindService(mConn);
            }
            mCallback = null;
            mListener = null;
        } finally {
            lock.unlock();
        }

    }

    private final ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]");
            isBind = true;
            mRemote = RemoteUPnPMediaRendererService.Stub.asInterface(service);
            try {
                mRemote.registerRendererCallback(new RemoteCallback());
            } catch (RemoteException e) {
                Log.e(TAG, "onServiceConnected: register error");
            }
            if (mListener != null) {
                mListener.onInit(new DlnaInterfaceImp());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected() called with: name = [" + name + "]");
            isBind = false;
            mRemote = null;
            if (mListener != null) {
                mListener.onDeInit();
            }
        }
    };

    public interface IInitListener {
        void onInit(IDlnaInterface remote);

        void onDeInit();
    }

    private class RemoteCallback extends RemoteUPnPMediaRendererListener.Stub {

        @Override
        public int[] DMR_SetAVTransportUrl(int instanceId, String url, UPnPAVObject uPnPAVObject) throws RemoteException {
            String title = uPnPAVObject.getStringValue(UPnPConstants.UPNP_AV_OBJECT_TITLE);
            int type = uPnPAVObject.getIntegerValue(UPnPConstants.UPNP_AV_OBJECT_TYPE);
            Log.d(TAG, "Recieve-DMR-Msg: DMR_SetAVTransportUrl called type = " + type);
            MediaType mediaType;
            if (type == 1) {
                mediaType = MediaType.UNKNOWN;
            } else if (type == 2) {
                mediaType = MediaType.AUDIO;
            } else if (type == 3) {
                mediaType = MediaType.VIDEO;
            } else if (type == 4) {
                mediaType = MediaType.IMAGE;
            } else {
                mediaType = MediaType.ERROR;
            }

            if (mCallback != null) {
                mCallback.commandSetTransportUrl(title, mediaType, url);
            }
            return new int[2];
        }

        @Override
        public int DMR_Play(int InstanceID, int Speed) throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_Play() called with: InstanceID = [" + InstanceID + "], Speed = [" + Speed + "]");
            if (mCallback != null) {
                mCallback.commandPlay();
            }
            return 0;
        }

        @Override
        public int DMR_PlayWithPlaySpeed(int InstanceID, String Speed) throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_PlayWithPlaySpeed() called with: InstanceID = [" + InstanceID + "], Speed = [" + Speed + "]");
            if (mCallback != null) {
                mCallback.commandPlayWithPlaySpeed();
            }
            return 0;
        }

        @Override
        public int DMR_Stop(int InstanceID) throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_Stop() called with: InstanceID = [" + InstanceID + "]");
            if (mCallback != null) {
                mCallback.commandStop();
            }
            return 0;
        }

        @Override
        public int DMR_Pause(int InstanceID) throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_Pause() called with: InstanceID = [" + InstanceID + "]");
            if (mCallback != null) {
                mCallback.commandPause();
            }
            return 0;
        }

        @Override
        public int DMR_Seek(int InstanceID, int SeekMode, String target, int len) throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_Seek() called with: InstanceID = [" + InstanceID + "], SeekMode = [" + SeekMode + "], target = [" + target + "], len = [" + len + "]");
            int timeInMS = 0;
            if (!TextUtils.isEmpty(target)) {
                String[] units = target.trim().split(":");
                if (units.length == 4) {
                    int hrs = Integer.parseInt(units[0]);
                    int min = Integer.parseInt(units[1]);
                    int sec = Integer.parseInt(units[2]);
                    int msec = Integer.parseInt(units[3]);
                    timeInMS = ((hrs * 60 * 60 * 1000) + (min * 60 * 1000) + (sec * 1000) + (msec));
                } else if (units.length == 3) {
                    int hrs = Integer.parseInt(units[0]);
                    int min = Integer.parseInt(units[1]);
                    int sec = Integer.parseInt(units[2]);
                    timeInMS = ((hrs * 60 * 60 * 1000) + (min * 60 * 1000) + (sec * 1000));
                }
            }
            if (mCallback != null) {
                mCallback.commandSeekTarget(timeInMS);
            }
            return 0;
        }

        @Override
        public UPnPTransform[] DMR_getAllAvaiableTransforms() throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_getAllAvaiableTransforms() called");
            return null;
        }

        @Override
        public UPnPTransformSetting[] DMR_getCurrentTransformsList() throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_getCurrentTransformsList() called");
            return null;
        }

        @Override
        public int DMR_CreatePlayList(UPnPAVObject[] uPnPAVObjects, int i) throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_CreatePlayList() called ");
            return 0;
        }

        @Override
        public int DMR_AddToPlayList(UPnPAVObject[] uPnPAVObjects, int i) throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_AddToPlayList() called");
            return 0;
        }

        @Override
        public int DMR_ResetPlayList() throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_ResetPlayList() called");
            return 0;
        }

        @Override
        public int DMR_Next() throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_Next() called");
            return 0;
        }

        @Override
        public int DMR_Previous() throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_Previous() called");
            return 0;
        }

        @Override
        public int DMR_SelectedTrack(int i) throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_SelectedTrack() called with: i = [" + i + "]");
            return 0;
        }

        @Override
        public UPnPTransformSetting DMR_generateSupportedExternalSubtitleList(UPnPAVObject uPnPAVObject) throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_generateSupportedExternalSubtitleList() called ");
            return null;
        }

        @Override
        public int DMR_setTransform(UPnPTransformSetting[] uPnPTransformSettings, int i) throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_setTransform() called");
            return 0;
        }

        @Override
        public UPnPTransformSetting[] DMR_getSupportedSubtitleList() throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_getSupportedSubtitleList() called");
            return null;
        }

        @Override
        public int DMR_getAllowedTransforms() throws RemoteException {
            Log.d(TAG, "Recieve-DMR-Msg: DMR_getAllowedTransforms() called");
            return 0;
        }
    }

    private class DlnaInterfaceImp implements IDlnaInterface {
        private int MR_SERVICE_RCS = 0;
        private int MR_RCS_VAR_Volume = 7;

        @Override
        public void registerCallback(IDlnaCallback callback) {
            mCallback = callback;
        }

        @Override
        public void updatePlayState(PlayState state) throws RemoteException {
            if (mRemote != null) {
                mRemote.DMR_SetPlayState(state == null ? PlayState.STOPPED.getCode() : state.getCode());
            }
        }

        @Override
        public void updateProgress(int progress, int total) throws RemoteException {
            int pos = Math.round((float)progress / 1000);
            int totaltime = Math.round((float)total / 1000);
            if (mRemote != null) {
                mRemote.DMR_SetPlayProgressState(pos, totaltime, 0, 0);
            }
        }

        @Override
        public void setVolume(int volume) throws RemoteException {
            if (mRemote != null) {
                mRemote.DMR_SetStateVar(MR_SERVICE_RCS, MR_RCS_VAR_Volume, String.valueOf(volume), 1);
            }
        }
    }
}
