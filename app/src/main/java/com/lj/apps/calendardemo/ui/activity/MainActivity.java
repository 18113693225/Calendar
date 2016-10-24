package com.lj.apps.calendardemo.ui.activity;


import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.View;

import com.lj.apps.calendardemo.Navigator;
import com.lj.apps.calendardemo.R;
import com.lj.apps.calendardemo.Utils.tool.GlideImageLoader;
import com.lj.apps.calendardemo.model.Home;
import com.lj.apps.calendardemo.ui.adapter.HomeAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class MainActivity extends BaseActivity implements OnBannerClickListener,
        HomeAdapter.OnItemClickListener {
    @Bind(R.id.banner)
    Banner mBanner;
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private ArrayList<Integer> images = new ArrayList<>();
    private ArrayList<Home> homes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBanner();
        initData();
        init();
        showBanner(images);
    }

    private void init() {
        showRecyclerView();
    }

    private void initBanner() {
        images.add(R.mipmap.pic1);
        images.add(R.mipmap.pic2);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Home home = new Home();
            home.title = "标题" + i;
            home.image = images.get(0);
            homes.add(home);
        }
    }

    private void showBanner(List<Integer> Images) {
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(Images);
        mBanner.setBannerAnimation(Transformer.DepthPage);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setDelayTime(6000);
        mBanner.setOnBannerClickListener(this);
        mBanner.start();
    }

    private void showRecyclerView() {
        recyclerViewListener();
        mAdapter = new HomeAdapter(MainActivity.this, homes, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .size(1)
                .color(getResources().getColor(R.color.bg_line))
                .build());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void recyclerViewListener() {
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("TAG", "newState" + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("TAG", "x" + dx + "  " + "y" + dy);
            }
        });
    }

    @Override
    public void OnBannerClick(int position) {
        Log.i("TAG", position + "");
    }


    @Override
    public void onItemClick(View v, Home home, int position) {
        Navigator.startDetailsActivity(this);
    }
}
