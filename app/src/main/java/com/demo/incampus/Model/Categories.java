package com.demo.incampus.Model;

public class Categories {
    private String heading, topicsArticles;
    private int bg_image;

    public Categories(String heading, String topicsArticles, int bg_image) {
        this.heading = heading;
        this.topicsArticles = topicsArticles;
        this.bg_image = bg_image;
    }

    public String getHeading() {
        return heading;
    }

    public String getTopicsArticles() {
        return topicsArticles;
    }

    public int getBg_image() {
        return bg_image;
    }
}
