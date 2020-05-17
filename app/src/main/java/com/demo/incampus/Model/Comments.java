package com.demo.incampus.Model;

public class Comments {

    private String username, time, commentText, hearts, replies;

    public Comments(String username, String time, String commentText, String hearts, String replies) {
        this.username = username;
        this.time = time;
        this.commentText = commentText;
        this.hearts = hearts;
        this.replies = replies;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getHearts() {
        return hearts;
    }

    public String getReplies() {
        return replies;
    }
}
