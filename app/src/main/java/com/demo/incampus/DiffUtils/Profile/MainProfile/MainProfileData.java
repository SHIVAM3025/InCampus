package com.demo.incampus.DiffUtils.Profile.MainProfile;

import com.google.gson.annotations.SerializedName;

public class MainProfileData {


    @SerializedName("data")
    private MainProfileUser data;

    public MainProfileUser getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=\n" + data +
                '}';
    }

    public void setData(MainProfileUser data) {
        this.data = data;
    }

}
