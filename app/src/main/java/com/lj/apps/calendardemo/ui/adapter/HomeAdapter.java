package com.lj.apps.calendardemo.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.lj.apps.calendardemo.model.Home;
import com.lj.apps.calendardemo.ui.holder.EmptyViewHolder;
import com.lj.apps.calendardemo.ui.holder.HomeViewHolder;

import java.util.List;

import support.ui.adapters.EasyViewHolder;

/**
 * Created by Administrator on 2016/10/24.
 */
public class HomeAdapter extends RecyclerView.Adapter<EasyViewHolder> {


    protected List<Home> mListData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View v, Home home, int position);
    }


    public HomeAdapter(Context context, List<Home> data, OnItemClickListener listener) {
        this.mListData = data;
        this.mContext = context;
        this.mOnItemClickListener = listener;

    }

    //创建ViewHolder
    @Override
    public EasyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EasyViewHolder holder = null;
        if (viewType == 0) {
            holder = new EmptyViewHolder(mContext, parent);
        } else {
            holder = new HomeViewHolder(mContext, parent);
        }
        return holder;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(EasyViewHolder holder, int position) {
        Home home = mListData.get(position);
        holder.bindTo(position, home);
        holder.itemView.setTag(home);
        holder.itemView.setTransitionName("shareName");
        setOnListener(holder);
    }

    protected void setOnListener(final RecyclerView.ViewHolder holder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getPosition();
                    Home home = (Home) holder.itemView.getTag();
                    mOnItemClickListener.onItemClick(holder.itemView, home, layoutPosition);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position == 0) {
            type = 0;
        } else {
            type = 1;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

}
