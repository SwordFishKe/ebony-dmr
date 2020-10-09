package com.tpv.xmic.dlna.dmr.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.tpv.xmic.dlna.dmr.DmrConstants;
import com.tpv.xmic.dlna.dmr.base.BaseApp;
import com.tpv.xmic.dlna.dmr.interfaces.IRemoteInterface;
import com.tpv.xmic.dlna.dmr.player.audio.AudioActivity;
import com.tpv.xmic.dlna.dmr.player.image.ImageActivity;
import com.tpv.xmic.dlna.dmr.player.video.VideoActivity;

import org.droidtv.tv.dlna.Dlna;
import org.droidtv.tv.dlna.interfaces.IDlnaCallback;
import org.droidtv.tv.dlna.interfaces.IDlnaInterface;
import org.droidtv.tv.dlna.interfaces.MediaType;
import org.droidtv.tv.dlna.interfaces.PlayState;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DmrService extends Service {
    private static final String TAG = DmrService.class.getSimpleName();
    private IDlnaInterface mDlna;
    private ProtocolBinder mBinder = null;
    private DlnaCallback mDlnaCallback = null;
    private RenderType mRenderType = null;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");
        mRenderType = RenderType.NO_PLAYING;
        initDlna();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (mBinder == null) {
            mBinder = new ProtocolBinder(new RemoteInterface());
        }
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
        mRenderType = RenderType.NO_PLAYING;
        Dlna.getInstance().deInitDlna(this);
        if (mBinder != null) {
            mBinder.setRender(null);
            mBinder = null;
        }
    }

    private void initDlna() {
        Log.d(TAG, "initDlna() called");
        Dlna.getInstance().initDlna(this, new Dlna.IInitListener() {
            @Override
            public void onInit(IDlnaInterface iDlnaInterface) {
                Log.d(TAG, "onInit: succeed");
                mDlna = iDlnaInterface;
                if (mDlnaCallback != null) {
                    mDlnaCallback = null;
                }
                if (mBinder == null) {
                    mBinder = new ProtocolBinder(new RemoteInterface());
                }
                mDlnaCallback = new DlnaCallback(new WeakReference<>(DmrService.this));
                mDlna.registerCallback(mDlnaCallback);
            }

            @Override
            public void onDeInit() {
                Log.d(TAG, "onDeInit succeed");
                mDlna = null;
            }
        });
    }

    private static class DlnaCallback implements IDlnaCallback {
        private WeakReference<DmrService> mRef;
        private Lock lock = new ReentrantLock();
        private Handler handler = new Handler(Looper.getMainLooper());

        public DlnaCallback(WeakReference<DmrService> reference) {
            this.mRef = reference;
        }

        private void runOnMainThread(Runnable runnable) {
            handler.post(() -> {
                try {
                    lock.lock();
                    if (runnable != null) {
                        runnable.run();
                    }
                } finally {
                    lock.unlock();
                }
            });
        }

        @Override
        public void commandSetTransportUrl(String mediaName, MediaType mediaType, String url) {
            runOnMainThread(() -> {
                Log.d(TAG, "commandSetTransportUrl() called with: mediaName = [" + mediaName + "], mediaType = [" + mediaType + "], url = [" + url + "]");
                Intent intent = null;
                RenderType type = RenderType.NO_PLAYING;
                switch (mediaType) {
                    case AUDIO:
                        type = RenderType.AUDIO;
                        intent = new Intent(BaseApp.getInstance(), AudioActivity.class);
                        break;
                    case VIDEO:
                    case UNKNOWN:
                        type = RenderType.VIDEO;
                        intent = new Intent(BaseApp.getInstance(), VideoActivity.class);
                        break;
                    case IMAGE:
                        type = RenderType.IMAGE;
                        intent = new Intent(BaseApp.getInstance(), ImageActivity.class);
                        break;
                    default:
                        break;
                }
                if (intent != null && !TextUtils.isEmpty(url)) {
                    if (type.equals(mRef.get().mRenderType)) {
                        if (mRef.get().mBinder != null && mRef.get().mBinder.getRender() != null) {
                            Log.d(TAG, "commandSetTransportUrl: play new media source");
                            mRef.get().mBinder.getRender().next(mediaName, url);
                        } else {
                            Log.e(TAG, "commandSetTransportUrl: error binder");
                        }
                    } else {
                        if (!RenderType.NO_PLAYING.equals(mRef.get().mRenderType)) {
                            Log.d(TAG, "commandSetTransportUrl: finish before media");
                            if (mRef.get().mBinder != null && mRef.get().mBinder.getRender() != null) {
                                mRef.get().mBinder.getRender().exit();
                            }
                        }
                        mRef.get().mRenderType = type;

                        Log.d(TAG, "commandSetTransportUrl: play new media type source");
                        intent.putExtra(DmrConstants.MEDIA_NAME, mediaName);
                        intent.putExtra(DmrConstants.MEDIA_URL, url);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        BaseApp.getInstance().startActivity(intent);
                    }
                } else {
                    Log.e(TAG, "commandSetTransportUrl: error type");
                }
            });
        }

        @Override
        public void commandPlay() {
            runOnMainThread(() -> {
                if (mRef.get().mBinder != null && mRef.get().mBinder.getRender() != null) {
                    mRef.get().mBinder.getRender().play();
                }
            });
        }

        @Override
        public void commandPlayWithPlaySpeed() {
            runOnMainThread(() -> {
                if (mRef.get().mBinder != null && mRef.get().mBinder.getRender() != null) {
                    mRef.get().mBinder.getRender().play();
                }
            });
        }

        @Override
        public void commandSeekTarget(int time) {
            runOnMainThread(() -> {
                if (mRef.get().mBinder != null && mRef.get().mBinder.getRender() != null) {
                    mRef.get().mBinder.getRender().seekTo(time);
                }
            });
        }

        @Override
        public void commandPause() {
            runOnMainThread(() -> {
                if (mRef.get().mBinder != null && mRef.get().mBinder.getRender() != null) {
                    mRef.get().mBinder.getRender().pause();
                }
            });
        }

        @Override
        public void commandStop() {
            runOnMainThread(() -> {
                if (mRef.get().mBinder != null && mRef.get().mBinder.getRender() != null) {
                    mRef.get().mBinder.getRender().stop();
                }
            });
        }
    }

    private class RemoteInterface implements IRemoteInterface {
        private ExecutorService mExecutor;

        RemoteInterface() {
            mExecutor = Executors.newFixedThreadPool(5);
        }

        @Override
        public void setRenderType(RenderType type) {
            Log.d(TAG, "setRenderType() called with: type = [" + type + "]");
            mRenderType = type == null ? RenderType.NO_PLAYING : type;
        }

        @Override
        public void notifyStartPlay() {
            mExecutor.execute(() -> {
                if (mDlna != null) {
                    try {
                        Log.d(TAG, "notifyStartPlay() called");
                        mDlna.updatePlayState(PlayState.PLAYING);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void notifyStopPlay() {
            mExecutor.execute(() -> {
                if (mDlna != null) {
                    try {
                        Log.d(TAG, "notifyStopPlay() called");
                        mDlna.updatePlayState(PlayState.STOPPED);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void notifyPausePlay() {
            mExecutor.execute(() -> {
                if (mDlna != null) {
                    try {
                        Log.d(TAG, "notifyPausePlay() called");
                        mDlna.updatePlayState(PlayState.PAUSED);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void notifySetVolume(int vol) {
            mExecutor.execute(() -> {
                if (mDlna != null) {
                    try {
                        Log.d(TAG, "notifySetVolume() called with: vol = [" + vol + "]");
                        mDlna.setVolume(vol);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void notifyUpdateProgress(int progress, int total) {
            mExecutor.execute(() -> {
                if (mDlna != null) {
                    try {
                        Log.d(TAG, "notifyUpdateProgress() called with: progress = [" + progress + "], total = [" + total + "]");
                        mDlna.updateProgress(progress, total);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
