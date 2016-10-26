package com.lj.apps.demo.ui.holder;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.lj.apps.demo.R;
import com.lj.apps.demo.model.Home;
import com.lj.apps.demo.ui.activity.SearchMapActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import support.ui.adapters.EasyViewHolder;

/**
 * Created by Administrator on 2016/10/24.
 */
public class EmptyViewHolder extends EasyViewHolder<Home> {

    Context mContext;

    public EmptyViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_empty);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void bindTo(int position, Home value) {

    }

    @TargetApi(Build.VERSION_CODES.M)
    @OnClick({R.id.home_search_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_search_bt:
                Intent intent = new Intent(mContext, SearchMapActivity.class);
                mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }

}
