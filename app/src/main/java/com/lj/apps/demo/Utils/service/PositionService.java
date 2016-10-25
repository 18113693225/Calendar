package com.lj.apps.demo.Utils.service;

import android.content.Context;


import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


/**
 * Created by zmy on 2015/12/22 0022.
 * 定位服务
 */
public class PositionService {

    private LocationClient locationClient;

    public PositionService(Context context, BDLocationListener listener) {
        locationClient = new LocationClient(context.getApplicationContext());
        locationClient.registerLocationListener(listener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setTimeOut(5000);
        option.setScanSpan(5000);
        locationClient.setLocOption(option);
    }

    public void start() {
        if (!locationClient.isStarted()) {
            locationClient.start();
            locationClient.requestLocation();
        }
    }

    public void stop() {
        if (this.locationClient != null) {
            locationClient.stop();
        }
    }


}
