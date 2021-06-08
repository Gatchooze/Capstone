package com.example.capstoneproject.ui.mall;

import android.os.Parcel;
import android.os.Parcelable;

public class MallModel implements Parcelable{
    private String mName;
    private String mRating;
    private String mCarCapacity;
    private String mMotorCapacity;
    private String mDistance;
    private String mStatus;

    public MallModel(String name, String rating, String carCapacity, String motorCapacity, String distance, String status) {
        mName = name;
        mRating = rating;
        mCarCapacity = carCapacity;
        mMotorCapacity = motorCapacity;
        mDistance = distance;
        mStatus = status;
    }

    protected MallModel(Parcel in) {
        mName = in.readString();
        mRating = in.readString();
        mCarCapacity = in.readString();
        mMotorCapacity = in.readString();
        mDistance = in.readString();
        mStatus = in.readString();
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


    public String getmName() {
        return mName;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmCarCapacity() {
        return mCarCapacity;
    }

    public String getmMotorCapacity() {
        return mMotorCapacity;
    }

    public String getmDistance() {
        return mDistance;
    }

    public String getmStatus() {
        return mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mRating);
        dest.writeString(mCarCapacity);
        dest.writeString(mMotorCapacity);
        dest.writeString(mDistance);
        dest.writeString(mStatus);
    }
}