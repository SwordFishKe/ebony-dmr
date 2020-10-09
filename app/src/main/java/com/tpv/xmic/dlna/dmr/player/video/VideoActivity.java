package com.tpv.xmic.dlna.dmr.player.video;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.tpv.xmic.dlna.dmr.R;
import com.tpv.xmic.dlna.dmr.base.BaseActivity;

public class VideoActivity extends BaseActivity implements View.OnClickListener, IVideoView {

    private SurfaceView mSurfaceView;
    private VideoPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mSurfaceView = findViewById(R.id.surface);
        mPresenter = new VideoPresenter();

        getLifecycle().addObserver(mPresenter);
        initSurfaceView();
    }

    private void initSurfaceView() {
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mPresenter != null) {
                    mPresenter.onSurfaceCreated();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mPresenter != null) {
                    mPresenter.onSurfaceDestroyed();
                }
            }
        });

        mSurfaceView.setOnClickListener(this :: onClick);
    }

    @Override
    public void onClick(View v) {
        mPresenter.displayControlBar();
    }


    @Override
    public SurfaceHolder getSurfaceHolder() {
        return mSurfaceView.getHolder();
    }

    @Override
    public void displaySurfaceBg(boolean display) {
        if (display){
            mSurfaceView.setBackgroundResource(R.drawable.player_bg);
        } else {
            mSurfaceView.setBackground(null);
        }
    }

    @Override
    public void displayLoading(boolean display) {
        findViewById(R.id.loading).setVisibility(display ? View.VISIBLE : View.GONE);
    }

    @Override
    public void exit() {
        finish();
    }
}