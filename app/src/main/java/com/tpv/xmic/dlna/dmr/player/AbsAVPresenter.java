package com.tpv.xmic.dlna.dmr.player;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LifecycleOwner;

import com.tpv.xmic.dlna.dmr.base.BaseApp;

import org.droidtv.ui.playback.AbsMediaPlayerControl;
import org.droidtv.ui.playback.PlayBackControl2;
import org.droidtv.ui.playback.utility.PlaybackControlConstants;

import java.io.IOException;
import java.util.Objects;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/29 9:58
 * desc   :
 * version: 1.0
 */
public abstract class AbsAVPresenter extends AbsPresenter {
    public static String TAG = AbsAVPresenter.class.getSimpleName();
    public PlayBackControl2 mPlayBackControl;

    public MediaPlayer mMediaPlay;
    public AVRenderState mPlayState = AVRenderState.NONE;
    private Handler mHandler = null;

    //for seek multi before complete
    protected boolean isSeekCompleted = true;
    protected int mTargetSeekPos = 0;

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        mHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        initController(owner);
        initMediaPlayer();
        super.onCreate(owner);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        super.onDestroy(owner);
        releaseMediaPlay();
        mPlayBackControl.hide();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    @Override
    public void prepare(String name, String url) {
        Log.d(TAG, "prepare() called with: name = [" + name + "], url = [" + url + "], play state = " + mPlayState);
        mPlayBackControl.setMetadata(PlaybackControlConstants.PLAY_SPEED_TEXT, name);

        if (mMediaPlay == null) {
            initMediaPlayer();
        } else {
            mMediaPlay.reset();
        }

        mPlayState = AVRenderState.IDLE;
        mMediaPlay.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build());

        try {
            setMediaDisplayView();
            mMediaPlay.setDataSource(url);
            mPlayState = AVRenderState.INITIALIZED;
            mMediaPlay.prepareAsync();
            mPlayState = AVRenderState.PREPARING;

        } catch (IOException e) {
            Log.e(TAG, "prepare: set data error", e);
        }

        if (mRemote != null && mRemote.getRemote() != null) {
            mRemote.getRemote().notifyStartPlay();
        }
    }

    @Override
    public void next(String name, String url) {
        super.next(name, url);
        Log.d(TAG, "next() called ");
        if (mMediaPlay == null) {
            initMediaPlayer();
        }
        mMediaPlay.reset();
        mPlayState = AVRenderState.IDLE;

        prepare(name, url);
    }

    @Override
    public void play() {
        runOnUiThread(() -> {
            Log.d(TAG, "play called, mPlayState = " + mPlayState);
            if (mRemote != null && mRemote.getRemote() != null) {
                mRemote.getRemote().notifyStartPlay();
            }
            displayControlBar();
            if (mMediaPlay != null && (mPlayState == AVRenderState.PREPARED || mPlayState == AVRenderState.PAUSED)) {
                mMediaPlay.start();
                if (mPlayState == AVRenderState.PREPARED) {
                    displayPlayerBG(false);
                }
                mPlayState = AVRenderState.STARTED;
                mPlayBackControl.resetPlayControls();
            }
        });
    }

    @Override
    public void stop() {
        runOnUiThread(() -> {
            Log.d(TAG, "stop() called, mPlayState = " + mPlayState);
            if (mRemote != null && mRemote.getRemote() != null) {
                //TODO
                mRemote.getRemote().notifyStopPlay();
            }
            if (mPlayState == AVRenderState.STARTED || mPlayState == AVRenderState.PREPARED
                    || mPlayState == AVRenderState.PAUSED || mPlayState == AVRenderState.COMPLETED) {
                mMediaPlay.stop();
                mPlayState = AVRenderState.STOPPED;
            }
        });
    }

    @Override
    public void pause() {
        runOnUiThread(() -> {
            Log.d(TAG, "pause() called, mPlayState = " + mPlayState);
            displayControlBar();
            if (mRemote != null && mRemote.getRemote() != null) {
                mRemote.getRemote().notifyPausePlay();
            }
            if (mMediaPlay != null && mPlayState == AVRenderState.STARTED) {
                mMediaPlay.pause();
                mPlayState = AVRenderState.PAUSED;
                mPlayBackControl.resetPlayControls();
            }
        });
    }

    @Override
    public void setVolume(int vol) {
        ((AudioManager)Objects.requireNonNull(BaseApp.getInstance().getSystemService(Context.AUDIO_SERVICE)))
                .setStreamVolume(AudioManager.STREAM_MUSIC, vol, AudioManager.FLAG_SHOW_UI);
    }

    @Override
    public void seekTo(int time) {
        runOnUiThread(() -> {
            Log.d(TAG, "seekTo() called with: time = [" + time + "], PlayState = " + mPlayState);
            if (mMediaPlay == null) {
                Log.e(TAG, "seekTo: MediaPlay is null");
                return;
            }
            if (mPlayState == AVRenderState.PREPARING || mPlayState == AVRenderState.RELEASING) {
                Log.e(TAG, "seekTo error, data not ready ");
                return;
            }
            if (!isSeekCompleted) {
                Log.d(TAG, "waiting seek complete last time");
                return;
            }
            mTargetSeekPos = Math.max(time, 0);
            isSeekCompleted = false;
            if (getDuration() > 0 && (mPlayState == AVRenderState.PREPARED || mPlayState == AVRenderState.STARTED
                    || mPlayState == AVRenderState.PAUSED)) {
                mMediaPlay.seekTo(time);
            }
        });
    }

    @Override
    public int getCurrentPosition() {
        Log.d(TAG, "getCurrentPosition() called, mPlayState = " + mPlayState);
        int pos = 0;
        if (mMediaPlay == null) {
            Log.e(TAG, "getCurrentPosition: mMediaPlay is null");
            return pos;
        }
        if (mPlayState == AVRenderState.INITIALIZED || mPlayState == AVRenderState.PREPARED
                || mPlayState == AVRenderState.STARTED || mPlayState == AVRenderState.PAUSED
                || mPlayState == AVRenderState.COMPLETED || mPlayState == AVRenderState.STOPPED) {
            pos = mMediaPlay.getCurrentPosition();
        } else {
            Log.e(TAG, "getCurrentPosition: error state");
        }
        return pos;
    }

    @Override
    public int getDuration() {
        Log.d(TAG, "getDuration() called, mPlayState = " + mPlayState);
        int duration = 0;
        if (mMediaPlay != null && (mPlayState == AVRenderState.STARTED || mPlayState == AVRenderState.PREPARED
                || mPlayState == AVRenderState.PAUSED || mPlayState == AVRenderState.STOPPED
                || mPlayState == AVRenderState.COMPLETED)) {
            duration = mMediaPlay.getDuration();
        }
        return duration;
    }

    @Override
    public void exit() {
        runOnUiThread(() -> mPlayBackControl.hide());
    }

    public void releaseMediaPlay() {
        if (mMediaPlay != null) {
            mMediaPlay.stop();
            mMediaPlay.reset();
            mMediaPlay.release();
            mMediaPlay = null;
            mPlayState = AVRenderState.END;
        }
    }

    public void initController(LifecycleOwner owner) {
        Log.d(TAG, "initController() called");

        mPlayBackControl = new PlayBackControl2(((Activity)owner).findViewById(android.R.id.content), false);
        mPlayBackControl.setMediaPlayer(new MediaController());
        mPlayBackControl.setEnabledPausePlay(true);
        mPlayBackControl.setEnabledNext(false);
        mPlayBackControl.setEnabledPrev(false);
        mPlayBackControl.setEnabledFfwd(false);
        mPlayBackControl.setEnabledRew(false);
        mPlayBackControl.setEnabledProgress(true);

        mPlayBackControl.setVisiblefwdrwd(false);
        mPlayBackControl.setVisibleNext(false);
        mPlayBackControl.setVisiblePrev(false);

        mPlayBackControl.showOptionKey(false);
        mPlayBackControl.registerNewKeyListenerUnhandledKeys(new UnHandleKeyEvent());
        mPlayBackControl.setSlowPlayTrickMode(true);
        mPlayBackControl.setEnabledTrickAndJumpMode(true);
        mPlayBackControl.enableSecondaryUpdate();

        mPlayBackControl.setOnProgressUpdateListener(this :: updateProgress);
    }

    private void updateProgress(int current, int duration) {
        if (mPlayState == AVRenderState.STARTED && mRemote != null && mRemote.getRemote() != null) {
            mRemote.getRemote().notifyUpdateProgress(current, duration);
        }
    }

    private void initMediaPlayer() {
        Log.d(TAG, "initMediaPlayer() called");
        if (mMediaPlay == null) {
            mMediaPlay = new MediaPlayer();
        }
        initMediaPlayListener();
        mMediaPlay.reset();
        mPlayState = AVRenderState.IDLE;
    }

    public void runOnUiThread(Runnable runnable) {
        if (mHandler != null) {
            mHandler.post(runnable);
        }
    }

    public void runOnUiThreadDelay(Runnable runnable, int millionTime) {
        if (mHandler != null) {
            mHandler.postDelayed(runnable, millionTime);
        }
    }

    public abstract void initMediaPlayListener();

    public abstract void displayPlayerBG(boolean display);

    public abstract void displayLoading(boolean display);

    public abstract void displayControlBar();

    public abstract void setMediaDisplayView();//only for video play

    private class UnHandleKeyEvent implements PlayBackControl2.NewKeyRegisterListener {

        @Override
        public boolean unHandledKeyEvents(KeyEvent keyEvent) {
            Log.d(TAG, "unHandleKeyEvents:" + keyEvent.getKeyCode());
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.KEYCODE_BACK:
                    case KeyEvent.KEYCODE_ESCAPE:
                    case KeyEvent.KEYCODE_TV:
                        exit();
                        break;
                }
            }
            return false;
        }

    }

    private class MediaController extends AbsMediaPlayerControl {

        @Override
        public void seekTo(int pos) {
            AbsAVPresenter.this.seekTo(pos);
        }

        @Override
        public void play() {
            AbsAVPresenter.this.play();
        }

        @Override
        public void stop() {
            AbsAVPresenter.this.stop();
        }

        @Override
        public void pause() {
            AbsAVPresenter.this.pause();
        }

        @Override
        public int getTotalDuration() {
            return getDuration();
        }

        @Override
        public int getCurrentDuration() {
            return getCurrentPosition();
        }

        @Override
        public boolean isPlaying() {
            return mPlayState == AVRenderState.STARTED;
        }

        @Override
        public int getBufferPercentage() {
            return 100;
        }

        @Override
        public void setSpeed(Boolean aBoolean, int i) {
        }
    }

}
