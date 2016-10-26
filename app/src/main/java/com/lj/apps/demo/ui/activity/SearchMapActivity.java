package com.lj.apps.demo.ui.activity;


import android.annotation.TargetApi;
import android.content.Context;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
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
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.lj.apps.demo.Navigator;
import com.lj.apps.demo.R;
import com.lj.apps.demo.Utils.service.PositionService;
import com.lj.apps.demo.ui.widget.SearchRecyclerView;
import com.smartydroid.android.starter.kit.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/26.
 */
public class SearchMapActivity extends BaseActivity implements BaiduMap.OnMarkerClickListener, BDLocationListener,
        OnGetSuggestionResultListener, TextWatcher, SearchRecyclerView.OnItemClickListener {

    private Context mContext;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private SuggestionSearch mSuggestionSearch;
    private PositionService mPositionService;
    private String address = "温江区天香卢2段33号";
    private String city = "成都";

    private InfoWindow mInfoWindow;
    @Bind(R.id.edit_address)
    EditText edit;
    @Bind(R.id.search_rv)
    SearchRecyclerView rv;


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

    private void init() {
        mContext = SearchMapActivity.this;
        if (null == baiduMap) {
            baiduMap = mMapView.getMap();
            baiduMap.setOnMarkerClickListener(this);
        }
        if (null == mPositionService) {
            mPositionService = new PositionService(SearchMapActivity.this, SearchMapActivity.this);
            mPositionService.start();
        }
        edit.addTextChangedListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
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
        mSuggestionSearch.destroy();
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
                Navigator.startTitleActivity(SearchMapActivity.this);
            }
        });
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        int type = bdLocation.getLocType();
        Log.i("TAG", "  " + type);
        if (bdLocation.hasAddr()) {
            mPositionService.stop();
            mPositionService = null;
            city = bdLocation.getCity();
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()), 14));
            initMarker(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        rv.setVisibility(View.VISIBLE);
        String text = s.toString();
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword(text)
                .city(city));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        List<SuggestionResult.SuggestionInfo> lists = suggestionResult.getAllSuggestions();
        if (null != lists) {
            rv.setData(lists, this);
        }
    }

    @OnClick({R.id.cancel_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_bt:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View v, SuggestionResult.SuggestionInfo info, int position) {
        rv.setVisibility(View.GONE);
        hideSoftInputMethod();
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(info.pt, 14));
        initMarker(info.pt);
    }
}
