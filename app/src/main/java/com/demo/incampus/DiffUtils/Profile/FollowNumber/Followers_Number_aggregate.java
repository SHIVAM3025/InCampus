package com.demo.incampus.DiffUtils.Profile.FollowNumber;

import androidx.annotation.NonNull;

import com.demo.incampus.CommonGraphqlModels.Count.Aggregate;
import com.google.gson.annotations.SerializedName;

public class Followers_Number_aggregate {
    @SerializedName("UserFollow_aggregate")
    private Aggregate aggregate;

    public Aggregate getAggregate() {
        return aggregate;
    }

    @NonNull
    @Override
    public String toString() {
        return "Followers_Number_aggregate{" +
                "UserFollow_aggregate=\n" + aggregate +
                '}';
    }

    public void setAggregate(Aggregate data) {
        this.aggregate = data;
    }



}
