package com.demo.incampus.DiffUtils.ManageEvents;

import com.google.gson.annotations.SerializedName;

public class ManageEvents_Data {

    @SerializedName("data")
    private ManageEvents_Events events_events;

    public ManageEvents_Events getEvents_events() {
        return events_events;
    }

    public void setEvents_events(ManageEvents_Events events_events) {
        this.events_events = events_events;
    }

    @Override
    public String toString() {
        return "ManageEvents_Data{" +
                "events_events=" + events_events +
                '}';
    }
}
