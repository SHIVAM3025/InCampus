package com.demo.incampus.DiffUtils.Comments;

import java.util.List;
import java.util.Objects;

public class CommentsResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        private List<Comment> Comments;

        public List<Comment> getComments() {
            return Comments;
        }
    }

    public static class Comment {
        private String id;
        private String user_id;
        private String content;
        private String upvotes;
        private String timestamp;
        private User UserID;

        public Comment(String id, String user_id, String content, String upvotes, String timestamp,
                       User UserID) {
            this.id = id;
            this.user_id = user_id;
            this.content = content;
            this.upvotes = upvotes;
            this.timestamp = timestamp;
            this.UserID = UserID;
        }

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getContent() {
            return content;
        }

        public String getUpvotes() {
            return upvotes;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public User getUser() {
            return UserID;
        }

        public String getUserName() {
            if (UserID ==null) return null;
            return getUser().getName();
        }

        public String getUserPicUrl() {
            if (UserID ==null) return null;
            return getUser().getPic_url();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Comment)) return false;
            Comment comment = (Comment) o;
            return Objects.equals(getId(), comment.getId()) &&
                    Objects.equals(getUser_id(), comment.getUser_id()) &&
                    Objects.equals(getContent(), comment.getContent()) &&
                    Objects.equals(getUpvotes(), comment.getUpvotes()) &&
                    Objects.equals(getTimestamp(), comment.getTimestamp()) &&
                    Objects.equals(UserID, comment.UserID);
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    public static class User {
        private String name;
        private String pic_url;

        public User() {

        }

        public String getName() {
            return name;
        }

        public String getPic_url() {
            return pic_url;
        }
    }

}
