package com.demo.incampus.DiffUtils.Fragment.CommunityAdmin;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Community_Admin_Response {

    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {

        @SerializedName("Community_members")
        private List<Community_members> community_members;

        public List<Community_members> getCommunity_members() {
            return community_members;
        }

        public void setCommunity_members(List<Community_members> community_members) {
            this.community_members = community_members;
        }
    }

    public static class Community_members {

        @SerializedName("community_to_member_relationship")
        private community_to_members_relationship community_to_members_relationship;

        @SerializedName("id")
        private String id;

        @SerializedName("community_id")
        private String community_id;

        public Community_Admin_Response.community_to_members_relationship getCommunity_to_members_relationship() {
            return community_to_members_relationship;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(String community_id) {
            this.community_id = community_id;
        }

        public String getId() {
            return id;
        }

        public void setCommunity_to_members_relationship(Community_Admin_Response.community_to_members_relationship community_to_members_relationship) {
            this.community_to_members_relationship = community_to_members_relationship;
        }
    }


    public static class community_to_members_relationship {
        @SerializedName("member_count")
        private String member_count;

        @SerializedName("name")
        private String name;

        @SerializedName("pic_url")
        private String pic_url;

        public community_to_members_relationship(String member_count, String name, String pic_url) {
            this.member_count = member_count;
            this.name = name;
            this.pic_url = pic_url;
        }

        public String getMember_count() {
            return member_count;
        }

        public void setMember_count(String member_count) {
            this.member_count = member_count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            community_to_members_relationship that = (community_to_members_relationship) o;
            return Objects.equals(member_count, that.member_count) &&
                    Objects.equals(name, that.name) &&
                    Objects.equals(pic_url, that.pic_url);
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "Community_Details{" +
                    "member_count='" + member_count + '\'' +
                    ", name='" + name + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    '}';
        }

    }


}
