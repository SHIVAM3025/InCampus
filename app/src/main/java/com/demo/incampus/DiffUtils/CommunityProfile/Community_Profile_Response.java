package com.demo.incampus.DiffUtils.CommunityProfile;


import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Community_Profile_Response {

    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("Posts")
        private List<Posts> posts;

        public Data(List<Posts> posts) {
            this.posts = posts;
        }

        public List<Posts> getPosts() {
            return posts;
        }
    }

    public static class Posts {

        @SerializedName("content")
        String content;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("name")
        String name;
        @SerializedName("no_of_comments")
        String no_of_comments;
        @SerializedName("post_community")
        private PostCommunity post_community;
        @SerializedName("post_to_user")
        private PostToUser post_to_user;

        public Posts(String content, String created_at, String name, String no_of_comments,
                                 Community_Profile_Response.PostCommunity post_community, Community_Profile_Response.PostToUser post_to_user) {
            this.content = content;
            this.created_at = created_at;
            this.name = name;
            this.no_of_comments = no_of_comments;
            this.post_community = post_community;
            this.post_to_user = post_to_user;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNo_of_comments() {
            return no_of_comments;
        }

        public void setNo_of_comments(String no_of_comments) {
            this.no_of_comments = no_of_comments;
        }

        public Community_Profile_Response.PostCommunity getPost_community() {
            return post_community;
        }

        public void setPost_community(Community_Profile_Response.PostCommunity post_community) {
            this.post_community = post_community;
        }

        public Community_Profile_Response.PostToUser getPost_to_user() {
            return post_to_user;
        }

        public void setPost_to_user(Community_Profile_Response.PostToUser post_to_user) {
            this.post_to_user = post_to_user;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Posts that = (Posts) o;
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

    public static class PostToUser
    {   @SerializedName("name")
        String name;

        public PostToUser() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public static class PostCommunity{
        @SerializedName("pic_url")
        String pic_url;

        public PostCommunity() {
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }


}
