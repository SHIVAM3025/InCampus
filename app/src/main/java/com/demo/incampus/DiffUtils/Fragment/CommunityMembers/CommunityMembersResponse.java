package com.demo.incampus.DiffUtils.Fragment.CommunityMembers;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class CommunityMembersResponse {

   private Data data;

    public Data getData() {
        return data;
    }

    public static class Data
    {
        private List<Community_members> community_members;

        public List<Community_members> getCommunity_members() {
            return community_members;
        }
    }

    public static class Community_members{
        private List<community_to_members_relationship> community_to_members_relationshipList;

        public List<community_to_members_relationship> getCommunity_to_members_relationshipList() {
            return community_to_members_relationshipList;
        }
    }


    public static class community_to_members_relationship
    {
        private Community_Details list;

        public Community_Details getList() {
            return list;
        }
    }

    public static class Community_Details
    {
        @SerializedName("member_count")
        private String member_count ;

        @SerializedName("name")
        private String name;

        @SerializedName("pic_url")
        private String pic_url;

        public Community_Details(String member_count, String name, String pic_url) {
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
            Community_Details that = (Community_Details) o;
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
