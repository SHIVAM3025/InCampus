package com.demo.incampus.Support;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event_Time_Conversion {

    private String time;

    public Event_Time_Conversion() {
    }

    public Event_Time_Conversion(String time) {
        this.time = time;
    }

    public String get_Time() {
        String hour_period = "am";

        String time = this.time;
        String[] split;
        if (this.time == null) {
            return this.time;
        }
        split = time.split(":");
        DateFormat df = new SimpleDateFormat("HH:mm");
        DateFormat outputformat = new SimpleDateFormat("hh:mm aa");
        Date date = null;
        String output = null;
        try {
            date = df.parse(split[0] + ":" + split[1]);
            output = outputformat.format(date);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return output;


    }


}
