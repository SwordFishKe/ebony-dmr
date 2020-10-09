package com.tpv.xmic.dlna.dmr.player.image;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.tpv.xmic.dlna.dmr.player.AbsPresenter;
import com.tpv.xmic.dlna.dmr.service.RenderType;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/27 16:02
 * desc   :
 * version: 1.0
 */
public class ImagePresenter extends AbsPresenter {
    private static final String TAG = ImagePresenter.class.getSimpleName();
    private IImageView mView;

    public ImagePresenter() {
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        super.onCreate(owner);
        mView = (IImageView)owner;
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
        Log.d(TAG, "onDestroy: called");
        super.onDestroy(owner);
        mView = null;
    }

    @Override
    public void setRenderType() {
        if (mRemote != null && mRemote.getRemote() != null) {
            mRemote.getRemote().setRenderType(RenderType.IMAGE);
        }
    }

    @Override
    public void prepare(String name, String url) {
        if (mView != null) {
            mView.prepare(name, url);
        }
        play();
    }

    @Override
    public void play() {
        if (mRemote != null && mRemote.getRemote() != null) {
            Log.d(TAG, "play() called");
            mRemote.getRemote().notifyStartPlay();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void seekTo(int time) {
    }

    @Override
    public void setVolume(int vol) {
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public void exit() {
        Log.d(TAG, "exit: ");
        mView.exit();
    }

    @Override
    public void next(String name, String url) {
        super.next(name, url);
        if (mView != null) {
            mView.next(name, url);
        }
        play();
    }


}
