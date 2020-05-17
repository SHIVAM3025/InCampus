package com.demo.incampus.DiffUtils.Profile.FollowNumber;

import com.google.gson.annotations.SerializedName;

public class Followers_Number_Data {


    @SerializedName("data")
    private Followers_Number_aggregate data;

    public Followers_Number_aggregate getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=\n" + data +
                '}';
    }

    public void setData(Followers_Number_aggregate data) {
        this.data = data;
    }



}
