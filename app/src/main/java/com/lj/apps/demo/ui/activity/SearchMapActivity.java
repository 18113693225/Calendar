package com.lj.apps.demo.ui.activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.lj.apps.demo.R;
import com.lj.apps.demo.Utils.service.PositionService;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;

import rx.Subscriber;
import support.ui.utilities.ToastUtils;

/**
 * Created by Administrator on 2016/10/26.
 */
public class SearchMapActivity extends BaseActivity implements BaiduMap.OnMarkerClickListener, BDLocationListener {


    private Context mContext;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private PositionService mPositionService;
    private String address = "温江区天香卢2段33号";

    BitmapDescriptor blue = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_map_blue);
    BitmapDescriptor red = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_map_red);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        mMapView = (MapView) findViewById(R.id.mapView);
        init();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void init() {
        mContext = SearchMapActivity.this;
        if (null == baiduMap) {
            baiduMap = mMapView.getMap();
            baiduMap.setOnMarkerClickListener(this);
        }
        getPermission();
        initAllMarker();
    }

    private void initMarker(LatLng point) {
        MarkerOptions ooA = new MarkerOptions().position(point).icon(red).title(address)
                .zIndex(9);
        baiduMap.addOverlay(ooA);
    }

    private void initAllMarker() {
        ArrayList<OverlayOptions> marks = new ArrayList<>();
        LatLng point1 = new LatLng(30.664913, 103.964425);
        LatLng point2 = new LatLng(30.659321, 103.95652);
        LatLng point3 = new LatLng(30.690382, 104.008838);
        LatLng point4 = new LatLng(30.690382, 104.008838);
        LatLng point5 = new LatLng(30.708268, 103.955802);
        LatLng point6 = new LatLng(30.566329, 103.84743);
        OverlayOptions oo1 = new MarkerOptions().position(point1).icon(blue).title(address)
                .zIndex(9);
        OverlayOptions oo2 = new MarkerOptions().position(point2).icon(blue).title(address)
                .zIndex(9);
        OverlayOptions oo3 = new MarkerOptions().position(point3).icon(blue).title(address)
                .zIndex(9);
        OverlayOptions oo4 = new MarkerOptions().position(point4).icon(blue).title(address)
                .zIndex(9);
        OverlayOptions oo5 = new MarkerOptions().position(point5).icon(blue).title(address)
                .zIndex(9);
        OverlayOptions oo6 = new MarkerOptions().position(point6).icon(blue).title(address)
                .zIndex(9);
        marks.add(oo1);
        marks.add(oo2);
        marks.add(oo3);
        marks.add(oo4);
        marks.add(oo5);
        marks.add(oo6);
        baiduMap.addOverlays(marks);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
        mPositionService = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        int type = bdLocation.getLocType();
        Log.i("TAG", "  " + type);
        if (bdLocation.hasAddr()) {
            mPositionService.stop();
            mPositionService = null;
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()), 14));
            initMarker(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
        }
    }

    private void getPermission() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            if (null == mPositionService) {
                                mPositionService = new PositionService(SearchMapActivity.this, SearchMapActivity.this);
                                mPositionService.start();
                            }
                        } else {
                            ToastUtils.toast("亲，不打开权限的话不能准确定位到你的位置哟");
                        }
                    }
                });
    }

}
