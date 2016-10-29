package com.lj.apps.demo.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.lj.apps.demo.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/26.
 */
public class SearchRecyclerView extends RecyclerView {

    private SimpleAdapter mAdapter;

    public SearchRecyclerView(Context context) {
        this(context, null);
    }

    public SearchRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        setNestedScrollingEnabled(false);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .size(1)
                .color(getResources().getColor(R.color.bg_line))
                .build());
        mAdapter = new SimpleAdapter();
        setAdapter(mAdapter);
    }

    public void setData(List<SuggestionResult.SuggestionInfo> data, OnItemClickListener listener) {
        mAdapter.setData(data, listener);
    }

    static class SimpleViewHolder extends ViewHolder {
        public Context mContext;
        public SuggestionResult.SuggestionInfo data;
        @Bind(R.id.search_text_key)
        TextView title;
        @Bind(R.id.search_text_city)
        TextView city;

        private SimpleViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.list_item_text, parent, false));
            mContext = context;
            ButterKnife.bind(this, itemView);
        }

        static SimpleViewHolder create(Context context, ViewGroup parent) {
            return new SimpleViewHolder(context, parent);
        }

        public void bind(SuggestionResult.SuggestionInfo data) {
            this.data = data;
            title.setText(data.key);
            city.setText(data.district);
        }

    }

    /**
     * RecyclerView适配器
     */
    private class SimpleAdapter extends Adapter<ViewHolder> {
        private ArrayList<SuggestionResult.SuggestionInfo> mData;
        private OnItemClickListener mOnItemClickListener;

        public SimpleAdapter() {
            mData = new ArrayList<>();
        }

        public void setData(List<SuggestionResult.SuggestionInfo> data, OnItemClickListener listener) {
            mData.clear();
            mData.addAll(data);
            this.mOnItemClickListener = listener;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return SimpleViewHolder.create(getContext(), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final SuggestionResult.SuggestionInfo data = mData.get(position);
            final SimpleViewHolder viewHolder = (SimpleViewHolder) holder;
            if (("").equals(data) || null == data) {
                return;
            } else {
                viewHolder.bind(data);
                holder.itemView.setTag(data);
                setOnListener(viewHolder);
            }
        }

        protected void setOnListener(final RecyclerView.ViewHolder holder) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int layoutPosition = holder.getPosition();
                        SuggestionResult.SuggestionInfo info = (SuggestionResult.SuggestionInfo) holder.itemView.getTag();
                        mOnItemClickListener.onItemClick(holder.itemView, info, layoutPosition);
                    }
                });
            }
        }


        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, SuggestionResult.SuggestionInfo info, int position);
    }
}
