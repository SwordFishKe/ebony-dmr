package com.tpv.xmic.dlna.dmr.player.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tpv.xmic.dlna.dmr.R;
import com.tpv.xmic.dlna.dmr.base.BaseActivity;

public class ImageActivity extends BaseActivity implements IImageView, Callback {
    private static final String TAG = ImageActivity.class.getSimpleName();
    private AppCompatImageView mImage;
    private AppCompatTextView mName;
    private ProgressBar mLoading;
    private ImagePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called ");
        setContentView(R.layout.activity_image);
        mImage = findViewById(R.id.image);
        mName = findViewById(R.id.fileName);
        mLoading = findViewById(R.id.loading);
        mPresenter = new ImagePresenter();
        getLifecycle().addObserver(mPresenter);

    }

    @Override
    public void prepare(String name, String url) {
        mName.setText(name);
        if (mLoading != null) {
            mLoading.setVisibility(View.VISIBLE);
        }
        Picasso.get().load(url).config(Bitmap.Config.ARGB_8888).tag(url).into(mImage, this);
    }

    @Override
    public void next(String name, String url) {
        runOnUiThread(() -> {
            if (mLoading != null) {
                mLoading.setVisibility(View.VISIBLE);
                mName.setText(name);
                Picasso.get().load(url).config(Bitmap.Config.ARGB_8888).into(mImage, this);
            }
        });

    }

    @Override
    public void exit() {
        Log.d(TAG, "exit() called");
        finish();
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "onSuccess() called");
        if (mLoading != null) {
            mLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(Exception e) {
        Log.d(TAG, "onError() called with: e = [" + e + "]");
        if (mLoading != null) {
            mLoading.setVisibility(View.GONE);
        }
    }
}