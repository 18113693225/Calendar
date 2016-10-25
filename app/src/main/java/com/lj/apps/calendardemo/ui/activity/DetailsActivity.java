package com.lj.apps.calendardemo.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lj.apps.calendardemo.R;

import butterknife.Bind;


/**
 * Created by Administrator on 2016/10/24.
 */
public class DetailsActivity extends BaseActivity {
    @Bind(R.id.image_top)
    ImageView top;
    @Bind(R.id.bottom)
    LinearLayout bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        bottomShow();
        top.setImageResource(intent.getIntExtra("img", 0));
    }

    private void bottomShow() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                bottom.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

}
