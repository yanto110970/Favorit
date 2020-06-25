package com.dicoding.favorit.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Following  implements Parcelable {
    private String userid;
    private String name;
    private String avatar;

    public Following() {

    }

    protected Following(Parcel in) {
        userid = in.readString();
        name = in.readString();
        avatar = in.readString();
    }

    public static final Creator<Following> CREATOR = new Creator<Following>() {
        @Override
        public Following createFromParcel(Parcel in) {
            return new Following(in);
        }

        @Override
        public Following[] newArray(int size) {
            return new Following[size];
        }
    };

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userid);
        dest.writeString(name);
        dest.writeString(avatar);
    }
}

