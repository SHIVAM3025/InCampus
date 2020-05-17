package com.demo.incampus.Query.Profile_Post_Eligibility;

import com.google.gson.annotations.SerializedName;

public class members_to_community {

    @SerializedName("members_to_community")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "members_to_community{" +
                "user=" + user +
                '}';
    }
}
