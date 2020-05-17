package com.demo.incampus.DiffUtils.Profile.CommunityNumber;

import androidx.annotation.NonNull;

import com.demo.incampus.CommonGraphqlModels.Count.Aggregate;
import com.google.gson.annotations.SerializedName;

public class Community_members_aggregate {


    @SerializedName("Community_members_aggregate")
    private Aggregate aggregate;

    public Aggregate getAggregate() {
        return aggregate;
    }

    @NonNull
    @Override
    public String toString() {
        return "Community_members_aggregate{" +
                "Community_members_aggregate=\n" + aggregate +
                '}';
    }

    public void setAggregate(Aggregate data) {
        this.aggregate = data;
    }

}
