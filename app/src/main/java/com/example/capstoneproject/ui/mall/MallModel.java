package com.example.capstoneproject.ui.mall;

import android.os.Parcel;
import android.os.Parcelable;

public class MallModel implements Parcelable{
    private String mText1;
    private String mText2;
    private String mText3;
    private String mText4;

    public MallModel(String text1, String text2, String text3, String text4) {
        mText1 = text1;
        mText2 = text2;
        mText3 = text3;
        mText4 = text4;
    }

    protected MallModel(Parcel in) {
        mText1 = in.readString();
        mText2 = in.readString();
        mText3 = in.readString();
        mText4 = in.readString();
    }

    public static final Creator<MallModel> CREATOR = new Creator<MallModel>() {
        @Override
        public MallModel createFromParcel(Parcel in) {
            return new MallModel(in);
        }

        @Override
        public MallModel[] newArray(int size) {
            return new MallModel[size];
        }
    };


    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    public String getText3() {
        return mText3;
    }

    public String getText4() {
        return mText4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mText1);
        dest.writeString(mText2);
        dest.writeString(mText3);
        dest.writeString(mText4);
    }
}