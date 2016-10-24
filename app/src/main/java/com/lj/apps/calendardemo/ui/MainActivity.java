package com.lj.apps.calendardemo.ui;


import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;

import com.lj.apps.calendardemo.R;
import com.lj.apps.calendardemo.Utils.tool.GlideImageLoader;
import com.lj.apps.calendardemo.model.Home;
import com.lj.apps.calendardemo.ui.adapter.HomeAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class MainActivity extends BaseActivity implements OnBannerClickListener, PullLoadMoreRecyclerView.PullLoadMoreListener,
        HomeAdapter.OnItemClickListener {
    @Bind(R.id.banner)
    Banner mBanner;
    @Bind(R.id.oder_rv)
    PullLoadMoreRecyclerView mRecyclerView;
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
        mRecyclerView.setOnPullLoadMoreListener(this);
        mRecyclerView.setPullRefreshEnable(true);
        mRecyclerView.setPushRefreshEnable(true);
        mRecyclerView.setFooterViewText("loading");
        mRecyclerView.setFooterViewTextColor(R.color.colorAccent);
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
        mRecyclerView.setPullLoadMoreCompleted();
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
        mAdapter = new HomeAdapter(MainActivity.this, homes, this);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void onRefresh() {
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                homes.clear();
                initData();
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onLoadMore() {
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                initData();
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onItemClick(View v, Home home, int position) {

    }
}
