package com.demo.incampus.DiffUtils.HomeActivity;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Home_Post_Response {

    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        private List<Post> Posts;

        public List<Post> getPosts() {
            return Posts;
        }
    }

    public static class Post {
        private String post_id;
        private String community_id;
        private String content;
        private String created_at;
        private String created_by;
        private String name;
        private String pic_url;
        private String upvotes;
        private String no_of_comments;

        private PostCommunity post_community;
        private PostToUser post_to_user;
        private postUpvotesByPostId_aggregate postUpvotesByPostId_aggregate;
        private List<postUpvotesByPostId> user_idList ;

        public Post(String post_id, String community_id, String content, String created_at, String created_by, String name, String pic_url,
                    String upvotes, String no_of_comments, PostCommunity post_community, PostToUser post_to_user,
                    Home_Post_Response.postUpvotesByPostId_aggregate postUpvotesByPostId_aggregate, List<postUpvotesByPostId> user_idList) {

            this.post_id = post_id;
            this.community_id = community_id;
            this.content = content;
            this.created_at = created_at;
            this.created_by = created_by;
            this.name = name;
            this.pic_url = pic_url;
            this.upvotes = upvotes;
            this.no_of_comments = no_of_comments;
            this.post_community = post_community;
            this.post_to_user = post_to_user;
            this.postUpvotesByPostId_aggregate = postUpvotesByPostId_aggregate;
            this.user_idList = user_idList;
        }


        public List<postUpvotesByPostId> getUser_idList() {
            if(user_idList == null){
                return null;
            }
                return user_idList;
        }

        public postUpvotesByPostId_aggregate getPostUpvotesByPostId_aggregate() {
            return postUpvotesByPostId_aggregate;
        }

        public String getPost_id() {
            return post_id;
        }

        public String getCommunity_id() {
            return community_id;
        }

        public String getContent() {
            return content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getCreated_by() {
            return created_by;
        }

        public String getName() {
            return name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public String getUpvotes() {
            return upvotes;
        }

        public String getNo_of_comments() {
            return no_of_comments;
        }

        public PostCommunity getPost_community() {
            return post_community;
        }


        public PostToUser getPost_to_user() {
            return post_to_user;
        }

        public String getCommunityName() {
            if (getPost_community() == null) return null;
            return getPost_community().getName();
        }

        public String getUserName() {
            return getPost_to_user().getName();
        }

        public String getUserPicUrl() {
            return getPost_to_user().getPic_url();
        }

        public String getUserUserId() {
            return getPost_to_user().getUser_id();
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Post post = (Post) o;
            return Objects.equals(post_id, post.post_id) &&
                    Objects.equals(community_id, post.community_id) &&
                    Objects.equals(content, post.content) &&
                    Objects.equals(created_at, post.created_at) &&
                    Objects.equals(created_by, post.created_by) &&
                    Objects.equals(name, post.name) &&
                    Objects.equals(pic_url, post.pic_url) &&
                    Objects.equals(upvotes, post.upvotes) &&
                    Objects.equals(no_of_comments, post.no_of_comments) &&
                    Objects.equals(post_community, post.post_community) &&
                    Objects.equals(post_to_user, post.post_to_user) &&
                    Objects.equals(postUpvotesByPostId_aggregate, post.postUpvotesByPostId_aggregate) &&
                    Objects.equals(user_idList, post.user_idList);
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    public static class PostCommunity {
        private String name;

        public PostCommunity() {

        }

        public String getName() {
            return name;
        }
    }

    public static class postUpvotesByPostId {

        @SerializedName("user_id")
        private String user_id;

        public postUpvotesByPostId() {

        }

        public postUpvotesByPostId(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }


    public static class postUpvotesByPostId_aggregate {

        private Aggregate aggregate;

        public postUpvotesByPostId_aggregate() {
        }

        public Aggregate getAggregate() {
            return aggregate;
        }

        public void setAggregate(Aggregate aggregate) {
            this.aggregate = aggregate;
        }
    }

    public static class Aggregate {
        String count;

        public Aggregate() {
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

    public static class PostToUser {
        private String name;
        private String pic_url;
        private String user_id;

        public PostToUser() {

        }

        public String getName() {
            return name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public String getUser_id() {
            return user_id;
        }
    }

}
