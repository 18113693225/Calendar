package com.lj.apps.demo.ui.holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lj.apps.demo.R;
import com.lj.apps.demo.model.Home;

import butterknife.Bind;
import butterknife.ButterKnife;
import support.ui.adapters.EasyViewHolder;

/**
 * Created by Administrator on 2016/10/24.
 */
public class HomeViewHolder extends EasyViewHolder<Home> {
    Context mContext;
    @Bind(R.id.item_img)
    ImageView img;
    @Bind(R.id.item_name)
    TextView name;

    public HomeViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.list_item_home);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void bindTo(int position, Home value) {
        img.setImageResource(value.image);
        name.setText(value.title);
    }
}
