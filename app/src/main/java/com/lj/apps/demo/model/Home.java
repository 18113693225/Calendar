package com.lj.apps.demo.model;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smartydroid.android.starter.kit.model.entity.Entity;

/**
 * Created by Administrator on 2016/10/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Home extends Entity {

    public String title;
    public Integer image;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeValue(this.image);
    }

    public Home() {
    }

    protected Home(Parcel in) {
        this.title = in.readString();
        this.image = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {
        @Override
        public Home createFromParcel(Parcel source) {
            return new Home(source);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };
}
