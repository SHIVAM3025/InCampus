package com.demo.incampus.DiffUtils.ManageEvents;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ManageEvent_Event {
    @SerializedName("cover_pic")
    private String cover_pic;

    @SerializedName("name")
    private String name;

    @SerializedName("event_id")
    private String event_id;

    @SerializedName("Event_to_community")
    private ManageEvent_SocietyName Event_to_community;

    public ManageEvent_Event(String cover_pic, String name, String event_id, ManageEvent_SocietyName event_to_community) {
        this.cover_pic = cover_pic;
        this.name = name;
        this.event_id = event_id;
        Event_to_community = event_to_community;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }


    public ManageEvent_SocietyName getEvent_to_community() {
        return Event_to_community;
    }

    public void setEvent_to_community(ManageEvent_SocietyName event_to_community) {
        Event_to_community = event_to_community;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManageEvent_Event that = (ManageEvent_Event) o;
        return Objects.equals(cover_pic, that.cover_pic) &&
                Objects.equals(name, that.name) &&
                Objects.equals(event_id, that.event_id) &&
                Objects.equals(Event_to_community, that.Event_to_community);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "ManageEvent_Event{" +
                "cover_pic='" + cover_pic + '\'' +
                ", name='" + name + '\'' +
                ", event_id='" + event_id + '\'' +
                ", Event_to_community=" + Event_to_community +
                '}';
    }
}
