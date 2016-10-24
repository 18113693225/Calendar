package com.lj.apps.calendardemo.ui.activity;


import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lj.apps.calendardemo.Navigator;
import com.lj.apps.calendardemo.R;
import com.lj.apps.calendardemo.model.Home;
import com.lj.apps.calendardemo.ui.adapter.HomeAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;


public class MainActivity extends BaseActivity implements
        HomeAdapter.OnItemClickListener {
    @Bind(R.id.banner)
    ImageView img;
    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    Integer[] images = {R.mipmap.pic1, R.mipmap.pic2};
    private ArrayList<Home> homes = new ArrayList<>();


    private LinearLayoutManager mLinearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        showRecyclerView();
    }


    private void initData() {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) img.getLayoutParams();
        Log.i("TAG", "高度 " + lp.width + "  宽度" + lp.height);
        for (int i = 0; i < 10; i++) {
            Home home = new Home();
            home.title = "标题" + i;
            home.image = images[0];
            homes.add(home);
        }
    }


    private void showRecyclerView() {
        mAdapter = new HomeAdapter(MainActivity.this, homes, this);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .size(1)
                .color(getResources().getColor(R.color.bg_line))
                .build());
        mRecyclerView.setAdapter(mAdapter);
        scrollListener();
    }

    private void scrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private Double totalDy = 0.0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy -= dy;
                if (totalDy < 0 && totalDy > -600) {
                    int a = (int) Math.abs((totalDy / 600) * 255);
                    img.setAlpha(255 - a);
                }
            }
        });
    }

    @Override
    public void onItemClick(View v, Home home, int position) {
        Navigator.startDetailsActivity(this);
    }

}
