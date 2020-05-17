package com.demo.incampus.DiffUtils.Explore;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Explore_Event_Response {
    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("Events")
        private List<Event> events;

        public List<Event> getEvents() {
            return events;
        }
    }

    public static class Event {
        @SerializedName("name")
        private String name;
        @SerializedName("event_id")
        private String eventId;
        @SerializedName("time")
        private String time;
        @SerializedName("registration_fees")
        private String registrationFees;
        @SerializedName("venue")
        private String venue;
        @SerializedName("venue_type")
        private String venueType;
        @SerializedName("description")
        private String description;
        @SerializedName("date")
        private String date;
        @SerializedName("created_by")
        private String createdBy;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("cover_pic")
        private String coverPic;
        @SerializedName("community_id")
        private String communityId;
        @SerializedName("category")
        private String category;
        @SerializedName("age_limit")
        private String ageLimit;
        @SerializedName("additional_notes")
        private String additionalNotes;
        @SerializedName("Event_to_community")
        private EventToCommunity eventToCommunity;

        public Event(String name, String eventId, String time, String registrationFees,
                     String venue, String venueType, String description, String date,
                     String createdBy, String createdAt, String coverPic, String communityId,
                     String category, String ageLimit, String additionalNotes,
                     EventToCommunity eventToCommunity) {
            this.name = name;
            this.eventId = eventId;
            this.time = time;
            this.registrationFees = registrationFees;
            this.venue = venue;
            this.venueType = venueType;
            this.description = description;
            this.date = date;
            this.createdBy = createdBy;
            this.createdAt = createdAt;
            this.coverPic = coverPic;
            this.communityId = communityId;
            this.category = category;
            this.ageLimit = ageLimit;
            this.additionalNotes = additionalNotes;
            this.eventToCommunity = eventToCommunity;
        }

        public String getName() {
            return name;
        }

        public String getEventId() {
            return eventId;
        }

        public String getTime() {
            return time;
        }

        public String getRegistrationFees() {
            return registrationFees;
        }

        public String getVenue() {
            return venue;
        }

        public String getVenueType() {
            return venueType;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getCoverPic() {
            return coverPic;
        }

        public String getCommunityId() {
            return communityId;
        }

        public String getCategory() {
            return category;
        }

        public String getAgeLimit() {
            return ageLimit;
        }

        public String getAdditionalNotes() {
            return additionalNotes;
        }

        public EventToCommunity getEventToCommunity() {
            return eventToCommunity;
        }

        public String getCommunityName() {
            if (eventToCommunity==null) return null;
            return getEventToCommunity().getName();
        }

        public String getCommunityPicUrl() {
            if (eventToCommunity==null) return null;
            return getEventToCommunity().getPic_url();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Event)) return false;
            Event event = (Event) o;
            return Objects.equals(getName(), event.getName()) &&
                    Objects.equals(getEventId(), event.getEventId()) &&
                    Objects.equals(getTime(), event.getTime()) &&
                    Objects.equals(getRegistrationFees(), event.getRegistrationFees()) &&
                    Objects.equals(getVenue(), event.getVenue()) &&
                    Objects.equals(getVenueType(), event.getVenueType()) &&
                    Objects.equals(getDescription(), event.getDescription()) &&
                    Objects.equals(getDate(), event.getDate()) &&
                    Objects.equals(getCreatedBy(), event.getCreatedBy()) &&
                    Objects.equals(getCreatedAt(), event.getCreatedAt()) &&
                    Objects.equals(getCoverPic(), event.getCoverPic()) &&
                    Objects.equals(getCommunityId(), event.getCommunityId()) &&
                    Objects.equals(getCategory(), event.getCategory()) &&
                    Objects.equals(getAgeLimit(), event.getAgeLimit()) &&
                    Objects.equals(getAdditionalNotes(), event.getAdditionalNotes()) &&
                    Objects.equals(getEventToCommunity(), event.getEventToCommunity());
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    public static class EventToCommunity {
        @SerializedName("community_id")
        private String communityId;
        @SerializedName("name")
        private String name;
        @SerializedName("pic_url")
        private String pic_url;

        public EventToCommunity() {

        }

        public String getCommunityId() {
            return communityId;
        }

        public String getName() {
            return name;
        }

        public String getPic_url() {
            return pic_url;
        }
    }
}
