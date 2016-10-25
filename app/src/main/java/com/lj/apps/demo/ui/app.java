package com.lj.apps.demo.ui;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;


/**
 * Created by Administrator on 2016/10/25.
 */
public class app extends Application {
    private static Context sContext;

    public static Context getsContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
