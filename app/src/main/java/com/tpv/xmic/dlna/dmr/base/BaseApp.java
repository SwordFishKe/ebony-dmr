package com.tpv.xmic.dlna.dmr.base;

import android.app.Application;
import android.util.Log;

import com.squareup.picasso.Picasso;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/9/25 15:00
 * desc   :
 * version: 1.0
 */
public class BaseApp extends Application {

    private static final String TAG = BaseApp.class.getSimpleName();

    private static BaseApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "DmrApp start...");
        sInstance = this;

        Picasso.Builder builder = new Picasso.Builder(this);
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);
    }

    public static BaseApp getInstance() {
        if (sInstance == null) {
            throw new IllegalArgumentException("DmrApp instance is null");
        } else {
            return sInstance;
        }
    }
}
