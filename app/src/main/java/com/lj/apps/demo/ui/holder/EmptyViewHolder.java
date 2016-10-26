package com.lj.apps.demo.ui.holder;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.lj.apps.demo.R;
import com.lj.apps.demo.Utils.service.PositionService;
import com.lj.apps.demo.model.Home;
import com.lj.apps.demo.ui.activity.SearchMapActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import support.ui.adapters.EasyViewHolder;
import support.ui.utilities.ToastUtils;

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
                getPermission();
                break;
            default:
                break;
        }
    }

    private void getPermission() {
        RxPermissions.getInstance(mContext)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent intent = new Intent(mContext, SearchMapActivity.class);
                            mContext.startActivity(intent);
                        } else {
                            ToastUtils.toast("亲，不打开权限的话不能准确定位到你的位置哟");
                        }
                    }
                });
    }
}
