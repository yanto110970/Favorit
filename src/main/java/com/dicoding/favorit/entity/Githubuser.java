package com.dicoding.favorit.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Githubuser implements Parcelable {
    private String userid;
    private String name;
    private String avatar;
    private String userurl;
    private String follower;
    private String following;


    public void setUserid(String userid) {
        this.userid = userid;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public void setUserurl(String userurl) {
        this.userurl = userurl;
    }
    public void setFollower(String follower) {
        this.follower = follower;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
    public String getUserid() {
        return userid;
    }
    public String getName() {
        return name;
    }
    public String getUserurl() {
        return userurl;
    }
    public String getAvatar() {
        return avatar;
    }
    public String getFollower() {
        return follower;
    }
    public String getfollowing() {
        return following;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userid);
        dest.writeString(this.name);
        dest.writeString(this.userurl);
        dest.writeString(this.avatar);
        dest.writeString(this.follower);
        dest.writeString(this.following);
    }

    public Githubuser() {
    }

    private Githubuser(Parcel in) {
        this.userid = in.readString();
        this.name = in.readString();
        this.userurl = in.readString();
        this.avatar = in.readString();
        this.follower = in.readString();
        this.following = in.readString();
    }

    public static final Parcelable.Creator<Githubuser> CREATOR = new Parcelable.Creator<Githubuser>() {
        @Override
        public Githubuser createFromParcel(Parcel source) {
            return new Githubuser(source);
        }
        @Override
        public Githubuser[] newArray(int size) {
            return new Githubuser[size];
        }
    };
}
