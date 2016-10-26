package com.lj.apps.demo.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lj.apps.demo.R;

import butterknife.Bind;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Administrator on 2016/10/26.
 */
public class TitleActivity extends BaseActivity {

    @Bind(R.id.blur)
    ImageView blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        initView();
    }

    private void initView() {

        Glide.with(this).load(R.mipmap.pic7).bitmapTransform(new BlurTransformation(this, 50)).into(blur);
    }


}
