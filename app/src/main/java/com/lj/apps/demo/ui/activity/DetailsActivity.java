package com.lj.apps.demo.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lj.apps.demo.Navigator;
import com.lj.apps.demo.R;

import butterknife.Bind;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * Created by Administrator on 2016/10/24.
 */
public class DetailsActivity extends BaseActivity {
    @Bind(R.id.image_top)
    ImageView top;
    @Bind(R.id.bottom)
    FrameLayout bottom;
    @Bind(R.id.blur)
    ImageView blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
    }

    private void initView() {
        bottomShow();
        Intent intent = getIntent();
        Integer url = intent.getIntExtra("img", 0);
        top.setImageResource(url);
        Glide.with(this).load(url).bitmapTransform(new BlurTransformation(this, 50)).into(blur);
    }

    private void bottomShow() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                bottom.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    @OnClick({R.id.map})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map:
                Navigator.startMapActivity(this, "温江区天香路2段88号");
                break;
            default:
                break;
        }
    }
}
