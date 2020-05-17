package com.demo.incampus.DiffUtils.Profile.MainProfile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainProfileUser {

    @SerializedName("User")
    private List<ProfileModel> User;

    public List<ProfileModel> getUser() {
        return User;
    }

    public void setUser(List<ProfileModel> user) {
        User = user;
    }

    @Override
    public String toString() {
        return "MainProfileUser{" +
                "User=\n" + User +
                '}';
    }


}
