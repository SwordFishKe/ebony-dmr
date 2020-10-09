package com.tpv.xmic.dlna.dmr.player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.tpv.xmic.dlna.dmr.DmrConstants;
import com.tpv.xmic.dlna.dmr.base.BaseApp;
import com.tpv.xmic.dlna.dmr.interfaces.IRenderFunc;
import com.tpv.xmic.dlna.dmr.service.DmrService;
import com.tpv.xmic.dlna.dmr.service.ProtocolBinder;
import com.tpv.xmic.dlna.dmr.service.RenderType;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/27 15:57
 * desc   :
 * version: 1.0
 */
public abstract class AbsPresenter implements DefaultLifecycleObserver, IRenderFunc {
    public static String TAG = AbsPresenter.class.getSimpleName();
    private PowerManager.WakeLock mWakeLock;
    private ScreenChangeEvents mScreenChangeEvents;
    private boolean isNeedWakeLock = false;

    public String mUrl;
    public String mMediaName;
    public ProtocolBinder mRemote;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        initIntent(((Activity)owner).getIntent());
        Intent intent = new Intent(BaseApp.getInstance().getApplicationContext(), DmrService.class);
        BaseApp.getInstance().getApplicationContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        registerScreenEvents();
        PowerManager powerManager = (PowerManager)BaseApp.getInstance().getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "dmr-player");
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        if (mRemote != null && mRemote.getRemote() != null) {
            mRemote.getRemote().setRenderType(RenderType.NO_PLAYING);
            mRemote.getRemote().notifyStopPlay();
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        if (mRemote != null) {
            mRemote = null;
        }
        BaseApp.getInstance().getApplicationContext().unbindService(mConnection);
        BaseApp.getInstance().getApplicationContext().unregisterReceiver(mScreenChangeEvents);
        if (isNeedWakeLock && mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    @Override
    public void next(String name, String url) {
        mMediaName = name;
        mUrl = url;
    }

    private void registerScreenEvents() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mScreenChangeEvents = new ScreenChangeEvents();
        BaseApp.getInstance().getApplicationContext().registerReceiver(mScreenChangeEvents, filter);
    }

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected() called");
            mRemote = ((ProtocolBinder)service);
            mRemote.setRender(AbsPresenter.this);
            if (TextUtils.isEmpty(mUrl)) {
                Log.e(TAG, "empty url ");
            } else {
                Log.d(TAG, "ready play media");
                setRenderType();
                prepare(mMediaName, mUrl);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected() called");
            mRemote = null;
        }
    };

    private void initIntent(Intent intent) {
        mUrl = intent.getStringExtra(DmrConstants.MEDIA_URL);
        mMediaName = intent.getStringExtra(DmrConstants.MEDIA_NAME);
    }

    private class ScreenChangeEvents extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(TAG, "action : " + action);
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                isNeedWakeLock = true;
                mWakeLock.acquire(3000);
                exit();
            } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
                isNeedWakeLock = false;
            }
        }
    }
}
