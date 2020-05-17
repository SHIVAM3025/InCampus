package com.demo.incampus.Model;

public class Notifications {

    private int profile_photo_;
    private String notification_title, notification_time;

    public Notifications(int profile_photo_, String notification_title, String notification_time) {
        this.profile_photo_ = profile_photo_;
        this.notification_title = notification_title;
        this.notification_time = notification_time;
    }

    public int getProfile_photo_() {
        return profile_photo_;
    }

    public String getNotification_title() {
        return notification_title;
    }

    public String getNotification_time() {
        return notification_time;
    }
}
