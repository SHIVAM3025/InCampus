package com.demo.incampus.DiffUtils.ManageEvents;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ManageEvents_Events {

    @SerializedName("Events")
    private List<ManageEvent_Event> event;


    public List<ManageEvent_Event> getEvent() {
        return event;
    }

    public void setEvent(List<ManageEvent_Event> event) {
        this.event = event;
    }


    @Override
    public String toString() {
        return "ManageEvents_Events{" +
                "event=" + event +
                '}';
    }
}
