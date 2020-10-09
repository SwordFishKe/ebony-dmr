package com.tpv.xmic.dlna.dmr.player.audio;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.tpv.xmic.dlna.dmr.player.AVRenderState;
import com.tpv.xmic.dlna.dmr.player.AbsAVPresenter;
import com.tpv.xmic.dlna.dmr.service.RenderType;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/29 16:54
 * desc   :
 * version: 1.0
 */
class AudioPresenter extends AbsAVPresenter {
    private static final String TAG = AudioPresenter.class.getSimpleName();
    private IAudioView mView;

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        mView = (IAudioView)owner;
        super.onCreate(owner);
        Log.d(TAG, "onCreate() called ");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "onResume: called");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        super.onPause(owner);
        Log.d(TAG, "onPause: called");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        super.onDestroy(owner);
        mView = null;
    }

    @Override
    public void initController(LifecycleOwner owner) {
        super.initController(owner);
        mPlayBackControl.setEnableTimeout(false);
        mPlayBackControl.setOnControlBarListener(this :: exit);
    }

    @Override
    public void initMediaPlayListener() {
        mMediaPlay.setOnPreparedListener(mp -> runOnUiThread(() -> {
            Log.d(TAG, "MediaPlay onPrepared() called ");
            mPlayState = AVRenderState.PREPARED;
            displayLoading(false);
            displayControlBar();
            mMediaPlay.start();
            mPlayState = AVRenderState.STARTED;
            mPlayBackControl.resetPlayControls();
        }));

        mMediaPlay.setOnErrorListener((mp, what, extra) -> {
            mPlayState = AVRenderState.ERROR;
            Log.e(TAG, "MediaPlay onPrepared() called: what = " + what + ", extra = " + extra);
            return true;
        });

        mMediaPlay.setOnCompletionListener(mp -> {
            Log.d(TAG, "MediaPlay onCompletion called ");
            mPlayState = AVRenderState.COMPLETED;
            displayLoading(false);
            exit();
        });

        mMediaPlay.setOnSeekCompleteListener(mp -> {
            Log.d(TAG, "MediaPlay onSeekComplete() called ");
            int pos = mp.getCurrentPosition();
            Log.d(TAG, "initMediaPlayListener: pos = " + pos + " target = " + mTargetSeekPos);
            if (Math.abs(pos - mTargetSeekPos) > 1000) {

            }
            isSeekCompleted = true;
            mPlayBackControl.onSeekCompleted();
        });
    }

    @Override
    public void displayPlayerBG(boolean display) {
    }

    @Override
    public void displayLoading(boolean display) {
        if (mView != null) {
            mView.displayLoading(display);
        }
    }

    @Override
    public void displayControlBar() {
        if (mPlayBackControl != null) {
            mPlayBackControl.show(Integer.MAX_VALUE);
        }
    }

    @Override
    public void setMediaDisplayView() {
    }

    @Override
    public void setRenderType() {
        if (mRemote != null && mRemote.getRemote() != null) {
            mRemote.getRemote().setRenderType(RenderType.AUDIO);
        }
    }

    @Override
    public void exit() {
        super.exit();
        if (mView != null) {
            mView.exit();
        } else {
            Log.e(TAG, "exit: view is null");
        }
    }
}
