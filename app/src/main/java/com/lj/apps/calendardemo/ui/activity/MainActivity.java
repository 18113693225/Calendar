package com.lj.apps.calendardemo.ui.activity;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

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


public class MainActivity extends BaseActivity implements
        HomeAdapter.OnItemClickListener {
    @Bind(R.id.banner)
    ImageView img;
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private ArrayList<Integer> images = new ArrayList<>();
    private ArrayList<Home> homes = new ArrayList<>();

    private Boolean mScaling = false;
    private DisplayMetrics metric;
    private LinearLayoutManager mLinearLayoutManager;
    private float mFirstPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBanner();
        initData();
        init();
        //  showBanner(images);
    }

    private void init() {
        metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) img.getLayoutParams();
        lp.width = metric.widthPixels;
        lp.height = metric.widthPixels * 9 / 16;
        img.setLayoutParams(lp);
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
//        mBanner.setImageLoader(new GlideImageLoader());
//        mBanner.setImages(Images);
//        mBanner.setBannerAnimation(Transformer.DepthPage);
//        mBanner.setIndicatorGravity(BannerConfig.CENTER);
//        mBanner.setDelayTime(6000);
//        mBanner.setOnBannerClickListener(this);
//        mBanner.start();
    }

    private void showRecyclerView() {
        if (null == mLinearLayoutManager) {
            mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        mAdapter = new HomeAdapter(MainActivity.this, homes, this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .size(1)
                .color(getResources().getColor(R.color.bg_line))
                .build());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onItemClick(View v, Home home, int position) {
        Navigator.startDetailsActivity(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) img.getLayoutParams();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                mScaling = false;
                replyImage();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScaling) {
                    //当图片也就是第一个item完全可见的时候，记录触摸屏幕的位置
                    if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                        mFirstPosition = event.getY();
                    } else {
                        break;
                    }
                }
                int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                if (distance < 0) {
                    break;
                }
                // 处理放大
                mScaling = true;
                lp.width = metric.widthPixels + distance;
                lp.height = (metric.widthPixels + distance) * 9 / 16;
                img.setLayoutParams(lp);
                return true;
        }
        return false;
    }

    private void replyImage() {
        final FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) img.getLayoutParams();
        final float w = img.getLayoutParams().width;// 图片当前宽度
        final float h = img.getLayoutParams().height;// 图片当前高度
        final float newW = metric.widthPixels;// 图片原宽度
        final float newH = metric.widthPixels * 9 / 16;// 图片原高度

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration(200);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                lp.width = (int) (w - (w - newW) * cVal);
                lp.height = (int) (h - (h - newH) * cVal);
                img.setLayoutParams(lp);
            }
        });
        anim.start();

    }
}
