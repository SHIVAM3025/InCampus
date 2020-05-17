package com.demo.incampus.DiffUtils.HomeActivity;

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

        public Post(String post_id, String community_id, String content, String created_at,
                    String created_by, String name, String pic_url, String upvotes,
                    String no_of_comments, PostCommunity post_community, PostToUser post_to_user) {
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
            if(getPost_community()==null)   return null;
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
            if (!(o instanceof Post)) return false;
            Post post = (Post) o;
            return Objects.equals(getPost_id(), post.getPost_id()) &&
                    Objects.equals(getCommunity_id(), post.getCommunity_id()) &&
                    Objects.equals(getContent(), post.getContent()) &&
                    Objects.equals(getCreated_at(), post.getCreated_at()) &&
                    Objects.equals(getCreated_by(), post.getCreated_by()) &&
                    Objects.equals(getName(), post.getName()) &&
                    Objects.equals(getPic_url(), post.getPic_url()) &&
                    Objects.equals(getUpvotes(), post.getUpvotes()) &&
                    Objects.equals(getNo_of_comments(), post.getNo_of_comments()) &&
                    Objects.equals(getPost_community(), post.getPost_community()) &&
                    Objects.equals(getPost_to_user(), post.getPost_to_user());
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
