package com.demo.incampus.Model;

public class ManageEvents {

    private String name,about;
    private int event_photo;

    public ManageEvents(String name, String about, int event_photo) {
        this.name = name;
        this.about = about;
        this.event_photo = event_photo;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public int getEvent_photo() {
        return event_photo;
    }
}
