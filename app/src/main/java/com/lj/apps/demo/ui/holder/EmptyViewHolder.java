package com.lj.apps.demo.ui.holder;

import android.content.Context;
import android.view.ViewGroup;

import com.lj.apps.demo.R;
import com.lj.apps.demo.model.Home;

import butterknife.ButterKnife;
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
}