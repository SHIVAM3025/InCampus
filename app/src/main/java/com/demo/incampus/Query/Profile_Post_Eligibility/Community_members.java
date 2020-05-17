package com.demo.incampus.Query.Profile_Post_Eligibility;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Community_members {

    @SerializedName("Community_members")
    private List<members_to_community> list;

    @Override
    public String toString() {
        return "Community_members{" +
                "list=" + list +
                '}';
    }

    public List<members_to_community> getList() {
        return list;
    }

    public void setList(List<members_to_community> list) {
        this.list = list;
    }
}
