package com.tpv.xmic.dlna.dmr.player.video;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.tpv.xmic.dlna.dmr.player.AVRenderState;
import com.tpv.xmic.dlna.dmr.player.AbsAVPresenter;
import com.tpv.xmic.dlna.dmr.service.RenderType;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/30 15:00
 * desc   :
 * version: 1.0
 */
public class VideoPresenter extends AbsAVPresenter {
    private static final String TAG = VideoPresenter.class.getSimpleName();
    private IVideoView mView;
    private boolean isSurfaceCreated = false;

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        mView = (IVideoView)owner;
        super.onCreate(owner);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        super.onDestroy(owner);
        mView = null;
    }

    public void onSurfaceCreated() {
        Log.d(TAG, "onSurfaceCreated() called");
        isSurfaceCreated = true;
        if (!TextUtils.isEmpty(mUrl)) {
            prepare(mMediaName, mUrl);
        }
    }

    public void onSurfaceDestroyed() {
        Log.d(TAG, "onSurfaceDestroyed() called");
        isSurfaceCreated = false;
        if (mPlayState == AVRenderState.NONE || mPlayState == AVRenderState.END || mPlayState == AVRenderState.RELEASING) {
            return;
        }
        releaseMediaPlay();
    }

    @Override
    public void prepare(String name, String url) {

        if (!isSurfaceCreated) {
            Log.e(TAG, "prepare: Surface no ready");
            return;
        }
        if (url.contains("?formatID=")) {
            url = url.substring(0, url.indexOf('?'));
            Log.d(TAG, "new dataUrl: " + url);
        }
        super.prepare(name, url);
    }

    @Override
    public void initMediaPlayListener() {
        mMediaPlay.setOnInfoListener((mp, what, extra) -> {
            Log.d(TAG, "mMediaPlay onInfo: called what = " + what);
            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                displayLoading(true);
            } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                displayLoading(false);
            }
            return true;
        });

        mMediaPlay.setOnPreparedListener(mp -> runOnUiThread(() -> {
            Log.d(TAG, "MediaPlay onPrepared() called ");
            mPlayState = AVRenderState.PREPARED;
            displayPlayerBG(false);
            displayLoading(false);
            displayControlBar();
            mMediaPlay.start();
            mPlayState = AVRenderState.STARTED;
            mPlayBackControl.resetPlayControls();
        }));

        mMediaPlay.setOnErrorListener((mp, what, extra) -> {
            mPlayState = AVRenderState.ERROR;
            displayPlayerBG(true);
            Log.e(TAG, "MediaPlay onPrepared() called: what = " + what + ", extra = " + extra);
            return true;
        });

        mMediaPlay.setOnCompletionListener(mp -> {
            Log.d(TAG, "MediaPlay onCompletion called ");
            displayPlayerBG(true);
            mPlayState = AVRenderState.COMPLETED;
            displayLoading(false);
            exit();
        });

        mMediaPlay.setOnSeekCompleteListener(mp -> {
            Log.d(TAG, "MediaPlay onSeekComplete() called ");
            displayControlBar();
            mPlayBackControl.getSeekBar().requestFocus();
            int pos = mp.getCurrentPosition();
            Log.d(TAG, "initMediaPlayListener: pos = " + pos + " target = " + mTargetSeekPos);
            if (Math.abs(pos - mTargetSeekPos) > 1000) {
                runOnUiThreadDelay(() -> {
                    isSeekCompleted = true;
                    seekTo(mTargetSeekPos);
                }, 300);
            } else {
                isSeekCompleted = true;
            }
            mPlayBackControl.onSeekCompleted();
        });
    }

    @Override
    public void displayPlayerBG(boolean display) {
        if (mView != null) {
            mView.displaySurfaceBg(display);
        }
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
            mPlayBackControl.show();
        }
    }

    @Override
    public void setMediaDisplayView() {
        mMediaPlay.setDisplay(mView.getSurfaceHolder());
    }

    @Override
    public void setRenderType() {
        if (mRemote != null && mRemote.getRemote() != null) {
            mRemote.getRemote().setRenderType(RenderType.VIDEO);
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
