package com.demo.incampus.Query.Profile_Post_Eligibility;

import com.google.gson.annotations.SerializedName;

public class CreatePostData {

    @SerializedName("data")
    private Community_members community_members;

    public Community_members getCommunity_members() {
        return community_members;
    }

    public void setCommunity_members(Community_members community_members) {
        this.community_members = community_members;
    }

    @Override
    public String toString() {
        return "CreatePostData{" +
                "community_members=" + community_members +
                '}';
    }
}
