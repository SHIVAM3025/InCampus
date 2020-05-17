package com.demo.incampus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ProfilePostModel {

    @SerializedName("pic_url")
    String pic_url;

    @SerializedName("created_by")
    String created_by;

    @SerializedName("content")
    String content;

    @SerializedName("upvotes")
    String upvotes;

    @SerializedName("created_at")
    String created_at;

    public ProfilePostModel() {
    }

    public ProfilePostModel(String pic_url, String created_by, String content, String upvotes , String created_at) {
        this.pic_url = pic_url;
        this.created_by = created_by;
        this.content = content;
        this.upvotes = upvotes;
        this.created_at =created_at;
    }

    public String getPic_url() {
        return pic_url;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getContent() {
        return content;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUpvotes(String upvotes) {
        this.upvotes = upvotes;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "pic_url=" + pic_url +
                ", created_by='" + created_by + '\'' +
                ", content='" + content + '\'' +
                ", upvotes='" + upvotes + '\'' +
                ", created_at='" + created_at + '\'' +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfilePostModel)) return false;
        ProfilePostModel userModel = (ProfilePostModel) o;
        return Objects.equals(getContent(), userModel.getContent()) &&
                Objects.equals(getCreated_by(), userModel.getCreated_by()) &&
                Objects.equals(getPic_url(), userModel.getPic_url()) &&
                Objects.equals(getCreated_at(), userModel.getCreated_at()) &&
                Objects.equals(getPic_url(), userModel.getPic_url());
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
