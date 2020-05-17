package com.demo.incampus.Model;

public class ExplorePosts {

    private String topic, name, time, content, date, event_time;
    private int profilePhoto;

    public ExplorePosts(String topic, String name, String time, String content, String date, String event_time, int profilePhoto) {
        this.topic = topic;
        this.name = name;
        this.time = time;
        this.content = content;
        this.date = date;
        this.event_time = event_time;
        this.profilePhoto = profilePhoto;
    }

    public String getTopic() {
        return topic;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public int getProfilePhoto() {
        return profilePhoto;
    }
}
