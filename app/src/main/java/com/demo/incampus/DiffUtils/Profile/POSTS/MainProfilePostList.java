package com.demo.incampus.DiffUtils.Profile.POSTS;

import com.demo.incampus.Model.ProfilePostModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainProfilePostList {

    @SerializedName("Posts")
    private List<ProfilePostModel> User;

    public List<ProfilePostModel> getUser() {
        return User;
    }

    public void setUser(List<ProfilePostModel> user) {
        User = user;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "User=\n" + User +
                '}';
    }

}
