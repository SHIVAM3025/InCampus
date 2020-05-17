package com.demo.incampus.DiffUtils.Categories;

import androidx.annotation.NonNull;

import com.demo.incampus.Model.Societies;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Community_categories {

    @SerializedName("Community")
    private List<Societies> User;


    public List<Societies> getUser() {
        return User;
    }



    @NonNull
    @Override
    public String toString() {
        return "UserData{" +
                "Community=\n" + User +
                '}';
    }

    public void setUser(List<Societies> user) {
        User = user;
    }
}
