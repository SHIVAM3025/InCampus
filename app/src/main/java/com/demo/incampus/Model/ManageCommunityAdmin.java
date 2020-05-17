package com.demo.incampus.Model;

public class ManageCommunityAdmin {

    private String society_name,followers;
    private int society_photo;

    public ManageCommunityAdmin(String society_name, String followers, int society_photo) {
        this.society_name = society_name;
        this.followers = followers;
        this.society_photo = society_photo;
    }

    public String getSociety_name() {
        return society_name;
    }

    public String getFollowers() {
        return followers;
    }

    public int getSociety_photo() {
        return society_photo;
    }
}
