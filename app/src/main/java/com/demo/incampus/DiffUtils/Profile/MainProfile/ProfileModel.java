package com.demo.incampus.DiffUtils.Profile.MainProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ProfileModel {

    @SerializedName("name")
    String name;

    @SerializedName("university")
    String university;

    @SerializedName("pic_url")
    String pic_url;

    public ProfileModel() {
    }

    public ProfileModel(String name, String university, String pic_url) {
        this.name = name;
        this.university = university;
        this.pic_url = pic_url;
    }

    public String getName() {
        return name;
    }

    public String getUniversity() {
        return university;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }


    @NonNull
    @Override
    public String toString() {
        return "UserModel{" +
                ", name='" + name + '\'' +
                ", university='" + university + '\'' +
                ", pic_url='" + pic_url + '\'' +
                "}\n";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ProfileModel)) return false;
        ProfileModel userModel = (ProfileModel) obj;
        return Objects.equals(getName(), userModel.getName()) &&
                Objects.equals(getUniversity(), userModel.getUniversity()) &&
                Objects.equals(getPic_url(), userModel.getPic_url());

    }

    @Override
    public int hashCode() {
        return 0;
    }
}
