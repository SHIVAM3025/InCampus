package com.demo.incampus.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Societies {
    @SerializedName("name")
    private String society_name;
    @SerializedName("member_count")
    private String followers;
    @SerializedName("pic_url")
    private String society_photo;
    @SerializedName("community_id")
    private String  community_id;

    public Societies() {
    }

    public Societies(String society_name, String followers, String society_photo, String community_id) {
        this.society_name = society_name;
        this.followers = followers;
        this.society_photo = society_photo;
        this.community_id = community_id;
    }



    public String getSociety_name() {
        return society_name;
    }

    public String getFollowers() {
        return followers;
    }

    public String getSociety_photo() {
        return society_photo;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setSociety_name(String society_name) {
        this.society_name = society_name;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public void setSociety_photo(String society_photo) {
        this.society_photo = society_photo;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Societies)) return false;
        Societies userModel = (Societies) obj;
        return Objects.equals(getCommunity_id(), userModel.getCommunity_id()) &&
                Objects.equals(getFollowers(), userModel.getFollowers()) &&
                Objects.equals(getSociety_name(), userModel.getSociety_name()) &&
                Objects.equals(getSociety_photo(), userModel.getSociety_photo());
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
