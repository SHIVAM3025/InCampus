package com.demo.incampus.DiffUtils.Categories;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Data_Categories {

    @SerializedName("data")
    private Community_categories data;

    public Community_categories getData() {
        return data;
    }

    @NonNull
    @Override
    public String toString() {
        return "Data{" +
                "data=\n" + data +
                '}';
    }

    public void setData(Community_categories data) {
        this.data = data;
    }
}
