package com.demo.incampus.CommonGraphqlModels.Count;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Count {

    @SerializedName("count")
    private String count_number;

    public Count() {

    }

    public String getCount_number() {
        return count_number;
    }

    public void setCount_number(String count_number) {
        this.count_number = count_number;
    }

    @NonNull
    @Override
    public String toString() {
        return "Count{" +
                "count=" + count_number +
                "}\n";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Count)) return false;
        Count count = (Count) obj;
        return  Objects.equals(getCount_number(), count.getCount_number());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
