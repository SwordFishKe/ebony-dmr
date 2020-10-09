package com.tpv.xmic.dlna.dmr.player.audio;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tpv.xmic.dlna.dmr.R;
import com.tpv.xmic.dlna.dmr.base.BaseActivity;

public class AudioActivity extends BaseActivity implements IAudioView {
    private static final String TAG = AudioActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        Log.d(TAG, "onCreate() called ");
        getLifecycle().addObserver(new AudioPresenter());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
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