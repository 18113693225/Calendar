package com.lj.apps.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.lj.apps.demo.R;


import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/10/25.
 */
public class MapActivity extends BaseActivity implements BaiduMap.OnMarkerClickListener {

    @Bind(R.id.toolBar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_center_tv)
    TextView toolbar_center_tv;
    @Bind(R.id.map_address_name)
    TextView name;
    @Bind(R.id.map_address_distance)
    TextView distance;
    private String address;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private InfoWindow mInfoWindow;

    DecimalFormat df = new DecimalFormat("######0.00");
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
        initAllMarker();
    }

    private void initMarker() {
        LatLng point = new LatLng(30.676716, 103.99274);
        MarkerOptions ooA = new MarkerOptions().position(point).icon(red).title(address)
                .zIndex(9);
        ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
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
        double dd = DistanceUtil.getDistance(point1, point2);
        distance.setText(df.format(dd) + "Km");
    }

    private void setUpToolbar() {
        toolbar.setTitle("");
        toolbar_center_tv.setText(address);
        name.setText(address);
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
        button.setText(marker.getTitle());
        LatLng ll = marker.getPosition();
        mInfoWindow = new InfoWindow(button, ll, -47);
        baiduMap.showInfoWindow(mInfoWindow);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                baiduMap.hideInfoWindow();
            }
        });
    }
}
