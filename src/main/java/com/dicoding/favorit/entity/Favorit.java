package com.dicoding.favorit.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorit implements Parcelable {
    public static final Creator<Favorit> CREATOR = new Creator<Favorit>() {
        @Override
        public Favorit createFromParcel(Parcel in) {
            return new Favorit(in);
        }

        @Override
        public Favorit[] newArray(int size) {
            return new Favorit[size];
        }
    };
    private int id;
    private String userid;
    private String username;
    private String date;
    private String avatar;
    private String userurl;

    public Favorit(int id, String userid, String username, String date, String avatar, String userurl) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.date = date;
        this.avatar = avatar;
        this.userurl = userurl;
    }

    public Favorit() {

    }

    public Favorit(Parcel in) {
        id = in.readInt();
        userid = in.readString();
        username = in.readString();
        date = in.readString();
        avatar = in.readString();
        userurl = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserurl() {
        return userurl;
    }

    public void setUserurl(String userurl) {
        this.userurl = userurl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userid);
        dest.writeString(username);
        dest.writeString(date);
        dest.writeString(avatar);
        dest.writeString(userurl);
    }
}

