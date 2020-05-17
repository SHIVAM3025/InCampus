package com.demo.incampus.DiffUtils.Profile.CommunityNumber;

import com.google.gson.annotations.SerializedName;

public class Community_Number_Data {

    @SerializedName("data")
    private Community_members_aggregate data;

    public Community_members_aggregate getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=\n" + data +
                '}';
    }

    public void setData(Community_members_aggregate data) {
        this.data = data;
    }

}
