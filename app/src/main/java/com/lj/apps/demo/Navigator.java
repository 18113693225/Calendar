package com.lj.apps.demo;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;


import com.lj.apps.demo.ui.activity.DetailsActivity;
import com.lj.apps.demo.ui.activity.MapActivity;
import com.lj.apps.demo.ui.activity.PanoramicActivity;
import com.lj.apps.demo.ui.activity.SearchMapActivity;
import com.lj.apps.demo.ui.activity.TitleActivity;

/**
 * Created by Administrator on 2016/10/24.
 */
public final class Navigator {

    /**
     * 跳转到详情
     */
    public static void startDetailsActivity(Activity activity) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    /**
     * 跳转到全景
     */
    public static void startPanoramicActivity(Activity activity) {
        Intent intent = new Intent(activity, PanoramicActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    /**
     * 跳转到地图
     */
    public static void startMapActivity(Activity activity, String str) {
        Intent intent = new Intent(activity, MapActivity.class);
        intent.putExtra("address", str);
        ActivityCompat.startActivity(activity, intent, null);
    }

    /**
     * 跳转到地图搜索
     */
    public static void startSearchMapActivity(Activity activity) {
        Intent intent = new Intent(activity, SearchMapActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    /**
     * 跳转到title
     */
    public static void startTitleActivity(Activity activity) {
        Intent intent = new Intent(activity, TitleActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
