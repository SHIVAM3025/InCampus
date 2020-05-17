package com.demo.incampus.Query.Profile_Post_Eligibility;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class User {

    @SerializedName("community_id")
    private String community_id;

    @SerializedName("name")
    private String name;

    public User(String community_id, String name) {
        this.community_id = community_id;
        this.name = name;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "community_id='" + community_id + '\'' +
                ", name=" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(community_id, user.community_id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
