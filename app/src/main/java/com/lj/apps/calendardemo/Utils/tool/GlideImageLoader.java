package com.lj.apps.calendardemo.Utils.tool;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lj.apps.calendardemo.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2016/10/24.
 */
public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
    }
}
