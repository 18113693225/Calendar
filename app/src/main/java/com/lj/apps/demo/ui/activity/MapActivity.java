package com.lj.apps.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.lj.apps.demo.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/10/25.
 */
public class MapActivity extends BaseActivity implements BaiduMap.OnMarkerClickListener {

    @Bind(R.id.toolBar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_center_tv)
    TextView toolbar_center_tv;
    private String address;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private InfoWindow mInfoWindow;

    BitmapDescriptor blue = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_map_blue);
    BitmapDescriptor red = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_map_red);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        init();
        setUpToolbar();
    }

    private void init() {
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        mMapView = (MapView) findViewById(R.id.mapView);
        if (null == baiduMap) {
            baiduMap = mMapView.getMap();
            baiduMap.setOnMarkerClickListener(this);
        }
        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(new LatLng(30.676716, 103.99274), 16));
        initMarker();
    }

    private void initMarker() {
        LatLng point = new LatLng(30.676716, 103.99274);
        MarkerOptions ooA = new MarkerOptions().position(point).icon(red).title(address)
                .zIndex(9);
        ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
        baiduMap.addOverlay(ooA);
    }

    private void setUpToolbar() {
        toolbar.setTitle("");
        toolbar_center_tv.setText(address);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
        blue.recycle();
        red.recycle();
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
        showWindow(marker);
        return true;
    }


    private void showWindow(Marker marker) {
        Button button = new Button(getApplicationContext());
        button.setBackgroundResource(R.drawable.popup);
        button.setText("测试数据");
        button.setBackgroundColor(0x0000f);
        button.setWidth(300);
        LatLng ll = marker.getPosition();
        mInfoWindow = new InfoWindow(button, ll, -47);
        baiduMap.showInfoWindow(mInfoWindow);
    }
}
