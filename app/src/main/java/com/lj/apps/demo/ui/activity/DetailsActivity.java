package com.lj.apps.demo.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lj.apps.demo.Navigator;
import com.lj.apps.demo.R;
import com.lj.apps.demo.Utils.service.MusicService;

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
    @Bind(R.id.music_bt)
    ImageView bt;
    @Bind(R.id.music)
    ImageView music;
    private boolean isPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        Integer url = intent.getIntExtra("img", 0);
        top.setImageResource(url);
        Glide.with(this).load(url).bitmapTransform(new BlurTransformation(this, 50)).into(blur);
        startMusic();
        bottomShow();
    }

    private void bottomShow() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                bottom.setVisibility(View.VISIBLE);
                scaleImp();
            }
        }, 500);
    }

    @OnClick({R.id.map, R.id.music_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map:
                stopMusic();
                top.clearAnimation();
                Navigator.startMapActivity(this, "温江区天香路2段88号");
                break;
            case R.id.music_bt:
                if (isPlay) {
                    startMusic();
                } else {
                    stopMusic();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        stopMusic();
        top.clearAnimation();
        super.onDestroy();
    }

    private void startMusic() {
        isPlay = false;
        bt.setImageResource(R.mipmap.ic_stop);
        rotateImp();
        startService(new Intent(DetailsActivity.this,
                MusicService.class));
    }

    public void stopMusic() {
        isPlay = true;
        music.clearAnimation();
        bt.setImageResource(R.mipmap.ic_start);
        stopService(new Intent(DetailsActivity.this,
                MusicService.class));
    }

    public void scaleImp() {
        Animation animation = AnimationUtils.loadAnimation(this,
                R.anim.scale_demo);
        top.startAnimation(animation);
    }

    public void rotateImp() {
        Animation animation = AnimationUtils.loadAnimation(this,
                R.anim.rotate_demo);
        music.startAnimation(animation);
    }
}
