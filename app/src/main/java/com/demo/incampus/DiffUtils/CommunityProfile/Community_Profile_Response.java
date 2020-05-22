package com.demo.incampus.DiffUtils.CommunityProfile;


import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Community_Profile_Response {

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("Posts")
        private List<Community_profile> community_profiles;

        public Data(List<Community_profile> community_profiles) {
            this.community_profiles = community_profiles;
        }

        public List<Community_profile> getCommunity_profiles() {
            return community_profiles;
        }
    }

    public static class Community_profile{
        @SerializedName("content")
        String content;

        @SerializedName("created_at")
        private String  created_at;

        @SerializedName("name")
        String name;
        @SerializedName("no_of_comments")
        String no_of_comments;

        private post_community post_community;

        private post_to_user post_to_user;


        public Community_profile(String content, String created_at, String name, String no_of_comments,
                                 Community_Profile_Response.post_community post_community, Community_Profile_Response.post_to_user post_to_user) {
            this.content = content;
            this.created_at = created_at;
            this.name = name;
            this.no_of_comments = no_of_comments;
            this.post_community = post_community;
            this.post_to_user = post_to_user;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getContent() {
            return content;
        }

        public String getName() {
            return name;
        }

        public String getNo_of_comments() {
            return no_of_comments;
        }

        public Community_Profile_Response.post_community getPost_community() {
            return post_community;
        }

        public Community_Profile_Response.post_to_user getPost_to_user() {
            return post_to_user;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Community_profile that = (Community_profile) o;
            return Objects.equals(content, that.content) &&
                    Objects.equals(created_at, that.created_at) &&
                    Objects.equals(name, that.name) &&
                    Objects.equals(no_of_comments, that.no_of_comments) &&
                    Objects.equals(post_community, that.post_community) &&
                    Objects.equals(post_to_user, that.post_to_user);
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    public static class post_to_user
    {   @SerializedName("name")
        String name;

        public post_to_user() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public static class post_community{

        String pic_url;

        public post_community() {
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }


}
