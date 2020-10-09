package com.tpv.xmic.dlna.dmr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.tpv.xmic.dlna.dmr.service.DmrService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, DmrService.class));
        new Handler().postDelayed(() -> finish(), 2000);
    }
}