package com.dicoding.favorit.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {
    private String Login;
    private String avatar;
    private String userurl;
    private String follower;
    private String following;

    public UserProfile() {
    }


    public String getLogin() {
        return Login;
    }
    public String getFollower() {
        return follower;
    }
    public String getFollowing() {
        return following;
    }
    public String getAvatar() {
        return avatar;
    }
    public String getUserurl() { return userurl;}

    public void setLogin(String Login) {
        this.Login = Login;
    }
    public void setFollower(String follower) {
        this.follower = follower;
    }
    public void setFollowing(String following) {
        this.following = following;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public void setUserurl(String userurl) {
        this.userurl = userurl;
    }

    public int describeContents() {
        return 0;
    }
    protected UserProfile(Parcel in) {
        Login = in.readString();
        avatar = in.readString();
        userurl = in.readString();
        follower = in.readString();
        following = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Login);
        dest.writeString(avatar);
        dest.writeString(userurl);
        dest.writeString(follower);
        dest.writeString(following);
    }
    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }
        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

}
