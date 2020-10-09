package com.tpv.xmic.dlna.dmr.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.tpv.xmic.dlna.dmr.service.DmrService;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive: " + action);
        if (TextUtils.equals(action, Intent.ACTION_BOOT_COMPLETED)) {
            Intent dmrIntent = new Intent(context, DmrService.class);
            context.startService(dmrIntent);
        }
    }
}
