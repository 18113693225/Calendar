package com.lj.apps.demo.Utils.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.lj.apps.demo.R;

public class MusicService extends Service {
    private MediaPlayer mPlayer;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.a);
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        mPlayer.start();
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        mPlayer.stop();
        mPlayer = null;
        super.onDestroy();
    }

}