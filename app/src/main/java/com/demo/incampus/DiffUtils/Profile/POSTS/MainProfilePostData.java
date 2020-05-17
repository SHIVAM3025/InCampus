package com.demo.incampus.DiffUtils.Profile.POSTS;

import com.google.gson.annotations.SerializedName;

public class MainProfilePostData {

    @SerializedName("data")
    private MainProfilePostList data;

    public MainProfilePostList getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=\n" + data +
                '}';
    }

    public void setData(MainProfilePostList data) {
        this.data = data;
    }

}
