package com.demo.incampus.CommonGraphqlModels.Count;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Aggregate {

    @SerializedName("aggregate")
    private Count count;

    public Count getCount() {
        return count;
    }

    @NonNull
    @Override
    public String toString() {
        return "Aggregate{" +
                "aggregate=\n" + count +
                '}';
    }

    public void getCount(Count data) {
        this.count = data;
    }
}
