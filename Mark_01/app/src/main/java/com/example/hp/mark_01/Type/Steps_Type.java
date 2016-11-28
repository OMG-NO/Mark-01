package com.example.hp.mark_01.Type;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp on 2016/8/18.
 */
public class Steps_Type implements Parcelable {
    private String picUrl;
    private String step;

    public Steps_Type(String picUrl, String step) {
        this.picUrl = picUrl;
        this.step = step;
    }

    protected Steps_Type(Parcel in) {
        picUrl = in.readString();
        step = in.readString();
    }

    public static final Creator<Steps_Type> CREATOR = new Creator<Steps_Type>() {
        @Override
        public Steps_Type createFromParcel(Parcel in) {
            return new Steps_Type(in);
        }

        @Override
        public Steps_Type[] newArray(int size) {
            return new Steps_Type[size];
        }
    };

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(picUrl);
        parcel.writeString(step);
    }
}
