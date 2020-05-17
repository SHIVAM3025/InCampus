package com.demo.incampus.Model;

public class ManageCommunityMembers {

    private String name,followers;
    private int profile_photo;

    public ManageCommunityMembers(String name, String followers, int profile_photo) {
        this.name = name;
        this.followers = followers;
        this.profile_photo = profile_photo;
    }

    public String getName() {
        return name;
    }

    public String getFollowers() {
        return followers;
    }

    public int getProfile_photo() {
        return profile_photo;
    }
}
