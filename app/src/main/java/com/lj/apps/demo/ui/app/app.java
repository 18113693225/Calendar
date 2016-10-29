package com.lj.apps.demo.ui.app;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;


/**
 * Created by Administrator on 2016/10/25.
 */
public class app extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
